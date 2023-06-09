package com.odos.smartaqua.lab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentReportLabBinding;
import com.odos.smartaqua.feed.FeedListFragmentViewModel;

public class LabReportFragment extends Fragment {
    private FragmentReportLabBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private LabReportFragmentViewModel labReportFragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        LabReportFragment feedListFragment = new LabReportFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_lab, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            labReportFragmentViewModel = new LabReportFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess);
            _binding.setViewModel(labReportFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            labReportFragmentViewModel.loadData();
            isLoaded = true;
        }
    }
}
