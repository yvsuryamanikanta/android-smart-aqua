package com.odos.smartaqua.warehouse.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentListSearchBinding _binding;
    private int catgId;
    private SearchFragmentViewModel viewmodel;

    public static Fragment newInstance(int catgId) {
        SearchFragment feedListFragment = new SearchFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("catgId", catgId);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_search, container, false);
        if (getArguments() != null) {
            catgId = getArguments().getInt("catgId");
            viewmodel = new SearchFragmentViewModel(getActivity(), _binding, catgId);
            _binding.setViewModel(viewmodel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewmodel.loadData();
    }
}
