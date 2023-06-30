package com.odos.smartaqua.warehouse.search;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.warehouse.products.ProductTypes;

import java.util.ArrayList;


public class SearchListViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ProductTypes> _arrayList;
    private Context _context;

    public SearchListViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<ProductTypes> arrayList) {
        super(fm, behavior);
        this._context = context;
        this._arrayList = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int catgId = _arrayList.get(position).getPtID();
        return SearchFragment.newInstance(catgId);
    }

    @Override
    public int getCount() {
        return _arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _arrayList.get(position).getProductType();
    }
}