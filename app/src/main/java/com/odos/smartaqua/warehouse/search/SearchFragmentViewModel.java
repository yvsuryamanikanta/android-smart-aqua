package com.odos.smartaqua.warehouse.search;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListSearchBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.SimpleDividerItemDecoration;
import com.odos.smartaqua.warehouse.products.ProductTypes;
import com.odos.smartaqua.warehouse.products.WareHouseModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchFragmentViewModel extends ViewModel implements ServiceAsyncResponse, SearchAdapter.ClickListener {

    int productCatgId;
    private Context _context;
    private boolean loading = true;
    private FragmentListSearchBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private String[] searchData;
    private double availablestock;

    private List<WareHouseModel> productList;
    private List<WareHouseModel> filteredList;
    private SearchAdapter mAdapter;

    public SearchFragmentViewModel(Context context, FragmentListSearchBinding searchBinding, int productCatId) {
        this._context = context;
        this._binding = searchBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        productCatgId = productCatId;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_STOCKLIST + productCatgId, null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
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
                            Log.e("########### "," "+new Gson().toJson(response));
                            if (response != null) {
                                productList = getListItemData(response);
                            } else {
                                productList = null;
                            }
                            filteredList = new ArrayList<>();
                            filteredList.addAll(productList);

                            _binding.itemList.addItemDecoration(new SimpleDividerItemDecoration(_context));
                            mAdapter = new SearchAdapter(filteredList, productList, this);
                            _binding.itemList.setAdapter(mAdapter);

                            _binding.searchBox.addTextChangedListener(new TextWatcher() {
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

                    }

                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once."+e, AquaConstants.FINISH);
                }

        }
    }

    private List<WareHouseModel> getListItemData(String response) {
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
                String actualprice = jsonObject.getString("actualprice");
                String purchaseprice = jsonObject.getString("purchaseprice");
                String discount = jsonObject.getString("discount");
                String createddate = jsonObject.getString("createddate");
                String productcategoryid = jsonObject.getString("productcategoryid");
                String productcode = jsonObject.getString("productcode");
                String quantityname = jsonObject.getString("quantityname");
                listViewItems.add(new WareHouseModel(stockid, userid, productid, quantitycategoryid, productname, newstock, oldstock,
                        availablestock, actualprice, purchaseprice,discount,createddate, productcategoryid, productcode,quantityname));

            }
        } catch (Exception e) {
            Helper.showMessage(_context, "Data corrupted please try again once." + e, AquaConstants.FINISH);
        }
        return listViewItems;
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
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
        String actualprice = wareHouseModel.getActualprice();
        String purchaseprice = wareHouseModel.getActualprice();
        String discount = wareHouseModel.getActualprice();
        String createddate = wareHouseModel.getCreateddate();
        String productcategoryid = wareHouseModel.getProductcategoryid();
        String quantityname = wareHouseModel.getQuantityname();
        String[] passingdata = new String[]{"" + stockid, "" + userid, "" + productid, "" + quantitycategoryid, productname, newstock, oldstock,
                availablestock, purchaseprice, createddate, productcategoryid,quantityname};
        Intent intent = new Intent();
        intent.putExtra("searchedvalues", passingdata);
        ((Activity) _context).setResult(Activity.RESULT_OK, intent);
        ((Activity) _context).finish();
    }

}
