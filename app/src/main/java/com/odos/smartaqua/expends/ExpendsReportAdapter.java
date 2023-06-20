package com.odos.smartaqua.expends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterExpendsReportBinding;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class ExpendsReportAdapter extends RecyclerView.Adapter<ExpendsReportAdapter.MyViewHolder> {

    ArrayList<ExpendsReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterExpendsReportBinding binding;

        public MyViewHolder(AdapterExpendsReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public ExpendsReportAdapter(Context context, ArrayList<ExpendsReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterExpendsReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_expends_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        ExpendsReportModel model = homeModelArrayList.get(position);
        holder.binding.share.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getViewScreenShot(holder.binding.header);
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

        if(isNullOrEmpty(model.getAmount())){
            holder.binding.txtAmount.setText(""+model.getAmount());
            holder.binding.linearAmount.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearAmount.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getModifieddate())){
            holder.binding.txtModifieddate.setText(""+model.getModifieddate());
            holder.binding.linearModifieddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearModifieddate.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getExpendsdate())){
            holder.binding.txtObservationdate.setText(""+model.getExpendsdate());
            holder.binding.linearObservationdate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearObservationdate.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(model.getReason())){
            holder.binding.txtReason.setText(""+model.getReason());
            holder.binding.linearReason.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearReason.setVisibility(View.GONE);
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
        void onClicked(ExpendsReportModel model, int pos);
    }
}