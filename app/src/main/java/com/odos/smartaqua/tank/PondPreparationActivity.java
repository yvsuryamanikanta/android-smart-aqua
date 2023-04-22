package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityPondprepareBinding;

public class PondPreparationActivity extends BaseActivity {
    private ActivityPondprepareBinding _activityPondprepareBinding;
    private PondPreparationViewModel pondPreparationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PondPreparationActivity.this);
        _activityPondprepareBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_pondprepare, activityBaseBinding.baseFragment, true);
        pondPreparationViewModel = new PondPreparationViewModel(PondPreparationActivity.this, _activityPondprepareBinding);
        _activityPondprepareBinding.setViewModel(pondPreparationViewModel);
        _activityPondprepareBinding.executePendingBindings();

    }
}
