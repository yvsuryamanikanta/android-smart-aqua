package com.odos.smartaqua.prelogin.forgot;


import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityForgotBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForgotViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityForgotBinding _activityForgotBinding;
    private int counter;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String verificationCode;

    public ForgotViewModel(Context context, ActivityForgotBinding binding) {
        this._context = context;
        this._activityForgotBinding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;

        _activityForgotBinding.linearEnterMobile.setVisibility(View.VISIBLE);
        _activityForgotBinding.linearOtp.setVisibility(View.GONE);
    }

    public void onForgotClick(View view) {
        if (_activityForgotBinding.edtMobilenumber.getText().length() == 10) {
            _activityForgotBinding.linearEnterMobile.setVisibility(View.GONE);
            _activityForgotBinding.linearOtp.setVisibility(View.VISIBLE);

            _activityForgotBinding.countDownText.setVisibility(View.VISIBLE);
            try {
                _activityForgotBinding.phonenumberText.setText(_activityForgotBinding.edtMobilenumber.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            getOtp(view);

        } else {
            Toast.makeText(_context, "10 digit mobile number is required.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginClick(View view) {
        ((Activity) _context).finish();
    }

    public void onVerifyClick(View view) {
        verificationCode = _activityForgotBinding.pinView.getText().toString();
        if (verificationCode.isEmpty()) {
            Toast.makeText(_context, "Enter verification code", Toast.LENGTH_SHORT).show();
        } else {
            if (CheckNetwork.isNetworkAvailable(_context)) {
                VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                        ServiceConstants.VERIFY_OTP + verificationCode + "/" + _activityForgotBinding.edtMobilenumber.getText().toString(), null,
                        Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
            } else {
                Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
            }
        }
    }


    public void getOtp(View view) {
        _activityForgotBinding.countDownText.setVisibility(View.VISIBLE);
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.RESEND_OTP + _activityForgotBinding.edtMobilenumber.getText().toString(), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
        }

//        getCountDown();
    }

    private void getCountDown() {
        counter = 120;
        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                _activityForgotBinding.countDownText.setText(String.valueOf(counter));
                _activityForgotBinding.txtResend.setTextColor(_context.getResources().getColor(R.color.lightgray));
                counter--;
            }

            public void onFinish() {
                _activityForgotBinding.countDownText.setVisibility(View.GONE);
                _activityForgotBinding.txtResend.setTextColor(_context.getResources().getColor(R.color.red));
            }

        }.start();
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonObject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
//                        if (response.equalsIgnoreCase("Sucess")) {
                        getCountDown();
                    } else {
                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "" + e, AquaConstants.HOLD);
                }
                break;

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (response.equalsIgnoreCase("Sucess")) {
                            AquaConstants.putIntent(_context, ChangePasswordActivity.class, 1, new String[]{_activityForgotBinding.phonenumberText.getText().toString()});
                        } else {
                            Helper.showMessage(_context, "Invalid OTP.", AquaConstants.HOLD);
                        }
                    } else {
                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {

    }

}
