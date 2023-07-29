package com.odos.smartaqua.feed;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.checktray.ChecktrayReportFragment;
import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.growth.GrowthReportFragment;
import com.odos.smartaqua.lab.LabReportFragment;
import com.odos.smartaqua.treatment.TreatmentsFragment;

import java.util.ArrayList;


public class FeedListViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CultureModel> _cultureModelArrayList;
    private Context _context;
    private String feedDate;
    private String item;

    public FeedListViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<CultureModel> cultureModelArrayList,String feedDate,String _item) {
        super(fm, behavior);
        this._context = context;
        this.feedDate = feedDate;
        this.item = _item;
        this._cultureModelArrayList = cultureModelArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(item.equalsIgnoreCase("feed")){
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
            return FeedListFragment.newInstance(cultureId,cultureAccess,feedDate);
        }else if(item.equalsIgnoreCase("checktray")){
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String tankId = _cultureModelArrayList.get(position).getTankid();
            return ChecktrayReportFragment.newInstance(cultureId,tankId);
        }else if(item.equalsIgnoreCase("lab")){
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
            return LabReportFragment.newInstance(cultureId,cultureAccess);
        }else if(item.equalsIgnoreCase("growth")){
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
            return GrowthReportFragment.newInstance(cultureId,cultureAccess);
        }else if(item.equalsIgnoreCase("treatment")){
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
            return TreatmentsFragment.newInstance(cultureId,cultureAccess);
        }else{
            int cultureId = _cultureModelArrayList.get(position).getCultureid();
            String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
            return FeedListFragment.newInstance(cultureId,cultureAccess,feedDate);
        }

    }

    @Override
    public int getCount() {
        return _cultureModelArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _cultureModelArrayList.get(position).getTankname();
    }
}