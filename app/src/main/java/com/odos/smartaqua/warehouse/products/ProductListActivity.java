package com.odos.smartaqua.warehouse.products;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.BrandListViewModel;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityBrandListBinding;
import com.odos.smartaqua.databinding.ActivityProductListBinding;
import com.odos.smartaqua.utils.Helper;

public class ProductListActivity extends BaseActivity {
    private ActivityProductListBinding _binding;
    private ProductListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ProductListActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_product_list, activityBaseBinding.baseFragment, true);
        viewModel = new ProductListViewModel(ProductListActivity.this, _binding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        activityBaseBinding.baseFragment.getLayoutParams().height = Helper.getDisplayheight(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUpViewPager();
    }

}
