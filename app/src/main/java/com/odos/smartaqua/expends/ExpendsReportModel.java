package com.odos.smartaqua.expends;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class ExpendsReportModel {

    String expendsid;
    String tankid;
    String amount;
    String reason;
    String expendsdate;
    String createddate;
    String modifieddate;

    public ExpendsReportModel(String tankid,
                              String expendsid,
                              String amount,
                              String reason,
                              String expendsdate,
                              String createddate,
                              String modifieddate) {
        this.tankid = tankid;
        this.expendsid = expendsid;
        this.amount = amount;
        this.reason = reason;
        this.expendsdate = expendsdate;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
    }

    //    @BindingAdapter("groupname")
//    public static void groupname(TextView view, String groupname) {
//        view.setText(groupname);
//    }
    @BindingAdapter("createddate")
    public static void createddate(TextView view, String _date) {
        view.setText(_date);
    }

    public String getExpendsid() {
        return expendsid;
    }

    public void setExpendsid(String expendsid) {
        this.expendsid = expendsid;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpendsdate() {
        return expendsdate;
    }

    public void setExpendsdate(String expendsdate) {
        this.expendsdate = expendsdate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

}