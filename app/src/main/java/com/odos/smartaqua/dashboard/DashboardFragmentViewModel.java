package com.odos.smartaqua.dashboard;

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

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
import com.odos.smartaqua.databinding.FragmentDashboardBinding;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.ListBottomSheetFragment;

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

public class DashboardFragmentViewModel extends BaseObservable implements ServiceAsyncResponse {

    private Context _context;
    private FragmentDashboardBinding _fragmentBinding;
    private String[] titles;
    private int[] icons;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private String tankId, cultureId, tankName, cultureResponse;
    private int tankPosition;
    private List<EventDay> events;
    private CalendarView calendarView;

    public DashboardFragmentViewModel(int position, Context context, FragmentDashboardBinding activityDashboardBinding, String tId, String cId, String tName, String response) {
        this._context = context;
        this.tankPosition = position;
        this.tankId = tId;
        this.cultureId = cId;
        this.tankName = tName;
        this.cultureResponse = response;
        this._fragmentBinding = activityDashboardBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
        initCalender();
    }

    private void initCalender() {
        calendarView = new CalendarView(_context);
        calendarView.setSwipeEnabled(true);
        calendarView.setHeaderColor(R.color.colorPrimary);
        calendarView.setHeaderLabelColor(R.color.white);
        calendarView.setAbbreviationsBarVisibility(View.VISIBLE);
        calendarView.setCalendarDayLayout(R.layout.custom_view_calender);
        _fragmentBinding.llCalender.addView(calendarView);
    }

    public void loadCalander() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.GET_DASHBOARD + Helper.getUserID(_context) + "/" + cultureId + "/" + tankId, null, Helper.headerParams(_context),
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
        switch (serviceno) {
            case 1:
                try {
                    events = new ArrayList<>();
                    _fragmentBinding.llCalender.removeAllViews();
                    initCalender();
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
                            calendarView.setOnDayClickListener(new OnDayClickListener() {
                                @Override
                                public void onDayClick(@NonNull EventDay eventDay) {
                                    Calendar clickedDayCalendar = eventDay.getCalendar();
                                    if (events.contains(eventDay)) {
                                        String selectedDate = getDate(clickedDayCalendar);
                                        ListBottomSheetFragment listBottomSheetFragment =
                                                ListBottomSheetFragment.newInstance(hashMap.get(selectedDate), selectedDate, tankId, tankPosition, cultureResponse);
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