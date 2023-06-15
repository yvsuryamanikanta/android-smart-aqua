package com.odos.smartaqua.animal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAnimalReportViewpagerBinding;

public class AnimalViewPagerActivity extends BaseActivity {
    private ActivityAnimalReportViewpagerBinding _binding;
    private AnimalReportPagerModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AnimalViewPagerActivity.this);
        _binding = DataBindingUtil.inflate(mInflater, R.layout.activity_animal_report_viewpager, activityBaseBinding.baseFragment, true);
        viewModel = new AnimalReportPagerModel(AnimalViewPagerActivity.this, _binding, activityBaseBinding);
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
