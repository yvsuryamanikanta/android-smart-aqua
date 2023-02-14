package com.odos.smartaqua.prelogin.forgot;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.databinding.ActivityForgotBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;

public class ForgotViewModel extends ViewModel {

    private Context _context;
    private ActivityForgotBinding _activityForgotBinding;

    public ForgotViewModel(Context context, ActivityForgotBinding activityForgotBinding) {
        this._context = context;
        this._activityForgotBinding = activityForgotBinding;
    }


    public void onForgotClick(View view) {
        if (isInputDataValid()) {
            //AquaConstants.putIntent(_context, DashBoardActivity.class, 1, null);
        } else {
            _activityForgotBinding.edtMobilenumber.setError("10 digit mobile number is required.");
        }
    }
    public void onLoginClick(View view) {
        ((Activity)_context).finish();
    }


    public String getuserMobileNumber() {
        return _activityForgotBinding.edtMobilenumber.getText().toString();
    }


    public boolean isInputDataValid() {
        return getuserMobileNumber().length() != 10;
    }
}
