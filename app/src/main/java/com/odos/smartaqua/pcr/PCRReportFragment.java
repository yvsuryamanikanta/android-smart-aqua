package com.odos.smartaqua.pcr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportPcrBinding;

public class PCRReportFragment extends Fragment {
    private FragmentReportPcrBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private PCRReportFragmentViewModel fragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        PCRReportFragment feedListFragment = new PCRReportFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_pcr, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            fragmentViewModel = new PCRReportFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess);
            _binding.setViewModel(fragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            fragmentViewModel.loadData();
            isLoaded = true;
        }
    }
}
