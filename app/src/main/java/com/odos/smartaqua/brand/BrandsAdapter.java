package com.odos.smartaqua.brand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterBrandcnstsBinding;
import com.odos.smartaqua.databinding.AdapterUserrolesBinding;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import java.util.ArrayList;

public class BrandsAdapter extends BaseAdapter {

    private ArrayList<Brandcnsts> _brandcnstsArrayList;
    private LayoutInflater inflate;
    private Context ctx;
    private AdapterBrandcnstsBinding _binding;

    public BrandsAdapter(Context context, ArrayList<Brandcnsts> brandcnstsArrayList) {
        this.ctx = context;
        this._brandcnstsArrayList = brandcnstsArrayList;
        if (inflate == null) {
            inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
    }

    @Override
    public int getCount() {
        return _brandcnstsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return _brandcnstsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            _binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_brandcnsts, parent, false);
            myViewHolder = new MyViewHolder(_binding);
            myViewHolder.view = _binding.getRoot();
            myViewHolder.view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        Brandcnsts brandcnsts = _brandcnstsArrayList.get(position);
        myViewHolder.binding.txtItemName.setText(brandcnsts.getBrandname());
        return myViewHolder.view;
    }


    private static class MyViewHolder {
        private View view;
        private AdapterBrandcnstsBinding binding;

        MyViewHolder(AdapterBrandcnstsBinding binding) {
            this.view = binding.getRoot();
            this.binding = binding;
        }
    }

}
