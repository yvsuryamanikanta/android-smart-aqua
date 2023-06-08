package com.odos.smartaqua.prelogin.forgot;

import android.app.Activity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityChangePasswordBinding;


public class ChangePasswordActivity extends Activity {

    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ChangePasswordActivity.this, R.layout.activity_change_password);
        binding.setViewModel(new ChangePasswordViewModel(ChangePasswordActivity.this, binding));
        binding.executePendingBindings();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }


}
