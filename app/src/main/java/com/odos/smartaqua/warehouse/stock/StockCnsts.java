package com.odos.smartaqua.warehouse.stock;

public class StockCnsts {

    private int stockid;
    private int productid;
    private int productcategoryid;
    private String productname;
    private String newstock;
    private String oldstock;
    private String availablestock;
    private String actualPrice;
    private String purchasePrice;
    private String discount;
    private String path;
    private String productcode;

    public StockCnsts(int stockid, int productid, int productcategoryid, String productname, String newstock, String oldstock, String availablestock, String actualprice,String purchaseprice,String discount, String path, String productcode) {
        this.stockid = stockid;
        this.productid = productid;
        this.productcategoryid = productcategoryid;
        this.productname = productname;
        this.newstock = newstock;
        this.oldstock = oldstock;
        this.availablestock = availablestock;
        this.actualPrice = actualprice;
        this.purchasePrice = purchaseprice;
        this.discount = discount;
        this.path = path;
        this.productcode = productcode;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public int getStockid() {
        return stockid;
    }

    public int getProductid() {
        return productid;
    }

    public int getProductcategoryid() {
        return productcategoryid;
    }

    public String getProductname() {
        return productname;
    }

    public String getNewstock() {
        return newstock;
    }

    public String getOldstock() {
        return oldstock;
    }

    public String getAvailablestock() {
        return availablestock;
    }

    public String getPath() {
        return path;
    }

    public String getProductcode() {
        return productcode;
    }
}
