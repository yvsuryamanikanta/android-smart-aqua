package com.odos.smartaqua.growth;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class GrowthReportModel {

    int templateID;
    int userID;
    String groupname;
    String feeddate;
    String feedProducts;
    String suppliments;

    public GrowthReportModel(int templateID, int userID, String groupname, String feeddate, String feedProducts, String suppliments) {
        this.templateID = templateID;
        this.userID = userID;
        this.groupname = groupname;
        this.feeddate = feeddate;
        this.feedProducts = feedProducts;
        this.suppliments = suppliments;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getFeeddate() {
        return feeddate;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getFeedProducts() {
        return feedProducts;
    }

    public void setFeedProducts(String feedProducts) {
        this.feedProducts = feedProducts;
    }

    public String getSuppliments() {
        return suppliments;
    }

    public void setSuppliments(String suppliments) {
        this.suppliments = suppliments;
    }

    @BindingAdapter("groupname")
    public static void groupname(TextView view, String groupname) {
        view.setText(groupname);
    }
    @BindingAdapter("feeddate")
    public static void feeddate(TextView view, String feeddate) {
        view.setText(feeddate);
    }

}
