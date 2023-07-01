package com.odos.smartaqua.warehouse.products;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class WareHouseModel {

    int stockid;
    int userid;
    int productid;
    int quantitycategoryid;
    String productname;
    String newstock;
    String oldstock;
    String availablestock;
    String actualprice;
    String purchaseprice;
    String discount;
    String createddate;
    String productcategoryid;
    String productcode;
    String quantityname;

    public WareHouseModel(int stockid, int userid, int productid, int quantitycategoryid, String productname, String newstock,
                          String oldstock, String availablestock ,String actualprice, String purchaseprice,String discount, String createddate, String productcategoryid
            , String productcode, String quantityname) {
        this.stockid = stockid;
        this.userid = userid;
        this.productid = productid;
        this.quantitycategoryid = quantitycategoryid;
        this.productname = productname;
        this.newstock = newstock;
        this.oldstock = oldstock;
        this.availablestock = availablestock;
        this.actualprice = actualprice;
        this.purchaseprice = purchaseprice;
        this.actualprice = actualprice;
        this.createddate = createddate;
        this.productcategoryid = productcategoryid;
        this.productcode = productcode;
        this.quantityname = quantityname;
    }

    public String getActualprice() {
        return actualprice;
    }

    public String getPurchaseprice() {
        return purchaseprice;
    }

    public String getDiscount() {
        return discount;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQuantitycategoryid() {
        return quantitycategoryid;
    }

    public void setQuantitycategoryid(int quantitycategoryid) {
        this.quantitycategoryid = quantitycategoryid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getNewstock() {
        return newstock;
    }

    public void setNewstock(String newstock) {
        this.newstock = newstock;
    }

    public String getOldstock() {
        return oldstock;
    }

    public void setOldstock(String oldstock) {
        this.oldstock = oldstock;
    }

    public String getAvailablestock() {
        return availablestock;
    }

    public void setAvailablestock(String availablestock) {
        this.availablestock = availablestock;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getProductcategoryid() {
        return productcategoryid;
    }

    public void setProductcategoryid(String productcategoryid) {
        this.productcategoryid = productcategoryid;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getQuantityname() {
        return quantityname;
    }

    public void setQuantityname(String quantityname) {
        this.quantityname = quantityname;
    }

    @BindingAdapter("productname")
    public static void productname(TextView view, String productname) {
        view.setText(productname);
    }

}
