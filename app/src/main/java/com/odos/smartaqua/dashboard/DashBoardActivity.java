package com.odos.smartaqua.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityDashboardBinding;
import com.odos.smartaqua.feed.FeedListViewPagerActivity;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.ListBottomSheetFragment;
import com.odos.smartaqua.utils.UploadBottomSheetFragment;

public class DashBoardActivity extends BaseActivity implements ListBottomSheetFragment.ItemClickListener, UploadBottomSheetFragment.ItemClickListener {

    private ActivityDashboardBinding _activityDashboardBinding;
    private DashBoardViewModel dashBoardViewModel;

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

    @Override
    public void onItemClick(String item) {

    }
}
