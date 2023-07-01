package com.odos.smartaqua.prelogin.profile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityUpdateProfileBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateProfileViewModel extends BaseObservable implements ServiceAsyncResponse {

    UserData userData;
    private Context _context;
    private ActivityUpdateProfileBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String filePath = "";
    private String fileType = "";

    public UpdateProfileViewModel(Context context, ActivityUpdateProfileBinding binding, UserData _userData) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        this.userData = _userData;

        _binding.edtMobile.setText(userData.usernumber);
        _binding.edtFirstname.setText(userData.username);
        _binding.edtEmail.setText(userData.useremail);
        _binding.edtLocation.setText(userData.userlocation);
        Log.e("$$$$$$$$$$", " "+userData.userimage);
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
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 2, true, "image");
    }


    public void onUpdateClick(View v) {
        String name = _binding.edtFirstname.getText().toString();
        String email = _binding.edtEmail.getText().toString();
        String mobile = _binding.edtMobile.getText().toString();
        String location = _binding.edtLocation.getText().toString();
        if (name.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Name Required", Toast.LENGTH_SHORT).show();
        } else {
            @SuppressLint("HardwareIds")
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("userid", Helper.getUserID(_context));
            postparams.put("usernumber", mobile);
            postparams.put("username", name);
            postparams.put("createdby", userData.createdby);
            postparams.put("userimage", filePath);
            postparams.put("roleid", userData.roleid);
            postparams.put("uniqueid", userData.uniqueid);
            postparams.put("useremail", email);
            postparams.put("userlocation", location);
            postparams.put("notificationid", userData.notificationid);
            postparams.put("rolecode", userData.rolecode);

            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.UPDATE_USER_DATA, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

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
                            Helper.showMessage(_context, "Updated Successfully.", AquaConstants.FINISH);
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
            case 2:
                try {
                    String status = jsonObject.getString("status");
                    Log.e("$$$$$$$$$ image data ", " "+new Gson().toJson(jsonObject));

                    if (status.equalsIgnoreCase("Sucess")) {
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
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {
    }
}