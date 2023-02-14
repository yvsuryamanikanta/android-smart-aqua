package com.odos.smartaqua.checktray;


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

public class ChecktrayObservationViewModel extends ViewModel implements ServiceAsyncResponse {
    private Context _context;
    private ActivityChecktrayObservationBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> pondArrayList, checktrayList,feedReportList,wastageColorList,pottaciumDefeciencyList,
    magniciumDeficiencyList,calciumDeficiencyList,mortalityTypeList,vibrioStatusList,crampStatusList;
    private int tankId, checktrayId;
    private String feedWastage,wastageColor,potacium,calcium,magnicium,vibrio,cramp,mortality;

    public ChecktrayObservationViewModel(Context context, ActivityChecktrayObservationBinding activityChecktrayObservationBinding) {
        this._context = context;
        this._binding = activityChecktrayObservationBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        initSpinners();
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
        _binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_binding.txtTimeDate.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(context, "Pls enter observation date", Toast.LENGTH_SHORT).show();
                }else{
                    String mortalityCount = _binding.edtMortalityCount.getText().toString();
                    HashMap<String, Object> postparams = new HashMap<>();
                    postparams.put("checktrayid", checktrayId);
                    postparams.put("tankid", tankId);
                    postparams.put("feedstatus", feedWastage);
                    postparams.put("wastagecolor", wastageColor);
                    postparams.put("potaciumdefeciency", potacium);
                    postparams.put("magniciumdefeciency", magnicium);
                    postparams.put("calciumdefeciency", calcium);
                    postparams.put("vibrieostatus", vibrio);
                    postparams.put("crampstatus", cramp);
                    postparams.put("mortalitytype", mortality);
                    postparams.put("mortalitycount", mortalityCount);
                    postparams.put("checktrayobsvdate", _binding.txtTimeDate.getText().toString());
                    VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                            ServiceConstants.SAVE_CHECKTRAY_OBSV, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 3, true);
                }
            }
        });

        _binding.llDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.getDateTimepicker(_context, _binding.txtTimeDate, AquaConstants.FINISH);
            }
        });
    }
    private void initSpinners(){
        feedReportList = new ArrayList<>();
        wastageColorList = new ArrayList<>();
        pottaciumDefeciencyList = new ArrayList<>();
        magniciumDeficiencyList = new ArrayList<>();
        calciumDeficiencyList = new ArrayList<>();
        mortalityTypeList = new ArrayList<>();
        vibrioStatusList = new ArrayList<>();
        crampStatusList = new ArrayList<>();
        UserRoles no = new UserRoles(1, "1", "NO", true);
        UserRoles yes = new UserRoles(2, "2", "YES", true);
        UserRoles redcolor = new UserRoles(3, "3", "RED", true);
        UserRoles yellowcolor = new UserRoles(4, "4", "YELLOW", true);
        UserRoles greencolor = new UserRoles(5, "5", "GREEN", true);
        UserRoles redmortality = new UserRoles(6, "6", "Red Mortality", true);
        UserRoles whitemortality = new UserRoles(7, "7", "White Mortality", true);
        UserRoles high = new UserRoles(8, "8", "HIGH", true);
        UserRoles low = new UserRoles(9, "9", "LOW", true);
        UserRoles medium = new UserRoles(10, "10", "MEDIUM", true);
        feedReportList.add(no);
        feedReportList.add(yes);
        _binding.spinFeedWaste.setAdapter(new UserRolesAdapter(_context,feedReportList));
        _binding.spinFeedWaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) feedReportList.get(i);
                feedWastage = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        wastageColorList.add(no);
        wastageColorList.add(redcolor);
        wastageColorList.add(yellowcolor);
        wastageColorList.add(greencolor);
        _binding.spinColorList.setAdapter(new UserRolesAdapter(_context,wastageColorList));
        _binding.spinColorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) wastageColorList.get(i);
                wastageColor = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mortalityTypeList.add(no);
        mortalityTypeList.add(redmortality);
        mortalityTypeList.add(whitemortality);
        _binding.spinMortalityList.setAdapter(new UserRolesAdapter(_context,mortalityTypeList));
        _binding.spinMortalityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) mortalityTypeList.get(i);
                mortality = userRoles.getRoleName();
                if(i==0){
                    _binding.txtHeaderMortality.setVisibility(View.GONE);
                    _binding.edtMortalityCount.setVisibility(View.GONE);
                    _binding.edtMortalityCount.setText("0");
                }else{
                    _binding.txtHeaderMortality.setVisibility(View.VISIBLE);
                    _binding.edtMortalityCount.setVisibility(View.VISIBLE);
                    _binding.edtMortalityCount.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pottaciumDefeciencyList.add(no);
        pottaciumDefeciencyList.add(high);
        pottaciumDefeciencyList.add(medium);
        pottaciumDefeciencyList.add(low);
        _binding.spinPotacium.setAdapter(new UserRolesAdapter(_context,pottaciumDefeciencyList));
        _binding.spinPotacium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) pottaciumDefeciencyList.get(i);
                potacium = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        magniciumDeficiencyList.add(no);
        magniciumDeficiencyList.add(high);
        magniciumDeficiencyList.add(medium);
        magniciumDeficiencyList.add(low);
        _binding.spinMagnicium.setAdapter(new UserRolesAdapter(_context,magniciumDeficiencyList));
        _binding.spinMagnicium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) magniciumDeficiencyList.get(i);
                magnicium = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calciumDeficiencyList.add(no);
        calciumDeficiencyList.add(high);
        calciumDeficiencyList.add(medium);
        calciumDeficiencyList.add(low);
        _binding.spinCalcium.setAdapter(new UserRolesAdapter(_context,calciumDeficiencyList));
        _binding.spinCalcium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) calciumDeficiencyList.get(i);
                calcium = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vibrioStatusList.add(no);
        vibrioStatusList.add(high);
        vibrioStatusList.add(medium);
        vibrioStatusList.add(low);
        _binding.spinVibrio.setAdapter(new UserRolesAdapter(_context,vibrioStatusList));
        _binding.spinVibrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) vibrioStatusList.get(i);
                vibrio = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        crampStatusList.add(no);
        crampStatusList.add(high);
        crampStatusList.add(medium);
        crampStatusList.add(low);
        _binding.spinCramp.setAdapter(new UserRolesAdapter(_context,crampStatusList));
        _binding.spinCramp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserRoles userRoles = (UserRoles) crampStatusList.get(i);
                cramp = userRoles.getRoleName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                pondArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        int roleID = jsonObject1.getInt("tankid");
                                        String roleCode = jsonObject1.getString("tanklocation");
                                        String roleName = jsonObject1.getString("tankname");
                                        UserRoles userRoles = new UserRoles(roleID, roleCode, roleName, true);
                                        pondArrayList.add(userRoles);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                UserRolesAdapter userRolesAdapter = new UserRolesAdapter(_context, pondArrayList);
                                _binding.spinTanklist.setAdapter(userRolesAdapter);
                                _binding.spinTanklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        UserRoles userRoles = (UserRoles) pondArrayList.get(position);
                                        tankId = userRoles.getRoleID();
                                        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                                ServiceConstants.LIST_CHECKTRAY + tankId, null, Helper.headerParams(_context),
                                                (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
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
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            checktrayList = new ArrayList<>();
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        int checktrayid = jsonObject1.getInt("checktrayid");
                                        String tankid = jsonObject1.getString("tankid");
                                        String checktrayname = jsonObject1.getString("checktrayname");
                                        UserRoles userRoles = new UserRoles(checktrayid, tankid, checktrayname, true);
                                        checktrayList.add(userRoles);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                            } else {
                                Helper.showMessage(_context, "No Checktrays Found Here.", AquaConstants.HOLD);
                            }UserRolesAdapter userRolesAdapter = new UserRolesAdapter(_context, checktrayList);
                            _binding.spinChecktraylist.setAdapter(userRolesAdapter);
                            _binding.spinChecktraylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    UserRoles userRoles = (UserRoles) checktrayList.get(position);
                                    checktrayId = userRoles.getRoleID();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        } else {
                            Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                        }
                    } else {
                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
            case 3:
                try {
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Helper.showMessage(_context, "Chaecktray Observation Saved", AquaConstants.FINISH);
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
