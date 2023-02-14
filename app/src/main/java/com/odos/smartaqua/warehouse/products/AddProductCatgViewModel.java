package com.odos.smartaqua.warehouse.products;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityAddproductBinding;
import com.odos.smartaqua.databinding.ActivityAddproductCatgBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProductCatgViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddproductCatgBinding _activityAddproductCatgBinding;
    private ServiceAsyncResponse serviceAsyncResponse;

    public AddProductCatgViewModel(Context context, ActivityAddproductCatgBinding activityAddproductCatgBinding) {
        this._context = context;
        this._activityAddproductCatgBinding = activityAddproductCatgBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;

    }

    public void saveProduct(View view) {
        String edtName = _activityAddproductCatgBinding.edtName.getText().toString();
        String edtCode = _activityAddproductCatgBinding.edtCode.getText().toString();
        if (edtName.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Product Type is required.", Toast.LENGTH_SHORT).show();
        } else if (edtCode.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Product Code is required.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> postParams = new HashMap<>();
            postParams.put("name", edtName);
            postParams.put("code", edtCode);
            postParams.put("cretaedby", "777");
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_PRODUCT_CATG, postParams, Helper.headerParams(_context),
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
                        _activityAddproductCatgBinding.edtName.setText("");
                        _activityAddproductCatgBinding.edtCode.setText("");
                    } else {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "Saving Failed.", AquaConstants.HOLD);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }


}
