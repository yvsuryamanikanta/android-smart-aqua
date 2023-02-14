package com.odos.smartaqua.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterDashboardBinding;

import java.util.ArrayList;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    ArrayList<DashBoardModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private DshBoardAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterDashboardBinding binding;

        public MyViewHolder(final AdapterDashboardBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public DashBoardAdapter(ArrayList<DashBoardModel> arrayList, DshBoardAdapterListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterDashboardBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_dashboard, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setHomemodel(homeModelArrayList.get(position));

        holder.binding.llDashboardCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(homeModelArrayList.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }


    public interface DshBoardAdapterListener {
        void onClicked(DashBoardModel dashBoardModel,int pos);
    }
}