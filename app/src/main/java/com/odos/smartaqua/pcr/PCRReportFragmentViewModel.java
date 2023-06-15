package com.odos.smartaqua.pcr;


import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportPcrBinding;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PCRReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, PCRReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportPcrBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int tankId;
    private String cultureAccess;
    private String[] searchData;
    private double availablestock;
    private String productId, productCatgId, mrp, productName;
    private ArrayList<PCRReportModel> modelArrayList;

    public PCRReportFragmentViewModel(Context context, FragmentReportPcrBinding binding, int tId, String cultureaccess) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        tankId = tId;
        cultureAccess = cultureaccess;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_PCR_OBSV + tankId, null, Helper.headerParams(_context),
                    serviceAsyncResponse, 11, true);
        }
    }

    public void loadAvailableStock(double stock) {
        availablestock = stock;
    }

    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 11:
                Log.e("######## 11 ", " " + jsonobject);
                ArrayList<PCRReportModel> modelArrayList = new ArrayList<>();
                try {
                    modelArrayList = new ArrayList<>();
                    String status = jsonobject.getString("status");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() != 0) {
                            _binding.recyclerView.setVisibility(View.VISIBLE);
                            _binding.txtNodata.setVisibility(View.GONE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String pcrobservationid = "" + jsonObject1.getInt("pcrobservationid");
                                String tankid = "" + jsonObject1.getInt("tankid");
                                String userid = "" + jsonObject1.getInt("userid");
                                String culturecode = jsonObject1.getString("culturecode");
                                String cultureloc = jsonObject1.getString("cultureloc");
                                String obsvdate = jsonObject1.getString("obsvdate");
                                String physicalActivity = jsonObject1.getString("physicalActivity");
                                String meanBodyLeangth = jsonObject1.getString("meanBodyLeangth");
                                String dorsalSpinesCount = jsonObject1.getString("dorsalSpinesCount");
                                String estimatedPlAge = jsonObject1.getString("estimatedPlAge");
                                String coefficientOfSizeVariation = jsonObject1.getString("coefficientOfSizeVariation");
                                String sampleSalinity = jsonObject1.getString("sampleSalinity");
                                String salinitySressSurvival = jsonObject1.getString("salinitySressSurvival");
                                String formalinSressSurvival = jsonObject1.getString("formalinSressSurvival");
                                String gillDevStatus = jsonObject1.getString("gillDevStatus");
                                String muscleGutRation = jsonObject1.getString("muscleGutRation");
                                String ectoparasiteattachments = jsonObject1.getString("ectoparasiteattachments");
                                String endoParasite = jsonObject1.getString("endoParasite");
                                String necrosis = jsonObject1.getString("necrosis");
                                String structuralDeformities = jsonObject1.getString("structuralDeformities");
                                String hepathopancreasLipid = jsonObject1.getString("hepathopancreasLipid");
                                String mbvOcclusionBodies = jsonObject1.getString("mbvOcclusionBodies");
                                String hypherTropiedNucleiInHp = jsonObject1.getString("hypherTropiedNucleiInHp");
                                String pcrResultWssv = jsonObject1.getString("pcrResultWssv");
                                String wssvCtValueSeviority = jsonObject1.getString("wssvCtValueSeviority");
                                String pcrResultEhp = jsonObject1.getString("pcrResultEhp");
                                String ehpCtValueSeviority = jsonObject1.getString("ehpCtValueSeviority");
                                String pcrResultIhhnv = jsonObject1.getString("pcrResultIhhnv");
                                String ihhnvCtValueSeviority = jsonObject1.getString("ihhnvCtValueSeviority");
                                String pcrResultEms = jsonObject1.getString("pcrResultEms");
                                String emsCtValueSeviority = jsonObject1.getString("emsCtValueSeviority");
                                String comments = jsonObject1.getString("comments");
                                String createddate = jsonObject1.getString("createddate");
                                String modifieddate = jsonObject1.getString("modifieddate");

                                PCRReportModel _model = new PCRReportModel(pcrobservationid, tankid
                                        , userid
                                        , culturecode
                                        , cultureloc
                                        , obsvdate
                                        , physicalActivity
                                        , meanBodyLeangth
                                        , dorsalSpinesCount
                                        , estimatedPlAge
                                        , coefficientOfSizeVariation
                                        , sampleSalinity
                                        , salinitySressSurvival
                                        , formalinSressSurvival
                                        , gillDevStatus
                                        , muscleGutRation
                                        , ectoparasiteattachments
                                        , endoParasite
                                        , necrosis
                                        , structuralDeformities
                                        , hepathopancreasLipid
                                        , mbvOcclusionBodies
                                        , hypherTropiedNucleiInHp
                                        , pcrResultWssv
                                        , wssvCtValueSeviority
                                        , pcrResultEhp
                                        , ehpCtValueSeviority
                                        , pcrResultIhhnv
                                        , ihhnvCtValueSeviority
                                        , pcrResultEms
                                        , emsCtValueSeviority
                                        , comments
                                        , createddate
                                        , modifieddate);
                                modelArrayList.add(_model);
                            }
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                            _binding.recyclerView.setLayoutManager(mLayoutManager);
                            _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            _binding.recyclerView.setAdapter(new PCRReportAdapter(_context, modelArrayList, this));
                        } else {
                            _binding.recyclerView.setVisibility(View.GONE);
                            _binding.txtNodata.setVisibility(View.VISIBLE);
                            _binding.txtNodata.setText("No Reports Available");
                        }
                    } else {
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.txtNodata.setVisibility(View.VISIBLE);
                        _binding.txtNodata.setText("No Reports Available");
                    }
                } catch (Exception e) {
                    _binding.recyclerView.setVisibility(View.GONE);
                    _binding.txtNodata.setVisibility(View.VISIBLE);
                    _binding.txtNodata.setText("something went wrong please restart app once." + e);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }

    @Override
    public void onClicked(PCRReportModel model, int pos) {

    }
}
