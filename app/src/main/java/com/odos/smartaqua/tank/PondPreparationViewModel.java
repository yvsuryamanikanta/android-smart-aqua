package com.odos.smartaqua.tank;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityPondprepareBinding;
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

public class PondPreparationViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityPondprepareBinding _activityPondprepareBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String filePath = "";
    private String fileType = "";
    private String[] values;
    private ArrayList<UserRoles> userRolesArrayList;

    public PondPreparationViewModel(Context context, ActivityPondprepareBinding activityPondprepareBinding) {
        this._context = context;
        this._activityPondprepareBinding = activityPondprepareBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        Helper.getCurrentLocation(_context);

        if (CheckNetwork.isNetworkAvailable(_context)) {
            //service no changed 1 to 3
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 3, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }

        setSpinnerData(_activityPondprepareBinding.spinDisease);
        setSpinnerData(_activityPondprepareBinding.spinRecordKeeping);
        setSpinnerData(_activityPondprepareBinding.spinDrying);
        setSpinnerData(_activityPondprepareBinding.spinScrapping);
        setSpinnerData(_activityPondprepareBinding.spinPloughing);
        setSpinnerData(_activityPondprepareBinding.spinLiminig);
        setSpinnerData(_activityPondprepareBinding.spinSoilChecking);
        setSpinnerData(_activityPondprepareBinding.spinApplMinerals);
        setSpinnerData(_activityPondprepareBinding.spinApplProbiaotics);
        setSpinnerData(_activityPondprepareBinding.spinApplFertilization);
        setSpinnerData(_activityPondprepareBinding.spinCheckEhp);

        {
            String bio[] = {"Good", "Average", "Poor", "Very Poor"};
            ArrayAdapter<String> spinnerBioAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, bio);
            spinnerBioAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinBioSecurity.setAdapter(spinnerBioAdapter);
        }
        {
            String waterFilled[] = {"Fresh", "Old"};
            ArrayAdapter<String> spinnerWaterFilledAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, waterFilled);
            spinnerWaterFilledAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinWaterFilledWith.setAdapter(spinnerWaterFilledAdapter);
        }
        {
            String waterSource[] = {"Bore", "Canal"};
            ArrayAdapter<String> spinnerWaterSourceAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, waterSource);
            spinnerWaterSourceAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinWaterSource.setAdapter(spinnerWaterSourceAdapter);
        }
        {
            String pondType[] = {"Earthen", "No"};
            ArrayAdapter<String> spinnerPondAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pondType);
            spinnerPondAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinPondType.setAdapter(spinnerPondAdapter);
        }
        {
            String filteration[] = {"0 No.s","1 No.s","1"};
            ArrayAdapter<String> spinnerFilterationAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, filteration);
            spinnerFilterationAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinFilteration.setAdapter(spinnerFilterationAdapter);
        }
        {
            String bleaching[] = {"0","1","2","3"};
            ArrayAdapter<String> spinnerBleachingAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, bleaching);
            spinnerBleachingAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinBleaching.setAdapter(spinnerBleachingAdapter);
        }
    }
    public void setSpinnerData(Spinner spinner){
        String data[] = {"Yes", "No"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
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
        String name = _activityPondprepareBinding.spinPond.getSelectedItem().toString();
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
                        _activityPondprepareBinding.spinPond.setSelection(0);
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
            case 3:
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
                                _activityPondprepareBinding.spinPond.setAdapter(userRolesAdapter);
                                _activityPondprepareBinding.spinPond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        UserRoles userRoles = (UserRoles) userRolesArrayList.get(position);
//                                        tankId = userRoles.getRoleID();
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
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
