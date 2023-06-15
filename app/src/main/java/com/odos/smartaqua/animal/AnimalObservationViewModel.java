package com.odos.smartaqua.animal;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityObservationAnimalBinding;
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

public class AnimalObservationViewModel extends ViewModel implements ServiceAsyncResponse {
    String wssv = "", ehp = "", ems = "", ihhnv = "";
    private Context _context;
    private ActivityObservationAnimalBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int tankId;
    private String[] values;

    public AnimalObservationViewModel(Context context, ActivityObservationAnimalBinding binding) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
        Log.e("$$$$$$$$$$$", " " + values[4]);
    }

    public void getObservationDate(View v) {
        Helper.getDateTimepicker(_context, _binding.txtObservationDate, AquaConstants.FINISH);
    }


    public void onSaveClick(View v) {
        if (tankId == -1) {
            Toast.makeText(_context, "Pls select your Pond.", Toast.LENGTH_SHORT).show();
        } else if (_binding.txtObservationDate.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls select Observation Date.", Toast.LENGTH_SHORT).show();
        } else if (_binding.edtGreenColonies.getText().toString().trim().equalsIgnoreCase("")
                && _binding.edtYellowColonies.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Please Enter Green/Yellow Colonies.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("tankid", tankId);
            postparams.put("obsvdate", _binding.txtObservationDate.getText().toString());
            postparams.put("greencolony", _binding.edtGreenColonies.getText().toString());
            postparams.put("yellowcolony", _binding.edtYellowColonies.getText().toString());
            postparams.put("comments", _binding.edtComment.getText().toString());
            postparams.put("userid", Helper.getUserID(_context));

            postparams.put("culturecode", "");
            postparams.put("cultureloc", "");

            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_ANIMAL_OBSV, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
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
                        Helper.showMessage(_context, "Animal Observation saved", AquaConstants.FINISH);
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
