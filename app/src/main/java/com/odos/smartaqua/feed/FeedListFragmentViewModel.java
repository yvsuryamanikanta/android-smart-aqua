package com.odos.smartaqua.feed;


import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FeedListFragmentViewModel extends ViewModel implements ServiceAsyncResponse, FeedListAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentListFeedBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int cultureId;
    private String cultureAccess;
    private String feedDate;
    private String[] searchData;
    private double availablestock;
    private String productId, productCatgId, mrp, productName;

    public FeedListFragmentViewModel(Context context, FragmentListFeedBinding activityAddFeedBinding, int cultureid, String cultureaccess, String _feedDate) {
        this._context = context;
        this._binding = activityAddFeedBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        cultureAccess = cultureaccess;
        feedDate = _feedDate;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            HashMap<String, Object> postParams = new HashMap<>();
            postParams.put("userID", Helper.getUserID(_context));
            postParams.put("cultureid", cultureId);
            postParams.put("feeddate", feedDate);
            postParams.put("type", "F");
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.FEED_LIST_BY_CULTUREID, postParams, Helper.headerParams(_context),
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
                    Log.e("%%%%%%%%% Feed res "," "+response);
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                _binding.recyclerView.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setVisibility(View.GONE);
                                ArrayList<FeedModel> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int templateID = jsonObject1.getInt("templateID");
                                    int userID = jsonObject1.getInt("userID");
                                    String groupname = jsonObject1.getString("groupname");
                                    String feeddate = jsonObject1.getString("feeddate");
                                    String feedProducts = jsonObject1.getString("feedProducts");
//                                    String suppliments = jsonObject1.getString("suppliments");
                                    // String comments = jsonObject1.getString("comments");
                                    FeedModel feedModel = new FeedModel(templateID, userID, groupname, feeddate, feedProducts, "", "comments");
                                    arrayList.add(feedModel);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new FeedListAdapter(_context, arrayList, this));
                            } else {
                                _binding.recyclerView.setVisibility(View.GONE);
                                _binding.txtNodata.setVisibility(View.VISIBLE);
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
    public void onClicked(FeedModel feedModel, int pos) {
        String[] values = new String[]{feedModel.getFeeddate(), feedModel.getGroupname(),
                feedModel.getFeedProducts(), feedModel.getSuppliments(), feedModel.getComments()};
        AquaConstants.putIntent(_context, FeedInfoActivity.class, AquaConstants.HOLD, values);
    }
}
