package com.odos.smartaqua.checktray;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.feed.FeedListFragment;

import java.util.ArrayList;


public class ChecktrayReportViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CultureModel> _checktrayObsvModelArrayList;
    private Context _context;

    public ChecktrayReportViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<CultureModel> checktrayObsvModelArrayList) {
        super(fm, behavior);
        this._context = context;
        this._checktrayObsvModelArrayList = checktrayObsvModelArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ChecktrayReportFragment.newInstance(_checktrayObsvModelArrayList.get(position).getCultureid(),_checktrayObsvModelArrayList.get(position).getTankid());
    }

    @Override
    public int getCount() {
        return _checktrayObsvModelArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _checktrayObsvModelArrayList.get(position).getTankname();
    }
}