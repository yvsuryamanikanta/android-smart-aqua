package com.odos.smartaqua.growth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.feed.FeedListFragment;

import java.util.ArrayList;


public class GrowthReportViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CultureModel> _cultureModelArrayList;
    private Context _context;

    public GrowthReportViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<CultureModel> cultureModelArrayList) {
        super(fm, behavior);
        this._context = context;
        this._cultureModelArrayList = cultureModelArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int cultureId = _cultureModelArrayList.get(position).getCultureid();
        String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
        return GrowthReportFragment.newInstance(cultureId,cultureAccess);
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