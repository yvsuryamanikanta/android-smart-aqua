package com.odos.smartaqua.expends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityExpendsReportViewpagerBinding;

public class ExpendsReportViewPagerActivity extends BaseActivity {
    private ActivityExpendsReportViewpagerBinding _binding;
    private ExpendsReportPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ExpendsReportViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_expends_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new ExpendsReportPagerModel(ExpendsReportViewPagerActivity.this, _binding, activityBaseBinding);
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
