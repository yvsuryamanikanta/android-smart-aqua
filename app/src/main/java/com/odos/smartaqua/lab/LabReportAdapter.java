package com.odos.smartaqua.lab;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterChecktrayReportBinding;
import com.odos.smartaqua.databinding.AdapterLabReportBinding;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.Helper;

import java.util.ArrayList;

public class LabReportAdapter extends RecyclerView.Adapter<LabReportAdapter.MyViewHolder> {

    ArrayList<LabReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterLabReportBinding binding;

        public MyViewHolder(AdapterLabReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public LabReportAdapter(Context context, ArrayList<LabReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterLabReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_lab_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        LabReportModel labReportModel = (LabReportModel) homeModelArrayList.get(position);
        holder.binding.setModel(homeModelArrayList.get(position));
        holder.binding.txtLab.setText("Lab Report ( "+labReportModel.getLabobsvdate()+" )");

        holder.binding.llLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(homeModelArrayList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }


    public interface ClickListener {
        void onClicked(LabReportModel labReportModel, int pos);
    }
}