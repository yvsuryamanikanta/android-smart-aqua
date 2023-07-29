package com.odos.smartaqua.tank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterCultureBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CultureAdapter extends RecyclerView.Adapter<CultureAdapter.MyViewHolder> {

    ArrayList<CultureModel> arrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public CultureAdapter(Context context, ArrayList<CultureModel> arrayList, ClickListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterCultureBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_culture, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CultureModel cultureModel = (CultureModel) arrayList.get(position);
        holder.binding.setCultures(cultureModel);

        holder.binding.txtSno.setText("S.No : " + (position + 1));
//        Date c = Calendar.getInstance().getTime();

//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);
        holder.binding.txtDate.setText(cultureModel.createddate);

        if (isNullOrEmpty(cultureModel.previousdecease)) {
            holder.binding.txtDecease.setText(cultureModel.previousdecease);
            holder.binding.linearPreviousDecease.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPreviousDecease.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.recordkeeping)) {
            holder.binding.txtRecordKeeping.setText(cultureModel.recordkeeping);
            holder.binding.linearRecordKeeping.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearRecordKeeping.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.drying)) {
            holder.binding.txtDrying.setText(cultureModel.drying);
            holder.binding.linearDrying.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearDrying.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.biosecurity)) {
            holder.binding.txtBioSecurity.setText(cultureModel.biosecurity);
            holder.binding.linearBioSecurity.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearBioSecurity.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.scrapping)) {
            holder.binding.txtScrapping.setText(cultureModel.scrapping);
            holder.binding.linearScrapping.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearScrapping.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.ploughing)) {
            holder.binding.txtPloughing.setText(cultureModel.ploughing);
            holder.binding.linearPloughing.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPloughing.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.liming)) {
            holder.binding.txtLiming.setText(cultureModel.liming);
            holder.binding.linearLiming.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearLiming.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.soilcheck)) {
            holder.binding.txtSoilCheck.setText(cultureModel.soilcheck);
            holder.binding.linearSoilChecking.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSoilChecking.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.fillingwatertype)) {
            holder.binding.txtFillingWater.setText(cultureModel.fillingwatertype);
            holder.binding.linearWaterFilled.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearWaterFilled.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(cultureModel.watersource)) {
            holder.binding.txtSourceWater.setText(cultureModel.watersource);
            holder.binding.linearWaterSource.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearWaterSource.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.pondtype)) {
            holder.binding.txtPondType.setText(cultureModel.pondtype);
            holder.binding.linearPondType.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPondType.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.filteration)) {
            holder.binding.txtFilteration.setText(cultureModel.filteration);
            holder.binding.linearFilteration.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearFilteration.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.bleaching)) {
            holder.binding.txtBleaching.setText(cultureModel.bleaching);
            holder.binding.linearBleaching.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearBleaching.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.minerals)) {
            holder.binding.txtMinerals.setText(cultureModel.minerals);
            holder.binding.linearMinerals.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMinerals.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.probiotics)) {
            holder.binding.txtProbiotics.setText(cultureModel.probiotics);
            holder.binding.linearProbiotics.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearProbiotics.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.fertilization)) {
            holder.binding.txtFertilization.setText(cultureModel.fertilization);
            holder.binding.linearFertilization.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearFertilization.setVisibility(View.GONE);
        }
        if (isNullOrSelect(cultureModel.ehp)) {
            holder.binding.txtEhp.setText(cultureModel.ehp);
            holder.binding.linearEhp.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearEhp.setVisibility(View.GONE);
        }

    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.equalsIgnoreCase("") &&
                !data.equalsIgnoreCase("no") &&
                !data.equalsIgnoreCase("null");
    }

    boolean isNullOrSelect(String data) {
        return data != null && !data.equalsIgnoreCase("") &&
                !data.equalsIgnoreCase("select") &&
                !data.equalsIgnoreCase("null");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface ClickListener {
        void onClicked(CultureModel CultureModel, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterCultureBinding binding;

        public MyViewHolder(AdapterCultureBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}