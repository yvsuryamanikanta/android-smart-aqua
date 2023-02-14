package com.odos.smartaqua.dashboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class DashBoardViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] _titles;
    private Context _context;

    public DashBoardViewPagerAdapter(Context context, FragmentManager fm, int behavior, String[] titles) {
        super(fm, behavior);
        this._context = context;
        this._titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        /*if (_titles[position].equalsIgnoreCase("Culture")) {
            return PondFragment.newInstance("tank");
        } else if (_titles[position].equalsIgnoreCase("Ware House")) {
            return WareHouseFragment.newInstance();
        } else if (_titles[position].equalsIgnoreCase("Market Rates")) {
            return MarketRatesFragment.newInstance();
        } else if (_titles[position].equalsIgnoreCase("Classifieds")) {
            return ClassifiedsFragment.newInstance();
        } else if (_titles[position].equalsIgnoreCase("Final Reports")) {
            return PondFragment.newInstance("reports");
        } else if (_titles[position].equalsIgnoreCase("Cultures")) {
            return CultureFragment.newInstance();
        } else {
            return null;
        }*/
        return null;
    }

    @Override
    public int getCount() {
        return _titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _titles[position];
    }
}