package com.odos.smartaqua.shocaseview;


import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.odos.smartaqua.databinding.FragmentShowBinding;

public class ShowViewModel extends ViewModel {

    private Context _context;
    private FragmentShowBinding _fragmentShowBinding;
    private String[] titles;
    private int[] icons;

    public ShowViewModel(Context context, FragmentShowBinding fragmentShowBinding) {
        this._context = context;
        this._fragmentShowBinding = fragmentShowBinding;
    }

}
