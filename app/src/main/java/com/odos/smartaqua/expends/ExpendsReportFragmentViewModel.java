package com.odos.smartaqua.expends;


import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentReportExpendsBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ExpendsReportFragmentViewModel extends ViewModel implements ServiceAsyncResponse, ExpendsReportAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentReportExpendsBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<ProductTypes> qtyTypesArrayList;
    private JSONArray products_jsonArray, suppliment_jsonArray;
    private JSONObject jsonObject, suppliment_jsonObject;
    private int product_qtyTypeID, suppliment_qtyTypeID;
    private String qtycategorycode, suppliment_qtycategorycode;
    private int cultureId;
    private String cultureAccess;
    private String[] searchData;
    private double availablestock;
    private String productId, productCatgId, mrp, productName;

    public ExpendsReportFragmentViewModel(Context context, FragmentReportExpendsBinding binding, int cultureid, String cultureaccess) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        cultureAccess = cultureaccess;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_EXPENDS_OBSV + cultureId, null, Helper.headerParams(_context),
                    serviceAsyncResponse, 1, true);
        }
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
                                _binding.recyclerView.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setVisibility(View.GONE);
                                ArrayList<ExpendsReportModel> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String expendsid = jsonObject1.getString("expendsid");
                                    String tankid = jsonObject1.getString("tankid");
                                    String amount = jsonObject1.getString("amount");
                                    String reason = jsonObject1.getString("reason");
                                    String expendsdate = jsonObject1.getString("expendsdate");
                                    String createddate = jsonObject1.getString("createddate");
                                    String modifieddate = jsonObject1.getString("modifieddate");
                                    ExpendsReportModel model = new ExpendsReportModel(tankid, expendsid, amount, reason, expendsdate, createddate, modifieddate);
                                    arrayList.add(model);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new ExpendsReportAdapter(_context, arrayList, this));
                            } else {
                                _binding.recyclerView.setVisibility(View.GONE);
                                _binding.txtNodata.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setText("No Reports Available");
                            }
                        }
                    } else {
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.txtNodata.setVisibility(View.VISIBLE);
                        _binding.txtNodata.setText("No Reports Available");
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
    public void onClicked(ExpendsReportModel model, int pos) {

    }
}
