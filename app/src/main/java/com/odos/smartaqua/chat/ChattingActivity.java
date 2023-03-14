package com.odos.smartaqua.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityChattingBinding;

public class ChattingActivity extends AppCompatActivity {
    private ActivityChattingBinding _activityChatBinding;
    private ChattingViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activityChatBinding = DataBindingUtil.setContentView(ChattingActivity.this, R.layout.activity_chatting);
        chatViewModel = new ChattingViewModel(ChattingActivity.this, _activityChatBinding);
        _activityChatBinding.setViewModel(chatViewModel);
        _activityChatBinding.executePendingBindings();

    }

}
