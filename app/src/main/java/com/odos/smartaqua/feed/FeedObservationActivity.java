package com.odos.smartaqua.feed;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.checktray.ChecktrayViewModel;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddChecktrayBinding;
import com.odos.smartaqua.databinding.ActivityFeedObservationBinding;

public class FeedObservationActivity extends BaseActivity {
    private ActivityFeedObservationBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(FeedObservationActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_feed_observation, activityBaseBinding.baseFragment, true);
        _binding.setViewModel(new FeedObservationViewModel(FeedObservationActivity.this, _binding));
        _binding.executePendingBindings();

    }

}
