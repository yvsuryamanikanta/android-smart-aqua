package com.odos.smartaqua.dashboard;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.databinding.ActivityDashboardViewpagerBinding;
import com.odos.smartaqua.utils.AquaConstants;


public class DashBoardViewPagerModel extends BaseObservable {
    private DashBoardViewPagerAdapter daDashBoardViewPagerAdapter;
    private Context _context;
    private ActivityDashboardViewpagerBinding _activityDashboardViewpagerBinding;
    private ActivityBaseBinding _activityBaseBinding;
    private int tabPosition;
    private String[] titles;


    public DashBoardViewPagerModel(Context context, ActivityDashboardViewpagerBinding activityDashboardViewpagerBinding, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._activityDashboardViewpagerBinding = activityDashboardViewpagerBinding;
        this._activityBaseBinding = activityBaseBinding;
        setUpViewPager();
    }

    private void setUpViewPager() {
        titles = ((Activity) _context).getIntent().getStringArrayExtra("values");
        tabPosition = ((Activity) _context).getIntent().getIntExtra("position", 0);
        String toolbarOption = "Add New";
        _activityBaseBinding.mytoolbar.txtAddOption.setText(toolbarOption);
        _activityBaseBinding.mytoolbar.txtAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToolBaroption(tabPosition);
            }
        });
        if (titles.length < 4) {
            _activityDashboardViewpagerBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        } else {
            _activityDashboardViewpagerBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        daDashBoardViewPagerAdapter = new DashBoardViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, titles);
        _activityDashboardViewpagerBinding.pager.setAdapter(daDashBoardViewPagerAdapter);
        _activityDashboardViewpagerBinding.tabLayout.setupWithViewPager(_activityDashboardViewpagerBinding.pager, true);
        _activityDashboardViewpagerBinding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_activityDashboardViewpagerBinding.tabLayout));
        _activityDashboardViewpagerBinding.pager.setCurrentItem(tabPosition);
        _activityDashboardViewpagerBinding.pager.setOffscreenPageLimit(titles.length);
        _activityDashboardViewpagerBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });
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


}
