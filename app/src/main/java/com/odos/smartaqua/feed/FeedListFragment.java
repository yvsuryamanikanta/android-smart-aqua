package com.odos.smartaqua.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;

public class FeedListFragment extends Fragment {
    private FragmentListFeedBinding _binding;
    private int cultureId;
    private String cultureAccess,feedDate;
    private FeedListFragmentViewModel feedListFragmentViewModel;

    public static Fragment newInstance(int cultureid, String cultureaccess,String feedDate) {
        FeedListFragment feedListFragment = new FeedListFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        bundle_data.putString("feedDate", feedDate);
        feedListFragment.setArguments(bundle_data);
        return feedListFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_feed, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            feedDate = getArguments().getString("feedDate");
            feedListFragmentViewModel = new FeedListFragmentViewModel(getActivity(), _binding, cultureId, cultureAccess,feedDate);
            _binding.setViewModel(feedListFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        feedListFragmentViewModel.loadData();
    }
}
