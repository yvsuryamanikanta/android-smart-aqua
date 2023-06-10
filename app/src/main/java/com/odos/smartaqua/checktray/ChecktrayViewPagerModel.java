package com.odos.smartaqua.checktray;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.databinding.ActivityChecktrayReportViewpagerBinding;
import com.odos.smartaqua.databinding.ActivityFeedListViewpagerBinding;
import com.odos.smartaqua.feed.FeedListViewPagerAdapter;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChecktrayViewPagerModel extends BaseObservable implements ServiceAsyncResponse {
    private ChecktrayReportViewPagerAdapter checktrayReportViewPagerAdapter;
    private Context _context;
    private ActivityChecktrayReportViewpagerBinding _binding;
    private ActivityBaseBinding _activityBaseBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ChecktrayObsvModel> checktrayObsvModelArrayList;
    private String[] values;
    private ArrayList<CultureModel> cultureModelArrayList;

    public ChecktrayViewPagerModel(Context context, ActivityChecktrayReportViewpagerBinding activityChecktrayReportViewpagerBinding, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._binding = activityChecktrayReportViewpagerBinding;
        this._activityBaseBinding = activityBaseBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity)_context).getIntent().getStringArrayExtra("values");
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setText(values[1]);
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setTextSize(9);
      //  setUpViewPager();
    }

    public void setUpViewPager() {
        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.GET_CULTURES + Helper.getUserID(_context), null, Helper.headerParams(_context),
                (ServiceAsyncResponse) serviceAsyncResponse, 11, false);
        /*VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.CHECKTRAY_OBSV_LIST + "1", null, Helper.headerParams(_context),
                (ServiceAsyncResponse) serviceAsyncResponse, 12, false);*/
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {

            case 11:
                try {
                    cultureModelArrayList = new ArrayList<>();
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    Log.e("$$$$$$$$$$  "," "+new Gson().toJson(response));
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
                                if (cultureModelArrayList.size() < 4) {
                                    _binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                                } else {
                                    _binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
                                checktrayReportViewPagerAdapter = new ChecktrayReportViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cultureModelArrayList);
                                _binding.pager.setAdapter(checktrayReportViewPagerAdapter);
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

            case 12:
                try {
                    checktrayObsvModelArrayList = new ArrayList<>();
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
                                        int checktrayobsvid = jsonObject1.getInt("checktrayobsvid");
                                        String checktrayid = jsonObject1.getString("checktrayid");
                                        String tankid = jsonObject1.getString("tankid");
                                        String feedstatus = jsonObject1.getString("feedstatus");
                                        String wastagecolor = jsonObject1.getString("wastagecolor");
                                        String mortalitytype = jsonObject1.getString("mortalitytype");
                                        String mortalitycount = jsonObject1.getString("mortalitycount");
                                        String potaciumdefeciency = jsonObject1.getString("potaciumdefeciency");
                                        String magniciumdefeciency = jsonObject1.getString("magniciumdefeciency");
                                        String calciumdefeciency = jsonObject1.getString("calciumdefeciency");
                                        String vibrieostatus = jsonObject1.getString("vibrieostatus");
                                        String crampstatus = jsonObject1.getString("crampstatus");
                                        String checktrayobsvdate = jsonObject1.getString("checktrayobsvdate");
                                        String createddate = jsonObject1.getString("createddate");
                                        String modifieddate = jsonObject1.getString("modifieddate");
                                        ChecktrayObsvModel checktrayObsvModel = new ChecktrayObsvModel(checktrayobsvid,checktrayid,tankid,feedstatus,wastagecolor,
                                                mortalitytype,mortalitycount,potaciumdefeciency,magniciumdefeciency,calciumdefeciency,vibrieostatus,crampstatus,
                                                checktrayobsvdate,createddate,modifieddate);
                                        checktrayObsvModelArrayList.add(checktrayObsvModel);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                if (checktrayObsvModelArrayList.size() < 4) {
                                    _binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                                } else {
                                    _binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
/*
                                checktrayReportViewPagerAdapter = new ChecktrayReportViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, checktrayObsvModelArrayList);
*/
                                _binding.pager.setAdapter(checktrayReportViewPagerAdapter);
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
