package com.odos.smartaqua.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityTankViewpagerBinding;

public class TankViewPagerActivity extends BaseActivity {
    private ActivityTankViewpagerBinding _binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(TankViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_tank_viewpager, activityBaseBinding.baseFragment, true);
        _binding.setViewModel(new TankViewPagerModel(TankViewPagerActivity.this, _binding, activityBaseBinding));
        _binding.executePendingBindings();
        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
    }
}
