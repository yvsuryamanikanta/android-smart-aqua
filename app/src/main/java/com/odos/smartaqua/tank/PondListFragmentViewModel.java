package com.odos.smartaqua.tank;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;

import com.applandeo.materialcalendarview.EventDay;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentPondlistBinding;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PondListFragmentViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private FragmentPondlistBinding _fragmentBinding;
    private String[] titles;
    private int[] icons;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private String tankId, cultureId, tankName, cultureResponse;
    private int tankPosition;
    private List<EventDay> events;

    public PondListFragmentViewModel(int position, Context context, FragmentPondlistBinding pondlistBinding, String tId, String cId, String tName, String response) {
        this._context = context;
        this.tankPosition = position;
        this.tankId = tId;
        this.cultureId = cId;
        this.tankName = tName;
        this.cultureResponse = response;
        this._fragmentBinding = pondlistBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
//        loadData();
    }
    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_TANKLIST + Helper.getUserID(_context) + "/" + cultureId + "/" + tankId, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void jsonObjectResponse(String service, JSONObject jsonObject, int serviceno) {
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}