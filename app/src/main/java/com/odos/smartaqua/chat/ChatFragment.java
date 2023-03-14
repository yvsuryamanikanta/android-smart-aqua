package com.odos.smartaqua.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {
    private FragmentChatBinding _binding;
    private int pos;
    private ChatFragmentViewModel chatFragmentViewModel;

    public static Fragment newInstance(int position) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("pos", position);
//        bundle_data.putString("", "");
        chatFragment.setArguments(bundle_data);
        return chatFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        if (getArguments() != null) {
            pos = getArguments().getInt("pos");
            chatFragmentViewModel = new ChatFragmentViewModel(getActivity(), _binding);
            _binding.setViewModel(chatFragmentViewModel);
            _binding.executePendingBindings();
        }
        return _binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        chatFragmentViewModel.loadData();
    }
}
