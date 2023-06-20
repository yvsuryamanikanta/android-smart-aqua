package com.odos.smartaqua.animal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterAnimalReportBinding;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class AnimalReportAdapter extends RecyclerView.Adapter<AnimalReportAdapter.MyViewHolder> {

    ArrayList<AnimalReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public AnimalReportAdapter(Context context, ArrayList<AnimalReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterAnimalReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_animal_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        AnimalReportModel model = (AnimalReportModel) homeModelArrayList.get(position);
        holder.binding.setModel(model);
        holder.binding.share.setOnClickListener(v -> {
            try{
                PdfGeneratorNew pdfGeneratorNew = new PdfGeneratorNew(_context);
                Bitmap bitmap = pdfGeneratorNew.getViewScreenShot(holder.binding.header);
                pdfGeneratorNew.saveImageToPDF(holder.binding.header, bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        holder.binding.txtPond.setText("" + model.getTankid());
        holder.binding.txtObservationDate.setText("" + model.getObsvdate());

        if (isNullOrEmpty(model.getGreencolony())) {
            holder.binding.txtGreenColonies.setText(model.getGreencolony());
            holder.binding.linearGreen.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearGreen.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getYellowcolony())) {
            holder.binding.txtYellowColonies.setText(model.getYellowcolony());
            holder.binding.linearYellow.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearYellow.setVisibility(View.GONE);
        }

        if (!model.getGreencolony().equals("") && model.getYellowcolony().equals("")) {
            holder.binding.txtTotalVibrioColonies.setText(model.getGreencolony());
        } else if (model.getGreencolony().equals("") && !model.getYellowcolony().equals("")) {
            holder.binding.txtTotalVibrioColonies.setText(model.getYellowcolony());
        } else if (!model.getGreencolony().equals("") && !model.getYellowcolony().equals("")) {
            holder.binding.txtTotalVibrioColonies.setText(""+
                    (Integer.parseInt(model.getGreencolony()) + Integer.parseInt(model.getYellowcolony())));
        }

        if (isNullOrEmpty(model.getComments())) {
            holder.binding.txtComment.setText(model.getComments());
            holder.binding.linearComment.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearComment.setVisibility(View.GONE);
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
        void onClicked(AnimalReportModel model, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterAnimalReportBinding binding;

        public MyViewHolder(AdapterAnimalReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}