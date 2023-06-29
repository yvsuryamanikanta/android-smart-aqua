package com.odos.smartaqua.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportWaterBinding;

public class WaterReportFragment extends Fragment {
    private FragmentReportWaterBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private String tankName;
    private WaterReportFragmentViewModel waterReportFragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int cultureid, String cultureaccess, String tankName) {
        WaterReportFragment feedListFragment = new WaterReportFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        bundle_data.putString("tankName", tankName);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_water, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            tankName = getArguments().getString("tankName");
            waterReportFragmentViewModel = new WaterReportFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess,tankName);
            _binding.setViewModel(waterReportFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            waterReportFragmentViewModel.loadData();
            isLoaded = true;
        }
    }
}
