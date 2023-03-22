package com.odos.smartaqua.feed;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityAddFeedBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;
import com.odos.smartaqua.warehouse.products.ProductTypesAdapter;
import com.odos.smartaqua.warehouse.products.SearchProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFeedViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddFeedBinding _activityAddFeedBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String[] responseData,values;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    //private double finalAvailableStock;
   // private double totalqty = 0.0;

    public AddFeedViewModel(Context context, ActivityAddFeedBinding activityAddFeedBinding) {
        this._context = context;
        this._activityAddFeedBinding = activityAddFeedBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity)_context).getIntent().getStringArrayExtra("values");
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.QTY_LIST, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_STOCK + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }

    public void getProducts(View view) {
        Intent i = new Intent(_context, SearchProductActivity.class);
        i.putExtra("values", responseData);
        i.putExtra("flag", "1");
        ((Activity) _context).startActivityForResult(i, 1);
    }

    public void getSuppliments(View view) {
        Intent i = new Intent(_context, SearchProductActivity.class);
        i.putExtra("values", responseData);
        i.putExtra("flag", "2");
        ((Activity) _context).startActivityForResult(i, 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void saveSuppliment(View view) {
        if (_activityAddFeedBinding.txtSupliment.getText().toString().equalsIgnoreCase("Add Suppliments")) {
            Toast.makeText(_context, "Add Product", Toast.LENGTH_SHORT).show();
        } else if (_activityAddFeedBinding.edtSuplimentQty.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Enter Quantity", Toast.LENGTH_SHORT).show();
        } else {
            String[] searchData = ((AddFeedActivity) _context).getStock();
            if (searchData != null) {
                double availablestock = Double.parseDouble(searchData[8]);
                double currentstock;
                if(suppliment_qtycategorycode.equalsIgnoreCase("Grams")){
                    currentstock = Double.parseDouble(_activityAddFeedBinding.edtSuplimentQty.getText().toString());
                }else{
                    currentstock = Double.parseDouble(_activityAddFeedBinding.edtSuplimentQty.getText().toString())*1000;
                }
                if (availablestock < currentstock) {
                    Helper.showMessage(_context, "you don`t have sufficient stock in your ware house", AquaConstants.HOLD);
                } else {
                    double avStock = availablestock - currentstock;
                    try {
                        suppliment_jsonObject = new JSONObject();
                        suppliment_jsonObject.put("productID", ((AddFeedActivity) _context).getproductid());
                        suppliment_jsonObject.put("productName", ((AddFeedActivity) _context).getproductname());
                        suppliment_jsonObject.put("productqty", ""+currentstock);
                        suppliment_jsonObject.put("priceperqty", ((AddFeedActivity) _context).getmrp());
                        suppliment_jsonObject.put("productcatgeoryID", ((AddFeedActivity) _context).getproductcategoryid());
                        suppliment_jsonObject.put("quantitycategoryid", suppliment_qtyTypeID);
                        suppliment_jsonObject.put("quantitycategoryname", "Grams");
                        suppliment_jsonObject.put("availablestock", String.valueOf(avStock));
                        suppliment_jsonArray.put(suppliment_jsonObject);
                        _activityAddFeedBinding.txtSupliment.setText(_context.getString(R.string.add_suppliments));
                        _activityAddFeedBinding.edtSuplimentQty.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    _activityAddFeedBinding.llSupplimentSet.removeAllViews();
                    loadSupplimentsView();
                }
            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void saveProduct(View view) {
        if (_activityAddFeedBinding.txtProduct.getText().toString().equalsIgnoreCase("Select Product Name")) {
            Toast.makeText(_context, "Add Product", Toast.LENGTH_SHORT).show();
        } else if (_activityAddFeedBinding.edtQty.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Enter Quantity", Toast.LENGTH_SHORT).show();
        } else {
            String[] searchData = ((AddFeedActivity) _context).getStock();
            if (searchData != null) {
                double availablestock = Double.parseDouble(searchData[8]);
                double currentstock;
                if(qtycategorycode.equalsIgnoreCase("Grams")){
                    currentstock = Double.parseDouble(_activityAddFeedBinding.edtQty.getText().toString());
                }else{
                    currentstock = Double.parseDouble(_activityAddFeedBinding.edtQty.getText().toString())*1000;
                }

                if (availablestock < currentstock) {
                    Helper.showMessage(_context, "you don`t have sufficient stock in your ware house", AquaConstants.HOLD);
                } else {
                    double avStock = availablestock - currentstock;
                    try {
                        jsonObject = new JSONObject();
                        jsonObject.put("productID", ((AddFeedActivity) _context).getproductid());
                        jsonObject.put("productcatgeoryID", ((AddFeedActivity) _context).getproductcategoryid());
                        jsonObject.put("productqty", ""+currentstock);
                        jsonObject.put("priceperqty", ((AddFeedActivity) _context).getmrp());
                        jsonObject.put("productName", ((AddFeedActivity) _context).getproductname());
                        jsonObject.put("quantitycategoryid", product_qtyTypeID);
                        jsonObject.put("quantitycategoryname", "Grams");
                        jsonObject.put("availablestock", String.valueOf(avStock));
                        products_jsonArray.put(jsonObject);
                        _activityAddFeedBinding.txtProduct.setText(_context.getString(R.string.enter_product_name));
                        _activityAddFeedBinding.edtQty.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                _activityAddFeedBinding.llFeedSet.removeAllViews();
              //  totalqty = 0.0;
                loadProductView();
                loadSupplimentsView();
            }
        }
    }

    public void getFeedDate(View view) {
        Helper.getDateTimepicker(_context, _activityAddFeedBinding.txtTimeDate, AquaConstants.FINISH);
    }

    public void saveFeed(View view) {

        if (_activityAddFeedBinding.edtFeedTitle.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Add Feed Title", Toast.LENGTH_SHORT).show();
        } else if (products_jsonArray.length() == 0) {
            Toast.makeText(_context, "Add atleast one product", Toast.LENGTH_SHORT).show();
        } else if (_activityAddFeedBinding.txtTimeDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pick feed date and time", Toast.LENGTH_SHORT).show();
        } else {

            try {
                String title = _activityAddFeedBinding.edtFeedTitle.getText().toString();
                String dateandtime = _activityAddFeedBinding.txtTimeDate.getText().toString();
                String comment = _activityAddFeedBinding.edtCommentsFeed.getText().toString();
                HashMap<String, Object> postParams = new HashMap<>();
                postParams.put("groupname", title);
                postParams.put("feedProducts", products_jsonArray);
                postParams.put("suppliments", suppliment_jsonArray);
                postParams.put("userID", Helper.getUserID(_context));
                postParams.put("cultureid", values[1]);
                postParams.put("access", values[3]);
                postParams.put("feeddate", _activityAddFeedBinding.txtTimeDate.getText().toString());
                postParams.put("comment", _activityAddFeedBinding.edtCommentsFeed.getText().toString());
                Log.e("data--==",""+new JSONObject(postParams));
                VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                        ServiceConstants.SAVE_FEED, postParams, Helper.headerParams(_context),
                        (ServiceAsyncResponse) serviceAsyncResponse, 3, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadProductView() {
        if (products_jsonArray.length() != 0) {
            for (int i = 0; i < products_jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = products_jsonArray.getJSONObject(i);
                    String productID = jsonObject.getString("productID");
                    String productName = jsonObject.getString("productName");
                    String productQty = jsonObject.getString("productqty");
                    String pricePerQty = jsonObject.getString("priceperqty");
                    String quantityname = jsonObject.getString("quantitycategoryname");
                    String quantityid = jsonObject.getString("quantitycategoryid");
                    final View child = ((Activity) _context).getLayoutInflater().inflate(R.layout.include_chld_feeditem, null);
                    _activityAddFeedBinding.llFeedSet.addView(child);
                    LinearLayout ll_add_textview = (LinearLayout) child.findViewById(R.id.ll_add_textview);
                    LinearLayout ll_add_imageview = (LinearLayout) child.findViewById(R.id.ll_add_imageview);
                    final TextView textView = new TextView(_context);
                    final TextView feed_id = new TextView(_context);
                    ImageView imageView = new ImageView(_context);
                    ll_add_textview.addView(textView);
                    ll_add_imageview.addView(imageView);
                    ll_add_imageview.addView(feed_id);
                    textView.setGravity(View.TEXT_ALIGNMENT_CENTER | View.TEXT_ALIGNMENT_TEXT_START);
                    textView.setTextColor(_context.getResources().getColor(R.color.red));
                    Double convertQty = Double.parseDouble(productQty)/1000;
                    String buildTesxt = productName + " --  " + convertQty + " K.G ";
                    textView.setText(buildTesxt);
                    feed_id.setText(productID);
                    feed_id.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.close);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < products_jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = products_jsonArray.getJSONObject(i);
                                    String productID = jsonObject.getString("productID");
                                    if (productID.equalsIgnoreCase(feed_id.getText().toString())) {
                                        _activityAddFeedBinding.llFeedSet.removeView(child);
                                        products_jsonArray.remove(i);
                                       // totalqty = totalqty - Double.parseDouble(productQty);
                                        loadSupplimentsView();
                                    }
                                } catch (JSONException e) {
                                }
                            }
                        }
                    });
                   // totalqty = totalqty + Double.parseDouble(productQty);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadSupplimentsView() {
        if (suppliment_jsonArray.length() != 0) {
            _activityAddFeedBinding.llSupplimentSet.removeAllViews();
            for (int i = 0; i < suppliment_jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = suppliment_jsonArray.getJSONObject(i);
                    String productID = jsonObject.getString("productID");
                    String productName = jsonObject.getString("productName");
                    String productQty = jsonObject.getString("productqty");
                    String pricePerQty = jsonObject.getString("priceperqty");
                    String quantityname = jsonObject.getString("quantitycategoryname");
                    String quantityid = jsonObject.getString("quantitycategoryid");
                    final View child = ((Activity) _context).getLayoutInflater().inflate(R.layout.include_chld_feeditem, null);
                    _activityAddFeedBinding.llSupplimentSet.addView(child);
                    LinearLayout ll_add_textview = (LinearLayout) child.findViewById(R.id.ll_add_textview);
                    LinearLayout ll_add_imageview = (LinearLayout) child.findViewById(R.id.ll_add_imageview);
                    final TextView textView = new TextView(_context);
                    final TextView feed_id = new TextView(_context);
                    ImageView imageView = new ImageView(_context);
                    ll_add_textview.addView(textView);
                    ll_add_imageview.addView(imageView);
                    ll_add_imageview.addView(feed_id);
                    //set prporties
                    textView.setGravity(View.TEXT_ALIGNMENT_CENTER | View.TEXT_ALIGNMENT_TEXT_START);
                    textView.setTextColor(_context.getResources().getColor(R.color.red));

                   // double final_totalqty = Double.parseDouble(productQty) * (totalqty/1000);
                    String buildTesxt = productName + " --  " + productQty + "   " + quantityname;
                    textView.setText(buildTesxt);
                    feed_id.setText(productID);
                    feed_id.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.close);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(View view) {
                            _activityAddFeedBinding.llSupplimentSet.removeView(child);
                            for (int i = 0; i < suppliment_jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = suppliment_jsonArray.getJSONObject(i);
                                    String feed_id_remove = jsonObject.getString("productID");
                                    if (feed_id_remove.equalsIgnoreCase(feed_id.getText().toString())) {
                                        suppliment_jsonArray.remove(i);
                                    }
                                } catch (Exception e) {
                                    Helper.showMessage(_context, "" + e, 0);
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    Helper.showMessage(_context, "" + e, 0);
                }
            }
        }
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
                                qtyTypesArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int quantitycategoryid = jsonObject1.getInt("quantitycategoryid");
                                    String qtycategory = jsonObject1.getString("qtycategory");
                                    String qtycategorycode = jsonObject1.getString("qtycategorycode");
                                    String cretaedby = jsonObject1.getString("cretaedby");
                                    ProductTypes productTypes = new ProductTypes(quantitycategoryid, qtycategorycode, qtycategory, cretaedby, 0);
                                    qtyTypesArrayList.add(productTypes);
                                }
                                ProductTypesAdapter productTypesAdapter = new ProductTypesAdapter(_context, qtyTypesArrayList);
                                _activityAddFeedBinding.spinQtyType.setAdapter(productTypesAdapter);
                                _activityAddFeedBinding.spinSupplimentType.setAdapter(productTypesAdapter);

                                _activityAddFeedBinding.spinQtyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) qtyTypesArrayList.get(position);
                                        product_qtyTypeID = productTypes.getPtID();
                                        qtycategorycode = productTypes.getPtCode();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                _activityAddFeedBinding.spinSupplimentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ProductTypes productTypes = (ProductTypes) qtyTypesArrayList.get(position);
                                        suppliment_qtyTypeID = productTypes.getPtID();
                                        suppliment_qtycategorycode = productTypes.getPtCode();
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
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        responseData = new String[]{response};
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.getCause(), AquaConstants.FINISH);
                }

                break;

            case 3:
                try {
                    String status = jsonobject.getString("status");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context,"Feed Created",AquaConstants.HOLD);
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.toString(), AquaConstants.FINISH);
                }

                break;


        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }

}
