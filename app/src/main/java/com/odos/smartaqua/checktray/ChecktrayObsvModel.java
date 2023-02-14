package com.odos.smartaqua.checktray;

public class ChecktrayObsvModel {
    private int checktrayobsvid;
    private String checktrayid;
    private String tankid;
    private String feedstatus;
    private String wastagecolor;
    private String mortalitytype ;
    private String mortalitycount;
    private String potaciumdefeciency;
    private String magniciumdefeciency;
    private String calciumdefeciency;
    private String vibrieostatus;
    private String crampstatus;
    private String checktrayobsvdate;
    private String createddate;
    private String modifieddate;

    public ChecktrayObsvModel(int checktrayobsvid, String checktrayid, String tankid, String feedstatus, String wastagecolor, String mortalitytype, String mortalitycount, String potaciumdefeciency, String magniciumdefeciency, String calciumdefeciency, String vibrieostatus, String crampstatus, String checktrayobsvdate, String createddate, String modifieddate) {
        this.checktrayobsvid = checktrayobsvid;
        this.checktrayid = checktrayid;
        this.tankid = tankid;
        this.feedstatus = feedstatus;
        this.wastagecolor = wastagecolor;
        this.mortalitytype = mortalitytype;
        this.mortalitycount = mortalitycount;
        this.potaciumdefeciency = potaciumdefeciency;
        this.magniciumdefeciency = magniciumdefeciency;
        this.calciumdefeciency = calciumdefeciency;
        this.vibrieostatus = vibrieostatus;
        this.crampstatus = crampstatus;
        this.checktrayobsvdate = checktrayobsvdate;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
    }

    public int getChecktrayobsvid() {
        return checktrayobsvid;
    }

    public String getChecktrayid() {
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

    public String getMortalitytype() {
        return mortalitytype;
    }

    public String getMortalitycount() {
        return mortalitycount;
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

    public String getVibrieostatus() {
        return vibrieostatus;
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