package com.odos.smartaqua.checktray;

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
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.Helper;

import java.util.ArrayList;

public class ChecktrayReportAdapter extends RecyclerView.Adapter<ChecktrayReportAdapter.MyViewHolder> {

    ArrayList<ChecktrayReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterChecktrayReportBinding binding;

        public MyViewHolder(AdapterChecktrayReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public ChecktrayReportAdapter(Context context, ArrayList<ChecktrayReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterChecktrayReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_checktray_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ChecktrayReportModel checktrayReportModel = (ChecktrayReportModel)homeModelArrayList.get(position);
        holder.binding.setModel(homeModelArrayList.get(position));
        holder.binding.txtChecktray.setText("East Checktray "+" ( "+checktrayReportModel.getChecktrayobsvdate()+" )");
        holder.binding.llChcktrayObsv.setOnClickListener(new View.OnClickListener() {
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
        void onClicked(ChecktrayReportModel checktrayReportModel, int pos);
    }
}