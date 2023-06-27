package com.odos.smartaqua.warehouse.products;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.odos.smartaqua.brand.Brandcnsts;
import com.odos.smartaqua.cultures.CultureModel;
import com.odos.smartaqua.expends.ExpendsReportFragment;

import java.util.ArrayList;


public class ProductsViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Brandcnsts> brandcnstsArrayList;
    private Context _context;

    public ProductsViewPagerAdapter(Context context, FragmentManager fm, int behavior, ArrayList<Brandcnsts> arrayList) {
        super(fm, behavior);
        this._context = context;
        this.brandcnstsArrayList = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int brandId = brandcnstsArrayList.get(position).getBrandid();
        return ProductsFragment.newInstance(brandId);
    }

    @Override
    public int getCount() {
        return brandcnstsArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return brandcnstsArrayList.get(position).getBrandname();
    }
}