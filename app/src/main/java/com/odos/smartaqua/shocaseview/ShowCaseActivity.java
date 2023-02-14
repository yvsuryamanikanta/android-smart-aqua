package com.odos.smartaqua.shocaseview;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityShowcaseBinding;
import com.odos.smartaqua.utils.Helper;


public class ShowCaseActivity extends BaseActivity {
    private ActivityShowcaseBinding activityShowcaseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ShowCaseActivity.this);
        activityShowcaseBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_showcase, activityBaseBinding.baseFragment, true);
        activityBaseBinding.baseFragment.getLayoutParams().height = Helper.getDisplayheight(this);
        activityShowcaseBinding.setShowcaseViewModel(new ShowcaseViewModel((ShowCaseActivity.this), activityShowcaseBinding));
        activityShowcaseBinding.executePendingBindings();


    }


}

