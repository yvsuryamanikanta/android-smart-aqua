package com.odos.smartaqua.feed;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

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
import com.odos.smartaqua.databinding.ActivityTankViewpagerBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TankViewPagerModel extends BaseObservable implements ServiceAsyncResponse {
    int pos = 0;
    private TankViewPagerAdapter tankViewPagerAdapter;
    private Context _context;
    private String tankId;
    private ActivityTankViewpagerBinding _binding;
    private ActivityBaseBinding _activityBaseBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<CultureModel> cultureModelArrayList;

    public TankViewPagerModel(Context context, ActivityTankViewpagerBinding activityTankViewpagerBinding, ActivityBaseBinding activityBaseBinding, String tankid) {
        this.tankId = tankid;
        this._context = context;
        this._binding = activityTankViewpagerBinding;
        this._activityBaseBinding = activityBaseBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        setUpViewPager();

    }

    private void setUpViewPager() {
        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.GET_CULTURES + Helper.getUserID(_context), null, Helper.headerParams(_context),
                serviceAsyncResponse, 1, false);


        /*if (titles.length < 4) {
            _binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        } else {
            _binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        tankViewPagerAdapter = new TankViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, titles);
        _binding.pager.setAdapter(tankViewPagerAdapter);
        _binding.tabLayout.setupWithViewPager(_binding.pager, true);
        _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
        _binding.pager.setCurrentItem(tabPosition);
        _binding.pager.setOffscreenPageLimit(titles.length);
        _binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                _activityBaseBinding.mytoolbar.txtAddOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateToolBaroption(tab.getPosition());
                    }
                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }


    private void navigateToolBaroption(int position) {
      /*  if (titles[position].equalsIgnoreCase("Culture")) {
            AquaConstants.putIntent(_context, AddTankActivity.class, 1, null);
        } else if (titles[position].equalsIgnoreCase("Ware House")) {
            AquaConstants.putIntent(_context, AddStockActivity.class, 1, null);
        } else if (titles[position].equalsIgnoreCase("Market Rates")) {
            AquaConstants.putIntent(_context, AddMarketratesActivity.class, 1, null);
        } else if (titles[position].equalsIgnoreCase("Classifieds")) {
            AquaConstants.putIntent(_context, AddClassifiedsActivity.class, 1, null);
        }*/
    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    Log.e("####### tankid    ", " " + tankId);

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
                                        if (tankid.equalsIgnoreCase(tankId)) {
                                            pos = i;
                                        }
                                        String culturename = jsonObject1.getString("culturename");
                                        String tankname = jsonObject1.getString("tankname");
                                        String culturenumber = jsonObject1.getString("culturenumber");
                                        String cultureimage = jsonObject1.getString("cultureimage");
                                        String culturestatus = jsonObject1.getString("culturestatus");
                                        String cultureaccess = jsonObject1.getString("cultureaccess");
                                        CultureModel cultureModel = new CultureModel(cultureid, userid, tankid, culturename, tankname,
                                                culturenumber, cultureimage, culturestatus, cultureaccess);
                                        cultureModelArrayList.add(cultureModel);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                tankViewPagerAdapter = new TankViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cultureModelArrayList);
                                _binding.pager.setAdapter(tankViewPagerAdapter);
                                _binding.tabLayout.setupWithViewPager(_binding.pager, true);
                                _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
                                _binding.pager.setCurrentItem(pos);
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
