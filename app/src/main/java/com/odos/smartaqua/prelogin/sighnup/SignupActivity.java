package com.odos.smartaqua.prelogin.sighnup;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivitySignupBinding;
import com.odos.smartaqua.prelogin.login.LoginActivity;
import com.odos.smartaqua.prelogin.login.LoginViewModel;
import com.odos.smartaqua.utils.Helper;


public class SignupActivity extends Activity {

    private ActivitySignupBinding activitySignupBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        activitySignupBinding.setSignupViewModel(new SignupViewModel(SignupActivity.this, activitySignupBinding));
        activitySignupBinding.executePendingBindings();
    }

}
