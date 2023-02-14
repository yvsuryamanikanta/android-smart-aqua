package com.odos.smartaqua.growth;


import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentReportGrowthBinding;
import com.odos.smartaqua.feed.FeedListAdapter;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GrowthReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, GrowthReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportGrowthBinding _binding;
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
    private String productId, productCatgId, mrp, productName;

    public GrowthReportFragmentViewModel(Context context, FragmentReportGrowthBinding fragmentReportGrowthBinding, int cultureid, String cultureaccess) {
        this._context = context;
        this._binding = fragmentReportGrowthBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        cultureAccess = cultureaccess;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.FEED_TEMPLATES + Helper.getUserID(_context), null, Helper.headerParams(_context),
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
                                ArrayList<GrowthReportModel> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int templateID = jsonObject1.getInt("templateID");
                                    int userID = jsonObject1.getInt("userID");
                                    String groupname = jsonObject1.getString("groupname");
                                    String feeddate = jsonObject1.getString("feeddate");
                                    String feedProducts = jsonObject1.getString("feedProducts");
                                    String suppliments = jsonObject1.getString("suppliments");
                                    GrowthReportModel growthReportModel = new GrowthReportModel(templateID, userID, groupname, feeddate,feedProducts, suppliments);
                                    arrayList.add(growthReportModel);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new GrowthReportAdapter(_context, arrayList, this));
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
    public void onClicked(GrowthReportModel growthReportModel, int pos) {

    }
}
