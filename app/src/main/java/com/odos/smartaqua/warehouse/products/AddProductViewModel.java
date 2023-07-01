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
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AddProductViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddproductBinding _activityAddproductBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> productTypesArrayList;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private ArrayList<ProductTypes> brandArrayList;
    private int brandID = 777;
    private int productTypeID = 777;
    private int qtyTypeID = 777;
    private String filePath = "";
    private String fileType = "";

    public AddProductViewModel(Context context, ActivityAddproductBinding activityAddproductBinding) {
        this._context = context;
        this._activityAddproductBinding = activityAddproductBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {

            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.PRODUCT_CAATEGORIES, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.BRAND_LIST, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 2, false);

            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.QTY_LIST, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 3, true);

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
        String edtName = _activityAddproductBinding.edtName.getText().toString();
        if (edtName.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Product Name is required.", Toast.LENGTH_SHORT).show();
        } else if (productTypeID == 777) {
            Toast.makeText(_context, "Select Product Type.", Toast.LENGTH_SHORT).show();
        } else if (brandID == 777) {
            Toast.makeText(_context, "Select Brand.", Toast.LENGTH_SHORT).show();
        } else if (qtyTypeID == 777) {
            Toast.makeText(_context, "Select Quantity Type.", Toast.LENGTH_SHORT).show();
        }  else {
            HashMap<String, Object> postParams = new HashMap<>();
            postParams.put("userid", Helper.getUserID(_context));
            postParams.put("productname", edtName);
            postParams.put("brandid", String.valueOf(brandID));
            postParams.put("productcatgeoryid", String.valueOf(productTypeID));
            postParams.put("quantitycategoryid", String.valueOf(qtyTypeID));
            postParams.put("costperqty", "0");
            postParams.put("productImage", filePath);

            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_PRODUCTS, postParams, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 4, true);
        }
    }

    public void uploadBitmap(Bitmap bitmap) {
        VolleyService.VolleyMultipartRequest(_context, bitmap, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 5, true, "image");
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                productTypesArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int ptID = jsonObject1.getInt("productcatgeoryid");
                                    String productType = jsonObject1.getString("name");
                                    String ptCode = jsonObject1.getString("code");
                                    String createDate = jsonObject1.getString("cretaedby");
                                    ProductTypes productTypes = new ProductTypes(ptID, productType, ptCode, createDate, 0);
                                    productTypesArrayList.add(productTypes);
                                }
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, productTypesArrayList);
                                _activityAddproductBinding.spinProductType.setAdapter(productTypesAdapter);
                                _activityAddproductBinding.spinProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) productTypesArrayList.get(position);
                                        productTypeID = productTypes.getPtID();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }
                    }
                } catch (JSONException e) {
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
                            if (jsonArray.length() != 0) {
                                brandArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int ptID = jsonObject1.getInt("brandid");
                                    String productType = jsonObject1.getString("brandname");
                                    String ptCode = jsonObject1.getString("brandcode");
                                    String createDate = jsonObject1.getString("cretaedby");
                                    ProductTypes productTypes = new ProductTypes(ptID, productType, ptCode, createDate, 0);
                                    brandArrayList.add(productTypes);
                                }
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, brandArrayList);
                                _activityAddproductBinding.spinBrand.setAdapter(productTypesAdapter);
                                _activityAddproductBinding.spinBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) brandArrayList.get(position);
                                        brandID = productTypes.getPtID();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;

            case 3:
                try {
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                qtyTypesArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int ptID = jsonObject1.getInt("quantitycategoryid");
                                    String productType = jsonObject1.getString("qtycategory");
                                    String ptCode = jsonObject1.getString("qtycategorycode");
                                    String createDate = jsonObject1.getString("cretaedby");
                                    ProductTypes productTypes = new ProductTypes(ptID, productType, ptCode, createDate, 0);
                                    qtyTypesArrayList.add(productTypes);
                                }
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, qtyTypesArrayList);
                                _activityAddproductBinding.spinQtyType.setAdapter(productTypesAdapter);
                                _activityAddproductBinding.spinQtyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) qtyTypesArrayList.get(position);
                                        qtyTypeID = productTypes.getPtID();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;
            case 4:
                String status = null;
                try {
                    status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                        _activityAddproductBinding.edtName.setText("");
                        _activityAddproductBinding.imgView.setImageResource(R.drawable.uploadicon);
                        filePath = "";
                        fileType = "";
                    } else {
                        Helper.showMessage(_context, response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "Product Saving Failed.", AquaConstants.HOLD);
                }

                break;

            case 5:
                try {
                    String imgStatus = jsonobject.getString("status");
                    if (imgStatus.equalsIgnoreCase("Sucess")) {
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
