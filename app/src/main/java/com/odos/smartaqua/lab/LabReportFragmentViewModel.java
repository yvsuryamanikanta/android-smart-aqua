package com.odos.smartaqua.lab;


import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentListFeedBinding;
import com.odos.smartaqua.databinding.FragmentReportLabBinding;
import com.odos.smartaqua.feed.FeedListAdapter;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LabReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, LabReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportLabBinding _binding;
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

    public LabReportFragmentViewModel(Context context, FragmentReportLabBinding fragmentReportLabBinding, int tId, String cultureaccess) {
        this._context = context;
        this._binding = fragmentReportLabBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        tankId = tId;
        cultureAccess = cultureaccess;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_LAB_OBSV + tankId, null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
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
            case 1:
                try {
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                ArrayList<LabReportModel> arrayList = new ArrayList<>();
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
                                    LabReportModel labReportModel = new LabReportModel(labobservationid, tankid, userid,
                                            phvalue, salinity, co3, hco3, cahardness, mghardness, calcium, magnesium,
                                            potassium, sodium, iron, ionizedammonia, unionizedammonia, nitrate,
                                            hydrogensulphide, labdo, co2, greenalgae, diatom, bluegreenalgae,
                                            dinoflegellates, zooplankton, dafloc, vibriogreencolonies, vibrioyellowcolonies,
                                            labobsvdate, createddate, modifieddate);
                                    arrayList.add(labReportModel);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new LabReportAdapter(_context, arrayList, this));
                            } else {
                                Helper.showMessage(_context, "No Data Available Now.", AquaConstants.HOLD);
                            }
                        }
                    } else {
                        Helper.showMessage(_context, "" + status, AquaConstants.HOLD);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once.", AquaConstants.FINISH);
                }
                break;
        }
    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {
    }

    @Override
    public void onClicked(LabReportModel labReportModel, int pos) {

    }
}
