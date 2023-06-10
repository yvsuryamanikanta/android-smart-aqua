package com.odos.smartaqua.checktray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityChecktrayObservationBinding;
import com.odos.smartaqua.databinding.ActivityChecktrayReportViewpagerBinding;
import com.odos.smartaqua.databinding.ActivityFeedListViewpagerBinding;
import com.odos.smartaqua.feed.FeedListViewPagerModel;

public class ChecktrayReportViewPagerActivity extends BaseActivity {
    private ActivityChecktrayReportViewpagerBinding _binding;
    private ChecktrayViewPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ChecktrayReportViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_checktray_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new ChecktrayViewPagerModel(ChecktrayReportViewPagerActivity.this, _binding, activityBaseBinding);
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
