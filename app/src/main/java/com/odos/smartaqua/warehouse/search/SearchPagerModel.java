package com.odos.smartaqua.warehouse.search;


import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.databinding.SearchViewpagerBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchPagerModel extends BaseObservable implements ServiceAsyncResponse {
    private SearchListViewPagerAdapter pagerAdapter;
    private Context _context;
    private SearchViewpagerBinding _binding;
    private ActivityBaseBinding _activityBaseBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> productTypesArrayList;
    private String[] values;

    public SearchPagerModel(Context context, SearchViewpagerBinding binding, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._binding = binding;
        this._activityBaseBinding = activityBaseBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
    }

    public void setUpViewPager() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.PRODUCT_CAATEGORIES, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
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
                            }
                            pagerAdapter = new SearchListViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, productTypesArrayList);
                            _binding.pager.setAdapter(pagerAdapter);
                            _binding.tabLayout.setupWithViewPager(_binding.pager, true);
                            _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
                            _binding.pager.setCurrentItem(0);
                            _binding.pager.setOffscreenPageLimit(jsonArray.length());
                        }
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;

        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
