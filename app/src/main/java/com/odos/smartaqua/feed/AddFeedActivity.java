package com.odos.smartaqua.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.odos.smartaqua.R;
import com.odos.smartaqua.common.BaseActivity;
import com.odos.smartaqua.databinding.ActivityAddFeedBinding;


public class AddFeedActivity extends BaseActivity {

    private ActivityAddFeedBinding activityAddFeedBinding;

    private String stockid, userid, productid, qtycategoryid, productname, newstock, oldstock, availablestock,
            mrp, createddate, productcategoryid,quantityname;

    private String[] stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater mInflater = LayoutInflater.from(AddFeedActivity.this);
        activityAddFeedBinding = DataBindingUtil.inflate(mInflater, R.layout.activity_add_feed, activityBaseBinding.baseFragment, true);
        activityAddFeedBinding.setViewModel(new AddFeedViewModel(AddFeedActivity.this, activityAddFeedBinding));
        activityAddFeedBinding.executePendingBindings();
    }

    public String getstockid() {
        return stockid;
    }

    public String getuserid() {
        return userid;
    }

    public String getproductid() {
        return productid;
    }

    public String getqtycategoryid() {
        return qtycategoryid;
    }

    public String getproductname() {
        return productname;
    }

    public String getnewstock() {
        return newstock;
    }

    public String getoldstock() {
        return oldstock;
    }

    public String getavailablestock() {
        return availablestock;
    }

    public String getmrp() {
        return mrp;
    }

    public String getcreateddate() {
        return createddate;
    }

    public String getproductcategoryid() {
        return productcategoryid;
    }

    public String getquantityname() {
        return quantityname;
    }

    public String[] getStock() {
        return stock;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        activityAddFeedBinding.txtProduct.setText(searchedvalues[4]);

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
                        activityAddFeedBinding.txtSupliment.setText(searchedvalues[4]);
                    }

                }
            }
        }
    }
}
