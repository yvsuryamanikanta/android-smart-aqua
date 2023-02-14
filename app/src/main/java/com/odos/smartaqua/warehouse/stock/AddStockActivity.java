package com.odos.smartaqua.warehouse.stock;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.databinding.DataBindingUtil;
import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddstockBinding;

public class AddStockActivity extends BaseActivity {
    private ActivityAddstockBinding _binding;
    public boolean image_flag;
    private AddStockViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddStockActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_addstock, activityBaseBinding.baseFragment, true);
        viewModel = new AddStockViewModel(AddStockActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        image_flag = false;
    }
}
