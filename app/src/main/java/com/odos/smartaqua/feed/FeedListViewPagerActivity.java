package com.odos.smartaqua.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityFeedListViewpagerBinding;

public class FeedListViewPagerActivity extends BaseActivity {
    private ActivityFeedListViewpagerBinding _binding;
    private FeedListViewPagerModel feedListViewPagerModel;
    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(FeedListViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_feed_list_viewpager, activityBaseBinding.baseFragment, true);
        feedListViewPagerModel = new FeedListViewPagerModel(FeedListViewPagerActivity.this, _binding, activityBaseBinding);
        _binding.setViewModel(feedListViewPagerModel);
        _binding.executePendingBindings();
        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoaded) {
            feedListViewPagerModel.setUpViewPager();
            isLoaded = true;
        }
    }
}
