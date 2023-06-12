package com.odos.smartaqua.treatment;

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
import com.odos.smartaqua.databinding.AdapterTreatmentsBinding;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.Helper;

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
        /*Helper.setTypeFace(_context, _context.getString(R.string.contentfont), holder.binding.txtName);
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


    public interface ClickListener {
        void onClicked(TreatmentModel treatmentModel, int pos);
    }
}