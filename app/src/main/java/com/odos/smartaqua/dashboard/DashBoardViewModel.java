package com.odos.smartaqua.dashboard;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.databinding.ActivityDashboardBinding;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.prelogin.sighnup.UserRolesAdapter;
import com.odos.smartaqua.sliders.TextSliderView;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.ListBottomSheetFragment;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DashBoardViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private ActivityDashboardBinding _activityDashboardBinding;
    private String[] titles;
    private int[] icons;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private String tankId, tankName;
    private int tankPosition;
    private List<EventDay> events;

    public DashBoardViewModel(Context context, ActivityDashboardBinding activityDashboardBinding) {
        this._context = context;
        this._activityDashboardBinding = activityDashboardBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        getSliderImages(_context);
        bottomNavigationMenu();
    }

    private void bottomNavigationMenu(){
        _activityDashboardBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home1:
                    if (Helper.getCameraPermission(_context)) {
                        UploadBottomSheetFragment addPhotoBottomDialogFragment =
                                UploadBottomSheetFragment.newInstance();
                        addPhotoBottomDialogFragment.show(((FragmentActivity) _context).getSupportFragmentManager(),
                                "tag");
                    } else {
                        Helper.getCameraPermission(_context);
                    }
                    break;
                case R.id.home2:
                    Toast.makeText(_context, "Home2.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home3:
                    Toast.makeText(_context, "Home3.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home4:
                    Toast.makeText(_context, "Home4.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home5:
                    Toast.makeText(_context, "Home5.", Toast.LENGTH_SHORT).show();
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
                                        String cultureid = jsonObject1.getString("cultureid");
                                        String tankname = jsonObject1.getString("tankname");
                                        String cultureimage = jsonObject1.getString("cultureimage");
                                        UserRoles userRoles = new UserRoles(roleID, cultureid, tankname, true);
                                        userRolesArrayList.add(userRoles);
                                    } catch (Exception e) {
                                        Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                                    }
                                }
                                UserRolesAdapter userRolesAdapter = new UserRolesAdapter(_context, userRolesArrayList);
                                _activityDashboardBinding.spinTanks.setAdapter(userRolesAdapter);
                                _activityDashboardBinding.spinTanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        UserRoles userRoles = (UserRoles) userRolesArrayList.get(position);
                                        tankId = String.valueOf(userRoles.getRoleID());
                                        tankName = userRoles.getRoleName();
                                        tankPosition = position;
                                        Long cultureId = Long.parseLong(userRoles.getRoleCode());
                                        VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                                ServiceConstants.GET_DASHBOARD + Helper.getUserID(_context) + "/" + cultureId + "/" + tankId, null, Helper.headerParams(_context),
                                                (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
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
            case 2:
                try {
                    events = new ArrayList<>();
                    _activityDashboardBinding.llCalender.removeAllViews();
                    CalendarView calendarView = new CalendarView(_context);
                    _activityDashboardBinding.llCalender.addView(calendarView);
                    String status = jsonObject.getString("status");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            HashMap<String, String> hashMap = new HashMap<>();
                            if (jsonArray.length() != 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    String feeddate = jsonObject1.getString("date");
                                    String groupname = jsonObject1.getString("groupname");
                                    hashMap.merge(feeddate, groupname, (a, b) -> a + b);
                                }
                            }
                            Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();
                            for (Map.Entry<String, String> entry : entrySet) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = formatter.parse(entry.getKey());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                if (entry.getValue().contains("FEED") && entry.getValue().contains("CHECKTRAY") && entry.getValue().contains("LAB")) {
                                    events.add(new EventDay(calendar, R.drawable.threedots));
                                } else if (entry.getValue().contains("FEED") && entry.getValue().contains("CHECKTRAY")) {
                                    events.add(new EventDay(calendar, R.drawable.twodots));
                                } else if (entry.getValue().contains("FEED") && entry.getValue().contains("LAB")) {
                                    events.add(new EventDay(calendar, R.drawable.twodots));
                                } else if (entry.getValue().contains("CHECKTRAY") && entry.getValue().contains("LAB")) {
                                    events.add(new EventDay(calendar, R.drawable.twodots));
                                } else if (entry.getValue().contains("FEED")) {
                                    events.add(new EventDay(calendar, R.mipmap.feedicon));
                                } else if (entry.getValue().contains("CHECKTRAY")) {
                                    events.add(new EventDay(calendar, R.mipmap.obsvicon));
                                } else if (entry.getValue().contains("LAB")) {
                                    events.add(new EventDay(calendar, R.mipmap.obsvicon));
                                } else {
                                    events.add(new EventDay(calendar, R.mipmap.obsvicon));
                                }

                            }
                            calendarView.setEvents(events);
                            calendarView.setHeaderLabelColor(R.color.white);
                            calendarView.setSwipeEnabled(true);
                            calendarView.setAbbreviationsBarVisibility(View.VISIBLE);
                            calendarView.setCalendarDayLayout(R.layout.custom_view_calender);
                            calendarView.setOnDayClickListener(new OnDayClickListener() {
                                @Override
                                public void onDayClick(@NonNull EventDay eventDay) {
                                    Calendar clickedDayCalendar = eventDay.getCalendar();
                                    if (events.contains(eventDay)) {
                                        String selectedDate = getDate(clickedDayCalendar);
                                        ListBottomSheetFragment listBottomSheetFragment =
                                                ListBottomSheetFragment.newInstance(hashMap.get(selectedDate), selectedDate, tankId, tankPosition);
                                        listBottomSheetFragment.show(((FragmentActivity) _context).getSupportFragmentManager(),
                                                "tag");
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.HOLD);
                }
                break;

        }

    }

    private String getDate(Calendar calendar) {
        String date = DateFormat.format("yyyy-MM-dd", calendar).toString();
        return date;
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }
}
