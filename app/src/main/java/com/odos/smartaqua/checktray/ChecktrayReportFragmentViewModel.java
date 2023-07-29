package com.odos.smartaqua.checktray;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentReportChecktrayBinding;
import com.odos.smartaqua.feed.FeedListAdapter;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChecktrayReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, ChecktrayReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportChecktrayBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int cultureId;
    private String tankId;
    private String[] searchData;
    private double availablestock;

    public ChecktrayReportFragmentViewModel(Context context, FragmentReportChecktrayBinding fragmentReportChecktrayBinding, int cultureid, String _tankid) {
        this._context = context;
        this._binding = fragmentReportChecktrayBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        tankId = _tankid;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.CHECKTRAY_OBSV_LIST + tankId, null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
        }
    }

    public void loadAvailableStock(double stock) {
        availablestock = stock;
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        Log.e("$$$$$$$$$ ", " "+jsonobject.toString());
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
                                ArrayList<ChecktrayReportModel> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int checktrayobsvid = jsonObject1.getInt("checktrayobsvid");
                                    int checktrayid = jsonObject1.getInt("checktrayid");
                                    String tankid = jsonObject1.getString("tankid");
                                    String feedstatus = jsonObject1.getString("feedstatus");
                                    String checktrayname = jsonObject1.getString("checktrayname");
                                    String wastagecolor = jsonObject1.getString("wastagecolor");
                                    String redmortality = jsonObject1.getString("redmortality");
                                    String redmortalitycount = jsonObject1.getString("redmortalitycount");
                                    String whitemortality = jsonObject1.getString("whitemortality");
                                    String whitemortalitycount = jsonObject1.getString("whitemortalitycount");
                                    String potaciumdefeciency = jsonObject1.getString("potaciumdefeciency");
                                    String magniciumdefeciency = jsonObject1.getString("magniciumdefeciency");
                                    String calciumdefeciency = jsonObject1.getString("calciumdefeciency");
                                    String crampstatus = jsonObject1.getString("crampstatus");
                                    String checktrayobsvdate = jsonObject1.getString("checktrayobsvdate");
                                    String createddate = jsonObject1.getString("createddate");
                                    String modifieddate = jsonObject1.getString("modifieddate");
                                    ChecktrayReportModel checktrayReportModel = new ChecktrayReportModel(checktrayobsvid,
                                            checktrayid, tankid, feedstatus, checktrayname,wastagecolor, redmortality, redmortalitycount, whitemortality,whitemortalitycount,potaciumdefeciency,
                                            magniciumdefeciency, calciumdefeciency, crampstatus, checktrayobsvdate, createddate, modifieddate);
                                    arrayList.add(checktrayReportModel);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new ChecktrayReportAdapter(_context, arrayList, this));
                            } else {
                                Helper.showMessage(_context, "No Data Available Now.", AquaConstants.HOLD);
                            }
                        }
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.HOLD);
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
    public void onClicked(ChecktrayReportModel checktrayReportModel, int pos) {
        String[] values = new String[]{checktrayReportModel.getFeedstatus()};
        AquaConstants.putIntent(_context, ChecktrayInfoActivity.class, AquaConstants.HOLD, values);
    }
}
