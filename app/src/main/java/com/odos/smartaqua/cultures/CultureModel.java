package com.odos.smartaqua.cultures;

public class CultureModel {
    int cultureid;
    String userid;
    String tankid;
    String culturename;
    String tankname;
    String culturenumber;
    String cultureimage;
    String culturestatus;
    String cultureaccess;

    public CultureModel(int cultureid, String userid, String tankid, String culturename, String tankname, String culturenumber, String cultureimage, String culturestatus, String cultureaccess) {
        this.cultureid = cultureid;
        this.userid = userid;
        this.tankid = tankid;
        this.culturename = culturename;
        this.tankname = tankname;
        this.culturenumber = culturenumber;
        this.cultureimage = cultureimage;
        this.culturestatus = culturestatus;
        this.cultureaccess = cultureaccess;
    }

    public int getCultureid() {
        return cultureid;
    }

    public String getUserid() {
        return userid;
    }

    public String getTankid() {
        return tankid;
    }

    public String getCulturename() {
        return culturename;
    }

    public String getTankname() {
        return tankname;
    }

    public String getCulturenumber() {
        return culturenumber;
    }

    public String getCultureimage() {
        return cultureimage;
    }

    public String getCulturestatus() {
        return culturestatus;
    }

    public String getCultureaccess() {
        return cultureaccess;
    }
}