package com.odos.smartaqua.treatment;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TreatmentModel {

    String treatmentsid;
    String createddate;
    String tankid;
    String decease;
    String solution;
    String traetmentdate;
    String modifieddate;

    public TreatmentModel(String treatmentsid,
                          String createddate,
                          String tankid,
                          String decease,
                          String solution,
                          String traetmentdate,
                          String modifieddate) {
        this.tankid = tankid;
        this.treatmentsid = treatmentsid;
        this.decease = decease;
        this.solution = solution;
        this.traetmentdate = traetmentdate;
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

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getDecease() {
        return decease;
    }

    public void setDecease(String decease) {
        this.decease = decease;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution1) {
        this.solution = solution1;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getTraetmentdate() {
        return traetmentdate;
    }

    public void setTraetmentdate(String traetmentdate) {
        this.traetmentdate = traetmentdate;
    }

    public String getTreatmentsid() {
        return treatmentsid;
    }

    public void setTreatmentsid(String treatmentsid) {
        this.treatmentsid = treatmentsid;
    }


}
