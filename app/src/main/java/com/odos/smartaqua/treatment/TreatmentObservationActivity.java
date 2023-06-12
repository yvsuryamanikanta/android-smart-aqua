package com.odos.smartaqua.treatment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityObservationTreatmentBinding;

public class TreatmentObservationActivity extends BaseActivity {
    private ActivityObservationTreatmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(TreatmentObservationActivity.this);
        binding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_treatment, activityBaseBinding.baseFragment, true);
        binding.setViewModel(new TreatmentObservationViewModel(TreatmentObservationActivity.this, binding));
        binding.executePendingBindings();

    }

}
