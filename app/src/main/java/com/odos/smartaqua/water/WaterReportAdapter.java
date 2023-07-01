package com.odos.smartaqua.water;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterWaterReportBinding;
import com.odos.smartaqua.feed.AddFeedActivity;
import com.odos.smartaqua.feed.TankViewPagerActivity;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.MyViewHolder> {

    ArrayList<WaterReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public WaterReportAdapter(Context context, ArrayList<WaterReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    public static float convertStringToFloat(String str) {

        float floatValue = 0.0f;
        try {
            floatValue = Float.parseFloat(str);
            // Print the expected float value
            Log.e("%%%%%", str + " after converting into float = " + floatValue);
        } catch (Exception e) {
            // Print the error
            Log.e("%%%%%", str + str + " cannot be converted to float: " + e.getMessage());
        }
        return floatValue;
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
        holder.binding.share.setOnClickListener(v -> {
            try {
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getScrollViewScreenShot(holder.binding.scrollView);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
      //  holder.binding.txtPond.setText("" + waterReportModel.getTankName());


        //"ph" value tankid
        if (isNullOrEmpty(waterReportModel.salinity)) {
            holder.binding.txtSalinity.setText("" + waterReportModel.salinity);
            holder.binding.linearSalinity.setVisibility(View.VISIBLE);

            float f1 = new Float("5");
            float f2 = new Float("20");
            float ph = convertStringToFloat(waterReportModel.phvalue);

            // comparing f1 and f2
            int compare1 = Float.compare(ph, f1);
            int compare2 = Float.compare(ph, f2);
            if (compare1 < 0 || compare2 > 0) {
                holder.binding.txtSalinity.setTextColor(_context.getResources().getColor(R.color.red));
            }
            holder.binding.txtSalinity.setOnClickListener(v -> callIntent(waterReportModel.tankid, "salinity"));
        } else {
            holder.binding.linearSalinity.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.phvalue)) {
            holder.binding.txtPh.setText("" + waterReportModel.phvalue);
            holder.binding.linearPh.setVisibility(View.VISIBLE);

            float f1 = new Float("7.6");
            float f2 = new Float("8.3");
            float ph = convertStringToFloat(waterReportModel.phvalue);

            // comparing f1 and f2
            int compare1 = Float.compare(ph, f1);
            int compare2 = Float.compare(ph, f2);
            if (compare1 < 0 || compare2 > 0) {
                holder.binding.txtPh.setTextColor(_context.getResources().getColor(R.color.red));
            }

            holder.binding.txtPh.setOnClickListener(v -> callIntent(waterReportModel.tankid, "ph"));
        } else {
            holder.binding.linearPh.setVisibility(View.GONE);
        }

        //Alkanity
        if (isNullOrEmpty(waterReportModel.getCo3())) {
            holder.binding.txtCo3.setText("" + waterReportModel.getCo3());
            holder.binding.linearCo3.setVisibility(View.VISIBLE);

            if (Integer.parseInt(waterReportModel.getCo3()) < 0 || Integer.parseInt(waterReportModel.getCo3()) > 40) {
                holder.binding.txtCo3.setTextColor(_context.getResources().getColor(R.color.red));
            }
            holder.binding.txtCo3.setOnClickListener(v -> callIntent(waterReportModel.tankid, "Co3"));
        } else {
            holder.binding.linearCo3.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.getHco3())) {
            holder.binding.txtHco3.setText("" + waterReportModel.getHco3());
            holder.binding.linearHco3.setVisibility(View.VISIBLE);
            if (Integer.parseInt(waterReportModel.getHco3()) < 100 || Integer.parseInt(waterReportModel.getHco3()) > 350) {
                holder.binding.txtHco3.setTextColor(_context.getResources().getColor(R.color.red));
            }
            holder.binding.txtHco3.setOnClickListener(v -> callIntent(waterReportModel.tankid, "Hco3"));
        } else {
            holder.binding.linearHco3.setVisibility(View.GONE);
        }

        holder.binding.linearTotalAlka.setVisibility(View.GONE);

        if (!isNullOrEmpty(waterReportModel.getCo3()) && !isNullOrEmpty(waterReportModel.getHco3())) {
            holder.binding.txtAlkalinity.setVisibility(View.GONE);
        } else {
            holder.binding.txtAlkalinity.setVisibility(View.VISIBLE);
        }

        if (!holder.binding.txtCo3.getText().toString().equalsIgnoreCase("") &&
                !holder.binding.txtHco3.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalAlka.setVisibility(View.VISIBLE);
            Float co3Value = Float.parseFloat(holder.binding.txtCo3.getText().toString());
            Float hco3Value = Float.parseFloat(holder.binding.txtHco3.getText().toString());
            Float total = co3Value + hco3Value;
            holder.binding.txtTotalAlkalinity.setText("" + total);
        } else if (!holder.binding.txtCo3.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalAlka.setVisibility(View.VISIBLE);
            holder.binding.txtTotalAlkalinity.setText(holder.binding.txtCo3.getText().toString());
        } else if (!holder.binding.txtHco3.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalAlka.setVisibility(View.VISIBLE);
            holder.binding.txtTotalAlkalinity.setText(holder.binding.txtHco3.getText().toString());
        } else {
            holder.binding.linearTotalAlka.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(waterReportModel.cahardness)) {
            holder.binding.txtCa.setText("" + waterReportModel.cahardness);
            holder.binding.linearCa.setVisibility(View.VISIBLE);
            if (Integer.parseInt(waterReportModel.cahardness) < 200 || Integer.parseInt(waterReportModel.cahardness) > 1500) {
                holder.binding.txtCa.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearCa.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.mghardness)) {
            holder.binding.txtMg.setText("" + waterReportModel.mghardness);
            holder.binding.linearMg.setVisibility(View.VISIBLE);
            if (Integer.parseInt(waterReportModel.mghardness) < 200 || Integer.parseInt(waterReportModel.mghardness) > 1500) {
                holder.binding.txtMg.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearMg.setVisibility(View.GONE);
        }

        if (!isNullOrEmpty(waterReportModel.cahardness) && !isNullOrEmpty(waterReportModel.mghardness)) {
            holder.binding.txtHardness.setVisibility(View.GONE);
        } else {
            holder.binding.txtHardness.setVisibility(View.VISIBLE);
        }

        if (!holder.binding.txtCa.getText().toString().equalsIgnoreCase("") &&
                !holder.binding.txtMg.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalHard.setVisibility(View.VISIBLE);
            Float cgValue = Float.parseFloat(holder.binding.txtCa.getText().toString());
            Float mgValue = Float.parseFloat(holder.binding.txtMg.getText().toString());
            Float total = cgValue + mgValue;
            holder.binding.txtTotal.setText("" + total);
        } else if (!holder.binding.txtCa.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalHard.setVisibility(View.VISIBLE);
            holder.binding.txtTotal.setText(holder.binding.txtCa.getText().toString());
        } else if (!holder.binding.txtMg.getText().toString().equalsIgnoreCase("")) {
            holder.binding.linearTotalHard.setVisibility(View.VISIBLE);
            holder.binding.txtTotal.setText(holder.binding.txtMg.getText().toString());
        } else {
            holder.binding.linearTotalHard.setVisibility(View.GONE);
        }

        //Minerals
        if (isNullOrEmpty(waterReportModel.calcium)) {
            holder.binding.txtCalcium.setText("" + waterReportModel.calcium);
            holder.binding.linearCalcium.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCalcium.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.magnesium)) {
            holder.binding.txtMagnesium.setText("" + waterReportModel.magnesium);
            holder.binding.linearMagnesium.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMagnesium.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel)){
//            holder.binding.txtRatio.setText(""+waterReportModel.magnesium);
//        }else {
        holder.binding.linearRatio.setVisibility(View.GONE);
//        }
        if (isNullOrEmpty(waterReportModel.potassium)) {
            holder.binding.txtPotassium.setText("" + waterReportModel.potassium);
            holder.binding.linearPotassium.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPotassium.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.sodium)) {
            holder.binding.txtSodium.setText("" + waterReportModel.sodium);
            holder.binding.linearSodium.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSodium.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel)){
//            holder.binding.txtRatioPoSod.setText("");
//        }else {
        holder.binding.linearRatioPoSod.setVisibility(View.GONE);
//        }
        if (isNullOrEmpty(waterReportModel.iron)) {
            holder.binding.txtIron.setText("" + waterReportModel.iron);
            holder.binding.linearIron.setVisibility(View.VISIBLE);

            float f1 = new Float("0.5");
            float iron = convertStringToFloat(waterReportModel.iron);

            int compare1 = Float.compare(iron, f1);
            if (compare1 > 0) {
                holder.binding.txtIron.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearIron.setVisibility(View.GONE);
        }
        if (!isNullOrEmpty(waterReportModel.calcium) && !isNullOrEmpty(waterReportModel.magnesium)
                && !isNullOrEmpty(waterReportModel.potassium) && !isNullOrEmpty(waterReportModel.sodium) && !isNullOrEmpty(waterReportModel.iron)) {
            holder.binding.txtMineral.setVisibility(View.GONE);
        } else {
            holder.binding.txtMineral.setVisibility(View.VISIBLE);
        }

        //gases
        if (isNullOrEmpty(waterReportModel.ionizedammonia)) {
            holder.binding.txtIonized.setText("" + waterReportModel.ionizedammonia);
            holder.binding.linearIonized.setVisibility(View.VISIBLE);

            float f1 = new Float("1.0");
            float value = convertStringToFloat(waterReportModel.ionizedammonia);

            int compare1 = Float.compare(value, f1);
            if (compare1 > 0) {
                holder.binding.txtIonized.setTextColor(_context.getResources().getColor(R.color.red));
            }

        } else {
            holder.binding.linearIonized.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.unionizedammonia)) {
            holder.binding.txtUnIonized.setText("" + waterReportModel.unionizedammonia);
            holder.binding.linearUnIonized.setVisibility(View.VISIBLE);
            float f1 = new Float("0.1");
            float value = convertStringToFloat(waterReportModel.unionizedammonia);

            int compare1 = Float.compare(value, f1);
            if (compare1 > 0) {
                holder.binding.txtUnIonized.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearUnIonized.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.nitrate)) {
            holder.binding.txtNitrate.setText("" + waterReportModel.nitrate);
            holder.binding.linearNitrate.setVisibility(View.VISIBLE);
            float f1 = new Float("0.25");
            float value = convertStringToFloat(waterReportModel.nitrate);

            int compare1 = Float.compare(value, f1);
            if (compare1 > 0) {
                holder.binding.txtNitrate.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearNitrate.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.hydrogensulphide)) {
            holder.binding.txtHydrogen.setText("" + waterReportModel.hydrogensulphide);
            holder.binding.linearHydrogen.setVisibility(View.VISIBLE);
            float f1 = new Float("0.01");
            float value = convertStringToFloat(waterReportModel.hydrogensulphide);

            int compare1 = Float.compare(value, f1);
            if (compare1 > 0) {
                holder.binding.txtHydrogen.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearHydrogen.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.labdo)) {
            holder.binding.txtDo.setText("" + waterReportModel.labdo);
            holder.binding.linearDo.setVisibility(View.VISIBLE);
            float f1 = new Float("3.5");
            float value = convertStringToFloat(waterReportModel.labdo);

            int compare1 = Float.compare(value, f1);
            if (compare1 < 0) {
                holder.binding.txtDo.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearDo.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.co2)) {
            holder.binding.txtCo2.setText("" + waterReportModel.co2);
            holder.binding.linearCo2.setVisibility(View.VISIBLE);
            float f1 = new Float("10");
            float value = convertStringToFloat(waterReportModel.co2);

            int compare1 = Float.compare(value, f1);
            if (compare1 > 0) {
                holder.binding.txtCo2.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearCo2.setVisibility(View.GONE);
        }

        if (!isNullOrEmpty(waterReportModel.ionizedammonia) && !isNullOrEmpty(waterReportModel.unionizedammonia)
                && !isNullOrEmpty(waterReportModel.nitrate) && !isNullOrEmpty(waterReportModel.hydrogensulphide)
                && !isNullOrEmpty(waterReportModel.labdo) && !isNullOrEmpty(waterReportModel.co2)) {
            holder.binding.txtGases.setVisibility(View.GONE);
        } else {
            holder.binding.txtGases.setVisibility(View.VISIBLE);
        }


        //Plak
        if (isNullOrEmpty(waterReportModel.greenalgae)) {
            holder.binding.txtGreen.setText("" + waterReportModel.greenalgae);
            holder.binding.linearGreen.setVisibility(View.VISIBLE);
            float f1 = new Float("60");
            float value = convertStringToFloat(waterReportModel.greenalgae);

            int compare1 = Float.compare(value, f1);
            if (compare1 < 0) {
                holder.binding.txtGreen.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearGreen.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.bluegreenalgae)) {
            holder.binding.txtBluegreen.setText("" + waterReportModel.bluegreenalgae);
            holder.binding.linearBluegreen.setVisibility(View.VISIBLE);
            float f1 = new Float("20");
            float value = convertStringToFloat(waterReportModel.bluegreenalgae);

            int compare1 = Float.compare(value, f1);
            if (compare1 < 0) {
                holder.binding.txtBluegreen.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearBluegreen.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.diatom)) {
            holder.binding.txtDiatom.setText("" + waterReportModel.diatom);
            holder.binding.txtDiatom.setVisibility(View.VISIBLE);
        } else {
            holder.binding.txtDiatom.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.dinoflegellates)) {
            holder.binding.txtDinoflegellates.setText("" + waterReportModel.dinoflegellates);
            holder.binding.linearDinoflegellates.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearDinoflegellates.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.zooplankton)) {
            holder.binding.txtZooplankton.setText("" + waterReportModel.zooplankton);
            holder.binding.linearZooplankton.setVisibility(View.VISIBLE);
            float f1 = new Float("20");
            float value = convertStringToFloat(waterReportModel.zooplankton);

            int compare1 = Float.compare(value, f1);
            if (compare1 < 0) {
                holder.binding.txtZooplankton.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearZooplankton.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(waterReportModel.dafloc)) {
            holder.binding.txtDa.setText("" + waterReportModel.dafloc);
            holder.binding.linearDa.setVisibility(View.VISIBLE);
            float f1 = new Float("20");
            float value = convertStringToFloat(waterReportModel.dafloc);

            int compare1 = Float.compare(value, f1);
            if (compare1 < 0) {
                holder.binding.txtDa.setTextColor(_context.getResources().getColor(R.color.red));
            }
        } else {
            holder.binding.linearDa.setVisibility(View.GONE);
        }

        if (!isNullOrEmpty(waterReportModel.greenalgae) && !isNullOrEmpty(waterReportModel.bluegreenalgae)
                && !isNullOrEmpty(waterReportModel.diatom) && !isNullOrEmpty(waterReportModel.dinoflegellates)
                && !isNullOrEmpty(waterReportModel.zooplankton) && !isNullOrEmpty(waterReportModel.dafloc)) {
            holder.binding.txtPlankton.setVisibility(View.GONE);
        } else {
            holder.binding.txtPlankton.setVisibility(View.VISIBLE);
        }

    }
    void callIntent(int tnakId, String value){
        Intent intent = new Intent(_context, TankViewPagerActivity.class);
        Bundle bundle_data = new Bundle();
        bundle_data.putString("tankid", ""+tnakId);
        bundle_data.putString("value", value);
        intent.putExtras(bundle_data);
        _context.startActivity(intent);
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.equalsIgnoreCase("");
    }

    @Override
    public int getItemCount() {
        return homeModelArrayList.size();
    }

    public interface ClickListener {
        void onClicked(WaterReportModel model, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterWaterReportBinding binding;

        public MyViewHolder(AdapterWaterReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}