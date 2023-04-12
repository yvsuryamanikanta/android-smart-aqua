package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityPreparationlistBinding;

public class PreparationListActivity extends BaseActivity{
    private ActivityPreparationlistBinding _activityPreparationListBinding;
    private PreparationListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(PreparationListActivity.this);
        _activityPreparationListBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_preparationlist, activityBaseBinding.baseFragment, true);
        listViewModel = new PreparationListViewModel(PreparationListActivity.this, _activityPreparationListBinding);
        _activityPreparationListBinding.setViewModel(listViewModel);
        _activityPreparationListBinding.executePendingBindings();

    }
}
