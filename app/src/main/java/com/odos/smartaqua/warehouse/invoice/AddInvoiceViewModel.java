package com.odos.smartaqua.warehouse.invoice;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.Brandcnsts;
import com.odos.smartaqua.brand.BrandsAdapter;
import com.odos.smartaqua.databinding.ActivityAddinvoiceBinding;
import com.odos.smartaqua.databinding.ActivityAddstockBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;
import com.odos.smartaqua.warehouse.products.ProductTypes;
import com.odos.smartaqua.warehouse.products.ProductTypesAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddInvoiceViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddinvoiceBinding _activityAddinvoiceBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private Float discountAmount;
    private String filePath = "";

    public AddInvoiceViewModel(Context context, ActivityAddinvoiceBinding activityAddinvoiceBinding) {
        this._context = context;
        this._activityAddinvoiceBinding = activityAddinvoiceBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;

        // load spinner
        String discounttypes[] = {"Rupees", "Percentage"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(_context, R.layout.spinner_item, discounttypes);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityAddinvoiceBinding.spinDiscountType.setAdapter(spinnerArrayAdapter);

        String paytypes[] = {"Cash", "Online", "Pending"};
        ArrayAdapter<String> payArrayAdapter = new ArrayAdapter<String>(_context, R.layout.spinner_item, paytypes);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityAddinvoiceBinding.spinPayTypes.setAdapter(payArrayAdapter);
    }

    public void onBrowse(View v) {
        if (Helper.getCameraPermission(_context)) {
            UploadBottomSheetFragment addPhotoBottomDialogFragment =
                    UploadBottomSheetFragment.newInstance();
            addPhotoBottomDialogFragment.show(((FragmentActivity) _context).getSupportFragmentManager(),
                    "tag");
        } else {
            Helper.getCameraPermission(_context);
        }
    }
    public void onPurchaseDate(View v) {
       Helper.getDateTimepicker(_context,_activityAddinvoiceBinding.txtPurchaseDate,AquaConstants.FINISH);
    }

    public void uploadBitmap(Bitmap bitmap) {
        VolleyService.VolleyMultipartRequest(_context, bitmap, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 5, true, "image");
    }

    public void saveProduct(View view) {
        String storedetails = _activityAddinvoiceBinding.edtStoredetails.getText().toString();
        String mrp = _activityAddinvoiceBinding.edtPriceQty.getText().toString();
        String edtDiscount = _activityAddinvoiceBinding.edtDiscount.getText().toString();
        String purchasedate = _activityAddinvoiceBinding.txtPurchaseDate.getText().toString();

        if (storedetails.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Store Details are required.", Toast.LENGTH_SHORT).show();
        } else if (purchasedate.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Select Purchase date.", Toast.LENGTH_SHORT).show();
        } else if (mrp.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Mrp rate is required.", Toast.LENGTH_SHORT).show();
        } else if (edtDiscount.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Discount is required if no discount enter 0.", Toast.LENGTH_SHORT).show();
        } else if (filePath.equalsIgnoreCase("")) {
            Toast.makeText(_context, "pls upload your image first.", Toast.LENGTH_SHORT).show();
        } else {
            String paymenttype = _activityAddinvoiceBinding.spinPayTypes.getSelectedItem().toString();
            if (_activityAddinvoiceBinding.spinDiscountType.getSelectedItem().equals("Rupees")) {
                discountAmount = Float.parseFloat(_activityAddinvoiceBinding.edtDiscount.getText().toString());
            } else {
                Float amount = Float.parseFloat(mrp);
                Float discount = Float.parseFloat(edtDiscount);
                if (discount > 100) {
                    Helper.showMessage(_context, "Discount is not greater than 100%", AquaConstants.HOLD);
                } else {
                    discountAmount = (amount / 100) * discount;
                }
            }
            HashMap<String, Object> postParams = new HashMap<>();
            postParams.put("userid", Helper.getUserID(_context));
            postParams.put("store", storedetails);
            postParams.put("purchasedate", purchasedate);
            postParams.put("discount", String.valueOf(discountAmount));
            postParams.put("amount", mrp);
            postParams.put("path", filePath);
            postParams.put("paymenttype", paymenttype);
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_INVOICE, postParams, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        }
    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {

            case 1:
                String status = null;
                try {
                    status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                        _activityAddinvoiceBinding.edtPriceQty.setText("");
                        _activityAddinvoiceBinding.edtDiscount.setText("");
                        _activityAddinvoiceBinding.imgView.setImageResource(R.drawable.uploadicon);
                    } else {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "Stock Saving Failed.", AquaConstants.HOLD);
                }
                break;
            case 5:
                try {
                    String imgstatus = jsonobject.getString("status");
                    if (imgstatus.equalsIgnoreCase("Sucess")) {
                        JSONObject response = jsonobject.getJSONObject("response");
                        filePath = response.getString("filepath");
                    }
                } catch (Exception e) {
                    Toast.makeText(_context, "image upload failed try again", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }


}
