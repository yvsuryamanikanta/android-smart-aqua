package com.odos.smartaqua.treatment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterChecktrayReportBinding;
import com.odos.smartaqua.databinding.AdapterTreatmentsBinding;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.growth.GrowthReportModel;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class TraetmentsAdapter extends RecyclerView.Adapter<TraetmentsAdapter.MyViewHolder> {

    ArrayList<TreatmentModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterTreatmentsBinding binding;

        public MyViewHolder(AdapterTreatmentsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public TraetmentsAdapter(Context context, ArrayList<TreatmentModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterTreatmentsBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_treatments, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        TreatmentModel model = homeModelArrayList.get(position);
        holder.binding.imgShare.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getScrollViewScreenShot(holder.binding.scrollView);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        holder.binding.txtReportingDate.setText("Observation Date: "+model.getCreateddate());
        /*        if(isNullOrEmpty(model.getCreateddate())){
            holder.binding.txtCreateddate.setText(""+model.getCreateddate());
            holder.binding.linearCreateddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCreateddate.setVisibility(View.GONE);
        }*/

        if(isNullOrEmpty(model.getTreatmentsid())){
            holder.binding.txtTreatmentId.setText(""+model.getTreatmentsid());
            holder.binding.linearTreatmentId.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearTreatmentId.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getDecease())){
            holder.binding.txtDecease.setText(""+model.getDecease());
            holder.binding.linearDecease.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearDecease.setVisibility(View.GONE);
        }

/*
        if(isNullOrEmpty(model.getTankid())){
            holder.binding.txtTankid.setText(""+model.getTankid());
            holder.binding.linearTank.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearTank.setVisibility(View.GONE);
        }
*/
        if(isNullOrEmpty(model.getModifieddate())){
            holder.binding.txtModifieddate.setText(""+model.getModifieddate());
            holder.binding.linearModifieddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearModifieddate.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(model.getSolution())){
            holder.binding.txtSolution.setText(""+model.getSolution());
            holder.binding.linearSolution.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearSolution.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

    public interface ClickListener {
        void onClicked(TreatmentModel treatmentModel, int pos);
    }
}