package com.odos.smartaqua.feed;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentAddFeedBinding;
import com.odos.smartaqua.warehouse.products.SearchProductActivity;

public class AddFeedFragment extends Fragment {
    private FragmentAddFeedBinding _binding;
    private int cultureId;
    private String cultureAccess;
    private AddFeedFragmentModel addFeedFragmentModel;
    private String stockid, userid, productid, qtycategoryid, productname, newstock, oldstock, availablestock,
            mrp, createddate, productcategoryid,quantityname;
    private String[] stock;

    public static Fragment newInstance(int cultureid, String cultureaccess) {
        AddFeedFragment addFeedFragment = new AddFeedFragment();
        Bundle bundle_data = new Bundle();
        bundle_data.putInt("cultureId", cultureid);
        bundle_data.putString("cultureAccess", cultureaccess);
        addFeedFragment.setArguments(bundle_data);
        return addFeedFragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_feed, container, false);
        if (getArguments() != null) {
            cultureId = getArguments().getInt("cultureId");
            cultureAccess = getArguments().getString("cultureAccess");
            addFeedFragmentModel = new AddFeedFragmentModel(getActivity(), _binding, cultureId, cultureAccess);
            _binding.setViewModel(addFeedFragmentModel);
            _binding.executePendingBindings();
            _binding.llProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), SearchProductActivity.class);
                    i.putExtra("flag", "1");
                    startActivityForResult(i, 1);
                }
            });
            _binding.llSupliment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), SearchProductActivity.class);
                    i.putExtra("flag", "2");
                    startActivityForResult(i, 2);
                }
            });
        }
        return _binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String[] searchedvalues = data.getStringArrayExtra("searchedvalues");
                    if (searchedvalues != null) {
                        stockid = searchedvalues[0];
                        userid = searchedvalues[1];
                        productid = searchedvalues[2];
                        qtycategoryid = searchedvalues[3];
                        productname = searchedvalues[4];
                        newstock = searchedvalues[5];
                        oldstock = searchedvalues[6];
                        availablestock = searchedvalues[7];
                        mrp = searchedvalues[8];
                        createddate = searchedvalues[9];
                        productcategoryid = searchedvalues[10];
                        quantityname = searchedvalues[11];
                        stock = searchedvalues;
                        _binding.txtProduct.setText(searchedvalues[4]);
                        addFeedFragmentModel.loadStock(stock);
                        addFeedFragmentModel.loadAvailableStock(Double.parseDouble(availablestock));
                        addFeedFragmentModel.loadProductId(productid);
                        addFeedFragmentModel.loadProductMrp(mrp);
                        addFeedFragmentModel.loadProductCtgId(productcategoryid);
                        addFeedFragmentModel.loadProductName(productname);
                        addFeedFragmentModel.loadQtyName(quantityname);
                    }
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String[] searchedvalues = data.getStringArrayExtra("searchedvalues");
                    if (searchedvalues != null) {
                        stockid = searchedvalues[0];
                        userid = searchedvalues[1];
                        productid = searchedvalues[2];
                        qtycategoryid = searchedvalues[3];
                        productname = searchedvalues[4];
                        newstock = searchedvalues[5];
                        oldstock = searchedvalues[6];
                        availablestock = searchedvalues[7];
                        mrp = searchedvalues[8];
                        createddate = searchedvalues[9];
                        productcategoryid = searchedvalues[10];
                        quantityname = searchedvalues[11];
                        stock = searchedvalues;
                        _binding.txtSupliment.setText(searchedvalues[4]);
                        addFeedFragmentModel.loadStock(stock);
                        addFeedFragmentModel.loadAvailableStock(Double.parseDouble(availablestock));
                        addFeedFragmentModel.loadProductId(productid);
                        addFeedFragmentModel.loadProductMrp(mrp);
                        addFeedFragmentModel.loadProductCtgId(productcategoryid);
                        addFeedFragmentModel.loadProductName(productname);
                        addFeedFragmentModel.loadQtyName(quantityname);
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        addFeedFragmentModel.loadData();
    }
}
