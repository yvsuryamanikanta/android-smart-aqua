package com.odos.smartaqua.dashboard;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class DashBoardModel {

    String name;
    int icon;

    public DashBoardModel(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    @BindingAdapter("icon")
    public static void loadImage(ImageView view, int icon) {
        // Picasso.with(view.getContext()).load(imageUrl).error(R.drawable.error).into(view);
        view.setImageResource(icon);
    }

    @BindingAdapter("name")
    public static void loadName(TextView view, String name) {
        view.setText(name);
    }

}
