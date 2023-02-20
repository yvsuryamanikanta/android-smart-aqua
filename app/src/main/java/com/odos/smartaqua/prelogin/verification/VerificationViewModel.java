package com.odos.smartaqua.prelogin.verification;


import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.dashboard.DashBoardActivity;
import com.odos.smartaqua.databinding.ActivityVerificationBinding;
import com.odos.smartaqua.shocaseview.ShowCaseActivity;
import com.odos.smartaqua.tank.AddPondActivity;
import com.odos.smartaqua.utils.ASPManager;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerificationViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityVerificationBinding _activityVerificationBinding;
    private int counter;
    private String[] values;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String verificationCode;

    public VerificationViewModel(Context context, ActivityVerificationBinding activityVerificationBinding) {
        this._context = context;
        this._activityVerificationBinding = activityVerificationBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        _activityVerificationBinding.countDownText.setVisibility(View.VISIBLE);
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        if (values != null) {
            try {
                _activityVerificationBinding.phonenumberText.setText(values[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getCountDown();
    }

    public void onVerifyClick(View view) {
        verificationCode = _activityVerificationBinding.pinView.getText().toString();
        if (verificationCode.isEmpty()) {
            Toast.makeText(_context, "Enter verification code", Toast.LENGTH_SHORT).show();
        } else {
            if (CheckNetwork.isNetworkAvailable(_context)) {
                VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                        ServiceConstants.VERIFY_OTP + verificationCode + "/" + values[2], null,
                        Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
            } else {
                Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
            }
        }
    }


    public void onResendClick(View view) {
        _activityVerificationBinding.countDownText.setVisibility(View.VISIBLE);
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.RESEND_OTP + values[2], null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
        }

        //getCountDown();
    }

    private void getCountDown() {
        counter = 120;
        new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                _activityVerificationBinding.countDownText.setText(String.valueOf(counter));
                _activityVerificationBinding.txtResend.setTextColor(_context.getResources().getColor(R.color.lightgray));
                counter--;
            }

            public void onFinish() {
                _activityVerificationBinding.countDownText.setVisibility(View.GONE);
                _activityVerificationBinding.txtResend.setTextColor(_context.getResources().getColor(R.color.red));
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
                        if (response.equalsIgnoreCase("Sucess")) {
                            getCountDown();
                        } else {
                            Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                        }
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
                            ASPManager.setKey(_context, AquaConstants.USER_ID, values[0]);
                            ASPManager.setKey(_context, AquaConstants.ROLE_ID, values[1]);
                            ASPManager.setKey(_context, AquaConstants.FCM_ID, values[3]);
                            ASPManager.setKey(_context, AquaConstants.USER_RESPONSE, values[4]);
                            ASPManager.setKey(_context, AquaConstants.ROLE_CODE, values[5]);
                            ASPManager.setKey(_context, AquaConstants.IS_LOGGED, true);
                            if (values[5].equalsIgnoreCase("F")) {
                                VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                        ServiceConstants.GET_TANKLIST + values[0],
                                        null, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 3, false);
                            } else if (values[5].equalsIgnoreCase("L")) {
                                AquaConstants.claerAllActivities(_context, DashBoardActivity.class, null);
                            } else if (values[5].equalsIgnoreCase("T")) {
                                AquaConstants.claerAllActivities(_context, DashBoardActivity.class, null);
                            }
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

            case 3:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() != 0) {
                            AquaConstants.claerAllActivities(_context, DashBoardActivity.class, null);
                        } else {
                            AquaConstants.claerAllActivities(_context, AddPondActivity.class, new String[]{"0"});
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.getMessage(), AquaConstants.FINISH);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {

    }
}
