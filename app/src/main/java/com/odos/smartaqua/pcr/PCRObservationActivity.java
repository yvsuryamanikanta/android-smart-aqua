package com.odos.smartaqua.pcr;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityObservationPcrBinding;

public class PCRObservationActivity extends BaseActivity {
    private ActivityObservationPcrBinding activityAddcultureBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PCRObservationActivity.this);
        activityAddcultureBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_pcr, activityBaseBinding.baseFragment, true);
        activityAddcultureBinding.setViewModel(new PCRObservationViewModel(PCRObservationActivity.this, activityAddcultureBinding));
        activityAddcultureBinding.executePendingBindings();

    }

}
