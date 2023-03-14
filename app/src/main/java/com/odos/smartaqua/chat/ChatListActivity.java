package com.odos.smartaqua.chat;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityChatListBinding;

public class ChatListActivity extends BaseActivity{
    private ActivityChatListBinding _activityChatBinding;
    private ChatListViewModel chatListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(ChatListActivity.this);
        _activityChatBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_chat_list, activityBaseBinding.baseFragment, true);
        chatListViewModel = new ChatListViewModel(ChatListActivity.this, _activityChatBinding);
        _activityChatBinding.setViewModel(chatListViewModel);
        _activityChatBinding.executePendingBindings();

    }

}
