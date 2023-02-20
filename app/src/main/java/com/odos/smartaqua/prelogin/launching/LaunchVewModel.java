package com.odos.smartaqua.prelogin.launching;

import android.content.Context;
import android.content.Intent;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.dashboard.DashBoardActivity;
import com.odos.smartaqua.databinding.ActivityLauncherBinding;
import com.odos.smartaqua.prelogin.login.LoginActivity;
import com.odos.smartaqua.shocaseview.ShowCaseActivity;
import com.odos.smartaqua.tank.AddPondActivity;
import com.odos.smartaqua.utils.ASPManager;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Observable;

public class LaunchVewModel extends Observable implements ServiceAsyncResponse {


    private Context _ctx;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ActivityLauncherBinding _activityLauncherBinding;

    public LaunchVewModel(Context context, ActivityLauncherBinding activityLauncherBinding) {
        this._ctx = context;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        _activityLauncherBinding = activityLauncherBinding;
        hitVersionDetailsApi(_ctx);
    }

    private void hitVersionDetailsApi(final Context _context) {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_VERSION, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), 0);
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
                            JSONObject jsonObject1 = new JSONObject(response);
                            boolean mandatory = jsonObject1.getBoolean("ismandatory");
                            int versionCode = jsonObject1.getInt("versioncode");
                            String updatePath = jsonObject1.getString("updatepath");
                            String updateMessage = jsonObject1.getString("updatemessage");
                            int current_app_version_code = Helper.getVersioncode(_ctx);

                            if (current_app_version_code != versionCode) {
                                Helper.updateDialog(_ctx, updatePath, updateMessage, mandatory);
                            } else {
                                boolean isLogged = ASPManager.getKey(_ctx, AquaConstants.IS_LOGGED, false);
                                if (isLogged) {
                                    String rolecode = ASPManager.getKey(_ctx, AquaConstants.ROLE_CODE, null);
                                    if (rolecode.equalsIgnoreCase("F")) {
                                        VolleyService.volleyGetRequest(_ctx, _ctx.getString(R.string.jsonobjectrequest),
                                                ServiceConstants.GET_TANKLIST + Helper.getUserID(_ctx),
                                                null, Helper.headerParams(_ctx), (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
                                    } else if (rolecode.equalsIgnoreCase("L")) {
                                        AquaConstants.claerAllActivities(_ctx, DashBoardActivity.class,null);
                                    } else if (rolecode.equalsIgnoreCase("T")) {
                                        AquaConstants.claerAllActivities(_ctx, DashBoardActivity.class,null);
                                    }
                                } else {
                                    String[] values = new String[]{""};
                                    AquaConstants.putIntent(_ctx, LoginActivity.class, AquaConstants.FINISH, values);
                                }
                            }
                        }
                    } else {
                        Helper.showMessage(_ctx, "something went wrong please restart app once.", AquaConstants.FINISH);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_ctx, "something went wrong please restart app once." + e.getMessage(), AquaConstants.FINISH);
                }
                break;

            case 2:
                try {
                    String status = jsonObject.getString("status");
                    String statusCode = jsonObject.getString("statusCode");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() != 0) {
                            AquaConstants.putIntent(_ctx, DashBoardActivity.class, AquaConstants.FINISH, null);
                        } else {
                            AquaConstants.putIntent(_ctx, AddPondActivity.class, AquaConstants.FINISH, new String[]{"0"});
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_ctx, "something went wrong please restart app once." + e.getMessage(), AquaConstants.FINISH);
                }

                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonArray, int serviceno) {
    }

}
