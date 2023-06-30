package com.odos.smartaqua.warehouse.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.SearchViewpagerBinding;

public class SearchViewPagerActivity extends BaseActivity {
    private SearchViewpagerBinding _binding;
    private SearchPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(SearchViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.search_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new SearchPagerModel(SearchViewPagerActivity.this, _binding, activityBaseBinding);
        _binding.setViewModel(viewModel);
        _binding.executePendingBindings();
        setToolBarIconClick(0);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUpViewPager();
    }
}
