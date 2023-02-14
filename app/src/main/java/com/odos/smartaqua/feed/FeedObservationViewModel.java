package com.odos.smartaqua.feed;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityAddChecktrayBinding;
import com.odos.smartaqua.databinding.ActivityFeedObservationBinding;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.prelogin.sighnup.UserRolesAdapter;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedObservationViewModel extends ViewModel implements ServiceAsyncResponse {
    private Context _context;
    private ActivityFeedObservationBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int tankId;

    public FeedObservationViewModel(Context context, ActivityFeedObservationBinding activityFeedObservationBinding) {
        this._context = context;
        this._binding = activityFeedObservationBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }

    public void onSaveClick(View v) {


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
                                UserRoles userRole = new UserRoles(777, "se", "select Tank", true);
                                userRolesArrayList.add(userRole);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        int roleID = jsonObject1.getInt("tankid");
                                        String roleCode = jsonObject1.getString("tanklocation");
                                        String roleName = jsonObject1.getString("tankname");
                                        UserRoles userRoles = new UserRoles(roleID, roleCode, roleName, true);
                                        userRolesArrayList.add(userRoles);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                UserRolesAdapter userRolesAdapter = new UserRolesAdapter(_context, userRolesArrayList);
                                _binding.spinTanklist.setAdapter(userRolesAdapter);
                                _binding.spinTanklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        UserRoles userRoles = (UserRoles) userRolesArrayList.get(position);
                                        tankId = userRoles.getRoleID();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            } else {
                                Helper.showMessage(_context, "No Tanks Found Here.", AquaConstants.FINISH);
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


        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
