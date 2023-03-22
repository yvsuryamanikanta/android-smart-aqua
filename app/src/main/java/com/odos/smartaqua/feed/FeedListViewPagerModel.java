package com.odos.smartaqua.feed;


import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.databinding.ActivityFeedListViewpagerBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FeedListViewPagerModel extends BaseObservable {
    private FeedListViewPagerAdapter feedListViewPagerAdapter;
    private Context _context;
    private ActivityFeedListViewpagerBinding _binding;
    private ActivityBaseBinding _activityBaseBinding;
    private ArrayList<CultureModel> cultureModelArrayList;
    private String[] values;

    public FeedListViewPagerModel(Context context, ActivityFeedListViewpagerBinding activityFeedListViewpagerBinding, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._binding = activityFeedListViewpagerBinding;
        this._activityBaseBinding = activityBaseBinding;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setText(values[1]);
        activityBaseBinding.mytoolbar.txtTootlbarTitle.setTextSize(13);
        setUpViewPager();
    }

    public void setUpViewPager() {
        if (!values[4].equalsIgnoreCase("null")) {
            try {
                cultureModelArrayList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(values[4]);
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
                            CultureModel cultureModel = new CultureModel(cultureid, userid, tankid, culturename, tankname,
                                    culturenumber, cultureimage, culturestatus, cultureaccess);
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
                    feedListViewPagerAdapter = new FeedListViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cultureModelArrayList, values[1], values[3]);
                    _binding.pager.setAdapter(feedListViewPagerAdapter);
                    _binding.tabLayout.setupWithViewPager(_binding.pager, true);
                    _binding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_binding.tabLayout));
                    _binding.pager.setCurrentItem(Integer.parseInt(values[0]));
                    _binding.pager.setOffscreenPageLimit(jsonArray.length());
                } else {
                    Helper.showMessage(_context, "No Data.", AquaConstants.FINISH);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Helper.showMessage(_context, "No Data", AquaConstants.FINISH);
        }
    }
}
