package com.odos.smartaqua.prelogin.sighnup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterUserrolesBinding;

import java.util.ArrayList;

public class UserRolesAdapter extends BaseAdapter {

    private ArrayList<UserRoles> _userRolesArrayList;
    private LayoutInflater inflate;
    private Context ctx;
    private AdapterUserrolesBinding adapterUserrolesBinding;

    public UserRolesAdapter(Context context, ArrayList<UserRoles> userRolesArrayList) {
        this.ctx = context;
        this._userRolesArrayList = userRolesArrayList;
        if (inflate == null) {
            inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
    }

    @Override
    public int getCount() {
        return _userRolesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return _userRolesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            adapterUserrolesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.adapter_userroles, parent, false);
            myViewHolder = new MyViewHolder(adapterUserrolesBinding);
            myViewHolder.view = adapterUserrolesBinding.getRoot();
            myViewHolder.view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        UserRoles userRoles = _userRolesArrayList.get(position);
        myViewHolder.binding.txtItemName.setText(userRoles.getRoleName());
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
