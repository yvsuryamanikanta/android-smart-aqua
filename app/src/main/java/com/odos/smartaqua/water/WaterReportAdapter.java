package com.odos.smartaqua.water;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterWaterReportBinding;
import com.odos.smartaqua.tank.CultureModel;
import com.odos.smartaqua.utils.PdfGeneratorNew;

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
        holder.binding.share.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getScrollViewScreenShot(holder.binding.scrollView);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        holder.binding.txtPond.setText(""+waterReportModel.getTankid());

        if(isNullOrEmpty(waterReportModel.salinity)){
            holder.binding.txtSalinity.setText(""+waterReportModel.salinity);
            holder.binding.linearSalinity.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearSalinity.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.phvalue)){
            holder.binding.txtPh.setText(""+waterReportModel.phvalue);
            holder.binding.linearPh.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearPh.setVisibility(View.GONE);
        }

        //Alkanity
        if(isNullOrEmpty(waterReportModel.getCo3())){
            holder.binding.txtCo3.setText(""+waterReportModel.getCo3());
            holder.binding.linearCo3.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCo3.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.getHco3())){
            holder.binding.txtHco3.setText(""+waterReportModel.getHco3());
            holder.binding.linearHco3.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearHco3.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel.)){
//            holder.binding.txtTotalAlka.setText("");
//        }else {
            holder.binding.linearTotalAlka.setVisibility(View.GONE);
//        }
        if(!isNullOrEmpty(waterReportModel.getCo3()) && !isNullOrEmpty(waterReportModel.getHco3())){
            holder.binding.txtAlkalinity.setVisibility(View.GONE);
        }else {
            holder.binding.txtAlkalinity.setVisibility(View.VISIBLE);
        }


//Hardness
        if(isNullOrEmpty(waterReportModel.cahardness)){
            holder.binding.txtCa.setText(""+waterReportModel.cahardness);
            holder.binding.linearCa.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCa.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.mghardness)){
            holder.binding.txtMg.setText(""+waterReportModel.mghardness);
            holder.binding.linearMg.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMg.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel.)){
//            holder.binding.txtTotalMg.setText();
//        }else {
            holder.binding.linearTotalHard.setVisibility(View.GONE);
//        }
        if(!isNullOrEmpty(waterReportModel.cahardness)&&!isNullOrEmpty(waterReportModel.mghardness)){
            holder.binding.txtHardness.setVisibility(View.GONE);
        }else {
            holder.binding.txtHardness.setVisibility(View.VISIBLE);
        }



        //Minerals
        if(isNullOrEmpty(waterReportModel.calcium)){
            holder.binding.txtCalcium.setText(""+waterReportModel.calcium);
            holder.binding.linearCalcium.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCalcium.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.magnesium)){
            holder.binding.txtMagnesium.setText(""+waterReportModel.magnesium);
            holder.binding.linearMagnesium.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearMagnesium.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel)){
//            holder.binding.txtRatio.setText(""+waterReportModel.magnesium);
//        }else {
            holder.binding.linearRatio.setVisibility(View.GONE);
//        }
        if(isNullOrEmpty(waterReportModel.potassium)){
            holder.binding.txtPotassium.setText(""+waterReportModel.potassium);
            holder.binding.linearPotassium.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearPotassium.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.sodium)){
            holder.binding.txtSodium.setText(""+waterReportModel.sodium);
            holder.binding.linearSodium.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearSodium.setVisibility(View.GONE);
        }
//        if(isNullOrEmpty(waterReportModel)){
//            holder.binding.txtRatioPoSod.setText("");
//        }else {
            holder.binding.linearRatioPoSod.setVisibility(View.GONE);
//        }
        if(isNullOrEmpty(waterReportModel.iron)){
            holder.binding.txtIron.setText(""+waterReportModel.iron);
            holder.binding.linearIron.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearIron.setVisibility(View.GONE);
        }
        if(!isNullOrEmpty(waterReportModel.calcium)&&!isNullOrEmpty(waterReportModel.magnesium)
                &&!isNullOrEmpty(waterReportModel.potassium)&&!isNullOrEmpty(waterReportModel.sodium)&&!isNullOrEmpty(waterReportModel.iron)){
            holder.binding.txtMineral.setVisibility(View.GONE);
        }else {
            holder.binding.txtMineral.setVisibility(View.VISIBLE);
        }

        //gases
        if(isNullOrEmpty(waterReportModel.ionizedammonia)){
            holder.binding.txtIonized.setText(""+waterReportModel.ionizedammonia);
            holder.binding.linearIonized.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearIonized.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.unionizedammonia)){
            holder.binding.txtUnIonized.setText(""+waterReportModel.unionizedammonia);
            holder.binding.linearUnIonized.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearUnIonized.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.nitrate)){
            holder.binding.txtNitrate.setText(""+waterReportModel.nitrate);
            holder.binding.linearNitrate.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearNitrate.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.hydrogensulphide)){
            holder.binding.txtHydrogen.setText(""+waterReportModel.hydrogensulphide);
            holder.binding.linearHydrogen.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearHydrogen.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.labdo)){
            holder.binding.txtDo.setText(""+waterReportModel.labdo);
            holder.binding.linearDo.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearDo.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.co2)){
            holder.binding.txtCo2.setText(""+waterReportModel.co2);
            holder.binding.linearCo2.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearCo2.setVisibility(View.GONE);
        }

        if(!isNullOrEmpty(waterReportModel.ionizedammonia)&&!isNullOrEmpty(waterReportModel.unionizedammonia)
                &&!isNullOrEmpty(waterReportModel.nitrate)&&!isNullOrEmpty(waterReportModel.hydrogensulphide)
                &&!isNullOrEmpty(waterReportModel.labdo)&&!isNullOrEmpty(waterReportModel.co2)){
            holder.binding.txtGases.setVisibility(View.GONE);
        }else {
            holder.binding.txtGases.setVisibility(View.VISIBLE);
        }


        //Plak
        if(isNullOrEmpty(waterReportModel.greenalgae)){
            holder.binding.txtGreen.setText(""+waterReportModel.greenalgae);
            holder.binding.linearGreen.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearGreen.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.bluegreenalgae)){
            holder.binding.txtBluegreen.setText(""+waterReportModel.bluegreenalgae);
            holder.binding.linearBluegreen.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearBluegreen.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.diatom)){
            holder.binding.txtDiatom.setText(""+waterReportModel.diatom);
            holder.binding.txtDiatom.setVisibility(View.VISIBLE);
        }else {
            holder.binding.txtDiatom.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.dinoflegellates)){
            holder.binding.txtDinoflegellates.setText(""+waterReportModel.dinoflegellates);
            holder.binding.linearDinoflegellates.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearDinoflegellates.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.zooplankton)){
            holder.binding.txtZooplankton.setText(""+waterReportModel.zooplankton);
            holder.binding.linearZooplankton.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearZooplankton.setVisibility(View.GONE);
        }
        if(isNullOrEmpty(waterReportModel.dafloc)){
            holder.binding.txtDa.setText(""+waterReportModel.dafloc);
            holder.binding.linearDa.setVisibility(View.VISIBLE);
        }else {
            holder.binding.linearDa.setVisibility(View.GONE);
        }

        if(!isNullOrEmpty(waterReportModel.greenalgae)&&!isNullOrEmpty(waterReportModel.bluegreenalgae)
                &&!isNullOrEmpty(waterReportModel.diatom)&&!isNullOrEmpty(waterReportModel.dinoflegellates)
                &&!isNullOrEmpty(waterReportModel.zooplankton)&&!isNullOrEmpty(waterReportModel.dafloc)){
            holder.binding.txtPlankton.setVisibility(View.GONE);
        }else {
            holder.binding.txtPlankton.setVisibility(View.VISIBLE);
        }

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
}