package com.odos.smartaqua.warehouse.stock;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.BrandListViewModel;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityBrandListBinding;
import com.odos.smartaqua.databinding.ActivityStockListBinding;
import com.odos.smartaqua.utils.Helper;

public class StockListActivity extends BaseActivity {
    private ActivityStockListBinding _binding;
    private StockListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(StockListActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_stock_list, activityBaseBinding.baseFragment, true);
        viewModel = new StockListViewModel(StockListActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
    }
}
