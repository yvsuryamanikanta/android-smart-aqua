package com.odos.smartaqua.tank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentDashboardBinding;
import com.odos.smartaqua.databinding.FragmentPondlistBinding;

public class PondListFragment extends Fragment {
    private FragmentPondlistBinding _binding;
    private int pos;
    private String tankId, cultureId, tankName, cultureResponse;
    private PondListFragmentViewModel fragmentViewModel;
    private boolean isLoaded = false;

    public static Fragment newInstance(int position, String tankId, String cultureId, String tankName, String response) {
        PondListFragment chatFragment = new PondListFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("pos", position);
        bundle_data.putString("tankId", tankId);
        bundle_data.putString("cultureId", cultureId);
        bundle_data.putString("tankName", tankName);
        bundle_data.putString("cultureResponse", response);
        chatFragment.setArguments(bundle_data);
        return chatFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pondlist, container, false);
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            tankId = getArguments().getString("tankId");
            cultureId = getArguments().getString("cultureId");
            tankName = getArguments().getString("tankName");
            cultureResponse = getArguments().getString("cultureResponse");
            fragmentViewModel = new PondListFragmentViewModel(pos, getActivity(), _binding, tankId, cultureId, tankName,cultureResponse);
            _binding.setViewModel(fragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
