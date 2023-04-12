package com.odos.smartaqua.dashboard;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.tabs.TabLayout;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.chat.ChatListActivity;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.databinding.ActivityDashboardBinding;
import com.odos.smartaqua.sliders.TextSliderView;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashBoardViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityDashboardBinding _activityDashboardBinding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private List<EventDay> events;
    private String tankId, tankName, cultureId, cultureaccess;
    private int tankPosition;
    private String response;

    public DashBoardViewModel(Context context, ActivityDashboardBinding activityDashboardBinding) {
        this._context = context;
        this._activityDashboardBinding = activityDashboardBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        _activityDashboardBinding.txtCounts.setSelected(true);
        _activityDashboardBinding.txtCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counts = "TODAY COUNT : 100C-250 : 90C-200 : 80C-180 : 70C-170 : 60C-150 : 50C-120 : 40C-100";
                Helper.showMessage(_context, counts.replaceAll(":", "\n\n").replaceAll("-", "    :  "), AquaConstants.HOLD);
            }
        });
        getSliderImages(_context);
        bottomNavigationMenu();
        loadCultures();
    }

    private void bottomNavigationMenu() {
        _activityDashboardBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.stock:
                    loadStockMenu(_context);
                    break;
                case R.id.chat:
                    AquaConstants.putIntent(_context, ChatListActivity.class, AquaConstants.HOLD, null);
                    break;
                case R.id.create:
                    loadCreateMenu(_context);
                    break;
                case R.id.compare:
                    loadCompareMenu(_context);
                    break;
                case R.id.more:
                    loadMoreMenu(_context);
                    break;
                default:
            }
            return true;
        });

    }

    private void getSliderImages(Context _context) {   // SLIDERS...
        for (int i = 0; i < 5; i++) {
            TextSliderView textSliderView = new TextSliderView(_context);
            textSliderView
                    .description("aquatechie")
                    .image("https://api.androidhive.info/images/minion.jpg");
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("sliderlink", "http://smartaquatech.com");
            textSliderView.getBundle()
                    .putString("tabname", "aquatechie");
            textSliderView.getBundle().putString("arrayid", String.valueOf(i));
            _activityDashboardBinding.sliderLayout.addSlider(textSliderView);
        }
    }

    public void loadCultures() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_CULTURES + Helper.getUserID(_context), null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, false);

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
                                    _activityDashboardBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                                } else {
                                    _activityDashboardBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                }
                                DashBoardViewPagerAdapter daDashBoardViewPagerAdapter = new DashBoardViewPagerAdapter(_context, ((AppCompatActivity) _context).getSupportFragmentManager(),
                                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, jsonArray);
                                _activityDashboardBinding.pager.setAdapter(daDashBoardViewPagerAdapter);
                                _activityDashboardBinding.tabLayout.setupWithViewPager(_activityDashboardBinding.pager, true);
                                _activityDashboardBinding.pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_activityDashboardBinding.tabLayout));
                                _activityDashboardBinding.pager.setCurrentItem(0);
                                _activityDashboardBinding.pager.setOffscreenPageLimit(jsonArray.length());
                                _activityDashboardBinding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void loadStockMenu(final Context activity) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_stock);
        myDialog.getWindow().setGravity(Gravity.START | Gravity.BOTTOM);
        ArrayList<String> arrayList;
        myDialog.getWindow().setGravity(Gravity.START | Gravity.BOTTOM);
        arrayList = new ArrayList<>(Arrays.asList("Add Brand", "Add Product", "Add Invoice", "Add Stock", "Stock Information", "Bills", "Expends"));
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BottomMenuAdapter(_context, arrayList, 1, tankId, tankName, cultureId, tankPosition, cultureaccess,response));
        myDialog.show();
    }

    private void loadCreateMenu(final Context activity) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_create);

        ArrayList<String> arrayList;
        myDialog.getWindow().setGravity(Gravity.BOTTOM);
        arrayList = new ArrayList<>(Arrays.asList("Pond", "Add Culture","Pond Preparation", "Add Stocking", "Add Feed", "Add CheckTray", "Add Treatments"));
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BottomMenuAdapter(_context, arrayList, 3, tankId, tankName, cultureId, tankPosition, cultureaccess,response));
        myDialog.show();
    }

    private void loadCompareMenu(final Context activity) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_compare);
        myDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>(Arrays.asList("Feed Report", "CheckTray Report", "Lab report", "Growth Report", "Expenditures"));
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BottomMenuAdapter(_context, arrayList, 4, tankId, tankName, cultureId, tankPosition, cultureaccess,response));
        myDialog.show();
    }

    private void loadMoreMenu(final Context activity) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_more);
        myDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>(Arrays.asList("Feed Observation", "CheckTray Observation", "Growth Observation", "Lab Observation", "Add Expends"));
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BottomMenuAdapter(_context, arrayList, 5, tankId, tankName, cultureId, tankPosition, cultureaccess,response));
        myDialog.show();
    }

}