package com.odos.smartaqua.prelogin.forgot;


import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityChangePasswordBinding;
import com.odos.smartaqua.prelogin.login.LoginActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswordViewModel extends ViewModel implements ServiceAsyncResponse {

    private Context _context;
    private ActivityChangePasswordBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;

    public ChangePasswordViewModel(Context context, ActivityChangePasswordBinding binding) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
    }

    public void submitPassword(View view){

        if(_binding.edtNewPassword.getText().length()>=8 &&_binding.edtConfirmPassword.getText().length()>=8) {
           if(_binding.edtNewPassword.getText().toString().equals(_binding.edtConfirmPassword.getText().toString())){
               if (CheckNetwork.isNetworkAvailable(_context)) {
                   HashMap<String, Object> postparams = new HashMap<>();
                   postparams.put("password", _binding.edtNewPassword.getText().toString());
//                   postparams.put("userid",  Helper.getUserID(_context));
//                   Log.e("#########"," "+Helper.getUserID(_context));

                   VolleyService.volleyservicePostRequest(_context, _context.getString(R.string.jsonobjectrequest),
                           ServiceConstants.CHANGE_PASSWORD, postparams, Helper.headerParams(_context), (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
               } else {
                   Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
               }
           }
        }
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonObject, int serviceno) {
        Log.e("#######"+serviceno, " "+new Gson().toJson(jsonObject));
        switch (serviceno) {
            case 1:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (response.equalsIgnoreCase("Sucess")) {
                            AquaConstants.putIntent(_context, LoginActivity.class, 0, null);
                        } else {
                            Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                        }
                    }else {
                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "" + e, AquaConstants.HOLD);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray response, int serviceno) {

    }

}
