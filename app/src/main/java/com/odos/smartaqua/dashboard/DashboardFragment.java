package com.odos.smartaqua.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding _binding;
    private int pos;
    private DashboardFragmentViewModel chatFragmentViewModel;

    public static Fragment newInstance(int position) {
        DashboardFragment chatFragment = new DashboardFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("pos", position);
//        bundle_data.putString("", "");
        chatFragment.setArguments(bundle_data);
        return chatFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            chatFragmentViewModel = new DashboardFragmentViewModel(pos, getActivity(), _binding);
            _binding.setViewModel(chatFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        chatFragmentViewModel.loadCultures();
    }
}
