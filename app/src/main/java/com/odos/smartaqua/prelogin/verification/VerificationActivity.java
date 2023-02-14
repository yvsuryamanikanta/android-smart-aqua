package com.odos.smartaqua.prelogin.verification;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityVerificationBinding;


public class VerificationActivity extends BaseActivity {

    private ActivityVerificationBinding activityVerificationBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(VerificationActivity.this);
        activityVerificationBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_verification, activityBaseBinding.baseFragment, true);
        activityVerificationBinding.setVerificationViewModel(new VerificationViewModel(VerificationActivity.this, activityVerificationBinding));
        activityVerificationBinding.executePendingBindings();
    }
}
