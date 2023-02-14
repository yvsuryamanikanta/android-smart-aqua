package com.odos.smartaqua.checktray;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddChecktrayBinding;

public class AddChecktrayActivity extends BaseActivity {
    private ActivityAddChecktrayBinding activityAddcultureBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddChecktrayActivity.this);
        activityAddcultureBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_add_checktray, activityBaseBinding.baseFragment, true);
        activityAddcultureBinding.setViewModel(new ChecktrayViewModel(AddChecktrayActivity.this, activityAddcultureBinding));
        activityAddcultureBinding.executePendingBindings();

    }

}
