package com.odos.smartaqua.tank;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    String previousdisease = "";
    String recordkeeping = "";
    String drying = "";
    String biosecurity = "";
    String scrapping = "";
    String ploughing = "";
    String liming = "";
    String soilcheck = "";
    String fillingwatertype = "";
    String watersource = "";
    String pondtype = "";
    String filteration = "";
    String bleaching = "";
    String minerals = "";
    String probiotics = "";
    String fertilization = "";
    String ehp = "";
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
            String bio[] = {"Select", "Good", "Average", "Poor", "Very Poor"};
            ArrayAdapter<String> spinnerBioAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, bio);
            spinnerBioAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinBioSecurity.setAdapter(spinnerBioAdapter);
        }
        {
            String waterFilled[] = {"Select", "Fresh", "Old"};
            ArrayAdapter<String> spinnerWaterFilledAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, waterFilled);
            spinnerWaterFilledAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinWaterFilledWith.setAdapter(spinnerWaterFilledAdapter);
        }
        {
            String waterSource[] = {"Select", "Bore", "Canal"};
            ArrayAdapter<String> spinnerWaterSourceAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, waterSource);
            spinnerWaterSourceAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinWaterSource.setAdapter(spinnerWaterSourceAdapter);
        }
        {
            String pondType[] = {"Select", "Earthen", "No"};
            ArrayAdapter<String> spinnerPondAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, pondType);
            spinnerPondAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinPondType.setAdapter(spinnerPondAdapter);
        }
        {
            String filteration[] = {"Select", "0 No.s", "1 No.s", "1"};
            ArrayAdapter<String> spinnerFilterationAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, filteration);
            spinnerFilterationAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinFilteration.setAdapter(spinnerFilterationAdapter);
        }
        {
            String bleaching[] = {"Select", "0", "1", "2", "3"};
            ArrayAdapter<String> spinnerBleachingAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, bleaching);
            spinnerBleachingAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            _activityPondprepareBinding.spinBleaching.setAdapter(spinnerBleachingAdapter);
        }

        spinnerClick();
    }

    public void setSpinnerData(Spinner spinner) {
        String data[] = {"No", "Yes"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    void spinnerClick() {
        _activityPondprepareBinding.spinBioSecurity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    biosecurity = _activityPondprepareBinding.spinBioSecurity.getSelectedItem().toString();
                } else {
                    biosecurity = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinWaterFilledWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    fillingwatertype = _activityPondprepareBinding.spinWaterFilledWith.getSelectedItem().toString();
                } else {
                    fillingwatertype = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _activityPondprepareBinding.spinWaterSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    watersource = _activityPondprepareBinding.spinWaterSource.getSelectedItem().toString();
                } else {
                    watersource = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _activityPondprepareBinding.spinPondType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    pondtype = _activityPondprepareBinding.spinPondType.getSelectedItem().toString();
                } else {
                    pondtype = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _activityPondprepareBinding.spinFilteration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    filteration = _activityPondprepareBinding.spinFilteration.getSelectedItem().toString();
                } else {
                    filteration = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _activityPondprepareBinding.spinBleaching.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    bleaching = _activityPondprepareBinding.spinBleaching.getSelectedItem().toString();
                } else {
                    bleaching = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        _activityPondprepareBinding.spinDisease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtDisease.setVisibility(View.VISIBLE);
                } else {
                    _activityPondprepareBinding.edtDisease.setVisibility(View.GONE);
                    previousdisease = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinRecordKeeping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtRecord.setVisibility(View.VISIBLE);
                } else {
                    _activityPondprepareBinding.edtRecord.setVisibility(View.GONE);
                    recordkeeping = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinDrying.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtDrying.setVisibility(View.VISIBLE);
                } else {
                    drying = "";
                    _activityPondprepareBinding.edtDrying.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinScrapping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtScrapping.setVisibility(View.VISIBLE);
                } else {
                    scrapping = "";
                    _activityPondprepareBinding.edtScrapping.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinPloughing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtPloughing.setVisibility(View.VISIBLE);
                } else {
                    ploughing = "";
                    _activityPondprepareBinding.edtPloughing.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinLiminig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtLiming.setVisibility(View.VISIBLE);
                } else {
                    liming = "";
                    _activityPondprepareBinding.edtLiming.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinSoilChecking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtSoil.setVisibility(View.VISIBLE);
                } else {
                    soilcheck = "";
                    _activityPondprepareBinding.edtSoil.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinApplMinerals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtMinerals.setVisibility(View.VISIBLE);
                } else {
                    minerals = "";
                    _activityPondprepareBinding.edtMinerals.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinApplProbiaotics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtProbiaotics.setVisibility(View.VISIBLE);
                } else {
                    probiotics = "";
                    _activityPondprepareBinding.edtProbiaotics.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinApplFertilization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtFertilization.setVisibility(View.VISIBLE);
                } else {
                    fertilization = "";
                    _activityPondprepareBinding.edtFertilization.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _activityPondprepareBinding.spinCheckEhp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _activityPondprepareBinding.edtCheckEhp.setVisibility(View.VISIBLE);
                } else {
                    ehp = "";
                    _activityPondprepareBinding.edtCheckEhp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.equalsIgnoreCase("");
    }

    boolean validate() {
        boolean flag = true;
        if (_activityPondprepareBinding.spinDisease.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtDisease.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinRecordKeeping.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtRecord.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinDrying.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtDrying.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinScrapping.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtScrapping.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinPloughing.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtPloughing.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinLiminig.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtLiming.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinSoilChecking.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtSoil.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinApplMinerals.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtMinerals.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinApplProbiaotics.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtProbiaotics.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinApplFertilization.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtFertilization.getText().toString().trim().length() == 0) {
            flag = false;
        }
        if (_activityPondprepareBinding.spinCheckEhp.getSelectedItemPosition() == 1
                && _activityPondprepareBinding.edtCheckEhp.getText().toString().trim().length() == 0) {
            flag = false;
        }
        return flag;
    }

    public void onSaveClick(View v) {
        validate();
        if (_activityPondprepareBinding.spinDisease.getSelectedItemPosition() == 1) {
            previousdisease = _activityPondprepareBinding.edtDisease.getText().toString();
            previousdisease = isNullOrEmpty(previousdisease) ? previousdisease : "";
        }
        if (_activityPondprepareBinding.spinRecordKeeping.getSelectedItemPosition() == 1) {
            recordkeeping = _activityPondprepareBinding.edtRecord.getText().toString();
            recordkeeping = isNullOrEmpty(recordkeeping) ? recordkeeping : "";
        }
        if (_activityPondprepareBinding.spinDrying.getSelectedItemPosition() == 1) {
            drying = _activityPondprepareBinding.edtDrying.getText().toString();
            drying = isNullOrEmpty(drying) ? drying : "";
        }
        if (_activityPondprepareBinding.spinScrapping.getSelectedItemPosition() == 1) {
            scrapping = _activityPondprepareBinding.edtScrapping.getText().toString();
            scrapping = isNullOrEmpty(scrapping) ? scrapping : "";
        }
        if (_activityPondprepareBinding.spinPloughing.getSelectedItemPosition() == 1) {
            ploughing = _activityPondprepareBinding.edtPloughing.getText().toString();
            ploughing = isNullOrEmpty(ploughing) ? ploughing : "";
        }
        if (_activityPondprepareBinding.spinLiminig.getSelectedItemPosition() == 1) {
            liming = _activityPondprepareBinding.edtLiming.getText().toString();
            liming = isNullOrEmpty(liming) ? liming : "";
        }
        if (_activityPondprepareBinding.spinSoilChecking.getSelectedItemPosition() == 1) {
            soilcheck = _activityPondprepareBinding.edtSoil.getText().toString();
            soilcheck = isNullOrEmpty(soilcheck) ? soilcheck : "";
        }
        if (_activityPondprepareBinding.spinApplMinerals.getSelectedItemPosition() == 1) {
            minerals = _activityPondprepareBinding.edtMinerals.getText().toString();
            minerals = isNullOrEmpty(minerals) ? minerals : "";
        }
        if (_activityPondprepareBinding.spinApplProbiaotics.getSelectedItemPosition() == 1) {
            probiotics = _activityPondprepareBinding.edtProbiaotics.getText().toString();
            probiotics = isNullOrEmpty(probiotics) ? probiotics : "";
        }
        if (_activityPondprepareBinding.spinApplFertilization.getSelectedItemPosition() == 1) {
            fertilization = _activityPondprepareBinding.edtFertilization.getText().toString();
            fertilization = isNullOrEmpty(fertilization) ? fertilization : "";
        }
        if (_activityPondprepareBinding.spinCheckEhp.getSelectedItemPosition() == 1) {
            ehp = _activityPondprepareBinding.edtCheckEhp.getText().toString();
            ehp = isNullOrEmpty(ehp) ? ehp : "";
        }

        if (_activityPondprepareBinding.spinBioSecurity.getSelectedItemPosition() > 0) {
            biosecurity = _activityPondprepareBinding.spinBioSecurity.getSelectedItem().toString();
        }
        if (_activityPondprepareBinding.spinWaterFilledWith.getSelectedItemPosition() > 0) {
            fillingwatertype = _activityPondprepareBinding.spinWaterFilledWith.getSelectedItem().toString();
        }
        if (_activityPondprepareBinding.spinWaterSource.getSelectedItemPosition() > 0) {
            watersource = _activityPondprepareBinding.spinWaterSource.getSelectedItem().toString();
        }
        if (_activityPondprepareBinding.spinPondType.getSelectedItemPosition() > 0) {
            pondtype = _activityPondprepareBinding.spinPondType.getSelectedItem().toString();
        }
        if (_activityPondprepareBinding.spinFilteration.getSelectedItemPosition() > 0) {
            filteration = _activityPondprepareBinding.spinFilteration.getSelectedItem().toString();
        }
        if (_activityPondprepareBinding.spinBleaching.getSelectedItemPosition() > 0) {
            bleaching = _activityPondprepareBinding.spinBleaching.getSelectedItem().toString();
        }

        HashMap<String, Object> postparams = new HashMap<>();
        postparams.put("tankid", tankId);
        postparams.put("previousdecease", previousdisease);
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
        postparams.put("insertdate", _activityPondprepareBinding.txtDate.getText().toString());

        if (!validate()) {
            Toast.makeText(_context, "Please fill the required fields", Toast.LENGTH_SHORT).show();
        } else {
            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.PREPARATION_SAVE, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
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

    public void onDateClick(View v){
        Helper.getDateTimepicker(_context,_activityPondprepareBinding.txtDate,AquaConstants.FINISH);
    }

}
