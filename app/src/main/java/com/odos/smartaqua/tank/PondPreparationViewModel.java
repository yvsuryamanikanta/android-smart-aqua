package com.odos.smartaqua.tank;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.BaseObservable;

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
    private String tankId;
    private String[] values;
    private ArrayList<UserRoles> userRolesArrayList;

    public PondPreparationViewModel(Context context, ActivityPondprepareBinding activityPondprepareBinding) {
        this._context = context;
        this._activityPondprepareBinding = activityPondprepareBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        Helper.getCurrentLocation(_context);

        if (CheckNetwork.isNetworkAvailable(_context)) {
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
            String filteration[] = {"0 No.s", "1 No.s", "1"};
            ArrayAdapter<String> spinnerFilterationAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, filteration);
            spinnerFilterationAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinFilteration.setAdapter(spinnerFilterationAdapter);
        }
        {
            String bleaching[] = {"0", "1", "2", "3"};
            ArrayAdapter<String> spinnerBleachingAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, bleaching);
            spinnerBleachingAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinBleaching.setAdapter(spinnerBleachingAdapter);
        }
    }

    public void setSpinnerData(Spinner spinner) {
        String data[] = {"Yes", "No"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }


    public void onSaveClick(View v) {
        String previousdecease = _activityPondprepareBinding.spinDisease.getSelectedItem().toString();
        String recordkeeping = _activityPondprepareBinding.spinRecordKeeping.getSelectedItem().toString();
        String drying = _activityPondprepareBinding.spinDrying.getSelectedItem().toString();
        String biosecurity = _activityPondprepareBinding.spinBioSecurity.getSelectedItem().toString();
        String scrapping = _activityPondprepareBinding.spinScrapping.getSelectedItem().toString();
        String ploughing = _activityPondprepareBinding.spinPloughing.getSelectedItem().toString();
        String liming = _activityPondprepareBinding.spinLiminig.getSelectedItem().toString();
        String soilcheck = _activityPondprepareBinding.spinSoilChecking.getSelectedItem().toString();
        String fillingwatertype = _activityPondprepareBinding.spinWaterFilledWith.getSelectedItem().toString();
        String watersource = _activityPondprepareBinding.spinWaterSource.getSelectedItem().toString();
        String pondtype = _activityPondprepareBinding.spinPondType.getSelectedItem().toString();
        String filteration = _activityPondprepareBinding.spinFilteration.getSelectedItem().toString();
        String bleaching = _activityPondprepareBinding.spinBleaching.getSelectedItem().toString();
        String minerals = _activityPondprepareBinding.spinApplMinerals.getSelectedItem().toString();
        String probiotics = _activityPondprepareBinding.spinApplProbiaotics.getSelectedItem().toString();
        String fertilization = _activityPondprepareBinding.spinApplFertilization.getSelectedItem().toString();
        String ehp = _activityPondprepareBinding.spinCheckEhp.getSelectedItem().toString();

        HashMap<String, Object> postparams = new HashMap<>();
        postparams.put("tankid", tankId);
        postparams.put("previousdecease", previousdecease);
        postparams.put("recordkeeping", recordkeeping);
        postparams.put("drying", drying);
        postparams.put("biosecurity", biosecurity);
        postparams.put("scrapping", scrapping);
        postparams.put("ploughing", ploughing);
        postparams.put("liming", liming);
        postparams.put("soilcheck", soilcheck);
        postparams.put("fillingwatertype", fillingwatertype);
        postparams.put("watersource", watersource);
        postparams.put("pondtype", pondtype);
        postparams.put("filteration", filteration);
        postparams.put("bleaching", bleaching);
        postparams.put("minerals", minerals);
        postparams.put("probiotics", probiotics);
        postparams.put("fertilization", fertilization);
        postparams.put("ehp", ehp);

        VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.PREPARATION_SAVE, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
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
                        Helper.showMessage(_context, "Culture Prepared successfully", AquaConstants.FINISH);
                    } else {
                        Helper.showMessage(_context, "" + errorMsg, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "saving failed please try again once" + e, AquaConstants.HOLD);
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
                                        tankId = "" + userRoles.getRoleID();
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
