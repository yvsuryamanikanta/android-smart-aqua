package com.odos.smartaqua.shocaseview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ShowViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] _titles;
    private Context _context;

    public ShowViewPagerAdapter(Context context, FragmentManager fm, int behavior, String[] titles) {
        super(fm, behavior);
        this._context = context;
        this._titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ShowFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Title";
    }
}