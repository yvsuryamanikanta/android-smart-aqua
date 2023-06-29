package com.odos.smartaqua.brand;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityBrandListBinding;
import com.odos.smartaqua.utils.Helper;

public class BrandListActivity extends BaseActivity {
    private ActivityBrandListBinding _binding;
    private BrandListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(BrandListActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_brand_list, activityBaseBinding.baseFragment, true);
        viewModel = new BrandListViewModel(BrandListActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
    }
}
