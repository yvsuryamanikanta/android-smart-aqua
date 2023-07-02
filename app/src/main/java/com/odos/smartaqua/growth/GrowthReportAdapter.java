package com.odos.smartaqua.growth;

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
import com.odos.smartaqua.checktray.ChecktrayReportModel;
import com.odos.smartaqua.databinding.AdapterChecktrayReportBinding;
import com.odos.smartaqua.databinding.AdapterGrowthReportBinding;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class GrowthReportAdapter extends RecyclerView.Adapter<GrowthReportAdapter.MyViewHolder> {

    ArrayList<GrowthReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterGrowthReportBinding binding;

        public MyViewHolder(AdapterGrowthReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public GrowthReportAdapter(Context context, ArrayList<GrowthReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterGrowthReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_growth_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.binding.setModel(homeModelArrayList.get(position));
        GrowthReportModel model = homeModelArrayList.get(position);
        holder.binding.imgShare.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getScrollViewScreenShot(holder.binding.scrollView);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        holder.binding.txtReportingDate.setText("Observation Date: "+model.getGrowthobservationdate());
//        if(isNullOrEmpty(model.getCreateddate())){
//            holder.binding.txtCreateddate.setText(""+model.getCreateddate());
//            holder.binding.linearCreateddate.setVisibility(View.VISIBLE);
//        }else {
//            holder.binding.linearCreateddate.setVisibility(View.GONE);
//        }

        if(isNullOrEmpty(model.getTankid())){
            holder.binding.txtTankid.setText(""+model.getTankid());
            holder.binding.linearTank.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearTank.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getCount())){
            holder.binding.txtCount.setText(""+model.getCount());
            holder.binding.linearCount.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCount.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getModifieddate())){
            holder.binding.txtModifieddate.setText(""+model.getModifieddate());
            holder.binding.linearModifieddate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearModifieddate.setVisibility(View.GONE);
        }

        if(isNullOrEmpty(model.getGrowthobservationdate())){
            holder.binding.txtGrowthobservationdate.setText(""+model.getGrowthobservationdate());
            holder.binding.linearGrowthobservationdate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearGrowthobservationdate.setVisibility(View.GONE);
        }

      /*  Helper.setTypeFace(_context, _context.getString(R.string.contentfont), holder.binding.txtName);
        holder.binding.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AquaConstants.putIntent(_context, EdittankTankActivity.class, 1, null);
            }
        });


        holder.binding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(_context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setCancelable(false);
                d.setContentView(R.layout.dialog_showmsg);
                d.show();
                TextView txt_content = (TextView) d.findViewById(R.id.txt_content);
                TextView txt_ok = (TextView) d.findViewById(R.id.txt_ok);
                TextView txt_cancel = (TextView) d.findViewById(R.id.txt_cancel);
                txt_cancel.setVisibility(View.VISIBLE);
                TextView txt_title = (TextView) d.findViewById(R.id.txt_title);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_content);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_ok);
                Helper.setTypeFace(_context, _context.getString(R.string.contentfont), txt_title);
                txt_content.setText(_context.getResources().getString(R.string.areyousuredelete));
                txt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

            }
        });

        holder.binding.llFeed.setOnClickListener(new View.OnClickListener() {
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

    boolean isNullOrEmpty(String data) {
        return data != null && !data.trim().equalsIgnoreCase("");
    }

    public interface ClickListener {
        void onClicked(GrowthReportModel growthReportModel, int pos);
    }
}