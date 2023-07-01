package com.odos.smartaqua.warehouse.products;


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
import com.odos.smartaqua.databinding.FragmentProductsBinding;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.odos.smartaqua.warehouse.stock.AddStockActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductsFragmentViewModel extends ViewModel implements ServiceAsyncResponse, ProductListAdapter.ClickListener {

    private Context _context;
    private boolean loading = true;
    private FragmentProductsBinding _binding;
    private ServiceAsyncResponse serviceAsyncResponse;
    private int brandId;
    ArrayList<ProductTypes> arrayList;

    public ProductsFragmentViewModel(Context context, FragmentProductsBinding binding, int id) {
        this._context = context;
        this._binding = binding;
        this.serviceAsyncResponse = (ServiceAsyncResponse) this;
        brandId = id;
        _binding.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AquaConstants.putIntent(_context, AddProductActivity.class, AquaConstants.HOLD, null);
            }
        });

    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.LIST_PRODUCTS + brandId + "/"+Helper.getUserID(_context), null, Helper.headerParams(_context),
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
                                    int ptID = jsonObject1.getInt("productid");
                                    String productType = jsonObject1.getString("productname");
                                    String ptCode = jsonObject1.getString("productcatgeoryid");
                                    String createDate = jsonObject1.getString("costperqty");
                                    String quantity = jsonObject1.getString("quantity");
                                    int quantitycategoryid = jsonObject1.getInt("quantitycategoryid");
                                    ProductTypes model = new ProductTypes(ptID, productType, ptCode, quantity, quantitycategoryid);
                                    arrayList.add(model);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, 1);
                                _binding.recyclerView.setLayoutManager(mLayoutManager);
                                _binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                _binding.recyclerView.setAdapter(new ProductListAdapter(_context, arrayList, this));
                            } else {
                                _binding.recyclerView.setVisibility(View.GONE);
                                _binding.txtNodata.setVisibility(View.VISIBLE);
                                _binding.txtNodata.setText("No Products Available");
                            }
                        }
                    } else {
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.txtNodata.setVisibility(View.VISIBLE);
                        _binding.txtNodata.setText("No Products Available");
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
    public void onClicked(ProductTypes model, int pos) {

    }
}
