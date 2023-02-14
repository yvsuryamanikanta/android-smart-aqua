package com.odos.smartaqua.warehouse.products;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddproductCatgBinding;

public class AddProductCatgActivity extends BaseActivity{
    private ActivityAddproductCatgBinding _binding;
    private AddProductCatgViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddProductCatgActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_addproduct_catg, activityBaseBinding.baseFragment, true);
        viewModel = new AddProductCatgViewModel(AddProductCatgActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
    }
}
