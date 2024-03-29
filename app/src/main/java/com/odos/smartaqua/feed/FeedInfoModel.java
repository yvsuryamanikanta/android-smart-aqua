package com.odos.smartaqua.feed;

public class FeedInfoModel {

    private int productID;
    private int productcatgeoryID;
    private int quantitycategoryid;
    private String productName;
    private String priceperqty;
    private String productqty;
    private String quantity;
    private String comments;

    FeedInfoModel(int _productID,int _productcatgeoryID,int quantitycategoryid,String productName,
                  String priceperqty,String productqty,String quantity,String comments){
        this.productID = _productID;
        this.productcatgeoryID = _productcatgeoryID;
        this.quantitycategoryid = quantitycategoryid;
        this.productName = productName;
        this.priceperqty = priceperqty;
        this.productqty = productqty;
        this.quantity = quantity;
        this.comments = comments;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int id) {
        this.productID = id;
    }

    public int getProductcatgeoryID() {
        return productcatgeoryID;
    }

    public void setProductcatgeoryID(int id) {
        this.productcatgeoryID = id;
    }

    public int getQuantitycategoryid() {
        return quantitycategoryid;
    }

    public void setQuantitycategoryid(int quantitycategoryid) {
        this.quantitycategoryid = quantitycategoryid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceperqty() {
        return priceperqty;
    }

    public void setPriceperqty(String priceperqty) {
        this.priceperqty = priceperqty;
    }

    public String getProductqty() {
        return productqty;
    }

    public void setProductqty(String productqty) {
        this.productqty = productqty;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

//        @BindingAdapter("groupname")
//    public static void groupname(TextView view, String groupname) {
//        view.setText(groupname);
//    }
//    @BindingAdapter("feeddate")
//    public static void feeddate(TextView view, String feeddate) {
//        view.setText(feeddate);
//    }

}
