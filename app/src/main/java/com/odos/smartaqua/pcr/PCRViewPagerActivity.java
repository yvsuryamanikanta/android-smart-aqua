package com.odos.smartaqua.pcr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityPcrReportViewpagerBinding;
import com.odos.smartaqua.databinding.ActivityWaterAnalysisReportViewpagerBinding;
import com.odos.smartaqua.water.WaterReportPagerModel;

public class PCRViewPagerActivity extends BaseActivity {
    private ActivityPcrReportViewpagerBinding _binding;
    private PCRReportPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PCRViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_pcr_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new PCRReportPagerModel(PCRViewPagerActivity.this, _binding, activityBaseBinding);
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
