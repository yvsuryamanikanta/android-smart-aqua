package com.odos.smartaqua.shocaseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentShowBinding;


public class ShowFragment extends Fragment {

    private FragmentShowBinding fragmentShowBinding;

    public static Fragment newInstance() {
        return new ShowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentShowBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_show, container, false);
        fragmentShowBinding.setShowViewModel(new ShowViewModel(getActivity(), fragmentShowBinding));
        fragmentShowBinding.executePendingBindings();
        return fragmentShowBinding.getRoot();
    }
}
