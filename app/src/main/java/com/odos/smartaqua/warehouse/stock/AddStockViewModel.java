package com.odos.smartaqua.warehouse.stock;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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

public class AddStockViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddstockBinding _activityAddstockBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> productTypesArrayList;
    private ArrayList<Brandcnsts> brandArrayList;
    private int brandID = 777;
    private int productTypeID = 777;
    private int qtyTypeID = 777;
    private String qtyTypeName;
    private Float discountAmount;
    private String filePath = "";
    private String fileType = "";

    public AddStockViewModel(Context context, ActivityAddstockBinding activityAddstockBinding) {
        this._context = context;
        this._activityAddstockBinding = activityAddstockBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.BRAND_LIST, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 2, false);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
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

    public void saveProduct(View view) {
        String edtQty = _activityAddstockBinding.edtQuantity.getText().toString();
        String actualPrice = _activityAddstockBinding.edtActcualPrice.getText().toString();
        String purchasePrice = _activityAddstockBinding.edtPurchasePrice.getText().toString();
        String edtUnitQty = _activityAddstockBinding.edtUnitWeight.getText().toString();

        if (brandID == 777) {
            Toast.makeText(_context, "Select Brand.", Toast.LENGTH_SHORT).show();
        } else if (productTypeID == 777) {
            Toast.makeText(_context, "Select Product Type.", Toast.LENGTH_SHORT).show();
        } else if (qtyTypeID == 777) {
            Toast.makeText(_context, "Select Quantity Type.", Toast.LENGTH_SHORT).show();
        } else if (edtQty.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Product Quantity is required.", Toast.LENGTH_SHORT).show();
        } else if (actualPrice.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Mrp rate is required.", Toast.LENGTH_SHORT).show();
        } else if (purchasePrice.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Purchase Price is required", Toast.LENGTH_SHORT).show();
        } else {
            if (actualPrice.equalsIgnoreCase("") || purchasePrice.equalsIgnoreCase("")) {
                discountAmount = 0.00f;
            } else {
                Float amount = Float.parseFloat(actualPrice);
                Float purchaseAmount = Float.parseFloat(purchasePrice);
                discountAmount = amount - purchaseAmount;
            }
            HashMap<String, Object> postParams = new HashMap<>();
            postParams.put("newstock", edtQty);
            postParams.put("userid", Helper.getUserID(_context));
            postParams.put("productid", productTypeID);
            postParams.put("quantitycategoryid", qtyTypeID);
            postParams.put("actualprice", actualPrice);
            postParams.put("purchaseprice", purchasePrice);
            postParams.put("discount", String.valueOf(discountAmount));
            postParams.put("path", filePath);
            Log.e("data--==",""+new JSONObject(postParams));
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_STOCK, postParams, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 3, true);

        }
    }

    public void uploadBitmap(Bitmap bitmap) {
        VolleyService.VolleyMultipartRequest(_context, bitmap, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 4, true, "image");
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    Log.e("data--==",""+jsonobject);
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            productTypesArrayList = new ArrayList<>();
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int ptID = jsonObject1.getInt("productid");
                                    String productType = jsonObject1.getString("productname");
                                    String ptCode = jsonObject1.getString("productcatgeoryid");
                                    String createDate = jsonObject1.getString("costperqty");
                                    String quantity = jsonObject1.getString("quantity");
                                    int quantitycategoryid = jsonObject1.getInt("quantitycategoryid");
                                    ProductTypes productTypes = new ProductTypes(ptID, productType, ptCode, quantity, quantitycategoryid);
                                    productTypesArrayList.add(productTypes);
                                }
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, productTypesArrayList);
                                _activityAddstockBinding.spinProduct.setAdapter(productTypesAdapter);
                                _activityAddstockBinding.spinProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) productTypesArrayList.get(position);
                                        productTypeID = productTypes.getPtID();
                                        Log.e("data--==",""+productTypeID);
                                        qtyTypeID = productTypes.getQuantitycategoryid();
                                        _activityAddstockBinding.txtQtyType.setText(productTypes.getQuantity());
                                        qtyTypeName = productTypes.getQuantity();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else {
                                ProductTypes productTypes1 = new ProductTypes(777, "--No Products Here--", "", "", 0);
                                productTypesArrayList.add(productTypes1);
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, productTypesArrayList);
                                _activityAddstockBinding.spinProduct.setAdapter(productTypesAdapter);
                            }
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;

            case 2:
                try {
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            brandArrayList = new ArrayList<>();
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int brandid = jsonObject1.getInt("brandid");
                                    String brandname = jsonObject1.getString("brandname");
                                    String brandcode = jsonObject1.getString("brandcode");
                                    String cretaedby = jsonObject1.getString("cretaedby");
                                    Brandcnsts brandcnsts = new Brandcnsts(brandid, brandname, brandcode, cretaedby);
                                    brandArrayList.add(brandcnsts);
                                }
                                BrandsAdapter brandsAdapter = new BrandsAdapter(_context, brandArrayList);
                                _activityAddstockBinding.spinBrand.setAdapter(brandsAdapter);

                                _activityAddstockBinding.spinBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Brandcnsts brandcnsts = (Brandcnsts) brandArrayList.get(position);
                                        brandID = brandcnsts.getBrandid();
                                        if (brandID == 777) {
                                            productTypeID = 777;
                                        } else {
                                            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                                    ServiceConstants.LIST_PRODUCTS + brandID+ "/"+Helper.getUserID(_context), null, Helper.headerParams(_context),
                                                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;

            case 3:
                String status = null;
                try {
                    status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                        _activityAddstockBinding.edtActcualPrice.setText("");
                        _activityAddstockBinding.edtQuantity.setText("");
                        _activityAddstockBinding.edtPurchasePrice.setText("");
                        filePath = "";
                    } else {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "Stock Saving Failed.", AquaConstants.HOLD);
                }
                break;

            case 4:
                try {
                    String path_status = jsonobject.getString("status");
                    if (path_status.equalsIgnoreCase("Sucess")) {
                        JSONObject response = jsonobject.getJSONObject("response");
                        filePath = response.getString("filepath");
                        fileType = response.getString("fileType");
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
