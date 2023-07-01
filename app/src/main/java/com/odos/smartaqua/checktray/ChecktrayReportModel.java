package com.odos.smartaqua.checktray;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class ChecktrayReportModel {

    private int checktrayobsvid;
    private int checktrayid;
    private String tankid;
    private String feedstatus;
    private String checktrayname;
    private String wastagecolor;
    private String redmortality;
    private String redmortalitycount;
    private String whitemortality;
    private String whitemortalitycount;
    private String potaciumdefeciency;
    private String magniciumdefeciency;
    private String calciumdefeciency;
    private String crampstatus;
    private String checktrayobsvdate;
    private String createddate;
    private String modifieddate;

    public ChecktrayReportModel(int checktrayobsvid, int checktrayid, String tankid, String feedstatus, String checktrayname, String wastagecolor, String redmortality, String redmortalitycount,String whitemortality, String whitemortalitycount, String potaciumdefeciency, String magniciumdefeciency, String calciumdefeciency, String crampstatus, String checktrayobsvdate, String createddate, String modifieddate) {
        this.checktrayobsvid = checktrayobsvid;
        this.checktrayid = checktrayid;
        this.tankid = tankid;
        this.feedstatus = feedstatus;
        this.checktrayname = checktrayname;
        this.wastagecolor = wastagecolor;
        this.redmortality = redmortality;
        this.redmortalitycount = redmortalitycount;
        this.whitemortality = whitemortality;
        this.whitemortalitycount = whitemortalitycount;
        this.potaciumdefeciency = potaciumdefeciency;
        this.magniciumdefeciency = magniciumdefeciency;
        this.calciumdefeciency = calciumdefeciency;
        this.crampstatus = crampstatus;
        this.checktrayobsvdate = checktrayobsvdate;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
    }

    public String getChecktrayname() {
        return checktrayname;
    }

    public int getChecktrayobsvid() {
        return checktrayobsvid;
    }

    public int getChecktrayid() {
        return checktrayid;
    }

    public String getTankid() {
        return tankid;
    }

    public String getFeedstatus() {
        return feedstatus;
    }

    public String getWastagecolor() {
        return wastagecolor;
    }

    public String getRedmortality() {
        return redmortality;
    }

    public String getRedmortalitycount() {
        return redmortalitycount;
    }

    public String getWhitemortality() {
        return whitemortality;
    }

    public String getWhitemortalitycount() {
        return whitemortalitycount;
    }

    public String getPotaciumdefeciency() {
        return potaciumdefeciency;
    }

    public String getMagniciumdefeciency() {
        return magniciumdefeciency;
    }

    public String getCalciumdefeciency() {
        return calciumdefeciency;
    }

    public String getCrampstatus() {
        return crampstatus;
    }

    public String getChecktrayobsvdate() {
        return checktrayobsvdate;
    }

    public String getCreateddate() {
        return createddate;
    }

    public String getModifieddate() {
        return modifieddate;
    }
}
