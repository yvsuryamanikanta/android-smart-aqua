package com.odos.smartaqua.tank;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityPondprepareBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class PondPreparationViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityPondprepareBinding _activityPondprepareBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String filePath = "";
    private String fileType = "";
    private String[] values;

    public PondPreparationViewModel(Context context, ActivityPondprepareBinding activityPondprepareBinding) {
        this._context = context;
        this._activityPondprepareBinding = activityPondprepareBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        Helper.getCurrentLocation(_context);
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

    public void onSaveClick(View v) {
        String name = _activityPondprepareBinding.edtName.getText().toString();
        String address = _activityPondprepareBinding.edtAddress.getText().toString();
        if (name.equalsIgnoreCase("")) {
            Toast.makeText(_context, "Name Required", Toast.LENGTH_SHORT).show();
        } else {
            @SuppressLint("HardwareIds")
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("tankname", name);
            postparams.put("tanklocation", address);
            postparams.put("tankimage", filePath);
            postparams.put("userid", Helper.getUserID(_context));

            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_TANK, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        }

    }

    public void uploadBitmap(Bitmap bitmap) {
        VolleyService.VolleyMultipartRequest(_context, bitmap, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.UPLOAD, (ServiceAsyncResponse) serviceAsyncResponse, 2, true, "image");
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
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (status.equalsIgnoreCase("Sucess")) {
                        _activityPondprepareBinding.edtName.setText("");
                        _activityPondprepareBinding.edtAddress.setText("");
                        filePath = "";
                        _activityPondprepareBinding.imgView.setImageDrawable(_context.getResources().getDrawable(R.drawable.uploadicon));
                        Helper.showMessageWithNavigation(_context, "Tank registered successfully. Do you want to create another Tank", Integer.parseInt(values[0]));
                    } else {
                        Helper.showMessage(_context, "" + errorMsg, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "saving failed please try again once", AquaConstants.HOLD);
                }
                break;
            case 2:
                try {
                    String status = jsonObject.getString("status");
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
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
