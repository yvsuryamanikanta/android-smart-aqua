package com.odos.smartaqua.prelogin.forgot;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityForgotBinding;
import com.odos.smartaqua.prelogin.login.LoginActivity;
import com.odos.smartaqua.prelogin.login.LoginViewModel;


public class ForgotPasswordActivity extends Activity {

    private ActivityForgotBinding activityForgotBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotBinding = DataBindingUtil.setContentView(ForgotPasswordActivity.this, R.layout.activity_forgot);
        activityForgotBinding.setForgotViewModel(new ForgotViewModel(ForgotPasswordActivity.this, activityForgotBinding));
        activityForgotBinding.executePendingBindings();
    }

}
