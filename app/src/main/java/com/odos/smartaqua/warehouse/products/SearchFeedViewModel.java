package com.odos.smartaqua.warehouse.products;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.lifecycle.ViewModel;


import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivitySearchProductBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.SimpleDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFeedViewModel extends ViewModel implements SearchProductAdapter.ClickListener, ServiceAsyncResponse {

    private Context _context;
    private ActivitySearchProductBinding _activitySearchProductBinding;
    //private String[] values;
    private String flag;
    private List<WareHouseModel> productList;
    private List<WareHouseModel> filteredList;
    private SearchProductAdapter mAdapter;
    private ServiceAsyncResponse serviceAsyncResponse;

    public SearchFeedViewModel(Context context, ActivitySearchProductBinding activitySearchProductBinding) {
        this._context = context;
        this._activitySearchProductBinding = activitySearchProductBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.LIST_STOCK + Helper.getUserID(_context), null, Helper.headerParams(_context),
                (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        //values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        flag = ((Activity) _context).getIntent().getStringExtra("flag");
        /*if (values != null) {
            productList = getListItemData(values[0],flag);
        } else {
            productList = null;
        }
        filteredList = new ArrayList<WareHouseModel>();
        filteredList.addAll(productList);

        _activitySearchProductBinding.itemList.addItemDecoration(new SimpleDividerItemDecoration(_context));
        mAdapter = new SearchProductAdapter(filteredList, productList, this);
        _activitySearchProductBinding.itemList.setAdapter(mAdapter);

        _activitySearchProductBinding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

    }

    private List<WareHouseModel> getListItemData(String response,String flagval) {
        List<WareHouseModel> listViewItems = new ArrayList<WareHouseModel>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int stockid = jsonObject.getInt("stockid");
                int userid = jsonObject.getInt("userid");
                int productid = jsonObject.getInt("productid");
                int quantitycategoryid = jsonObject.getInt("quantitycategoryid");
                String productname = jsonObject.getString("productname");
                String newstock = jsonObject.getString("newstock");
                String oldstock = jsonObject.getString("oldstock");
                String availablestock = jsonObject.getString("availablestock");
                String mrp = jsonObject.getString("mrp");
                String createddate = jsonObject.getString("createddate");
                String productcategoryid = jsonObject.getString("productcategoryid");
                String productcode = jsonObject.getString("productcode");
                String quantityname = jsonObject.getString("quantityname");

                if (flagval.equalsIgnoreCase("1")) {
                    if (productcode.equalsIgnoreCase("F")) {
                        listViewItems.add(new WareHouseModel(stockid, userid, productid, quantitycategoryid, productname, newstock, oldstock,
                                availablestock, mrp, createddate, productcategoryid, productcode,quantityname));
                    }
                }

                if (flagval.equalsIgnoreCase("2")) {
                    if (productcode.equalsIgnoreCase("S")) {
                        listViewItems.add(new WareHouseModel(stockid, userid, productid, quantitycategoryid, productname, newstock, oldstock,
                                availablestock, mrp, createddate, productcategoryid, productcode,quantityname));
                    }
                }

            }
        } catch (Exception e) {
            Helper.showMessage(_context, "Data corrupted please try again once."+e, AquaConstants.FINISH);
        }
        return listViewItems;
    }

    @Override
    public void onClicked(WareHouseModel wareHouseModel, int pos) {

        int stockid = wareHouseModel.getStockid();
        int userid = wareHouseModel.getUserid();
        int productid = wareHouseModel.getProductid();
        int quantitycategoryid = wareHouseModel.getQuantitycategoryid();
        String productname = wareHouseModel.getProductname();
        String newstock = wareHouseModel.getNewstock();
        String oldstock = wareHouseModel.getOldstock();
        String availablestock = wareHouseModel.getAvailablestock();
        String mrp = wareHouseModel.getMrp();
        String createddate = wareHouseModel.getCreateddate();
        String productcategoryid = wareHouseModel.getProductcategoryid();
        String quantityname = wareHouseModel.getQuantityname();
        String[] passingdata = new String[]{"" + stockid, "" + userid, "" + productid, "" + quantitycategoryid, productname, newstock, oldstock,
                availablestock, mrp, createddate, productcategoryid,quantityname};
        Intent intent = new Intent();
        intent.putExtra("searchedvalues", passingdata);
        ((Activity) _context).setResult(Activity.RESULT_OK, intent);
        ((Activity) _context).finish();
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno){
            case 1:
                try {
                    String status = jsonobject.getString("status");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if(response!=null){
                            productList = getListItemData(response,flag);
                        }else {
                            productList = null;
                        }
                        filteredList = new ArrayList<WareHouseModel>();
                        filteredList.addAll(productList);

                        _activitySearchProductBinding.itemList.addItemDecoration(new SimpleDividerItemDecoration(_context));
                        mAdapter = new SearchProductAdapter(filteredList, productList, this);
                        _activitySearchProductBinding.itemList.setAdapter(mAdapter);

                        _activitySearchProductBinding.searchBox.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                mAdapter.getFilter().filter(s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.getCause(), AquaConstants.FINISH);
                }

                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
