package com.odos.smartaqua.soil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterSoilReportBinding;
import com.odos.smartaqua.utils.PdfGeneratorNew;

import java.util.ArrayList;

public class SoilReportAdapter extends RecyclerView.Adapter<SoilReportAdapter.MyViewHolder> {

    ArrayList<SoilReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public SoilReportAdapter(Context context, ArrayList<SoilReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterSoilReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_soil_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        SoilReportModel model = (SoilReportModel) homeModelArrayList.get(position);
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

        if (isNullOrEmpty(model.getAmmonia())) {
            holder.binding.txtAmmonia.setText(model.getAmmonia() + " ppm");
            holder.binding.linearAmmonia.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearAmmonia.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getCa())) {
            holder.binding.txtCa.setText(model.getCa() + " ppm");
            holder.binding.linearCa.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCa.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMg())) {
            holder.binding.txtMg.setText(model.getMg() + " ppm");
            holder.binding.linearMg.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMg.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getClay())) {
            holder.binding.txtClay.setText(model.getClay() + " ppm");
            holder.binding.linearClay.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearClay.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getIron())) {
            holder.binding.txtIron.setText(model.getIron() + " ppm");
            holder.binding.linearIron.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearIron.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMicrobiology())) {
            holder.binding.txtMicrobiology.setText(model.getMicrobiology());
            holder.binding.linearMicrobiology.setVisibility(View.VISIBLE);
        } else {
            holder.binding.txtMicrobiology.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getNitrogen())) {
            holder.binding.txtNitrogen.setText(model.getNitrogen() + " %");
            holder.binding.linearNitrogen.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearNitrogen.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getOrganiccarbon())) {
            holder.binding.txtOrganicCarbon.setText(model.getOrganiccarbon() + " %");
            holder.binding.linearOrganicCarbon.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearOrganicCarbon.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getOrganicmatter())) {
            holder.binding.txtOrganicMatter.setText(model.getOrganicmatter() + " %");
            holder.binding.linearOrganicMatter.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearOrganicMatter.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPhosphorous())) {
            holder.binding.txtPhosphorous.setText(model.getPhosphorous() + " %");
            holder.binding.linearPhosphorous.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPhosphorous.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPhvalue())) {
            holder.binding.txtPhValue.setText(model.getPhvalue());
            holder.binding.linearPhValue.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPhValue.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPottasium())) {
            holder.binding.txtPottasium.setText(model.getPottasium() + " ppm");
            holder.binding.linearPottasium.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPottasium.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getSalinity())) {
            holder.binding.txtSalinity.setText(model.getSalinity() + " ppm");
            holder.binding.linearSalinity.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSalinity.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getSand())) {
            holder.binding.txtSand.setText(model.getSand() + " %");
            holder.binding.linearSand.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSand.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getSlit())) {
            holder.binding.txtSlit.setText(model.getSlit() + " %");
            holder.binding.linearSlit.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSlit.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getSoiltype())) {
            holder.binding.txtSoilType.setText(model.getSoiltype());
            holder.binding.linearSoilType.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSoilType.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getSulphur())) {
            holder.binding.txtSulphur.setText(model.getSulphur() + " ppm");
            holder.binding.linearSulphur.setVisibility(View.VISIBLE);
        } else {
            holder.binding.txtSulphur.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getTexture())) {
            holder.binding.txtTexture.setText(model.getTexture());
            holder.binding.linearTexture.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearTexture.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getZinc())) {
            holder.binding.txtZinc.setText(model.getZinc() + " ppm");
            holder.binding.linearZinc.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearZinc.setVisibility(View.GONE);
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
        void onClicked(SoilReportModel model, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterSoilReportBinding binding;

        public MyViewHolder(AdapterSoilReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}