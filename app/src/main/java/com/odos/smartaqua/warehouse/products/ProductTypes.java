package com.odos.smartaqua.warehouse.products;

public class ProductTypes {

    private int ptID;
    private String productType;
    private String ptCode;
    private String quantity;
    private int quantitycategoryid;

    public ProductTypes(int ptID, String productType, String ptCode, String quantity, int quantitycategoryid) {
        this.ptID = ptID;
        this.productType = productType;
        this.ptCode = ptCode;
        this.quantity = quantity;
        this.quantitycategoryid = quantitycategoryid;
    }

    public int getPtID() {
        return ptID;
    }

    public String getProductType() {
        return productType;
    }

    public String getPtCode() {
        return ptCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getQuantitycategoryid() {
        return quantitycategoryid;
    }
}
