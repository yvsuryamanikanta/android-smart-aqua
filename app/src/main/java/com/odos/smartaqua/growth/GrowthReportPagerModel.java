package com.odos.smartaqua.growth;


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
import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.databinding.ActivityGrowthReportViewpagerBinding;
import com.odos.smartaqua.feed.FeedListViewPagerAdapter;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GrowthReportPagerModel extends BaseObservable implements ServiceAsyncResponse {
    private GrowthReportViewPagerAdapter growthReportViewPagerAdapter;
    private Context _context;
    private ActivityGrowthReportViewpagerBinding _binding;
    private ActivityBaseBinding _activityBaseBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<CultureModel> cultureModelArrayList;
    private String[] values;

    public GrowthReportPagerModel(Context context, ActivityGrowthReportViewpagerBinding activityGrowthReportViewpagerBinding, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._binding = activityGrowthReportViewpagerBinding;
        this._activityBaseBinding = activityBaseBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity)_context).getIntent().getStringArrayExtra("values");
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setText(values[1]);
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setTextSize(11);
      //  setUpViewPager();
    }

    public void setUpViewPager() {
        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.GET_CULTURES + Helper.getUserID(_context), null, Helper.headerParams(_context),
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
                    cultureModelArrayList = new ArrayList<>();
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
                                        int cultureid = jsonObject1.getInt("cultureid");
                                        String userid = jsonObject1.getString("userid");
                                        String tankid = jsonObject1.getString("tankid");
                                        String culturename = jsonObject1.getString("culturename");
                                        String tankname = jsonObject1.getString("tankname");
                                        String culturenumber = jsonObject1.getString("culturenumber");
                                        String cultureimage = jsonObject1.getString("cultureimage");
                                        String culturestatus = jsonObject1.getString("culturestatus");
                                        String cultureaccess = jsonObject1.getString("cultureaccess");
                                        CultureModel cultureModel = new CultureModel(cultureid,userid,tankid,culturename,tankname,
                                                culturenumber,cultureimage,culturestatus,cultureaccess);
                                        cultureModelArrayList.add(cultureModel);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                growthReportViewPagerAdapter = new GrowthReportViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cultureModelArrayList);
                                _binding.pager.setAdapter(growthReportViewPagerAdapter);
                                _binding.tabLayout.setupWithViewPager(_binding.pager, true);
                                _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
                                _binding.pager.setCurrentItem(0);
                                _binding.pager.setOffscreenPageLimit(jsonArray.length());
                            } else {
                                Helper.showMessage(_context, "No Data.", AquaConstants.FINISH);
                            }
                        } else {
                            Helper.showMessage(_context, "No Data", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "No Data", AquaConstants.FINISH);
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
}
