package com.odos.smartaqua.tank;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.BaseObservable;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityStockingBinding;
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

public class StockingViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityStockingBinding _activityStockingBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private String tankId;


    public StockingViewModel(Context context, ActivityStockingBinding activityStockingBinding) {
        this._context = context;
        this._activityStockingBinding = activityStockingBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }

        String pcr_result[] = {"+ve", "-ve"};
        ArrayAdapter<String> spinnerPCRResultAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_result);
        spinnerPCRResultAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityStockingBinding.spinPcrResult.setAdapter(spinnerPCRResultAdapter);

        String pcr_hepa[] = {"good", "bad"};
        ArrayAdapter<String> spinnerHepatopancreasAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_hepa);
        spinnerHepatopancreasAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityStockingBinding.spinHepatopancreas.setAdapter(spinnerHepatopancreasAdapter);

        String pcr_accl[] = {"yes", "no"};
        ArrayAdapter<String> spinnerAcclinitizationAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pcr_accl);
        spinnerAcclinitizationAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityStockingBinding.spinAcclinitization.setAdapter(spinnerAcclinitizationAdapter);

        String pc_bag[] = {"pcs", "bags"};
        ArrayAdapter<String> spinnerPckageAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pc_bag);
        spinnerPckageAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        _activityStockingBinding.spinPakagingType.setAdapter(spinnerPckageAdapter);
    }

    public void saveStocking(View view) {

        String ammonia = _activityStockingBinding.edtAmmonia.getText().toString();
        String nitrite = _activityStockingBinding.edtNitrite.getText().toString();
        String alkalnity = _activityStockingBinding.edtAlkalinity.getText().toString();
        String hardness = _activityStockingBinding.edtHardness.getText().toString();
        String iron = _activityStockingBinding.edtIron.getText().toString();
        String mineral = _activityStockingBinding.edtMineralConsumption.getText().toString();
        String clorine = _activityStockingBinding.edtChlorine.getText().toString();
        String salnity = _activityStockingBinding.edtSalinity.getText().toString();
        String transparancy = _activityStockingBinding.edtTransparency.getText().toString();
        String watercolor = _activityStockingBinding.edtWaterColor.getText().toString();
        String waterdepth = _activityStockingBinding.edtWaterDepth.getText().toString();
        String plsize = _activityStockingBinding.edtPlSize.getText().toString();
        String plpcrresult = _activityStockingBinding.edtPlPackaging.getText().toString();
        String plpackingdensity = _activityStockingBinding.edtPlPackaging.getText().toString();
        String plage = _activityStockingBinding.edtPlAge.getText().toString();
        String hepathopancreasCondition = _activityStockingBinding.spinHepatopancreas.getSelectedItem().toString();
        String avgnoofplPerBag = _activityStockingBinding.edtAvgPlBag.getText().toString();
        String acclinitization = _activityStockingBinding.spinAcclinitization.getSelectedItem().toString();
        String seedtrnsportationtime = _activityStockingBinding.edtTransportationTime.getText().toString();
        String modeoftransport = _activityStockingBinding.edtTransportMode.getText().toString();

        HashMap<String, Object> postparams = new HashMap<>();
        postparams.put("tankid", tankId);
        postparams.put("ammonia", ammonia);
        postparams.put("nitrite", nitrite);
        postparams.put("alkalnity", alkalnity);
        postparams.put("hardness", hardness);
        postparams.put("iron", iron);
        postparams.put("mineral", mineral);
        postparams.put("clorine", clorine);
        postparams.put("salnity", salnity);
        postparams.put("transparancy", transparancy);
        postparams.put("watercolor", watercolor);
        postparams.put("waterdepth", waterdepth);
        postparams.put("plsize", plsize);
        postparams.put("plpcrresult", plpcrresult);
        postparams.put("plpackingdensity", plpackingdensity);
        postparams.put("plage", plage);
        postparams.put("hepathopancreasCondition", hepathopancreasCondition);
        postparams.put("avgnoofplPerBag", avgnoofplPerBag);
        postparams.put("acclinitization", acclinitization);
        postparams.put("seedtrnsportationtime", seedtrnsportationtime);
        postparams.put("modeoftransport", modeoftransport);

        VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                ServiceConstants.STOCKING_SAVE, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
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
                                _activityStockingBinding.spinPond.setAdapter(userRolesAdapter);
                                _activityStockingBinding.spinPond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, "Stocking successfully", AquaConstants.FINISH);
                    } else {
                        Helper.showMessage(_context, "" + errorMsg, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "saving failed please try again once" + e, AquaConstants.HOLD);
                }
                break;

        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }


}
