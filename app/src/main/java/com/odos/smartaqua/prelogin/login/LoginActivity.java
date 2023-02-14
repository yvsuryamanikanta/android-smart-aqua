package com.odos.smartaqua.prelogin.login;

import android.app.Activity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityLoginBinding;


public class LoginActivity extends Activity {

    private ActivityLoginBinding activityLoginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        activityLoginBinding.setLoginViewModel(new LoginViewModel(LoginActivity.this, activityLoginBinding));
        activityLoginBinding.executePendingBindings();
    }

}
