package com.odos.smartaqua.brand;


import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityBrandListBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BrandListViewModel extends BaseObservable implements ServiceAsyncResponse, BrandListAdapter.ClickListener {

    private Context _context;
    private ActivityBrandListBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<Brandcnsts> arrayList;

    public BrandListViewModel(Context context, ActivityBrandListBinding activityBrandListBinding) {
        this._context = context;
        this._binding = activityBrandListBinding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.BRAND_LIST, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);
        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }
        _binding.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AquaConstants.putIntent(_context, AddBrandActivity.class, AquaConstants.HOLD, null);
            }
        });
    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @Override
    public void jsonObjectResponse(String service, JSONObject jsonobject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    arrayList = new ArrayList<>();
                    String status = jsonobject.getString("status");
                    String statusCode = jsonobject.getString("statusCode");
                    String response = jsonobject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        if (!response.equalsIgnoreCase("null")) {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                _binding.recyclerView.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    int brandid = jsonObject1.getInt("brandid");
                                    String brandname = jsonObject1.getString("brandname");
                                    String brandcode = jsonObject1.getString("brandcode");
                                    String cretaedby = jsonObject1.getString("cretaedby");
                                    Brandcnsts model = new Brandcnsts(brandid, brandname, brandcode, cretaedby);
                                    arrayList.add(model);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new BrandListAdapter(_context, arrayList, this));
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
    public void onClicked(Brandcnsts model, int pos) {

    }
}
