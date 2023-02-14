package com.odos.smartaqua.growth;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.checktray.ChecktrayViewModel;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddChecktrayBinding;
import com.odos.smartaqua.databinding.ActivityObservationGrowthBinding;

public class GrowthObservationActivity extends BaseActivity {
    private ActivityObservationGrowthBinding activityAddcultureBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(GrowthObservationActivity.this);
        activityAddcultureBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_growth, activityBaseBinding.baseFragment, true);
        activityAddcultureBinding.setViewModel(new GrowthObservationViewModel(GrowthObservationActivity.this, activityAddcultureBinding));
        activityAddcultureBinding.executePendingBindings();

    }

}
