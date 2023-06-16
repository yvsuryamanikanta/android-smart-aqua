package com.odos.smartaqua.pcr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.odos.smartaqua.R;


public class PCRtoPdf extends AppCompatActivity {
    PCRReportModel model;
    ProgressDialog progressIndicator;
    private View finalInvoiceViewToPrint;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcr_to_pdf);
        model = (PCRReportModel) getIntent().getSerializableExtra("model");

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);

        finalInvoiceViewToPrint = viewGroup.findViewById(R.id.header);

        TextView observationDate = finalInvoiceViewToPrint.findViewById(R.id.txt_observation_date);
        TextView pond = finalInvoiceViewToPrint.findViewById(R.id.txt_pond);
        TextView txtPhysicalActivity = finalInvoiceViewToPrint.findViewById(R.id.txt_physical_activity);
        LinearLayout linearPhysicalActivity = finalInvoiceViewToPrint.findViewById(R.id.linear_physical_activity);
        TextView txtMeanbodyLength = finalInvoiceViewToPrint.findViewById(R.id.txt_meanbody_length);
        LinearLayout linearMeanbodyLength = finalInvoiceViewToPrint.findViewById(R.id.linear_meanbody_length);
        TextView txtDorsalSpines = finalInvoiceViewToPrint.findViewById(R.id.txt_dorsal_spines);
        LinearLayout linearDorsalSpines = finalInvoiceViewToPrint.findViewById(R.id.linear_dorsal_spines);
        TextView txtPlAge = finalInvoiceViewToPrint.findViewById(R.id.txt_pl_age);
        LinearLayout linearPlAge = finalInvoiceViewToPrint.findViewById(R.id.linear_pl_age);
        TextView txtSizeVariation = finalInvoiceViewToPrint.findViewById(R.id.txt_size_variation);
        LinearLayout linearSizeVariation = finalInvoiceViewToPrint.findViewById(R.id.linear_size_variation);
        TextView txtSampleSalinity = finalInvoiceViewToPrint.findViewById(R.id.txt_sample_salinity);
        LinearLayout linearSampleSalinity = finalInvoiceViewToPrint.findViewById(R.id.linear_sample_salinity);
        TextView txtSalinityStressLevel = finalInvoiceViewToPrint.findViewById(R.id.txt_salinity_stress_level);
        LinearLayout linearSalinityStressLevel = finalInvoiceViewToPrint.findViewById(R.id.linear_salinity_stress_level);
        TextView txtFormalinStressLevel = finalInvoiceViewToPrint.findViewById(R.id.txt_formalin_stress_level);
        LinearLayout linearFormalinStressLevel = finalInvoiceViewToPrint.findViewById(R.id.linear_formalin_stress_level);
        TextView txtGillDevelopmentStatus = finalInvoiceViewToPrint.findViewById(R.id.txt_gill_development_status);
        LinearLayout linearGillDevelopmentStatus = finalInvoiceViewToPrint.findViewById(R.id.linear_gill_development_status);
        TextView txtMuscleGutRatio = finalInvoiceViewToPrint.findViewById(R.id.txt_muscle_gut_ratio);
        LinearLayout linearMuscleGutRatio = finalInvoiceViewToPrint.findViewById(R.id.linear_muscle_gut_ratio);
        TextView txtEctoparasiteAttachments = finalInvoiceViewToPrint.findViewById(R.id.txt_ectoparasite_attachments);
        LinearLayout linearEctoparasite = finalInvoiceViewToPrint.findViewById(R.id.linear_ectoparasite);
        TextView txtEndoparasite = finalInvoiceViewToPrint.findViewById(R.id.txt_endoparasite);
        LinearLayout linearEndoparasite = finalInvoiceViewToPrint.findViewById(R.id.linear_endoparasite);
        TextView txtNecrosis = finalInvoiceViewToPrint.findViewById(R.id.txt_necrosis);
        LinearLayout linearNecrosis = finalInvoiceViewToPrint.findViewById(R.id.linear_necrosis);
        TextView txtStructuralDeformities = finalInvoiceViewToPrint.findViewById(R.id.txt_structural_deformities);
        LinearLayout linearStructuralDeformities = finalInvoiceViewToPrint.findViewById(R.id.linear_structural_deformities);
        TextView txtHepatopancreasLipid = finalInvoiceViewToPrint.findViewById(R.id.txt_hepatopancreas_lipid);
        LinearLayout linearHepatopancreasLipid = finalInvoiceViewToPrint.findViewById(R.id.linear_hepatopancreas_lipid);
        TextView txtMBVOcclusionBodies = finalInvoiceViewToPrint.findViewById(R.id.txt_MBV_occlusion_bodies);
        LinearLayout linearMBVOcclusionBodies = finalInvoiceViewToPrint.findViewById(R.id.linear_MBV_occlusion_bodies);
        TextView txtHypertrophied = finalInvoiceViewToPrint.findViewById(R.id.txt_hypertrophied);
        LinearLayout linearHypertrophied = finalInvoiceViewToPrint.findViewById(R.id.linear_hypertrophied);
        TextView txtPCRResultWSSV = finalInvoiceViewToPrint.findViewById(R.id.txt_PCR_result_WSSV);
        LinearLayout linearPCRResultWSSV = finalInvoiceViewToPrint.findViewById(R.id.linear_PCR_result_WSSV);
        TextView txtPCRResultEhp = finalInvoiceViewToPrint.findViewById(R.id.txt_PCR_result_Ehp);
        LinearLayout linearPCRResultEhp = finalInvoiceViewToPrint.findViewById(R.id.linear_PCR_result_Ehp);
        TextView txtPCRResultIHHNV = finalInvoiceViewToPrint.findViewById(R.id.txt_PCR_result_IHHNV);
        LinearLayout linearPCRResultIHHNV = finalInvoiceViewToPrint.findViewById(R.id.linear_PCR_result_IHHNV);
        TextView txtPCRResultEms = finalInvoiceViewToPrint.findViewById(R.id.txt_PCR_result_Ems);
        LinearLayout linearPCRResultEms = finalInvoiceViewToPrint.findViewById(R.id.linear_PCR_result_Ems);
        TextView txtCTValueWSSV = finalInvoiceViewToPrint.findViewById(R.id.txt_CT_Value_WSSV);
        LinearLayout linearCTValueWssv = finalInvoiceViewToPrint.findViewById(R.id.linear_CT_Value_Wssv);
        TextView txtCTValueEhp = finalInvoiceViewToPrint.findViewById(R.id.txt_CT_Value_Ehp);
        LinearLayout linearCTValueEhp = finalInvoiceViewToPrint.findViewById(R.id.linear_CT_Value_Ehp);
        TextView txtCTValueIHHNV = finalInvoiceViewToPrint.findViewById(R.id.txt_CT_Value_IHHNV);
        LinearLayout linearCTValueIHHNV = finalInvoiceViewToPrint.findViewById(R.id.linear_CT_Value_IHHNV);
        TextView txtCTValueEms = finalInvoiceViewToPrint.findViewById(R.id.txt_CT_Value_Ems);
        LinearLayout linearCTValueEms = finalInvoiceViewToPrint.findViewById(R.id.linear_CT_Value_Ems);
        TextView txtComment = finalInvoiceViewToPrint.findViewById(R.id.txt_comment);
        LinearLayout linearComment = finalInvoiceViewToPrint.findViewById(R.id.linear_comment);

        observationDate.setText("" + model.getObsvdate());
        pond.setText("" + model.getTankid());
        if (isNullOrEmpty(model.getCreateddate())) {
            txtPhysicalActivity.setText("" + model.getPhysicalActivity());
            linearPhysicalActivity.setVisibility(View.VISIBLE);
        } else {
            linearPhysicalActivity.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMeanBodyLeangth())) {
            txtMeanbodyLength.setText("" + model.getMeanBodyLeangth());
            linearMeanbodyLength.setVisibility(View.VISIBLE);
        } else {
            linearMeanbodyLength.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getDorsalSpinesCount())) {
            txtDorsalSpines.setText("" + model.getDorsalSpinesCount());
            linearDorsalSpines.setVisibility(View.VISIBLE);
        } else {
            linearDorsalSpines.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEstimatedPlAge())) {
            txtPlAge.setText("" + model.getEstimatedPlAge());
            linearPlAge.setVisibility(View.VISIBLE);
        } else {
            linearPlAge.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getCoefficientOfSizeVariation())) {
            txtSizeVariation.setText("" + model.getCoefficientOfSizeVariation());
            linearSizeVariation.setVisibility(View.VISIBLE);
        } else {
            linearSizeVariation.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getSampleSalinity())) {
            txtSampleSalinity.setText("" + model.getSampleSalinity());
            linearSampleSalinity.setVisibility(View.VISIBLE);
        } else {
            linearSampleSalinity.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getSalinitySressSurvival())) {
            txtSalinityStressLevel.setText("" + model.getSalinitySressSurvival());
            linearSalinityStressLevel.setVisibility(View.VISIBLE);
        } else {
            linearSalinityStressLevel.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getFormalinSressSurvival())) {
            txtFormalinStressLevel.setText("" + model.getFormalinSressSurvival());
            linearFormalinStressLevel.setVisibility(View.VISIBLE);
        } else {
            linearFormalinStressLevel.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getGillDevStatus())) {
            txtGillDevelopmentStatus.setText("" + model.getGillDevStatus());
            linearGillDevelopmentStatus.setVisibility(View.VISIBLE);
        } else {
            linearGillDevelopmentStatus.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMuscleGutRation())) {
            txtMuscleGutRatio.setText("" + model.getMuscleGutRation());
            linearMuscleGutRatio.setVisibility(View.VISIBLE);
        } else {
            linearMuscleGutRatio.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEctoparasiteattachments())) {
            txtEctoparasiteAttachments.setText("" + model.getEctoparasiteattachments());
            linearEctoparasite.setVisibility(View.VISIBLE);
        } else {
            linearEctoparasite.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getEndoParasite())) {
            txtEndoparasite.setText("" + model.getEndoParasite());
            linearEndoparasite.setVisibility(View.VISIBLE);
        } else {
            linearEndoparasite.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getNecrosis())) {
            txtNecrosis.setText("" + model.getNecrosis());
            linearNecrosis.setVisibility(View.VISIBLE);
        } else {
            linearNecrosis.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getStructuralDeformities())) {
            txtStructuralDeformities.setText("" + model.getStructuralDeformities());
            linearStructuralDeformities.setVisibility(View.VISIBLE);
        } else {
            linearStructuralDeformities.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getHepathopancreasLipid())) {
            txtHepatopancreasLipid.setText("" + model.getHepathopancreasLipid());
            linearHepatopancreasLipid.setVisibility(View.VISIBLE);
        } else {
            linearHepatopancreasLipid.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getMbvOcclusionBodies())) {
            txtMBVOcclusionBodies.setText("" + model.getMbvOcclusionBodies());
            linearMBVOcclusionBodies.setVisibility(View.VISIBLE);
        } else {
            linearMBVOcclusionBodies.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getHypherTropiedNucleiInHp())) {
            txtHypertrophied.setText("" + model.getHypherTropiedNucleiInHp());
            linearHypertrophied.setVisibility(View.VISIBLE);
        } else {
            linearHypertrophied.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultWssv())) {
            txtPCRResultWSSV.setText("" + model.getPcrResultWssv());
            linearPCRResultWSSV.setVisibility(View.VISIBLE);
        } else {
            linearPCRResultWSSV.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultEhp())) {
            txtPCRResultEhp.setText("" + model.getPcrResultEhp());
            linearPCRResultEhp.setVisibility(View.VISIBLE);
        } else {
            linearPCRResultEhp.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultIhhnv())) {
            txtPCRResultIHHNV.setText("" + model.getPcrResultIhhnv());
            linearPCRResultIHHNV.setVisibility(View.VISIBLE);
        } else {
            linearPCRResultIHHNV.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getPcrResultEms())) {
            txtPCRResultEms.setText("" + model.getPcrResultEms());
            linearPCRResultEms.setVisibility(View.VISIBLE);
        } else {
            linearPCRResultEms.setVisibility(View.GONE);
        }

        if (isNullOrEmpty(model.getWssvCtValueSeviority())) {
            txtCTValueWSSV.setText("" + model.getWssvCtValueSeviority());
            linearCTValueWssv.setVisibility(View.VISIBLE);
        } else {
            linearCTValueWssv.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getEhpCtValueSeviority())) {
            txtCTValueEhp.setText("" + model.getEhpCtValueSeviority());
            linearCTValueEhp.setVisibility(View.VISIBLE);
        } else {
            linearCTValueEhp.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getIhhnvCtValueSeviority())) {
            txtCTValueIHHNV.setText("" + model.getIhhnvCtValueSeviority());
            linearCTValueIHHNV.setVisibility(View.VISIBLE);
        } else {
            linearCTValueIHHNV.setVisibility(View.GONE);
        }
        if (isNullOrEmpty(model.getEmsCtValueSeviority())) {
            txtCTValueEms.setText("" + model.getEmsCtValueSeviority());
            linearCTValueEms.setVisibility(View.VISIBLE);
        } else {
            linearCTValueEms.setVisibility(View.GONE);
        }


        if (isNullOrEmpty(model.getComments())) {
            txtComment.setText("" + model.getComments());
            linearComment.setVisibility(View.VISIBLE);
        } else {
            linearComment.setVisibility(View.GONE);
        }

//        progressIndicator = new ProgressDialog(PCRtoPdf.this);
//        progressIndicator.setIndeterminate(true);
//        progressIndicator.setMessage(getString(R.string.pleaseWait));
//        progressIndicator.setCancelable(true);
//        progressIndicator.setCanceledOnTouchOutside(false);
//        progressIndicator.show();

        new Handler().postDelayed(() -> generatePdf(), 1000);
    }

    boolean isNullOrEmpty(String data) {
        return data != null && !data.equalsIgnoreCase("");
    }


    public void generatePdf() {
        PdfGenerator.getBuilder()
                .setContext(PCRtoPdf.this)
                .fromViewSource()
                .fromView(finalInvoiceViewToPrint)
                /* "fromLayoutXML()" takes array of layout resources.
                 * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                /* It takes default page size like A4,A5. You can also set custom page size in pixel
                 * by calling ".setCustomPageSize(int widthInPX, int heightInPX)" here. */
                .setFileName("report - " + model.getObsvdate())
                /* It is file name */

                .setFolderNameOrPath("Downloads")
                /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
                 * FolderA creates first.Then FolderB inside FolderB and also FolderC inside the FolderB and finally
                 * the pdf file named "Test-PDF.pdf" will be store inside the FolderB. */
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                /* It true then the generated pdf will be shown after generated. */
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Log.d("%%%%%%%%", "onFailure: " + failureResponse.getErrorMessage());
                        /* If pdf is not generated by an error then you will findout the reason behind it
                         * from this FailureResponse. */
                        //Toast.makeText(MainActivity.this, "Failure : "+failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "" + failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        Log.d("%%%%%%%%%", "log: " + log);
                        /*It shows logs of events inside the pdf generation process*/
                    }

                    @Override
                    public void onStartPDFGeneration() {

                    }

                    @Override
                    public void onFinishPDFGeneration() {

                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);
                        /* If PDF is generated successfully then you will find SuccessResponse
                         * which holds the PdfDocument,File and path (where generated pdf is stored)*/
                        //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Log.d("%%%%%", "Success: " + response.getPath());

                    }
                });

//        progressIndicator.dismiss();
    }


}
