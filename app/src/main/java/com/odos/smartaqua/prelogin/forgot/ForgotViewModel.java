package com.odos.smartaqua.prelogin.forgot;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.databinding.ActivityForgotBinding;

public class ForgotViewModel extends ViewModel {

    private Context _context;
    private ActivityForgotBinding _activityForgotBinding;

    public ForgotViewModel(Context context, ActivityForgotBinding activityForgotBinding) {
        this._context = context;
        this._activityForgotBinding = activityForgotBinding;
    }

    public void onForgotClick(View view) {
        if (_activityForgotBinding.edtMobilenumber.getText().length() == 10) {
            //AquaConstants.putIntent(_context, DashBoardActivity.class, 1, null);
        } else {
            Toast.makeText(_context, "10 digit mobile number is required.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginClick(View view) {
        ((Activity) _context).finish();
    }
}
