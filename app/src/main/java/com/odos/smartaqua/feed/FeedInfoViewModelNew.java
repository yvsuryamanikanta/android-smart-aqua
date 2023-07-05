package com.odos.smartaqua.feed;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.databinding.ActivityFeedInfoNewBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedInfoViewModelNew extends ViewModel implements ServiceAsyncResponse, FeedInfoAdapter.ClickListener {

    List<FeedInfoModel> jsonList = new ArrayList<>();
    List<FeedInfoModel> jsonListDup2 = new ArrayList<>();
    HashMap<Integer, List<FeedInfoModel>> data;
    List<FeedInfoModel> feedInfoModels = new ArrayList<>();
    private Context _context;
    private ActivityFeedInfoNewBinding _activityFeedInfoBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String[] values;

    public FeedInfoViewModelNew(Context context, ActivityFeedInfoNewBinding activityFeedInfoBinding) {
        this._context = context;
        this._activityFeedInfoBinding = activityFeedInfoBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        data = new HashMap<>();
        try {
            JSONArray feedJsonArray = new JSONArray(values[2]);
            if (feedJsonArray.length() != 0) {
                for (int i = 0; i < feedJsonArray.length(); i++) {
                    JSONObject jsonObject1 = feedJsonArray.getJSONObject(i);
                    int productID = jsonObject1.getInt("productID");
                    int productcatgeoryID = jsonObject1.getInt("productcatgeoryID");
                    int quantitycategoryid = jsonObject1.getInt("quantitycategoryid");
                    String productName = jsonObject1.getString("productName");
                    String priceperqty = jsonObject1.getString("priceperqty");
                    String productqty = jsonObject1.getString("productqty");
                    String quantity = jsonObject1.getString("quantity");
                    String comments = jsonObject1.getString("comments");

                    FeedInfoModel feedInfoModel = new FeedInfoModel(productID, productcatgeoryID, quantitycategoryid, productName,
                            priceperqty, productqty, quantity, comments);
                    jsonList.add(feedInfoModel);
                    jsonListDup2.add(feedInfoModel);

                }

            }
        } catch (Exception e) {
            Log.e("data--==", "" + e);
        }

        try {
            if (jsonList.size() != 0) {
                jsonList.sort((a, b) -> {
                    int compare = 0;
                    int keyA = a.getProductcatgeoryID();
                    int keyB = b.getProductcatgeoryID();
                    compare = Integer.compare(keyA, keyB);
                    return compare;
                });
            }
        } catch (Exception e) {
            Log.e("data--==", "" + e);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
        _activityFeedInfoBinding.recyclerView.setLayoutManager(mLayoutManager);
        _activityFeedInfoBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        _activityFeedInfoBinding.recyclerView.setAdapter(new FeedInfoAdapter(_context, jsonList, this));






        try {
            if (jsonListDup2.size() != 0) {
                jsonListDup2.sort((a, b) -> {
                    int compare = 0;
                    int keyA = a.getProductcatgeoryID();
                    int keyB = b.getProductcatgeoryID();
                    compare = Integer.compare(keyA, keyB);
                    return compare;
                });
            }
        } catch (Exception e) {
            Log.e("data--==", "" + e);
        }

        jsonList.forEach(feedInfoModel -> {
            feedInfoModels.clear();
            int catgId = feedInfoModel.getProductcatgeoryID();
            if (!data.containsKey(catgId)) {
                feedInfoModels.add(feedInfoModel);
                Log.e("--------", " " + catgId);
                jsonListDup2.forEach(infoModel -> {
                    Log.e("--------0000", " " + infoModel.getProductcatgeoryID());
                    if (feedInfoModel != infoModel && !data.containsKey(catgId) && catgId == infoModel.getProductcatgeoryID()) {
                        feedInfoModels.add(infoModel);
                    }
                });
                if (jsonListDup2.size() > 0)
                    jsonListDup2.remove(0);

                    data.put(catgId, feedInfoModels);
            }
            Log.e("$$$$$$$ Map ", " " + new Gson().toJson(data));
        });


    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {
    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }

    @Override
    public void onClicked(FeedInfoModel feedModel, int pos) {

    }
}
