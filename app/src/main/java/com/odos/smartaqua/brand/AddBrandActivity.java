package com.odos.smartaqua.brand;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddbrandBinding;
import com.odos.smartaqua.databinding.ActivityAddproductCatgBinding;
import com.odos.smartaqua.warehouse.products.AddProductCatgViewModel;

public class AddBrandActivity extends BaseActivity{
    private ActivityAddbrandBinding _binding;
    private AddBrandViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddBrandActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_addbrand, activityBaseBinding.baseFragment, true);
        viewModel = new AddBrandViewModel(AddBrandActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
    }
}
