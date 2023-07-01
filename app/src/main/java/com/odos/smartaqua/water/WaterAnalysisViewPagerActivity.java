package com.odos.smartaqua.water;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityWaterAnalysisReportViewpagerBinding;

public class WaterAnalysisViewPagerActivity extends BaseActivity {
    private ActivityWaterAnalysisReportViewpagerBinding _binding;
    private WaterReportPagerModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(WaterAnalysisViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_water_analysis_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new WaterReportPagerModel(WaterAnalysisViewPagerActivity.this, _binding, activityBaseBinding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        setToolBarIconClick(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUpViewPager();
    }
}
