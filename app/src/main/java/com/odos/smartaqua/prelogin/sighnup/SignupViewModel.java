package com.odos.smartaqua.prelogin.sighnup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivitySignupBinding;
import com.odos.smartaqua.prelogin.verification.VerificationActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivitySignupBinding _activitySignupBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int userRoleId;
    private String userRoleCode;

    public SignupViewModel(Context context, ActivitySignupBinding activitySignupBinding) {
        this._context = context;
        this._activitySignupBinding = activitySignupBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_ROLES, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }


    public void onLoginClick() {
        ((Activity) _context).finish();
    }

    public void onSignupClick() {
        String name = _activitySignupBinding.edtName.getText().toString();
        String email = _activitySignupBinding.edtEmail.getText().toString();
        String mobilenumber = _activitySignupBinding.edtMobileNumber.getText().toString();
        String password = _activitySignupBinding.edtPassword.getText().toString();

        if (name.equalsIgnoreCase("") && mobilenumber.equalsIgnoreCase("")
                && userRoleId == 777 & password.equalsIgnoreCase("") ) {
            Toast.makeText(_context, "user details are required", Toast.LENGTH_SHORT).show();
        } else if (name.equalsIgnoreCase("User Name is required")) {
            Toast.makeText(_context, "", Toast.LENGTH_SHORT).show();
        } else if (mobilenumber.length() != 10) {
            Toast.makeText(_context, "10 digit mobile number isrequired", Toast.LENGTH_SHORT).show();
        } else if (userRoleId == 777) {
            Toast.makeText(_context, "Select Your Role", Toast.LENGTH_SHORT).show();
        } else if (password.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Password is required.", Toast.LENGTH_SHORT).show();
        } else {
            @SuppressLint("HardwareIds")
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("roleid", String.valueOf(userRoleId));
            postparams.put("usernumber", mobilenumber);
            postparams.put("username", name);
            postparams.put("useremail", email);
            postparams.put("userlocation", "");
            postparams.put("userimage", "");
            postparams.put("createdby", "777");
            postparams.put("uniqueid", Helper.getUniqueId(_context));
            postparams.put("password", password);
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_USER, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
        }
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
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                userRolesArrayList = new ArrayList<>();
                                UserRoles userRole = new UserRoles(777, "se", "select role", true);
                                userRolesArrayList.add(userRole);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        int roleID = jsonObject1.getInt("roleid");
                                        String roleCode = jsonObject1.getString("rolecode");
                                        String roleName = jsonObject1.getString("rolename");
                                        UserRoles userRoles = new UserRoles(roleID, roleCode, roleName, true);
                                        userRolesArrayList.add(userRoles);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                UserRolesAdapter userRolesAdapter = new UserRolesAdapter(_context, userRolesArrayList);
                                _activitySignupBinding.spinUsers.setAdapter(userRolesAdapter);
                                _activitySignupBinding.spinUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        UserRoles userRoles = (UserRoles) userRolesArrayList.get(position);
                                        userRoleId = userRoles.getRoleID();
                                        userRoleCode = userRoles.getRoleCode();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            } else {
                                Helper.showMessage(_context, "No Roles Found Here.", AquaConstants.FINISH);
                            }
                        } else {
                            Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                    }
                } catch (JSONException e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }

                break;

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (response != null) {
                            if (response.contains("altready registered")) {
                                Helper.showMessage(_context, response, AquaConstants.HOLD);
                            } else {
                                JSONObject jsonObject1 = new JSONObject(response);
                                String userid = jsonObject1.getString("userid");
                                String roleid = jsonObject1.getString("roleid");
                                String usernumber = jsonObject1.getString("usernumber");
                                String notificationid = jsonObject1.getString("notificationid");
                                String rolecode = jsonObject1.getString("rolecode");
                                String[] values = new String[]{userid, roleid, usernumber, notificationid, response,rolecode};
                                AquaConstants.putIntent(_context, VerificationActivity.class, AquaConstants.HOLD, values);
                            }
                        } else {
                            Helper.showMessage(_context, response, AquaConstants.HOLD);
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
