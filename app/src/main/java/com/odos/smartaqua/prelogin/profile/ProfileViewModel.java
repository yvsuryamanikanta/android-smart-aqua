package com.odos.smartaqua.prelogin.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityProfileBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityProfileBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;

    public ProfileViewModel(Context context, ActivityProfileBinding binding) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;


    }

    void loadProfile() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_USER_DATA + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
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
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                int userid = jsonObject1.getInt("userid");
                                int roleid = jsonObject1.getInt("roleid");
                                String uniqueid = jsonObject1.getString("uniqueid");
                                String username = jsonObject1.getString("username");
                                String usernumber = jsonObject1.getString("usernumber");
                                String useremail = jsonObject1.getString("useremail");
                                String userlocation = jsonObject1.getString("userlocation");
                                String userimage = jsonObject1.getString("userimage");
                                String createdby = jsonObject1.getString("createdby");
                                String notificationid = jsonObject1.getString("notificationid");
                                String rolecode = jsonObject1.getString("rolecode");


                                UserData data = new UserData(userid, roleid, uniqueid, username, usernumber, useremail,
                                        userlocation, userimage, createdby, notificationid, rolecode);
                                Picasso.with(_context).load(userimage).into(_binding.imgProfile);

                                _binding.txtFirstName.setText(username);
                                _binding.txtEmail.setText(useremail);
                                _binding.txtMobile.setText(usernumber);
                                _binding.txtLocatio.setText(userlocation);

                                _binding.txtEdit.setOnClickListener(v -> {
                                    Intent intent = new Intent(_context, UpdateProfileActivity.class);
                                    intent.putExtra("userdata", data);
                                    _context.startActivity(intent);
                                });
                            }


                        } else {
                            Helper.showMessage(_context, "Please try again.", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "Please try again.", AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "Please try again.", AquaConstants.FINISH);
                }
                break;

        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {
    }
}