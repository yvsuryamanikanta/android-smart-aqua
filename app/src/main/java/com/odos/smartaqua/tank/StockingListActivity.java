package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityStockinglistBinding;

public class StockingListActivity extends BaseActivity{
    private ActivityStockinglistBinding _binding;
    private StockingListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(StockingListActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_stockinglist, activityBaseBinding.baseFragment, true);
        listViewModel = new StockingListViewModel(StockingListActivity.this, _binding);
        _binding.setViewModel(listViewModel);
        _binding.executePendingBindings();

    }
}
