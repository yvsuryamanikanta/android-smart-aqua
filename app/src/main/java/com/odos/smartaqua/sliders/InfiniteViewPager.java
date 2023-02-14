package com.odos.smartaqua.sliders;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.PagerAdapter;

import com.odos.smartaqua.sliders.adapters.InfinitePagerAdapter;


public class InfiniteViewPager extends ViewPagerEx {

    @Override
    public void setAdapter(InfinitePagerAdapter pagerAdapter) {

    }

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

}