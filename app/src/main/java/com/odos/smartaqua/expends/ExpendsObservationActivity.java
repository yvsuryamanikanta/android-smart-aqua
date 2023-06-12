package com.odos.smartaqua.expends;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityObservationExpendsBinding;

public class ExpendsObservationActivity extends BaseActivity {
    private ActivityObservationExpendsBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ExpendsObservationActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_expends, activityBaseBinding.baseFragment, true);
        _binding.setViewModel(new ExpendsObservationViewModel(ExpendsObservationActivity.this, _binding));
        _binding.executePendingBindings();

    }

}
