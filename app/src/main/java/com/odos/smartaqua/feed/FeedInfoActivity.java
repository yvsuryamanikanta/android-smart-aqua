package com.odos.smartaqua.feed;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddFeedBinding;
import com.odos.smartaqua.databinding.ActivityFeedInfoBinding;
import com.odos.smartaqua.databinding.ActivityFeedInfoNewBinding;


public class FeedInfoActivity extends BaseActivity {

    private ActivityFeedInfoNewBinding activityFeedInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(FeedInfoActivity.this);
        activityFeedInfoBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_feed_info_new, activityBaseBinding.baseFragment, true);
        activityFeedInfoBinding.setViewModel(new FeedInfoViewModelNew(FeedInfoActivity.this, activityFeedInfoBinding));
        activityFeedInfoBinding.executePendingBindings();
    }
}
