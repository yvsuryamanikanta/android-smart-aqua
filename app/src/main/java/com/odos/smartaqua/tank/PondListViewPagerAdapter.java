package com.odos.smartaqua.tank;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;


public class PondListViewPagerAdapter extends FragmentStatePagerAdapter {

    private JSONArray jsonArray;
    private Context _context;

    public PondListViewPagerAdapter(Context context, FragmentManager fm, int behavior, JSONArray response) {
        super(fm, behavior);
        this._context = context;
        this.jsonArray = response;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            String tankId = jsonObject.getString("tankid");
            String cultureId = jsonObject.getString("cultureid");
            String tankName = jsonObject.getString("tankname");
            String cultureimage = jsonObject.getString("cultureimage");
            return PondListFragment.newInstance(position, tankId, cultureId, tankName,""+jsonArray);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            return jsonObject.getString("tankname");
        } catch (Exception e) {
            return "No Title";
        }

    }
}