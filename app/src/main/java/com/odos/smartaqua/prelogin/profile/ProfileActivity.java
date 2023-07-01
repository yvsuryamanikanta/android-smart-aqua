package com.odos.smartaqua.prelogin.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityProfileBinding;


public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(ProfileActivity.this, R.layout.activity_profile);
        LayoutInflater mInflater = LayoutInflater.from(ProfileActivity.this);
        binding = DataBindingUtil.inflate(mInflater, R.layout.activity_profile, activityBaseBinding.baseFragment, true);

        binding.setViewModel(new ProfileViewModel(ProfileActivity.this, binding));
        binding.executePendingBindings();

        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);

    }

}
