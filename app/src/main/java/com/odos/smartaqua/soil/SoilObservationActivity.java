package com.odos.smartaqua.soil;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityObservationSoilBinding;

public class SoilObservationActivity extends BaseActivity {
    private ActivityObservationSoilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(SoilObservationActivity.this);
        binding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_soil, activityBaseBinding.baseFragment, true);
        binding.setViewModel(new SoilObservationViewModel(SoilObservationActivity.this, binding));
        binding.executePendingBindings();

    }

}
