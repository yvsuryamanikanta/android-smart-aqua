package com.odos.smartaqua.warehouse.products;


import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.BrandListAdapter;
import com.odos.smartaqua.brand.Brandcnsts;
import com.odos.smartaqua.databinding.ActivityProductListBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListViewModel extends BaseObservable implements ServiceAsyncResponse, BrandListAdapter.ClickListener {

    private Context _context;
    private ActivityProductListBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<Brandcnsts> arrayList;
    private ProductsViewPagerAdapter viewpagerAdapter;

    public ProductListViewModel(Context context, ActivityProductListBinding activityProductListBinding) {
        this._context = context;
        this._binding = activityProductListBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
    }

    public void setUpViewPager() {
        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.BRAND_LIST, null, Helper.headerParams(_context),
                (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
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
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        int brandid = jsonObject1.getInt("brandid");
                                        String brandname = jsonObject1.getString("brandname");
                                        String brandcode = jsonObject1.getString("brandcode");
                                        String cretaedby = jsonObject1.getString("cretaedby");
                                        Brandcnsts model = new Brandcnsts(brandid, brandname, brandcode, cretaedby);
                                        arrayList.add(model);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                viewpagerAdapter = new ProductsViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, arrayList);
                                _binding.pager.setAdapter(viewpagerAdapter);
                                _binding.tabLayout.setupWithViewPager(_binding.pager, true);
                                _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
                                _binding.pager.setCurrentItem(0);
                                _binding.pager.setOffscreenPageLimit(jsonArray.length());
                            } else {
                                Helper.showMessage(_context, "No Brands.", AquaConstants.FINISH);
                            }
                        } else {
                            Helper.showMessage(_context, "No Brands", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "No Brands", AquaConstants.FINISH);
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
    public void onClicked(Brandcnsts model, int pos) {

    }
}
