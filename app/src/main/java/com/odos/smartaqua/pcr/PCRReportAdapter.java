package com.odos.smartaqua.pcr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.AdapterPcrReportBinding;

import java.util.ArrayList;

public class PCRReportAdapter extends RecyclerView.Adapter<PCRReportAdapter.MyViewHolder> {

    ArrayList<PCRReportModel> homeModelArrayList;
    private LayoutInflater layoutInflater;
    private ClickListener listener;
    private Context _context;


    public PCRReportAdapter(Context context, ArrayList<PCRReportModel> arrayList, ClickListener listener) {
        this.homeModelArrayList = arrayList;
        this.listener = listener;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterPcrReportBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.adapter_pcr_report, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        PCRReportModel model = (PCRReportModel) homeModelArrayList.get(position);
        holder.binding.setModel(model);

        holder.binding.txtPond.setText("" + model.getTankid());
        holder.binding.txtObservationDate.setText("" + model.getObsvdate());

        if (isNullOrEmpty(model.getCreateddate())) {
            holder.binding.txtPhysicalActivity.setText("" + model.getPhysicalActivity());
            holder.binding.linearPhysicalActivity.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPhysicalActivity.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMeanBodyLeangth())) {
            holder.binding.txtMeanbodyLength.setText("" + model.getMeanBodyLeangth());
            holder.binding.linearMeanbodyLength.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMeanbodyLength.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getDorsalSpinesCount())) {
            holder.binding.txtDorsalSpines.setText("" + model.getDorsalSpinesCount());
            holder.binding.linearDorsalSpines.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearDorsalSpines.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEstimatedPlAge())) {
            holder.binding.txtPlAge.setText("" + model.getEstimatedPlAge());
            holder.binding.linearPlAge.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPlAge.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getCoefficientOfSizeVariation())) {
            holder.binding.txtSizeVariation.setText("" + model.getCoefficientOfSizeVariation());
            holder.binding.linearSizeVariation.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSizeVariation.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getSampleSalinity())) {
            holder.binding.txtSampleSalinity.setText("" + model.getSampleSalinity());
            holder.binding.linearSampleSalinity.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSampleSalinity.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getSalinitySressSurvival())) {
            holder.binding.txtSalinityStressLevel.setText("" + model.getSalinitySressSurvival());
            holder.binding.linearSalinityStressLevel.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearSalinityStressLevel.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getFormalinSressSurvival())) {
            holder.binding.txtFormalinStressLevel.setText("" + model.getFormalinSressSurvival());
            holder.binding.linearFormalinStressLevel.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearFormalinStressLevel.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getGillDevStatus())) {
            holder.binding.txtGillDevelopmentStatus.setText("" + model.getGillDevStatus());
            holder.binding.linearGillDevelopmentStatus.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearGillDevelopmentStatus.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMuscleGutRation())) {
            holder.binding.txtMuscleGutRatio.setText("" + model.getMuscleGutRation());
            holder.binding.linearMuscleGutRatio.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMuscleGutRatio.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEctoparasiteattachments())) {
            holder.binding.txtEctoparasiteAttachments.setText("" + model.getEctoparasiteattachments());
            holder.binding.linearEctoparasite.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearEctoparasite.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEndoParasite())) {
            holder.binding.txtEndoparasite.setText("" + model.getEndoParasite());
            holder.binding.linearEndoparasite.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearEndoparasite.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getNecrosis())) {
            holder.binding.txtNecrosis.setText("" + model.getNecrosis());
            holder.binding.linearNecrosis.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearNecrosis.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getStructuralDeformities())) {
            holder.binding.txtStructuralDeformities.setText("" + model.getStructuralDeformities());
            holder.binding.linearStructuralDeformities.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearStructuralDeformities.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getHepathopancreasLipid())) {
            holder.binding.txtHepatopancreasLipid.setText("" + model.getHepathopancreasLipid());
            holder.binding.linearHepatopancreasLipid.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearHepatopancreasLipid.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMbvOcclusionBodies())) {
            holder.binding.txtMBVOcclusionBodies.setText("" + model.getMbvOcclusionBodies());
            holder.binding.linearMBVOcclusionBodies.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearMBVOcclusionBodies.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getHypherTropiedNucleiInHp())) {
            holder.binding.txtHypertrophied.setText("" + model.getHypherTropiedNucleiInHp());
            holder.binding.linearHypertrophied.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearHypertrophied.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultWssv())) {
            holder.binding.txtPCRResultWSSV.setText("" + model.getPcrResultWssv());
            holder.binding.linearPCRResultWSSV.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPCRResultWSSV.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultEhp())) {
            holder.binding.txtPCRResultEhp.setText("" + model.getPcrResultEhp());
            holder.binding.linearPCRResultEhp.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPCRResultEhp.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultIhhnv())) {
            holder.binding.txtPCRResultIHHNV.setText("" + model.getPcrResultIhhnv());
            holder.binding.linearPCRResultIHHNV.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPCRResultIHHNV.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultEms())) {
            holder.binding.txtPCRResultEms.setText("" + model.getPcrResultEms());
            holder.binding.linearPCRResultEms.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearPCRResultEms.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getWssvCtValueSeviority())) {
            holder.binding.txtCTValueWSSV.setText("" + model.getWssvCtValueSeviority());
            holder.binding.linearCTValueWssv.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCTValueWssv.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getEhpCtValueSeviority())) {
            holder.binding.txtCTValueEhp.setText("" + model.getEhpCtValueSeviority());
            holder.binding.linearCTValueEhp.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCTValueEhp.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getIhhnvCtValueSeviority())) {
            holder.binding.txtCTValueIHHNV.setText("" + model.getIhhnvCtValueSeviority());
            holder.binding.linearCTValueIHHNV.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCTValueIHHNV.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getEmsCtValueSeviority())) {
            holder.binding.txtCTValueEms.setText("" + model.getEmsCtValueSeviority());
            holder.binding.linearCTValueEms.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearCTValueEms.setVisibility(View.GONE);
        }


        if (isNullOrEmpty(model.getComments())) {
            holder.binding.txtComment.setText("" + model.getComments());
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
        void onClicked(PCRReportModel model, int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final AdapterPcrReportBinding binding;

        public MyViewHolder(AdapterPcrReportBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}