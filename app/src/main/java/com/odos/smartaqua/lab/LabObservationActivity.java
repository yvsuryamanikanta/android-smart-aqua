package com.odos.smartaqua.lab;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityLabObservationBinding;

public class LabObservationActivity extends BaseActivity {
    private ActivityLabObservationBinding activityLabObservationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(LabObservationActivity.this);
        activityLabObservationBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_lab_observation, activityBaseBinding.baseFragment, true);
        activityLabObservationBinding.setViewModel(new LabObservationViewModel(LabObservationActivity.this, activityLabObservationBinding));
        activityLabObservationBinding.executePendingBindings();

    }

}
