package com.odos.smartaqua.tank;

public class CultureModel {
    String previousdecease;
    String recordkeeping;
    String drying;
    String biosecurity;
    String scrapping;
    String ploughing;
    String liming;
    String soilcheck;
    String fillingwatertype;
    String watersource;
    String pondtype;
    String bleaching;
    String minerals;
    String probiotics;
    String filteration;
    String fertilization;
    String ehp;
    String createddate;

    public CultureModel(String previousdecease,
            String recordkeeping,
            String drying,
            String biosecurity,
            String scrapping,
            String ploughing,
            String liming,
            String soilcheck,
            String fillingwatertype,
            String watersource,
            String pondtype,
            String bleaching,
            String minerals,
            String probiotics,
            String filteration,
            String fertilization,
            String ehp,
            String createddate) {
        this.previousdecease = previousdecease;
        this.recordkeeping= recordkeeping;
        this.drying = drying;
        this.biosecurity =biosecurity;
        this.scrapping =scrapping;
        this.ploughing =ploughing;
        this.liming =liming;
        this.soilcheck =soilcheck;
        this.fillingwatertype = fillingwatertype;
        this.watersource = watersource;
        this.pondtype = pondtype;
        this.bleaching = bleaching;
        this.minerals = minerals;
        this.probiotics = probiotics;
        this.filteration = filteration;
        this.fertilization =fertilization;
        this.ehp =ehp;
        this.createddate =createddate;
    }

    public String getPreviousdecease() {
        return previousdecease;
    }

    public void setPreviousdecease(String previousdecease) {
        this.previousdecease = previousdecease;
    }

    public String getRecordkeeping() {
        return recordkeeping;
    }

    public void setRecordkeeping(String recordkeeping) {
        this.recordkeeping = recordkeeping;
    }

    public String getDrying() {
        return drying;
    }

    public void setDrying(String drying) {
        this.drying = drying;
    }

    public String getBiosecurity() {
        return biosecurity;
    }

    public void setBiosecurity(String biosecurity) {
        this.biosecurity = biosecurity;
    }

    public String getScrapping() {
        return scrapping;
    }

    public void setScrapping(String scrapping) {
        this.scrapping = scrapping;
    }

    public String getPloughing() {
        return ploughing;
    }

    public void setPloughing(String ploughing) {
        this.ploughing = ploughing;
    }

    public String getLiming() {
        return liming;
    }

    public void setLiming(String liming) {
        this.liming = liming;
    }

    public String getSoilcheck() {
        return soilcheck;
    }

    public void setSoilcheck(String soilcheck) {
        this.soilcheck = soilcheck;
    }

    public String getFillingwatertype() {
        return fillingwatertype;
    }

    public void setFillingwatertype(String fillingwatertype) {
        this.fillingwatertype = fillingwatertype;
    }

    public String getWatersource() {
        return watersource;
    }

    public void setWatersource(String watersource) {
        this.watersource = watersource;
    }

    public String getPondtype() {
        return pondtype;
    }

    public void setPondtype(String pondtype) {
        this.pondtype = pondtype;
    }

    public String getBleaching() {
        return bleaching;
    }

    public void setBleaching(String bleaching) {
        this.bleaching = bleaching;
    }

    public String getMinerals() {
        return minerals;
    }

    public void setMinerals(String minerals) {
        this.minerals = minerals;
    }

    public String getProbiotics() {
        return probiotics;
    }

    public void setProbiotics(String probiotics) {
        this.probiotics = probiotics;
    }

    public String getFilteration() {
        return filteration;
    }

    public void setFilteration(String filteration) {
        this.filteration = filteration;
    }

    public String getFertilization() {
        return fertilization;
    }

    public void setFertilization(String fertilization) {
        this.fertilization = fertilization;
    }

    public String getEhp() {
        return ehp;
    }

    public void setEhp(String ehp) {
        this.ehp = ehp;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }
}
