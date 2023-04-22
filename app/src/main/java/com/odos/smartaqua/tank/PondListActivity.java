package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityPondlistBinding;
import com.odos.smartaqua.utils.Helper;

public class PondListActivity extends BaseActivity{
    private ActivityPondlistBinding _activityPondListBinding;
    private PondListViewModel pondListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PondListActivity.this);
        _activityPondListBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_pondlist, activityBaseBinding.baseFragment, true);
        pondListViewModel = new PondListViewModel(PondListActivity.this, _activityPondListBinding);
        _activityPondListBinding.setViewModel(pondListViewModel);
        _activityPondListBinding.executePendingBindings();
        activityBaseBinding.baseFragment.getLayoutParams().height = Helper.getDisplayheight(this);

    }
}
