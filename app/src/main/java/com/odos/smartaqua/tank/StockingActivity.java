package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddstockBinding;
import com.odos.smartaqua.databinding.ActivityStockingBinding;
import com.odos.smartaqua.warehouse.stock.AddStockViewModel;

public class StockingActivity extends BaseActivity {
    private ActivityStockingBinding _binding;
    public boolean image_flag;
    private StockingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(StockingActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_stocking, activityBaseBinding.baseFragment, true);
        viewModel = new StockingViewModel(StockingActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        image_flag = false;
    }
}
