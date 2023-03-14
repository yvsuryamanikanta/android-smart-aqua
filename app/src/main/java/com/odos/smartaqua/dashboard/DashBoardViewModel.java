package com.odos.smartaqua.dashboard;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.AddBrandActivity;
import com.odos.smartaqua.chat.ChatListActivity;
import com.odos.smartaqua.checktray.AddChecktrayActivity;
import com.odos.smartaqua.checktray.ChecktrayInfoActivity;
import com.odos.smartaqua.checktray.ChecktrayObservationActivity;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.databinding.ActivityDashboardBinding;
import com.odos.smartaqua.feed.AddFeedActivity;
import com.odos.smartaqua.feed.FeedObservationActivity;
import com.odos.smartaqua.growth.GrowthObservationActivity;
import com.odos.smartaqua.lab.LabObservationActivity;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.prelogin.sighnup.UserRolesAdapter;
import com.odos.smartaqua.sliders.TextSliderView;
import com.odos.smartaqua.tank.AddPondActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.ListBottomSheetFragment;
import com.odos.smartaqua.warehouse.invoice.AddInvoiceActivity;
import com.odos.smartaqua.warehouse.products.AddProductActivity;
import com.odos.smartaqua.warehouse.stock.AddStockActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    }

    private void bottomNavigationMenu() {
        _activityDashboardBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.stock:
                    showAlert(_context);
                    break;
                case R.id.chat:
                    AquaConstants.putIntent(_context, ChatListActivity.class, AquaConstants.HOLD, null);
                    break;
                case R.id.create:
                    showAlert3(_context, 3);
                    break;
                case R.id.compare:
                    showAlert4(_context);
                    break;
                case R.id.more:
                    showAlert5(_context);
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
                                UserRoles userRoles1 = new UserRoles(0, "00", "Select your Tank", true);
                                userRolesArrayList.add(userRoles1);
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
                                        if (userRoles.getRoleID() == 0) {
                                            CalendarView calendarView = new CalendarView(_context);
                                            _activityDashboardBinding.llCalender.addView(calendarView);
                                        } else {
                                            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                                                    ServiceConstants.GET_DASHBOARD + Helper.getUserID(_context) + "/" + cultureId + "/" + tankId, null, Helper.headerParams(_context),
                                                    (ServiceAsyncResponse) serviceAsyncResponse, 2, true);
                                        }
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

    private void showAlert(final Context activity) {
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
        recyclerView.setAdapter(new ListAdapter(_context, arrayList, 1));
        myDialog.show();
    }

    private void showAlert3(final Context activity, int flag) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.custom_alert_create);

        ArrayList<String> arrayList;
        myDialog.getWindow().setGravity(Gravity.BOTTOM);
        arrayList = new ArrayList<>(Arrays.asList("Add Tank / Pond", "Add Culture", "Add Access", "Add Feed", "Add CheckTray", "Add Treatments"));
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ListAdapter(_context, arrayList, 3));
        myDialog.show();
    }

    private void showAlert4(final Context activity) {
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
        recyclerView.setAdapter(new ListAdapter(_context, arrayList, 4));
        myDialog.show();
    }

    private void showAlert5(final Context activity) {
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
        recyclerView.setAdapter(new ListAdapter(_context, arrayList, 5));
        myDialog.show();
    }

    public static class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
        ArrayList<String> data;
        private LayoutInflater layoutInflater;
        private Context _context;
        private int _flag;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) itemView.findViewById(R.id.txtView);
            }
        }

        public ListAdapter(Context context, ArrayList<String> arrayList, int flag) {
            this.data = arrayList;
            this._context = context;
            this._flag = flag;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(parent.getContext());
            }
            View layout = layoutInflater.inflate(R.layout.list_item_create, parent, false);
            return new MyViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            holder.textView.setText("" + data.get(position));
            holder.textView.setOnClickListener(v -> {
                if (_flag == 1) {
                    switch (position) {
                        case 0:
                            AquaConstants.putIntent(_context, AddBrandActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 1:
                            AquaConstants.putIntent(_context, AddProductActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 2:
                            AquaConstants.putIntent(_context, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 3:
                            AquaConstants.putIntent(_context, AddStockActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 4:
                            // AquaConstants.putIntent(_context, AddStockActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 5:
                            // AquaConstants.putIntent(_context, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 6:
                            Toast.makeText(_context, "expenditure", Toast.LENGTH_SHORT).show();
                            break;

                    }
                } else if (_flag == 3) {
                    switch (position) {
                        case 0:
                            AquaConstants.putIntent(_context, AddPondActivity.class, AquaConstants.HOLD, new String[]{"1"});
                            break;
                        case 1:
                            AquaConstants.putIntent(_context, AddCultureActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 2:
                            Toast.makeText(_context, "provide culture access", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            AquaConstants.putIntent(_context, AddFeedActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 4:
                            AquaConstants.putIntent(_context, AddChecktrayActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 5:
                            Toast.makeText(_context, "add treatment", Toast.LENGTH_SHORT).show();
                            break;
                    }

                } else if (_flag == 4) {
                    switch (position) {
                        case 0:
                            Toast.makeText(_context, "Feed report", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(_context, "CheckTray report", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(_context, "Lab report", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(_context, "Growth report", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(_context, "Expends report", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else if (_flag == 5) {
                    switch (position) {
                        case 0:
                            AquaConstants.putIntent(_context, FeedObservationActivity.class, AquaConstants.HOLD, new String[]{"1"});
                            break;
                        case 1:
                            AquaConstants.putIntent(_context, ChecktrayObservationActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 2:
                            AquaConstants.putIntent(_context, GrowthObservationActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 3:
                            AquaConstants.putIntent(_context, LabObservationActivity.class, AquaConstants.HOLD, null);
                            break;
                        case 4:
                            Toast.makeText(_context, "expends", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}