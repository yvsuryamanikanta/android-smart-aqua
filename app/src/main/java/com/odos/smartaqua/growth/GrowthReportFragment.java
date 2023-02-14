package com.odos.smartaqua.growth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentReportGrowthBinding;
import com.odos.smartaqua.feed.FeedListFragmentViewModel;

public class GrowthReportFragment extends Fragment {
    private FragmentReportGrowthBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private GrowthReportFragmentViewModel growthReportFragmentViewModel;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        GrowthReportFragment feedListFragment = new GrowthReportFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_growth, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            growthReportFragmentViewModel = new GrowthReportFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess);
            _binding.setViewModel(growthReportFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        growthReportFragmentViewModel.loadData();
    }
}
