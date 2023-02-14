package com.odos.smartaqua.checktray;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityChecktrayInfoBinding;
import com.odos.smartaqua.databinding.ActivityFeedInfoBinding;
import com.odos.smartaqua.feed.FeedInfoViewModel;


public class ChecktrayInfoActivity extends BaseActivity {

    private ActivityChecktrayInfoBinding activityChecktrayInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ChecktrayInfoActivity.this);
        activityChecktrayInfoBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_checktray_info, activityBaseBinding.baseFragment, true);
        activityChecktrayInfoBinding.setViewModel(new ChecktrayInfoViewModel(ChecktrayInfoActivity.this, activityChecktrayInfoBinding));
        activityChecktrayInfoBinding.executePendingBindings();
    }
}
