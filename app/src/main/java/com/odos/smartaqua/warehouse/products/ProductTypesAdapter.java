package com.odos.smartaqua.warehouse.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;


import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterUserrolesBinding;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import java.util.ArrayList;

public class ProductTypesAdapter extends BaseAdapter {

    private ArrayList<ProductTypes> _productTypesArrayList;
    private LayoutInflater inflate;
    private Context ctx;
    private AdapterUserrolesBinding adapterUserrolesBinding;

    public ProductTypesAdapter(Context context, ArrayList<ProductTypes> productTypesArrayList) {
        this.ctx = context;
        this._productTypesArrayList = productTypesArrayList;
        if (inflate == null) {
            inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
    }

    @Override
    public int getCount() {
        return _productTypesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return _productTypesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            adapterUserrolesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_userroles, parent, false);
            myViewHolder = new MyViewHolder(adapterUserrolesBinding);
            myViewHolder.view = adapterUserrolesBinding.getRoot();
            myViewHolder.view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        ProductTypes productTypes = _productTypesArrayList.get(position);
        myViewHolder.binding.txtItemName.setText(productTypes.getProductType());
        return myViewHolder.view;
    }


    private static class MyViewHolder {
        private View view;
        private AdapterUserrolesBinding binding;

        MyViewHolder(AdapterUserrolesBinding binding) {
            this.view = binding.getRoot();
            this.binding = binding;
        }
    }

}
