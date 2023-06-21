package com.odos.smartaqua.checktray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterChecktrayReportBinding;
import com.odos.smartaqua.utils.PdfGeneratorNew;

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
        ChecktrayReportModel model = (ChecktrayReportModel)homeModelArrayList.get(position);
        holder.binding.setModel(homeModelArrayList.get(position));

        holder.binding.share.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getScrollViewScreenShot(holder.binding.scrollView);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        if(isNullOrEmpty(model.getCreateddate())){
            holder.binding.txtCreateddate.setText(""+model.getCreateddate());
            holder.binding.linearCreateddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCreateddate.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(model.getTankid())){
            holder.binding.txtTankid.setText(""+model.getTankid());
            holder.binding.linearTank.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearTank.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(""+model.getChecktrayid())){
            holder.binding.txtChecktrayid.setText(""+model.getChecktrayid());
            holder.binding.linearChecktrayid.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearChecktrayid.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(""+model.getFeedstatus())){
            holder.binding.txtFeedstatus.setText(""+model.getFeedstatus());
            holder.binding.linearFeedstatus.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearFeedstatus.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(""+model.getChecktrayobsvid())){
            holder.binding.txtChecktrayObservid.setText(""+model.getChecktrayobsvid());
            holder.binding.linearChecktrayObservid.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearChecktrayObservid.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(model.getWastagecolor())){
            holder.binding.txtWastagecolor.setText(""+model.getWastagecolor());
            holder.binding.linearWastagecolor.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearWastagecolor.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getMortalitytype())){
            holder.binding.txtMortalitytype.setText(""+model.getMortalitytype());
            holder.binding.linearMortalitytype.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMortalitytype.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getMortalitycount())){
            holder.binding.txtMortalitycount.setText(""+model.getMortalitycount());
            holder.binding.linearMortalitycount.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMortalitycount.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getPotaciumdefeciency())){
            holder.binding.txtPotaciumdefeciency.setText(""+model.getPotaciumdefeciency());
            holder.binding.linearPotaciumdefeciency.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearPotaciumdefeciency.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getMagniciumdefeciency())){
            holder.binding.txtMagniciumdefeciency.setText(""+model.getMagniciumdefeciency());
            holder.binding.linearMagniciumdefeciency.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMagniciumdefeciency.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getCalciumdefeciency())){
            holder.binding.txtCalciumdefeciency.setText(""+model.getCalciumdefeciency());
            holder.binding.linearCalciumdefeciency.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCalciumdefeciency.setVisibility(View.GONE);
        }

//        if(isNullOrEmpty(model.())){
//            holder.binding.txtCalciumdefeciency.setText(""+model.());
//            holder.binding.linearVibrieostatus.setVisibility(View.VISIBLE);
//        }else {
            holder.binding.linearVibrieostatus.setVisibility(View.GONE);
//        }


        if(isNullOrEmpty(model.getCrampstatus())){
            holder.binding.txtCrampstatus.setText(""+model.getCrampstatus());
            holder.binding.linearCrampstatus.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCrampstatus.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getChecktrayobsvdate())){
            holder.binding.txtChecktrayobsvdate.setText(""+model.getChecktrayobsvdate());
            holder.binding.linearChecktrayobsvdate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearChecktrayobsvdate.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getModifieddate())){
            holder.binding.txtModifieddate.setText(""+model.getModifieddate());
            holder.binding.linearModifieddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearModifieddate.setVisibility(View.GONE);
        }




        //        holder.binding.txtChecktray.setText("East Checktray "+" ( "+model.getChecktrayobsvdate()+" )");
        /*holder.binding.llChcktrayObsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(homeModelArrayList.get(position), position);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }


    public interface ClickListener {
        void onClicked(ChecktrayReportModel model, int pos);
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

}