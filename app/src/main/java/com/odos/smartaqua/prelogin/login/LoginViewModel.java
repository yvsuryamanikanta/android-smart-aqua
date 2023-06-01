package com.odos.smartaqua.prelogin.login;


import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.dashboard.DashBoardActivity;
import com.odos.smartaqua.databinding.ActivityLoginBinding;
import com.odos.smartaqua.prelogin.forgot.ForgotPasswordActivity;
import com.odos.smartaqua.prelogin.sighnup.SignupActivity;
import com.odos.smartaqua.shocaseview.ShowCaseActivity;
import com.odos.smartaqua.tank.AddPondActivity;
import com.odos.smartaqua.utils.ASPManager;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityLoginBinding _activityLoginBinding;
    private ServiceAsyncResponse serviceAsyncResponse;

    public LoginViewModel(Context context, ActivityLoginBinding activityLoginBinding) {
        this._context = context;
        this._activityLoginBinding = activityLoginBinding;
        ASPManager.setKey(_context, AquaConstants.TANKCOUNT, "1");
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;

        if (CheckNetwork.isNetworkAvailable(_context)) {
            boolean fcmstatus = ASPManager.getKey(_context, AquaConstants.FEC_STATUS, false);
            if (!fcmstatus) {
                String deviceVersion = Build.VERSION.RELEASE;
                String deviceName = Build.MODEL;
                String deviceBrand = Build.BRAND;
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Helper.showMessage(_context, "Your Device is not Compatable pls install app again we will resolve issue asap", AquaConstants.FINISH);
                                    return;
                                }
                                String token = task.getResult().replace(" ", "%20");
                                if (token.equalsIgnoreCase("")) {
                                    Helper.showMessage(_context, "Server problem please try again once.", AquaConstants.FINISH);
                                } else {
                                    HashMap<String, Object> postparams = new HashMap<>();
                                    postparams.put("devicename", deviceName + "-" + deviceBrand);
                                    postparams.put("deviceversion", deviceVersion);
                                    postparams.put("uniqueID", Helper.getUniqueId(_context));
                                    postparams.put("notificationid", token);
                                    postparams.put("devicetype", "A");
                                    VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                            ServiceConstants.SAVE_DEVICE, postparams, Helper.headerParams(_context),
                                            (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
                                }
                            }
                        });
            }
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }

    public void onVerifyClick(View view) {

        String mobilenumber = _activityLoginBinding.edtMobilenumber.getText().toString();
        String password = _activityLoginBinding.edtPassword.getText().toString();

        if (mobilenumber.length() != 10 && password.equalsIgnoreCase("")) {
            Toast.makeText(_context, "10 digit mobile number and Password is required", Toast.LENGTH_SHORT).show();
        } else if (mobilenumber.length() != 10) {
            Toast.makeText(_context, "10 digit mobile number is required.", Toast.LENGTH_SHORT).show();
        } else if (password.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Password is required", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("usernumber", mobilenumber);
            postparams.put("password", password);
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.USER_LOGIN, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
        }
    }

    public void onRegisterClick(View view) {
        AquaConstants.putIntent(_context, SignupActivity.class, 1, null);
    }

    public void onForgotClick(View view) {
        AquaConstants.putIntent(_context, ForgotPasswordActivity.class, 1, null);
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
                        if (!response.equalsIgnoreCase("null")) {
                            ASPManager.setKey(_context, AquaConstants.FEC_STATUS, true);
                        } else {
                            Helper.showMessage(_context, "App Id regigistration failed please close and reopen app once.", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "App Id regigistration failed please close and reopen app once.", AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "App Id regigistration failed please close and reopen app once.", AquaConstants.FINISH);
                }
                break;
            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        JSONObject jsonObject1 = new JSONObject(response);
                        String userid = jsonObject1.getString("userid");
                        String roleid = jsonObject1.getString("roleid");
                        String usernumber = jsonObject1.getString("usernumber");
                        String notificationid = jsonObject1.getString("notificationid");
                        String rolecode = jsonObject1.getString("rolecode");
                        ASPManager.setKey(_context, AquaConstants.USER_ID, userid);
                        ASPManager.setKey(_context, AquaConstants.ROLE_ID, roleid);
                        ASPManager.setKey(_context, AquaConstants.FCM_ID, usernumber);
                        ASPManager.setKey(_context, AquaConstants.USER_RESPONSE, notificationid);
                        ASPManager.setKey(_context, AquaConstants.ROLE_CODE, rolecode);
                        ASPManager.setKey(_context, AquaConstants.IS_LOGGED, true);
                        if (rolecode.equalsIgnoreCase("F")) {
                            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                    ServiceConstants.GET_TANKLIST + userid,
                                    null, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 3, false);
                        } else if (rolecode.equalsIgnoreCase("L")) {
                            // AquaConstants.claerAllActivities(_context, DashBoardActivity.class);
                        } else if (rolecode.equalsIgnoreCase("T")) {
                            //   AquaConstants.claerAllActivities(_context, DashBoardActivity.class);
                        }
                    } else {
                        Helper.showMessage(_context, "" + response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
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
                            AquaConstants.claerAllActivities(_context, DashBoardActivity.class,null);
                        } else {
                            AquaConstants.claerAllActivities(_context, AddPondActivity.class, new String[]{"0"});
                        }
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.getMessage(), AquaConstants.FINISH);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {
    }
}