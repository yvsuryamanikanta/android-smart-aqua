package com.odos.smartaqua.animal;


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
import com.odos.smartaqua.databinding.FragmentReportAnimalBinding;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class AnimalReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, AnimalReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportAnimalBinding _binding;
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
    private ArrayList<AnimalReportModel> modelArrayList;

    public AnimalReportFragmentViewModel(Context context, FragmentReportAnimalBinding binding, int tId, String cultureaccess) {
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
                    ServiceConstants.LIST_ANIMAL_OBSV + tankId, null, Helper.headerParams(_context),
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
                ArrayList<AnimalReportModel> modelArrayList = new ArrayList<>();
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
                                String pcrobservationid = jsonObject1.getString("pcrobservationid");
                                String tankid = jsonObject1.getString("tankid");
                                String obsvdate = jsonObject1.getString("obsvdate");
                                String yellowcolony = jsonObject1.getString("yellowcolony");
                                String greencolony = jsonObject1.getString("greencolony");
                                String comments = jsonObject1.getString("comments");

                                AnimalReportModel _model = new AnimalReportModel(pcrobservationid, tankid, obsvdate, greencolony, yellowcolony, comments);
                                modelArrayList.add(_model);
                            }
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                            _binding.recyclerView.setLayoutManager(mLayoutManager);
                            _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            _binding.recyclerView.setAdapter(new AnimalReportAdapter(_context, modelArrayList, this));
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
    public void onClicked(AnimalReportModel model, int pos) {

    }
}
