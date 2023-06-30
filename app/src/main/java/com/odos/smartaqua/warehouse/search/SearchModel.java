package com.odos.smartaqua.warehouse.search;

public class SearchModel {

    int productid;
    int quantitycategoryid;
    int brandid;
    int productcatgeoryid;
    String productname;
    String costperqty;
    String quantity;

    public SearchModel(int productid,int quantitycategoryid,int brandid,int productcatgeoryid,
                       String productname,
                       String costperqty,
                       String quantity) {
        this.productcatgeoryid = productcatgeoryid;
        this.productid = productid;
        this.quantitycategoryid = quantitycategoryid;
        this.brandid = brandid;
        this.productname = productname;
        this.costperqty = costperqty;
        this.quantity = quantity;
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

    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    public int getProductcatgeoryid() {
        return productcatgeoryid;
    }

    public void setProductcatgeoryid(int productcatgeoryid) {
        this.productcatgeoryid = productcatgeoryid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
