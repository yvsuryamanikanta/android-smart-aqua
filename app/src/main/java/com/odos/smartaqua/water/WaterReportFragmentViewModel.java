package com.odos.smartaqua.water;


import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportWaterBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class WaterReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, WaterReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportWaterBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int tankId;
    private String cultureAccess,tankName;
    private String[] searchData;
    private double availablestock;
    private String productId, productCatgId, mrp, productName;
    private ArrayList<WaterReportModel> waterReportModelArrayList;

    public WaterReportFragmentViewModel(Context context, FragmentReportWaterBinding binding, int tId, String cultureaccess, String tName) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        tankId = tId;
        cultureAccess = cultureaccess;
        tankName = tName;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_LAB_OBSV + tankId, null, Helper.headerParams(_context),
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
                ArrayList<WaterReportModel> waterReportModelArrayList = new ArrayList<>();
                try {
                    waterReportModelArrayList = new ArrayList<>();
                    String status = jsonobject.getString("status");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                _binding.recyclerView.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int labobservationid = jsonObject1.getInt("labobservationid");
                                    int tankid = jsonObject1.getInt("tankid");
                                    int userid = jsonObject1.getInt("userid");
                                    String phvalue = jsonObject1.getString("phvalue");
                                    String salinity = jsonObject1.getString("salinity");
                                    String co3 = jsonObject1.getString("co3");
                                    String hco3 = jsonObject1.getString("hco3");
                                    String cahardness = jsonObject1.getString("cahardness");
                                    String mghardness = jsonObject1.getString("mghardness");
                                    String calcium = jsonObject1.getString("calcium");
                                    String magnesium = jsonObject1.getString("magnesium");
                                    String potassium = jsonObject1.getString("potassium");
                                    String sodium = jsonObject1.getString("sodium");
                                    String iron = jsonObject1.getString("iron");
                                    String ionizedammonia = jsonObject1.getString("ionizedammonia");
                                    String unionizedammonia = jsonObject1.getString("unionizedammonia");
                                    String nitrate = jsonObject1.getString("nitrate");
                                    String hydrogensulphide = jsonObject1.getString("hydrogensulphide");
                                    String labdo = jsonObject1.getString("labdo");
                                    String co2 = jsonObject1.getString("co2");
                                    String greenalgae = jsonObject1.getString("greenalgae");
                                    String diatom = jsonObject1.getString("diatom");
                                    String bluegreenalgae = jsonObject1.getString("bluegreenalgae");
                                    String dinoflegellates = jsonObject1.getString("dinoflegellates");
                                    String zooplankton = jsonObject1.getString("zooplankton");
                                    String dafloc = jsonObject1.getString("dafloc");
                                    String vibriogreencolonies = jsonObject1.getString("vibriogreencolonies");
                                    String vibrioyellowcolonies = jsonObject1.getString("vibrioyellowcolonies");
                                    String labobsvdate = jsonObject1.getString("labobsvdate");
                                    String createddate = jsonObject1.getString("createddate");
                                    String modifieddate = jsonObject1.getString("modifieddate");
                                    WaterReportModel _model = new WaterReportModel(labobservationid, tankid, userid,
                                            phvalue, salinity, co3, hco3, cahardness, mghardness, calcium, magnesium,
                                            potassium, sodium, iron, ionizedammonia, unionizedammonia, nitrate,
                                            hydrogensulphide, labdo, co2, greenalgae, diatom, bluegreenalgae,
                                            dinoflegellates, zooplankton, dafloc, vibriogreencolonies, vibrioyellowcolonies,
                                            labobsvdate, createddate, modifieddate,tankName);
                                    waterReportModelArrayList.add(_model);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new WaterReportAdapter(_context, waterReportModelArrayList, this));
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
                    _binding.txtNodata.setText("something went wrong please restart app once."+e);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }

    @Override
    public void onClicked(WaterReportModel model, int pos) {

    }
}
