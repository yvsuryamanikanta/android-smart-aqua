package com.odos.smartaqua.feed;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.cultures.CultureModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class TankViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CultureModel> _cultureModelArrayList;
    private Context _context;

    public TankViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<CultureModel> cultureModelArrayList) {
        super(fm, behavior);
        this._context = context;
        this._cultureModelArrayList = cultureModelArrayList;
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
        int cultureId = _cultureModelArrayList.get(position).getCultureid();
        String cultureAccess = _cultureModelArrayList.get(position).getCultureaccess();
        return AddFeedFragment.newInstance(cultureId,cultureAccess);
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