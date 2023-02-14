package com.odos.smartaqua.checktray;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityChecktrayObservationBinding;

public class ChecktrayObservationActivity extends BaseActivity {
    private ActivityChecktrayObservationBinding activityChecktrayObservationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ChecktrayObservationActivity.this);
        activityChecktrayObservationBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_checktray_observation, activityBaseBinding.baseFragment, true);
        activityChecktrayObservationBinding.setViewModel(new ChecktrayObservationViewModel(ChecktrayObservationActivity.this, activityChecktrayObservationBinding));
        activityChecktrayObservationBinding.executePendingBindings();

    }

}
