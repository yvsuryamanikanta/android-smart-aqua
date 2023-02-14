package com.odos.smartaqua.lab;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityChecktrayObservationBinding;
import com.odos.smartaqua.databinding.ActivityLabObservationBinding;
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

public class LabObservationViewModel extends ViewModel implements ServiceAsyncResponse {
    private Context _context;
    private ActivityLabObservationBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int tankId;

    public LabObservationViewModel(Context context, ActivityLabObservationBinding activityLabObservationBinding) {
        this._context = context;
        this._binding = activityLabObservationBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
        _binding.llDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.getDateTimepicker(_context, _binding.txtTimeDate, AquaConstants.FINISH);
            }
        });
    }

    public void onSaveClick(View v) {
        if(tankId ==-1){
            Toast.makeText(_context, "Pls select your Pond.", Toast.LENGTH_SHORT).show();
        }else if(_binding.txtTimeDate.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(_context, "Pls enter observation date", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("userid", Helper.getUserID(_context));
            postparams.put("tankid", tankId);
            postparams.put("bluegreenalgae", _binding.edtBlueGreenAlgae.getText().toString());
            postparams.put("cahardness", _binding.edtCaHardness.getText().toString());
            postparams.put("calcium", _binding.edtCalcium.getText().toString());
            postparams.put("co2", _binding.edtCo2.getText().toString());
            postparams.put("co3", _binding.edtCo3.getText().toString());
            postparams.put("dafloc", _binding.edtDaFloc.getText().toString());
            postparams.put("diatom", _binding.edtDiatom.getText().toString());
            postparams.put("dinoflegellates", _binding.edtDinoflegellates.getText().toString());
            postparams.put("greenalgae", _binding.edtGreenAlgae.getText().toString());
            postparams.put("hco3", _binding.edtHco3.getText().toString());
            postparams.put("hydrogensulphide", _binding.edtHydrogenSulphide.getText().toString());
            postparams.put("ionizedammonia", _binding.edtIonAmmonia.getText().toString());
            postparams.put("iron", _binding.edtChlorine.getText().toString());
            postparams.put("labdo", _binding.edtDo.getText().toString());
            postparams.put("magnesium", _binding.edtMagnicium.getText().toString());
            postparams.put("mghardness", _binding.edtMgHardness.getText().toString());
            postparams.put("nitrate", _binding.edtNitrite.getText().toString());
            postparams.put("phvalue", _binding.edtPh.getText().toString());
            postparams.put("potassium", _binding.edtPotassiam.getText().toString());
            postparams.put("salinity", _binding.edtSalinity.getText().toString());
            postparams.put("sodium", _binding.edtSodium.getText().toString());
            postparams.put("unionizedammonia", _binding.edtUnIonAmmonia.getText().toString());
            postparams.put("vibriogreencolonies", _binding.edtGreenVibrio.getText().toString());
            postparams.put("vibrioyellowcolonies", _binding.edtYellowVibrio.getText().toString());
            postparams.put("zooplankton", _binding.edtZooplankton.getText().toString());
            postparams.put("labobsvdate", _binding.txtTimeDate.getText().toString());
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_LAB_OBSV, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);

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
            case 2:
                try {
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, "Lab Observation saved", AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e.getMessage(), AquaConstants.FINISH);
                }
                break;

        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
