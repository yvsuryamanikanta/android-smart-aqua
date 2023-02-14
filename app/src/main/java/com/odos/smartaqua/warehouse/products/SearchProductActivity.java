package com.odos.smartaqua.warehouse.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivitySearchProductBinding;
import com.odos.smartaqua.utils.Helper;


public class SearchProductActivity extends BaseActivity {

    private ActivitySearchProductBinding activitySearchProductBinding;
    private SearchFeedViewModel searchFeedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(SearchProductActivity.this);
        activitySearchProductBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_search_product, activityBaseBinding.baseFragment, true);
        activityBaseBinding.mytoolbar.txtAddOption.setVisibility(View.INVISIBLE);
        searchFeedViewModel = new SearchFeedViewModel(SearchProductActivity.this, activitySearchProductBinding);
        activitySearchProductBinding.setViewModel(searchFeedViewModel);
        activitySearchProductBinding.executePendingBindings();
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
        activityBaseBinding.baseFragment.getLayoutParams().height = Helper.getDisplayheight(this);
        setToolBarIconClick(1);
    }
}