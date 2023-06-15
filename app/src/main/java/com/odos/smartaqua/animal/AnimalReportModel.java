package com.odos.smartaqua.animal;

public class AnimalReportModel {

    private String pcrobservationid;
    private String tankid;
    private String obsvdate;
    private String greencolony;
    private String yellowcolony;
    private String comments;

    public AnimalReportModel(String pcrobservationid,
                             String tankid,
                             String obsvdate,
                             String greencolony,
                             String yellowcolony,
                             String comments) {

        this.pcrobservationid = pcrobservationid;
        this.tankid = tankid;
        this.obsvdate = obsvdate;
        this.greencolony = greencolony;
        this.yellowcolony = yellowcolony;
        this.comments = comments;
    }

    public String getPcrobservationid() {
        return pcrobservationid;
    }

    public void setPcrobservationid(String pcrobservationid) {
        this.pcrobservationid = pcrobservationid;
    }

    public String getTankid() {
        return tankid;
    }

    public void setTankid(String tankid) {
        this.tankid = tankid;
    }

    public String getObsvdate() {
        return obsvdate;
    }

    public void setObsvdate(String obsvdate) {
        this.obsvdate = obsvdate;
    }

    public String getGreencolony() {
        return greencolony;
    }

    public void setGreencolony(String greencolony) {
        this.greencolony = greencolony;
    }

    public String getYellowcolony() {
        return yellowcolony;
    }

    public void setYellowcolony(String yellowcolony) {
        this.yellowcolony = yellowcolony;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
