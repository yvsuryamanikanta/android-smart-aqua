package com.odos.smartaqua.common;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.brand.AddBrandActivity;
import com.odos.smartaqua.checktray.AddChecktrayActivity;
import com.odos.smartaqua.checktray.ChecktrayObservationActivity;
import com.odos.smartaqua.cultures.AddCultureActivity;
import com.odos.smartaqua.dashboard.BottomMenuAdapter;
import com.odos.smartaqua.databinding.ActivityBaseBinding;
import com.odos.smartaqua.feed.TankViewPagerActivity;
import com.odos.smartaqua.growth.GrowthObservationActivity;
import com.odos.smartaqua.lab.LabObservationActivity;
import com.odos.smartaqua.prelogin.login.LoginActivity;
import com.odos.smartaqua.tank.PondListActivity;
import com.odos.smartaqua.utils.ASPManager;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.AddProductActivity;
import com.odos.smartaqua.warehouse.products.AddProductCatgActivity;
import com.odos.smartaqua.warehouse.quantity.AddQtyCatgActivity;
import com.odos.smartaqua.warehouse.stock.AddStockActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class BaseActivity extends AppCompatActivity {

    public ActivityBaseBinding activityBaseBinding;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public FrameLayout baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaseBinding = DataBindingUtil.setContentView(BaseActivity.this, R.layout.activity_base);
        baseFragment = (FrameLayout) findViewById(R.id.baseFragment);
        activityBaseBinding.setBaseViewModel(new BaseViewModel(BaseActivity.this, activityBaseBinding));
        activityBaseBinding.executePendingBindings();
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.GONE);
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.GONE);
        Helper.setTypeFace(BaseActivity.this, getString(R.string.contentfont), ActivityBaseBinding.inflate(getLayoutInflater()).mytoolbar.txtTootlbarTitle);
        setToolBarIcon(R.drawable.menu);
        setupDrawer();
        addDrawerItems();
        activityBaseBinding.mytoolbar.imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });
        bottomNavigationMenu();
    }


    public void doLogout() {
        final Dialog d = new Dialog(BaseActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_showupdatemessage);
        d.show();
        TextView txt_content_updateapp = (TextView) d.findViewById(R.id.txt_content_updateapp);
        TextView txt_ok_update = (TextView) d.findViewById(R.id.txt_ok_update);
        TextView txt_update_no = (TextView) d.findViewById(R.id.txt_update_no);
        TextView txt_title_show = (TextView) d.findViewById(R.id.txt_title_show);
        Helper.setTypeFace(BaseActivity.this, getString(R.string.contentfont), txt_ok_update);
        Helper.setTypeFace(BaseActivity.this, getString(R.string.contentfont), txt_update_no);
        Helper.setTypeFace(BaseActivity.this, getString(R.string.contentfont), txt_title_show);
        Helper.setTypeFace(BaseActivity.this, getString(R.string.contentfont), txt_content_updateapp);
        txt_content_updateapp.setText("Do You Want to logout now? \n");
        txt_ok_update.setText("YES");
        txt_update_no.setText("NO");
        txt_ok_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ASPManager.setKey(BaseActivity.this, AquaConstants.USER_ID, "");
                ASPManager.setKey(BaseActivity.this, AquaConstants.ROLE_ID, "");
                ASPManager.setKey(BaseActivity.this, AquaConstants.FCM_ID, "");
                ASPManager.setKey(BaseActivity.this, AquaConstants.USER_RESPONSE, "");
                ASPManager.setKey(BaseActivity.this, AquaConstants.ROLE_CODE, "");
                ASPManager.setKey(BaseActivity.this, AquaConstants.IS_LOGGED, false);
                AquaConstants.claerAllActivities(BaseActivity.this, LoginActivity.class,null);
            }
        });

        txt_update_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
    }

    public void setToolBarIcon(int flag) {
        if (flag == 1) {
            activityBaseBinding.mytoolbar.toolbar.setNavigationIcon(R.drawable.menu);
        } else {
            activityBaseBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            activityBaseBinding.mytoolbar.toolbar.setNavigationIcon(R.drawable.back);
        }
    }

    public void setToolBarIconClick(int flag) {
        activityBaseBinding.mytoolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    finish();
                } else {
                    if (activityBaseBinding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        activityBaseBinding.drawerLayout.closeDrawer(Gravity.LEFT);
                    } else {
                        activityBaseBinding.drawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
            }
        });
    }

    public void addDrawerItems() {
        if (Helper.getUsrtType(BaseActivity.this) != null) {
            if (Helper.getUsrtType(BaseActivity.this).equalsIgnoreCase("ADMIN")) {
                String[] from;
                from = new String[]{"Add Tank", "Create Culture", "Add Brand", "Quantity Category",
                        "Product Category", "Create Product","Create Stock", "Create Feed",
                         "Add CheckTray", "CheckTray Observation", "Lab Observation", "Growth Observation"};
                DrawerAdapter adapter1 = new DrawerAdapter(BaseActivity.this, from);
                activityBaseBinding.navList.setAdapter(adapter1);
                activityBaseBinding.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        } else {
                            switch (position) {
                                case 0:
                                    AquaConstants.putIntent(BaseActivity.this, PondListActivity.class, AquaConstants.HOLD, new String[]{"1"});
                                    break;
                                case 1:
                                    AquaConstants.putIntent(BaseActivity.this, AddCultureActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 2:
                                    AquaConstants.putIntent(BaseActivity.this, AddBrandActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 3:
                                    AquaConstants.putIntent(BaseActivity.this, AddQtyCatgActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 4:
                                    AquaConstants.putIntent(BaseActivity.this, AddProductCatgActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 5:
                                    AquaConstants.putIntent(BaseActivity.this, AddProductActivity.class, AquaConstants.HOLD, null);
                                    break;
                               /* case 6:
                                    AquaConstants.putIntent(BaseActivity.this, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                                    break;*/
                                case 6:
                                    AquaConstants.putIntent(BaseActivity.this, AddStockActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 7:
                                    AquaConstants.putIntent(BaseActivity.this, TankViewPagerActivity.class, AquaConstants.HOLD, null);
                                    break;
                               /* case 8:
                                    AquaConstants.putIntent(BaseActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, new String[]{"0"});
                                    break;*/
                                case 8:
                                    AquaConstants.putIntent(BaseActivity.this, AddChecktrayActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 9:
                                    AquaConstants.putIntent(BaseActivity.this, ChecktrayObservationActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 10:
                                    AquaConstants.putIntent(BaseActivity.this, LabObservationActivity.class, AquaConstants.HOLD, null);
                                    break;

                                case 11:
                                    AquaConstants.putIntent(BaseActivity.this, GrowthObservationActivity.class, AquaConstants.HOLD, null);
                                    break;
                            }
                        }
                    }
                });
            }if (Helper.getUsrtType(BaseActivity.this).equalsIgnoreCase("ADMIN")) {
                String[] from;
                from = new String[]{"Add Tank", "Create Culture", "Add Brand", "Quantity Category",
                        "Product Category", "Create Product","Create Stock", "Create Feed",
                         "Add Checktray", "Checktray Observation", "Lab Observation", "Growth Observation"};
                DrawerAdapter adapter1 = new DrawerAdapter(BaseActivity.this, from);
                activityBaseBinding.navList.setAdapter(adapter1);
                activityBaseBinding.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        } else {
                            switch (position) {
                                case 0:
                                    AquaConstants.putIntent(BaseActivity.this, PondListActivity.class, AquaConstants.HOLD, new String[]{"1"});
                                    break;
                                case 1:
                                    AquaConstants.putIntent(BaseActivity.this, AddCultureActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 2:
                                    AquaConstants.putIntent(BaseActivity.this, AddBrandActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 3:
                                    AquaConstants.putIntent(BaseActivity.this, AddQtyCatgActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 4:
                                    AquaConstants.putIntent(BaseActivity.this, AddProductCatgActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 5:
                                    AquaConstants.putIntent(BaseActivity.this, AddProductActivity.class, AquaConstants.HOLD, null);
                                    break;
                               /* case 6:
                                    AquaConstants.putIntent(BaseActivity.this, AddInvoiceActivity.class, AquaConstants.HOLD, null);
                                    break;*/
                                case 6:
                                    AquaConstants.putIntent(BaseActivity.this, AddStockActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 7:
                                    AquaConstants.putIntent(BaseActivity.this, TankViewPagerActivity.class, AquaConstants.HOLD, null);
                                    break;
                               /* case 8:
                                    AquaConstants.putIntent(BaseActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, new String[]{"0"});
                                    break;*/
                                case 8:
                                    AquaConstants.putIntent(BaseActivity.this, AddChecktrayActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 9:
                                    AquaConstants.putIntent(BaseActivity.this, ChecktrayObservationActivity.class, AquaConstants.HOLD, null);
                                    break;
                                case 10:
                                    AquaConstants.putIntent(BaseActivity.this, LabObservationActivity.class, AquaConstants.HOLD, null);
                                    break;

                                case 11:
                                    AquaConstants.putIntent(BaseActivity.this, GrowthObservationActivity.class, AquaConstants.HOLD, null);
                                    break;
                            }
                        }
                    }
                });
            } else if (Helper.getUsrtType(BaseActivity.this).equalsIgnoreCase("L")) {
                String[] from;
                from = new String[]{};
                DrawerAdapter adapter1 = new DrawerAdapter(BaseActivity.this, from);
                activityBaseBinding.navList.setAdapter(adapter1);
            } else if (Helper.getUsrtType(BaseActivity.this).equalsIgnoreCase("T")) {
                String[] from;
                from = new String[]{};
                DrawerAdapter adapter1 = new DrawerAdapter(BaseActivity.this, from);
                activityBaseBinding.navList.setAdapter(adapter1);
            }
        }


    }


    private void setupDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityBaseBinding.drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        activityBaseBinding.drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }


    public boolean checkRutimePermissions(String permissionname) {
        if (ContextCompat.checkSelfPermission(BaseActivity.this, permissionname)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (activityBaseBinding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            activityBaseBinding.drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
    private void bottomNavigationMenu() {
        activityBaseBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.stock:
                    loadBottomMenu(BaseActivity.this,1,R.layout.custom_alert_stock);
                    break;
                case R.id.lab:
                    loadBottomMenu(BaseActivity.this,2,R.layout.custom_alert_lab);
                    break;
                case R.id.create:
                    loadBottomMenu(BaseActivity.this,3,R.layout.custom_alert_create);
                    break;
                case R.id.compare:
                    loadBottomMenu(BaseActivity.this,4,R.layout.custom_alert_compare);
                    break;
                case R.id.more:
                    loadBottomMenu(BaseActivity.this,5,R.layout.custom_alert_more);
                    break;
                default:
            }
            return true;
        });

    }

    private void loadBottomMenu(final Context activity, int flag, int layout) {
        final Dialog myDialog = new Dialog(activity, R.style.ThemeDialogCustom);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(layout);
        ArrayList<String> arrayList;
        if(flag == 1){
            myDialog.getWindow().setGravity(Gravity.START | Gravity.BOTTOM);
            arrayList = new ArrayList<>(Arrays.asList("Brands", "Products", "Stock", "Add Expenses"));
        }else if(flag == 2){
            myDialog.getWindow().setGravity(Gravity.START | Gravity.BOTTOM);
            arrayList = new ArrayList<>(Arrays.asList("Water Analisys", "PCR Analisys", "Soil Analisys", "Animal Analisys"));
        }else if(flag == 3){
            myDialog.getWindow().setGravity(Gravity.BOTTOM);
            arrayList = new ArrayList<>(Arrays.asList("Pond Info", "Add Culture", "Pond Preparation", "Add Stocking", "Add Feed", "Add CheckTray"));
        }else if(flag == 4){
            myDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);
            arrayList = new ArrayList<>(Arrays.asList("Feed Report", "CheckTray Report", "Water analysis report", "PCR report", "Soil Report","Animal Report","Growth Report", "Treatments","Expenditures"));
        }else if(flag == 5){
            myDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);
            arrayList = new ArrayList<>(Arrays.asList("CheckTray Observation", "Growth Observation","Disease and Treatments","Profile"));
        }else{
            arrayList = new ArrayList<>();
        }
        RecyclerView recyclerView = myDialog.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BaseActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BottomMenuAdapter(BaseActivity.this, arrayList, flag));
        myDialog.show();
    }
}
