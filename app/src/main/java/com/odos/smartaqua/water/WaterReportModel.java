package com.odos.smartaqua.water;

public class WaterReportModel {

    int labobservationid;
    int tankid;
    int userid;
    String phvalue;
    String salinity;
    String co3;
    String hco3;
    String cahardness;
    String mghardness;
    String calcium;
    String magnesium;
    String potassium;
    String sodium;
    String iron;
    String ionizedammonia;
    String unionizedammonia;
    String nitrate;
    String hydrogensulphide;
    String labdo;
    String co2;
    String greenalgae;
    String diatom;
    String bluegreenalgae;
    String dinoflegellates;
    String zooplankton;
    String dafloc;
    String vibriogreencolonies;
    String vibrioyellowcolonies;
    String labobsvdate;
    String createddate;
    String modifieddate;
    String tankName;

    public WaterReportModel(int labobservationid, int tankid, int userid, String phvalue, String salinity, String co3, String hco3, String cahardness, String mghardness, String calcium, String magnesium, String potassium, String sodium, String iron, String ionizedammonia, String unionizedammonia, String nitrate, String hydrogensulphide, String labdo, String co2, String greenalgae, String diatom, String bluegreenalgae, String dinoflegellates, String zooplankton, String dafloc, String vibriogreencolonies, String vibrioyellowcolonies, String labobsvdate, String createddate, String modifieddate, String tankName) {
        this.labobservationid = labobservationid;
        this.tankid = tankid;
        this.userid = userid;
        this.phvalue = phvalue;
        this.salinity = salinity;
        this.co3 = co3;
        this.hco3 = hco3;
        this.cahardness = cahardness;
        this.mghardness = mghardness;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.potassium = potassium;
        this.sodium = sodium;
        this.iron = iron;
        this.ionizedammonia = ionizedammonia;
        this.unionizedammonia = unionizedammonia;
        this.nitrate = nitrate;
        this.hydrogensulphide = hydrogensulphide;
        this.labdo = labdo;
        this.co2 = co2;
        this.greenalgae = greenalgae;
        this.diatom = diatom;
        this.bluegreenalgae = bluegreenalgae;
        this.dinoflegellates = dinoflegellates;
        this.zooplankton = zooplankton;
        this.dafloc = dafloc;
        this.vibriogreencolonies = vibriogreencolonies;
        this.vibrioyellowcolonies = vibrioyellowcolonies;
        this.labobsvdate = labobsvdate;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
        this.tankName = tankName;
    }

    public String getTankName() {
        return tankName;
    }

    public int getLabobservationid() {
        return labobservationid;
    }

    public int getTankid() {
        return tankid;
    }

    public int getUserid() {
        return userid;
    }

    public String getPhvalue() {
        return phvalue;
    }

    public String getSalinity() {
        return salinity;
    }

    public String getCo3() {
        return co3;
    }

    public String getHco3() {
        return hco3;
    }

    public String getCahardness() {
        return cahardness;
    }

    public String getMghardness() {
        return mghardness;
    }

    public String getCalcium() {
        return calcium;
    }

    public String getMagnesium() {
        return magnesium;
    }

    public String getPotassium() {
        return potassium;
    }

    public String getSodium() {
        return sodium;
    }

    public String getIron() {
        return iron;
    }

    public String getIonizedammonia() {
        return ionizedammonia;
    }

    public String getUnionizedammonia() {
        return unionizedammonia;
    }

    public String getNitrate() {
        return nitrate;
    }

    public String getHydrogensulphide() {
        return hydrogensulphide;
    }

    public String getLabdo() {
        return labdo;
    }

    public String getCo2() {
        return co2;
    }

    public String getGreenalgae() {
        return greenalgae;
    }

    public String getDiatom() {
        return diatom;
    }

    public String getBluegreenalgae() {
        return bluegreenalgae;
    }

    public String getDinoflegellates() {
        return dinoflegellates;
    }

    public String getZooplankton() {
        return zooplankton;
    }

    public String getDafloc() {
        return dafloc;
    }

    public String getVibriogreencolonies() {
        return vibriogreencolonies;
    }

    public String getVibrioyellowcolonies() {
        return vibrioyellowcolonies;
    }

    public String getLabobsvdate() {
        return labobsvdate;
    }

    public String getCreateddate() {
        return createddate;
    }

    public String getModifieddate() {
        return modifieddate;
    }
}
