package com.odos.smartaqua.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityLabReportViewpagerBinding;
import com.odos.smartaqua.lab.LabReportPagerModel;

public class WaterAnalysisActivity extends BaseActivity {
    private ActivityLabReportViewpagerBinding _binding;
    private LabReportPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(WaterAnalysisActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_lab_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new LabReportPagerModel(WaterAnalysisActivity.this, _binding, activityBaseBinding);
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
