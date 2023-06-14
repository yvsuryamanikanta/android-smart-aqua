package com.odos.smartaqua.pcr;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityObservationPcrBinding;
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

public class PCRObservationViewModel extends ViewModel implements ServiceAsyncResponse {
    String wssv = "", ehp = "", ems = "", ihhnv = "";
    private Context _context;
    private ActivityObservationPcrBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private int tankId;

    public PCRObservationViewModel(Context context, ActivityObservationPcrBinding pcrBinding) {
        this._context = context;
        this._binding = pcrBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }

        setSpinnerData(_binding.spinResultForWssv);
        setSpinnerData(_binding.spinResultForEhp);
        setSpinnerData(_binding.spinResultForIhhnv);
        setSpinnerData(_binding.spinResultForEms);

        _binding.spinResultForWssv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _binding.linearWssvCtValue.setVisibility(View.VISIBLE);
                } else {
                    _binding.linearWssvCtValue.setVisibility(View.GONE);
                    wssv = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _binding.spinResultForEhp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _binding.linearEhpCtValue.setVisibility(View.VISIBLE);
                } else {
                    _binding.linearEhpCtValue.setVisibility(View.GONE);
                    ehp = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _binding.spinResultForIhhnv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _binding.linearIhhnvCtValue.setVisibility(View.VISIBLE);
                } else {
                    _binding.linearIhhnvCtValue.setVisibility(View.GONE);
                    ihhnv = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        _binding.spinResultForEms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    _binding.linearEmsCtValue.setVisibility(View.VISIBLE);
                } else {
                    _binding.linearEmsCtValue.setVisibility(View.GONE);
                    ems = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getObservationDate(View v) {
        Helper.getDateTimepicker(_context, _binding.txtObservationDate, AquaConstants.FINISH);
    }

    public void setSpinnerData(Spinner spinner) {
        String data[] = {"NEGATIVE", "POSITIVE"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(_context, R.layout.spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }


    public void onSaveClick(View v) {
        if (_binding.spinResultForWssv.getSelectedItemPosition() > 0) {
            wssv = _binding.edtWssvCtValue.getText().toString();
        }
        if (_binding.spinResultForEhp.getSelectedItemPosition() > 0) {
            ehp = _binding.edtEhpCtValue.getText().toString();
        }
        if (_binding.spinResultForIhhnv.getSelectedItemPosition() > 0) {
            ihhnv = _binding.edtIhhnvCtValue.getText().toString();
        }
        if (_binding.spinResultForEms.getSelectedItemPosition() > 0) {
            ems = _binding.edtEmsCtValue.getText().toString();
        }


        if (tankId == -1) {
            Toast.makeText(_context, "Pls select your Pond.", Toast.LENGTH_SHORT).show();
        } else if (_binding.txtObservationDate.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls select Observation Date.", Toast.LENGTH_SHORT).show();
        } else if (_binding.spinResultForWssv.getSelectedItemPosition() > 0 &&
                _binding.edtWssvCtValue.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls enter CT value & Severity for PCR result tor WSSV.", Toast.LENGTH_SHORT).show();
        } else if (_binding.spinResultForEhp.getSelectedItemPosition() > 0 &&
                _binding.edtEhpCtValue.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls enter CT value & Severity for PCR result tor Ehp.", Toast.LENGTH_SHORT).show();
        } else if (_binding.spinResultForIhhnv.getSelectedItemPosition() > 0 &&
                _binding.edtIhhnvCtValue.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls enter CT value & Severity PCR result tor IHHNV.", Toast.LENGTH_SHORT).show();
        } else if (_binding.spinResultForEms.getSelectedItemPosition() > 0 &&
                _binding.edtEmsCtValue.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(_context, "Pls enter CT value & Severity PCR result tor EMS.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> postparams = new HashMap<>();
            postparams.put("tankid", tankId);
            postparams.put("coefficientOfSizeVariation", _binding.edtSizeVariation.getText().toString());
            postparams.put("comments", _binding.edtComment.getText().toString());
            postparams.put("dorsalSpinesCount", _binding.edtDorsalSpines.getText().toString());
            postparams.put("ectoparasiteattachments", _binding.edtEctoparasiteAttachments.getText().toString());
            postparams.put("ehpCtValueSeviority", _binding.edtEhpCtValue.getText().toString());
            postparams.put("emsCtValueSeviority", _binding.edtEmsCtValue.getText().toString());
            postparams.put("endoParasite", _binding.edtEndoparasite.getText().toString());
            postparams.put("estimatedPlAge", _binding.edtPlAge.getText().toString());
            postparams.put("formalinSressSurvival", _binding.edtFormalinStressLevel.getText().toString());
            postparams.put("gillDevStatus", _binding.edtGiiiDevelopmentStatus.getText().toString());
            postparams.put("hepathopancreasLipid", _binding.edtHepatopancreas.getText().toString());
            postparams.put("hypherTropiedNucleiInHp", _binding.edtHypertrophied.getText().toString());
            postparams.put("ihhnvCtValueSeviority", _binding.edtIhhnvCtValue.getText().toString());
            postparams.put("mbvOcclusionBodies", _binding.edtMBVOcclusionBodies.getText().toString());
            postparams.put("meanBodyLeangth", _binding.edtMeanBodyLength.getText().toString());
            postparams.put("muscleGutRation", _binding.edtMuscleGutRatio.getText().toString());
            postparams.put("necrosis", _binding.edtNecrosis.getText().toString());
            postparams.put("obsvdate", _binding.txtObservationDate.getText().toString());
            postparams.put("pcrResultEhp", _binding.spinResultForEhp.getSelectedItem().toString());
            postparams.put("pcrResultEms", _binding.spinResultForEms.getSelectedItem().toString());
            postparams.put("pcrResultIhhnv", _binding.spinResultForIhhnv.getSelectedItem().toString());
            postparams.put("pcrResultWssv", _binding.spinResultForWssv.getSelectedItem().toString());
            postparams.put("physicalActivity", _binding.edtPhysicalActivity.getText().toString());
            postparams.put("salinitySressSurvival", _binding.edtSalinityStressLevel.getText().toString());
            postparams.put("sampleSalinity", _binding.edtSampleSalinity.getText().toString());
            postparams.put("structuralDeformities", _binding.edtStructuralDeformities.getText().toString());
            postparams.put("wssvCtValueSeviority", _binding.edtWssvCtValue.getText().toString());
            postparams.put("userid", Helper.getUserID(_context));
            postparams.put("culturecode", "");
            postparams.put("cultureloc", "");

            VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.SAVE_PCR_OBSV, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
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
                        Helper.showMessage(_context, "PCR Observation saved", AquaConstants.FINISH);
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
