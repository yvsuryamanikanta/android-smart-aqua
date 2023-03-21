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
    private String tankId,cultureId,tankName;
    private DashboardFragmentViewModel chatFragmentViewModel;

    public static Fragment newInstance(int position,String tankId,String cultureId,String tankName) {
        DashboardFragment chatFragment = new DashboardFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("pos", position);
        bundle_data.putString("tankId", tankId);
        bundle_data.putString("cultureId", cultureId);
        bundle_data.putString("tankName", tankName);
        chatFragment.setArguments(bundle_data);
        return chatFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            tankId = getArguments().getString("tankId");
            cultureId = getArguments().getString("cultureId");
            tankName = getArguments().getString("tankName");
            chatFragmentViewModel = new DashboardFragmentViewModel(pos, getActivity(), _binding,tankId,cultureId,tankName);
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
