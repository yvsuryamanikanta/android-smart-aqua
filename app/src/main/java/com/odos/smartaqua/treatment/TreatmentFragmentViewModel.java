package com.odos.smartaqua.treatment;


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
import com.odos.smartaqua.databinding.FragmentListTraetmentBinding;
import com.odos.smartaqua.feed.FeedListAdapter;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.products.ProductTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TreatmentFragmentViewModel extends ViewModel implements ServiceAsyncResponse, TraetmentsAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentListTraetmentBinding _binding;
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

    public TreatmentFragmentViewModel(Context context, FragmentListTraetmentBinding fragmentListTraetmentBinding, int cultureid, String cultureaccess) {
        this._context = context;
        this._binding = fragmentListTraetmentBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        products_jsonArray = new JSONArray();
        suppliment_jsonArray = new JSONArray();
        cultureId = cultureid;
        cultureAccess = cultureaccess;

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.FEED_TEMPLATES + Helper.getUserID(_context), null, Helper.headerParams(_context),
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
                                ArrayList<TreatmentModel> arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String tankid = jsonObject1.getString("tankid");
                                    String createddate = jsonObject1.getString("createddate");
                                    String decease = jsonObject1.getString("decease");
                                    String modifieddate = jsonObject1.getString("modifieddate");
                                    String solution = jsonObject1.getString("solution");
                                    String traetmentdate = jsonObject1.getString("traetmentdate");
                                    String treatmentsid = jsonObject1.getString("treatmentsid");
                                    TreatmentModel treatmentModel = new TreatmentModel(treatmentsid, createddate, tankid, decease, solution, traetmentdate, modifieddate);
                                    arrayList.add(treatmentModel);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new TraetmentsAdapter(_context, arrayList, this));
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
    public void onClicked(TreatmentModel treatmentModel, int pos) {

    }
}
