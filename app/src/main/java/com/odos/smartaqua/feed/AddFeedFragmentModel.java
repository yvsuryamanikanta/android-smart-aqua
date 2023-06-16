package com.odos.smartaqua.feed;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentAddFeedBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;
import com.odos.smartaqua.warehouse.products.ProductTypesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class AddFeedFragmentModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private boolean loading = true;
    private FragmentAddFeedBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int cultureId;
    private String cultureAccess;
    private String[] searchData;
    private double availablestock;
    private String productId, productCatgId, mrp, productName, qtyName;

    public AddFeedFragmentModel(Context context, FragmentAddFeedBinding activityAddFeedBinding, int cultureid, String cultureaccess) {
        this._context = context;
        this._binding = activityAddFeedBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        cultureAccess = cultureaccess;
    }

    public void loadStock(String[] stock) {
        searchData = stock;
    }

    public void loadAvailableStock(double stock) {
        availablestock = stock;
    }

    public void loadProductId(String pId) {
        productId = pId;
    }

    public void loadProductName(String pName) {
        productName = pName;
    }

    public void loadQtyName(String _qtyName) {
        qtyName = _qtyName;
    }

    public void loadProductMrp(String pMrp) {
        mrp = pMrp;
    }

    public void loadProductCtgId(String pCtgId) {
        productCatgId = pCtgId;
    }

    public void loadData() {
        if (loading) {
            loading = false;
            if (CheckNetwork.isNetworkAvailable(_context)) {
                VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                        ServiceConstants.QTY_LIST, null, Helper.headerParams(_context),
                        (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
            } else {
                Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
            }
        }
    }

    public void saveSuppliment(View view) {
        if (_binding.txtSupliment.getText().toString().equalsIgnoreCase("Add Suppliments")) {
            Toast.makeText(_context, "Add Product", Toast.LENGTH_SHORT).show();
        } else if (_binding.edtSuplimentQty.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Enter Quantity", Toast.LENGTH_SHORT).show();
        } else {
            if (searchData != null) {
                double currentstock;
                if (qtyName.equalsIgnoreCase("KG") && suppliment_qtycategorycode.equalsIgnoreCase("KG")) {
                    currentstock = Double.parseDouble(_binding.edtSuplimentQty.getText().toString());
                } else if (qtyName.equalsIgnoreCase("KG") && suppliment_qtycategorycode.equalsIgnoreCase("Grams")) {
                    currentstock = Double.parseDouble(_binding.edtSuplimentQty.getText().toString());
                    currentstock = currentstock / 1000;
                } else if (qtyName.equalsIgnoreCase("Grams") && suppliment_qtycategorycode.equalsIgnoreCase("Grams")) {
                    currentstock = Double.parseDouble(_binding.edtSuplimentQty.getText().toString());
                } else if (qtyName.equalsIgnoreCase("Grams") && suppliment_qtycategorycode.equalsIgnoreCase("KG")) {
                    currentstock = Double.parseDouble(_binding.edtSuplimentQty.getText().toString()) * 1000;
                } else {
                    currentstock = Double.parseDouble(_binding.edtSuplimentQty.getText().toString());
                }
                if (availablestock < currentstock) {
                    Helper.showMessage(_context, "you don`t have sufficient stock in your ware house", AquaConstants.HOLD);
                } else {
                    double avStock = availablestock - currentstock;
                    try {
                        suppliment_jsonObject = new JSONObject();
                        suppliment_jsonObject.put("productID", productId);
                        suppliment_jsonObject.put("productName", productName);
                        suppliment_jsonObject.put("productqty", "" + currentstock);
                        suppliment_jsonObject.put("priceperqty", mrp);
                        suppliment_jsonObject.put("productcatgeoryID", productCatgId);
                        suppliment_jsonObject.put("quantitycategoryid", suppliment_qtyTypeID);
                        suppliment_jsonObject.put("quantitycategoryname", qtyName);
                        suppliment_jsonObject.put("availablestock", String.valueOf(avStock));
                        suppliment_jsonArray.put(suppliment_jsonObject);
                        _binding.txtSupliment.setText(_context.getString(R.string.add_suppliments));
                        _binding.edtSuplimentQty.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    _binding.llSupplimentSet.removeAllViews();
                    loadSupplimentsView();
                }
            }


        }
    }

    public void saveProduct(View view) {
        if (_binding.txtProduct.getText().toString().equalsIgnoreCase("Select Product Name")) {
            Toast.makeText(_context, "Add Product", Toast.LENGTH_SHORT).show();
        } else if (_binding.edtQty.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Enter Quantity", Toast.LENGTH_SHORT).show();
        } else {
            if (searchData != null) {
                double currentstock;
                if (qtyName.equalsIgnoreCase("KG") && qtycategorycode.equalsIgnoreCase("KG")) {
                    currentstock = Double.parseDouble(_binding.edtQty.getText().toString());
                } else if (qtyName.equalsIgnoreCase("KG") && qtycategorycode.equalsIgnoreCase("Grams")) {
                    currentstock = Double.parseDouble(_binding.edtQty.getText().toString());
                    currentstock = currentstock / 1000;
                } else if (qtyName.equalsIgnoreCase("Grams") && qtycategorycode.equalsIgnoreCase("Grams")) {
                    currentstock = Double.parseDouble(_binding.edtQty.getText().toString());
                } else if (qtyName.equalsIgnoreCase("Grams") && qtycategorycode.equalsIgnoreCase("KG")) {
                    currentstock = Double.parseDouble(_binding.edtQty.getText().toString()) * 1000;
                } else {
                    currentstock = Double.parseDouble(_binding.edtQty.getText().toString());
                }
                if (availablestock < currentstock) {
                    Helper.showMessage(_context, "you don`t have sufficient stock in your ware house", AquaConstants.HOLD);
                } else {
                    double avStock = availablestock - currentstock;
                    try {
                        jsonObject = new JSONObject();
                        jsonObject.put("productID", productId);
                        jsonObject.put("productcatgeoryID", productCatgId);
                        jsonObject.put("productqty", "" + currentstock);
                        jsonObject.put("priceperqty", mrp);
                        jsonObject.put("productName", productName);
                        jsonObject.put("quantitycategoryid", product_qtyTypeID);
                        jsonObject.put("quantitycategoryname", qtyName);
                        jsonObject.put("availablestock", String.valueOf(avStock));
                        products_jsonArray.put(jsonObject);
                        _binding.txtProduct.setText(_context.getString(R.string.enter_product_name));
                        _binding.edtQty.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                _binding.llFeedSet.removeAllViews();
                //  totalqty = 0.0;
                loadProductView();
                loadSupplimentsView();
            }
        }
    }

    public void getFeedDate(View view) {
        Helper.getDateTimepicker(_context, _binding.txtTimeDate, AquaConstants.HOLD);
    }

    public void saveFeed(View view) {

        if (_binding.edtFeedTitle.getText().toString().length() == 0) {
            Toast.makeText(_context, "Add Today Feed Count", Toast.LENGTH_SHORT).show();
        } else if (products_jsonArray.length() == 0) {
            Toast.makeText(_context, "Add atleast one product", Toast.LENGTH_SHORT).show();
        } else if (_binding.txtTimeDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pick feed date and time", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String dateandtime = _binding.txtTimeDate.getText().toString();
                String comment = _binding.edtCommentsFeed.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateandtime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String formateddate = DateFormat.format("yyyy-MM-dd", calendar).toString();
                HashMap<String, Object> postParams = new HashMap<>();
                postParams.put("groupname", _binding.edtFeedTitle.getText().toString());
                postParams.put("feedProducts", products_jsonArray);
                postParams.put("suppliments", suppliment_jsonArray);
                postParams.put("userID", Helper.getUserID(_context));
                postParams.put("cultureid", cultureId);
                postParams.put("access", cultureAccess);
                postParams.put("feeddate", formateddate);
                postParams.put("feeddateandtime", dateandtime);
                postParams.put("comment", comment);
                Log.e("data--==", "" + new JSONObject(postParams));
                VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                        ServiceConstants.SAVE_FEED, postParams, Helper.headerParams(_context),
                        (ServiceAsyncResponse) serviceAsyncResponse, 3, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

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
                    _binding.llFeedSet.addView(child);
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
                    //  Double convertQty = Double.parseDouble(productQty) / 1000;
                    String buildTesxt = productName + " --  " + productQty + quantityname;
                    textView.setText(buildTesxt);
                    feed_id.setText(productID);
                    //  feed_id.setVisibility(View.GONE);
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
                                        _binding.llFeedSet.removeView(child);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void loadSupplimentsView() {
        if (suppliment_jsonArray.length() != 0) {
            _binding.llSupplimentSet.removeAllViews();
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
                    _binding.llSupplimentSet.addView(child);
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
                            _binding.llSupplimentSet.removeView(child);
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
                Log.e("data--==", "" + jsonobject);
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
                                _binding.spinQtyType.setAdapter(productTypesAdapter);
                                _binding.spinSupplimentType.setAdapter(productTypesAdapter);

                                _binding.spinQtyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                                _binding.spinSupplimentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            case 3:
                try {
                    String statusCode = jsonobject.getString("statusCode");
                    String status = jsonobject.getString("status");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        products_jsonArray = new JSONArray();
                        suppliment_jsonArray = new JSONArray();
                        _binding.llFeedSet.removeAllViews();
                        _binding.llSupplimentSet.removeAllViews();
                        _binding.edtFeedTitle.setText("");
                        _binding.edtCommentsFeed.setText("");
                        _binding.txtTimeDate.setText("");
                        Helper.showMessage(_context, "Feed Saved", AquaConstants.HOLD);
                    } else {
                        Helper.showMessage(_context, "" + jsonobject.getString("response"), AquaConstants.HOLD);
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
