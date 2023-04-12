package com.odos.smartaqua.tank;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.databinding.ActivityPondlistBinding;
import com.odos.smartaqua.databinding.ActivityPreparationlistBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreparationListViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityPreparationlistBinding _activityPreparationListBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private String[] values;
    private String response;
    private String tankId, tankName, cultureId, cultureaccess;
    private int tankPosition;

    public PreparationListViewModel(Context context, ActivityPreparationlistBinding activityPreparationlistBinding) {
        this._context = context;
        this._activityPreparationListBinding = activityPreparationlistBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        values = ((Activity) _context).getIntent().getStringArrayExtra("values");
        Helper.getCurrentLocation(_context);
        loadData();
    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_CULTURES + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
    }



    public void addPreparation(View v) {
        AquaConstants.putIntent(_context, PondPreparationActivity.class, AquaConstants.HOLD, new String[]{"1"});
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
                    response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                if (jsonArray.length() < 4) {
                                    _activityPreparationListBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                                } else {
                                    _activityPreparationListBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
                                PreparationListViewPagerAdapter adapter = new PreparationListViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, jsonArray);
                                _activityPreparationListBinding.pager.setAdapter(adapter);
                                _activityPreparationListBinding.tabLayout.setupWithViewPager(_activityPreparationListBinding.pager, true);
                                _activityPreparationListBinding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_activityPreparationListBinding.tabLayout));
                                _activityPreparationListBinding.pager.setCurrentItem(0);
                                _activityPreparationListBinding.pager.setOffscreenPageLimit(jsonArray.length());
                                _activityPreparationListBinding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {
                                        int position = tab.getPosition();
                                        try {
                                            JSONObject jsonObject = jsonArray.getJSONObject(position);
                                            tankId = jsonObject.getString("tankid");
                                            tankName = jsonObject.getString("tankname");
                                            cultureId = jsonObject.getString("cultureid");
                                            cultureaccess = jsonObject.getString("cultureaccess");
                                            tankPosition = position;
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });
                            } else {
                                Toast.makeText(_context, "no culture created Please add culture", Toast.LENGTH_SHORT).show();
                                AquaConstants.putIntent(_context, AddCultureActivity.class, AquaConstants.HOLD, null);
                            }
                        } else {
                            Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                        }
                    } else {
                        Toast.makeText(_context, "no culture created Please add culture", Toast.LENGTH_SHORT).show();
                        AquaConstants.putIntent(_context, AddCultureActivity.class, AquaConstants.HOLD, null);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }
}
