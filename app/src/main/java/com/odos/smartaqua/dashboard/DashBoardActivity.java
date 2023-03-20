package com.odos.smartaqua.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.odos.smartaqua.R;
import com.odos.smartaqua.chat.ChatFragment;
import com.odos.smartaqua.chat.ViewPagerFragmentAdapter;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityDashboardBinding;
import com.odos.smartaqua.feed.FeedListViewPagerActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.ListBottomSheetFragment;

public class DashBoardActivity extends BaseActivity implements ListBottomSheetFragment.ItemClickListener {

    private ActivityDashboardBinding _activityDashboardBinding;
    private DashBoardViewModel dashBoardViewModel;
    private ViewPagerFragmentAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(DashBoardActivity.this);
        _activityDashboardBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_dashboard, activityBaseBinding.baseFragment, true);
        dashBoardViewModel = new DashBoardViewModel(DashBoardActivity.this, _activityDashboardBinding);
        _activityDashboardBinding.setDashBoardViewModel(dashBoardViewModel);
        _activityDashboardBinding.executePendingBindings();
        activityBaseBinding.mytoolbar.imgSearch.setVisibility(View.VISIBLE);
        activityBaseBinding.mytoolbar.imgLogout.setVisibility(View.VISIBLE);
        activityBaseBinding.baseFragment.getLayoutParams().height = Helper.getDisplayheight(DashBoardActivity.this);
        setToolBarIconClick(1);
        setToolBarIcon(1);
        _activityDashboardBinding.bottomNavigation.setBackground(null);

        Log.e("###### -- Userid", " "+Helper.getUserID(DashBoardActivity.this) );
        initData();
    }

    @Override
    public void onItemClick(String item, String selectedDate, String tankId, int pos) {
        try {
            String[] tankdetails = new String[]{"" + pos, selectedDate, tankId, item};
            AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            /*if (item.equalsIgnoreCase("feed")) {
                String[] tankdetails = new String[]{""+pos,selectedDate,tankId};
                AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            } else if (item.equalsIgnoreCase("checktray")) {
                String[] tankdetails = new String[]{"1","Checktray Reports"};
                AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            } else if (item.equalsIgnoreCase("lab")) {
                String[] tankdetails = new String[]{"1","Lab Reports"};
                AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            }else if (item.equalsIgnoreCase("growth")) {
                String[] tankdetails = new String[]{"1","Growth Reports"};
                AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            }else if (item.equalsIgnoreCase("treatment")) {
                String[] tankdetails = new String[]{"1","Treatment"};
                AquaConstants.putIntent(DashBoardActivity.this, FeedListViewPagerActivity.class, AquaConstants.HOLD, tankdetails);
            }*/
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dashBoardViewModel.loadCultures();
    }
    public void initData() {
        myAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        myAdapter.addFragment(DashboardFragment.newInstance(0));
        myAdapter.addFragment(DashboardFragment.newInstance(1));
        myAdapter.addFragment(DashboardFragment.newInstance(2));
        // set Orientation in your ViewPager2
        _activityDashboardBinding.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        _activityDashboardBinding.viewpager.setAdapter(myAdapter);
//        _activityDashboardBinding.viewpager.setPageTransformer(new MarginPageTransformer(1500));
        _activityDashboardBinding.viewpager.setOffscreenPageLimit(1);
        _activityDashboardBinding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

//        new TabLayoutMediator(_activityDashboardBinding.slidingTabs, _activityDashboardBinding.viewpager, (tab, position) -> {
//            switch (position) {
//                case 0:
//                    tab.setText("Chat");
//                    break;
//                case 1:
//                    tab.setText("Report");
//                    break;
//                case 2:
//                    tab.setText("Details");
//                    break;
//            }
//        }).attach();

    }

}
