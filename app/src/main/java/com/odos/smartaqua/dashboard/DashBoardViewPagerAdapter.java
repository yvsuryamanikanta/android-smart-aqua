package com.odos.smartaqua.dashboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;


public class DashBoardViewPagerAdapter extends FragmentStatePagerAdapter {

    private JSONArray jsonArray;
    private Context _context;

    public DashBoardViewPagerAdapter(Context context, FragmentManager fm, int behavior, JSONArray response) {
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
            return DashboardFragment.newInstance(position, tankId, cultureId, tankName);
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