package com.odos.smartaqua.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityDashboardViewpagerBinding;
import com.odos.smartaqua.utils.Helper;

public class DashBoardViewPagerActivity extends BaseActivity {
    private ActivityDashboardViewpagerBinding activityDashboardViewpagerBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(DashBoardViewPagerActivity.this);
        activityDashboardViewpagerBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_dashboard_viewpager, activityBaseBinding.baseFragment, true);
        activityDashboardViewpagerBinding.setDashBoardViewPagerModel(new DashBoardViewPagerModel(DashBoardViewPagerActivity.this, activityDashboardViewpagerBinding, activityBaseBinding));
        activityDashboardViewpagerBinding.executePendingBindings();


        setToolBarIconClick(1);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
        if (Helper.getUsrtType(this).equalsIgnoreCase("F")) {
            activityBaseBinding.mytoolbar.txtAddOption.setVisibility(View.VISIBLE);
        } else {
            activityBaseBinding.mytoolbar.txtAddOption.setVisibility(View.INVISIBLE);
        }

    }
}
