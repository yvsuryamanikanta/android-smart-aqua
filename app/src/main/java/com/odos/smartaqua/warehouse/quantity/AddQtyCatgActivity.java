package com.odos.smartaqua.warehouse.quantity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddproductCatgBinding;
import com.odos.smartaqua.databinding.ActivityAddqtyCatgBinding;
import com.odos.smartaqua.warehouse.products.AddProductCatgViewModel;

public class AddQtyCatgActivity extends BaseActivity{
    private ActivityAddqtyCatgBinding _binding;
    private AddQtyCatgViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddQtyCatgActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_addqty_catg, activityBaseBinding.baseFragment, true);
        viewModel = new AddQtyCatgViewModel(AddQtyCatgActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
    }
}
