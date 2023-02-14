package com.odos.smartaqua.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.odos.smartaqua.R;


public class DrawerAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] elements;

    public DrawerAdapter(Activity context, String[] menuCategories) {
        super(context, R.layout.layout_customdrawer, menuCategories);
        this.context = context;
        this.elements = menuCategories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_customdrawer, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        txtTitle.setText(elements[position]);
        return rowView;
    }
}
