package com.odos.smartaqua.cultures;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityAddcultureBinding;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.prelogin.sighnup.UserRolesAdapter;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AddCultureViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityAddcultureBinding _activityAddcultureBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int tankId;
    private String filePath ="";
    private String fileType ="";

    public AddCultureViewModel(Context context, ActivityAddcultureBinding activityAddcultureBinding) {
        this._context = context;
        this._activityAddcultureBinding = activityAddcultureBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }
    public void onBrowse(View v) {
        if (Helper.getCameraPermission(_context)) {
            UploadBottomSheetFragment addPhotoBottomDialogFragment =
                    UploadBottomSheetFragment.newInstance();
            addPhotoBottomDialogFragment.show(((FragmentActivity) _context).getSupportFragmentManager(),
                    "tag");
        } else {
            Helper.getCameraPermission(_context);
        }
    }
    public void uploadBitmap(Bitmap bitmap) {
        VolleyService.VolleyMultipartRequest(_context, bitmap, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 3, true,"image");
    }

    public void onSaveClick(View v) {
        String culture_name = _activityAddcultureBinding.edtPondName.getText().toString();
        if (tankId == 777 || culture_name.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Name and Crop Number Required", Toast.LENGTH_SHORT).show();
        }else if (filePath.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls Upload Image first", Toast.LENGTH_SHORT).show();
        }  else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userid", Helper.getUserID(_context));
            hashMap.put("tankid", tankId);
            hashMap.put("culturename", culture_name);
            hashMap.put("culturenumber", "");
            hashMap.put("cultureimage", filePath);
            hashMap.put("culturestatus", "start");
            hashMap.put("cultureaccess", "CULT-"+Helper.createRandomInteger(22,324324));
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_CULTURE, hashMap, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
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
                                //UserRoles userRole = new UserRoles(777, "se", "Select Your Tank", true);
                                //userRolesArrayList.add(userRole);
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
                                _activityAddcultureBinding.spinTanklist.setAdapter(userRolesAdapter);
                                _activityAddcultureBinding.spinTanklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        if (!response.equalsIgnoreCase("Success")) {
                            Helper.showMessage(_context, "culture saved successfully", AquaConstants.FINISH);
                        }else{
                            Helper.showMessage(_context, ""+response, AquaConstants.HOLD);
                        }
                    }else{
                        Helper.showMessage(_context, ""+response, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
            case 3:
                try {
                    String status = jsonObject.getString("status");
                    if(status.equalsIgnoreCase("Sucess")){
                        JSONObject response = jsonObject.getJSONObject("response");
                        filePath = response.getString("filepath");
                        fileType = response.getString("fileType");
                    }
                } catch (Exception e) {
                    Toast.makeText(_context, "image upload failed try again", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
