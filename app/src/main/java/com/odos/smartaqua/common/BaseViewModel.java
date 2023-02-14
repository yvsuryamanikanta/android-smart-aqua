package com.odos.smartaqua.common;


import android.content.Context;

import androidx.databinding.BaseObservable;

import com.odos.smartaqua.databinding.ActivityBaseBinding;


public class BaseViewModel extends BaseObservable {

    private Context _context;
    private ActivityBaseBinding _activityBaseBinding;

    public BaseViewModel(Context context, ActivityBaseBinding activityBaseBinding) {
        this._context = context;
        this._activityBaseBinding = activityBaseBinding;
    }





}
