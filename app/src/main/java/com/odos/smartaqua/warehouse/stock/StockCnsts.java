package com.odos.smartaqua.warehouse.stock;

public class StockCnsts {

    private int stockid;
    private int productid;
    private int productcategoryid;
    private String productname;
    private String newstock;
    private String oldstock;
    private String availablestock;
    private String mrp;
    private String path;
    private String productcode;

    public StockCnsts(int stockid, int productid, int productcategoryid, String productname, String newstock, String oldstock, String availablestock, String mrp, String path, String productcode) {
        this.stockid = stockid;
        this.productid = productid;
        this.productcategoryid = productcategoryid;
        this.productname = productname;
        this.newstock = newstock;
        this.oldstock = oldstock;
        this.availablestock = availablestock;
        this.mrp = mrp;
        this.path = path;
        this.productcode = productcode;
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

    public String getMrp() {
        return mrp;
    }

    public String getPath() {
        return path;
    }

    public String getProductcode() {
        return productcode;
    }
}
