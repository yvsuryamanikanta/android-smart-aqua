package com.odos.smartaqua.animal;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityObservationAnimalBinding;

public class AnimalObservationActivity extends BaseActivity {
    private ActivityObservationAnimalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AnimalObservationActivity.this);
        binding = DataBindingUtil.inflate(mInflater, R.layout.activity_observation_animal, activityBaseBinding.baseFragment, true);
        binding.setViewModel(new AnimalObservationViewModel(AnimalObservationActivity.this, binding));
        binding.executePendingBindings();

    }

}
