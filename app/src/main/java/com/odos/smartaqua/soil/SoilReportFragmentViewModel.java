package com.odos.smartaqua.soil;


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
import com.odos.smartaqua.databinding.FragmentReportSoilBinding;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SoilReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, SoilReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportSoilBinding _binding;
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
    private ArrayList<SoilReportModel> modelArrayList;

    public SoilReportFragmentViewModel(Context context, FragmentReportSoilBinding binding, int tId, String cultureaccess) {
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
                    ServiceConstants.LIST_SOIL_OBSV + tankId, null, Helper.headerParams(_context),
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
                ArrayList<SoilReportModel> modelArrayList = new ArrayList<>();
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
                                 String soilobservationid= jsonObject1.getString("soilobservationid");
                                 String tankid= jsonObject1.getString("tankid");
                                 String userid= jsonObject1.getString("userid");
                                 String culturecode= jsonObject1.getString("culturecode");
                                 String cultureloc= jsonObject1.getString("cultureloc");
                                 String obsvdate= jsonObject1.getString("obsvdate");
                                 String soiltype= jsonObject1.getString("soiltype");
                                 String phvalue= jsonObject1.getString("phvalue");
                                 String salinity= jsonObject1.getString("salinity");
                                 String texture= jsonObject1.getString("texture");
                                 String sand= jsonObject1.getString("sand");
                                 String slit= jsonObject1.getString("slit");
                                 String clay= jsonObject1.getString("clay");
                                 String organiccarbon= jsonObject1.getString("organiccarbon");
                                 String organicmatter= jsonObject1.getString("organicmatter");
                                 String nitrogen= jsonObject1.getString("nitrogen");
                                 String phosphorous= jsonObject1.getString("phosphorous");
                                 String pottasium= jsonObject1.getString("pottasium");
                                 String sulphur= jsonObject1.getString("sulphur");
                                 String ca= jsonObject1.getString("ca");
                                 String mg= jsonObject1.getString("mg");
                                 String iron= jsonObject1.getString("iron");
                                 String zinc= jsonObject1.getString("zinc");
                                 String ammonia= jsonObject1.getString("ammonia");
                                 String microbiology= jsonObject1.getString("microbiology");
                                 String comments= jsonObject1.getString("comments");
                                 String createddate= jsonObject1.getString("createddate");
                                 String modifieddate= jsonObject1.getString("modifieddate");


                                SoilReportModel _model = new SoilReportModel(soilobservationid,
                                        tankid,
                                        userid,
                                        culturecode,
                                        cultureloc,
                                        obsvdate,
                                        soiltype,
                                        phvalue,
                                        salinity,
                                        texture,
                                        sand,
                                        slit,
                                        clay,
                                        organiccarbon,
                                        organicmatter,
                                        nitrogen,
                                        phosphorous,
                                        pottasium,
                                        sulphur,
                                        ca,
                                        mg,
                                        iron,
                                        zinc,
                                        ammonia,
                                        microbiology,
                                        comments,
                                        createddate,
                                        modifieddate);
                                modelArrayList.add(_model);
                            }
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                            _binding.recyclerView.setLayoutManager(mLayoutManager);
                            _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            _binding.recyclerView.setAdapter(new SoilReportAdapter(_context, modelArrayList, this));
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
    public void onClicked(SoilReportModel model, int pos) {

    }
}
