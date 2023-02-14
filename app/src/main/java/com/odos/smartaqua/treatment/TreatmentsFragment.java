package com.odos.smartaqua.treatment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentListTraetmentBinding;
import com.odos.smartaqua.feed.FeedListFragmentViewModel;

public class TreatmentsFragment extends Fragment {
    private FragmentListTraetmentBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private TreatmentFragmentViewModel treatmentFragmentViewModel;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        TreatmentsFragment feedListFragment = new TreatmentsFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_traetment, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            treatmentFragmentViewModel = new TreatmentFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess);
            _binding.setViewModel(treatmentFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        treatmentFragmentViewModel.loadData();
    }
}
