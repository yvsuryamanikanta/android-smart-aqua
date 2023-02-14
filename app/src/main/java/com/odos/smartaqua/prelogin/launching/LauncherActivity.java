package com.odos.smartaqua.prelogin.launching;

import android.app.Activity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityLauncherBinding;
import com.odos.smartaqua.utils.Helper;


public class LauncherActivity extends Activity {


    private ActivityLauncherBinding activityLauncherBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLauncherBinding = DataBindingUtil.setContentView(LauncherActivity.this, R.layout.activity_launcher);
        activityLauncherBinding.setLaunchVewModel(new LaunchVewModel(LauncherActivity.this, activityLauncherBinding));
        activityLauncherBinding.executePendingBindings();
        Helper.setTypeFace(LauncherActivity.this, getString(R.string.contentfont), activityLauncherBinding.txtCopyRights);
    }
}
