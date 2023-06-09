package com.odos.smartaqua.water;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterWaterReportBinding;
import com.odos.smartaqua.tank.CultureModel;

import java.util.ArrayList;

public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.MyViewHolder> {

    ArrayList<WaterReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterWaterReportBinding binding;

        public MyViewHolder(AdapterWaterReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public WaterReportAdapter(Context context, ArrayList<WaterReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterWaterReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_water_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        WaterReportModel waterReportModel = (WaterReportModel) homeModelArrayList.get(position);
        holder.binding.setModel(waterReportModel);

    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }


    public interface ClickListener {
        void onClicked(WaterReportModel model, int pos);
    }
}