package com.odos.smartaqua.growth;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class GrowthReportModel {

    String growthobsvid;
    String tankid;
    String count;
    String growthobservationdate;
    String createddate;
    String modifieddate;

    public GrowthReportModel(String tankid, String growthobsvid, String count, String growthobservationdate,
                             String createddate, String modifieddate) {
        this.tankid = tankid;
        this.growthobsvid = growthobsvid;
        this.count = count;
        this.growthobservationdate = growthobservationdate;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
    }

    public String getGrowthobsvid() {
        return growthobsvid;
    }

    public void setGrowthobsvid(String growthobsvid) {
        this.growthobsvid = growthobsvid;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGrowthobservationdate() {
        return growthobservationdate;
    }

    public void setGrowthobservationdate(String growthobservationdate) {
        this.growthobservationdate = growthobservationdate;
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


//    @BindingAdapter("groupname")
//    public static void groupname(TextView view, String groupname) {
//        view.setText(groupname);
//    }
    @BindingAdapter("createddate")
    public static void createddate(TextView view, String _date) {
        view.setText(_date);
    }

}
