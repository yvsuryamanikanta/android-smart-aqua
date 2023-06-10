package com.odos.smartaqua.growth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityGrowthReportViewpagerBinding;

public class GrowthReportViewPagerActivity extends BaseActivity {
    private ActivityGrowthReportViewpagerBinding _binding;
    private GrowthReportPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(GrowthReportViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_growth_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new GrowthReportPagerModel(GrowthReportViewPagerActivity.this, _binding, activityBaseBinding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUpViewPager();
    }
}
