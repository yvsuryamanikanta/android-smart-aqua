package com.odos.smartaqua.warehouse.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentProductsBinding;
import com.odos.smartaqua.databinding.FragmentReportExpendsBinding;
import com.odos.smartaqua.expends.ExpendsReportFragmentViewModel;

public class ProductsFragment extends Fragment {
    private FragmentProductsBinding _binding;
    private int brandId;
    private ProductsFragmentViewModel fragmentViewModel;

    public static Fragment newInstance(int brandId) {
        ProductsFragment feedListFragment = new ProductsFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("brandId", brandId);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        if (getArguments() != null) {
            brandId = getArguments().getInt("brandId");
            fragmentViewModel = new ProductsFragmentViewModel(getActivity(), _binding, brandId);
            _binding.setViewModel(fragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentViewModel.loadData();
    }
}
