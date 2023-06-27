package com.odos.smartaqua.warehouse.stock;


import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.AddBrandActivity;
import com.odos.smartaqua.databinding.ActivityStockListBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockListViewModel extends BaseObservable implements ServiceAsyncResponse, StockListAdapter.ClickListener {

    private Context _context;
    private ActivityStockListBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<StockCnsts> arrayList;

    public StockListViewModel(Context context, ActivityStockListBinding activityStockListBinding) {
        this._context = context;
        this._binding = activityStockListBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_STOCK + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
        _binding.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AquaConstants.putIntent(_context, AddStockActivity.class, AquaConstants.HOLD, null);
            }
        });
    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    arrayList = new ArrayList<>();
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                _binding.recyclerView.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int stockid = jsonObject1.getInt("stockid");
                                    int productid = jsonObject1.getInt("productid");
                                    int productcategoryid = jsonObject1.getInt("productcategoryid");
                                    String productname = jsonObject1.getString("productname");
                                    String newstock = jsonObject1.getString("newstock");
                                    String oldstock = jsonObject1.getString("oldstock");
                                    String availablestock = jsonObject1.getString("availablestock");
                                    String mrp = jsonObject1.getString("mrp");
                                    String path = jsonObject1.getString("path");
                                    String productcode = jsonObject1.getString("productcode");
                                    StockCnsts model = new StockCnsts(stockid, productid, productcategoryid, productname,
                                            newstock, oldstock, availablestock, mrp, path, productcode);
                                    arrayList.add(model);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new StockListAdapter(_context, arrayList, this));
                            } else {
                                _binding.recyclerView.setVisibility(View.GONE);
                                _binding.txtNodata.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setText("No Reports Available");
                            }
                        }
                    } else {
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.txtNodata.setVisibility(View.VISIBLE);
                        _binding.txtNodata.setText("No Reports Available");
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }


    @Override
    public void onClicked(StockCnsts model, int pos) {

    }
}
