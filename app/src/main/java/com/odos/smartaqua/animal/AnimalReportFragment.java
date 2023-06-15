package com.odos.smartaqua.animal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportAnimalBinding;

public class AnimalReportFragment extends Fragment {
    private FragmentReportAnimalBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private AnimalReportFragmentViewModel fragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        AnimalReportFragment feedListFragment = new AnimalReportFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_animal, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            fragmentViewModel = new AnimalReportFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess);
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
