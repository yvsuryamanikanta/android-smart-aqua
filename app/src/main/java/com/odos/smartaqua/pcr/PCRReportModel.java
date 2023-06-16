package com.odos.smartaqua.pcr;

import java.io.Serializable;

public class PCRReportModel implements Serializable {

    private String pcrobservationid;
    private String tankid;
    private String userid;
    private String culturecode;
    private String cultureloc;
    private String obsvdate;
    private String physicalActivity;
    private String meanBodyLeangth;
    private String dorsalSpinesCount;
    private String estimatedPlAge;
    private String coefficientOfSizeVariation;
    private String sampleSalinity;
    private String salinitySressSurvival;
    private String formalinSressSurvival;
    private String gillDevStatus;
    private String muscleGutRation;
    private String ectoparasiteattachments;
    private String endoParasite;
    private String necrosis;
    private String structuralDeformities;
    private String hepathopancreasLipid;
    private String mbvOcclusionBodies;
    private String hypherTropiedNucleiInHp;
    private String pcrResultWssv;
    private String wssvCtValueSeviority;
    private String pcrResultEhp;
    private String ehpCtValueSeviority;
    private String pcrResultIhhnv;
    private String ihhnvCtValueSeviority;
    private String pcrResultEms;
    private String emsCtValueSeviority;
    private String comments;
    private String createddate;
    private String modifieddate;

    public PCRReportModel(String pcrobservationid,
                          String tankid,
                          String userid,
                          String culturecode,
                          String cultureloc,
                          String obsvdate,
                          String physicalActivity,
                          String meanBodyLeangth,
                          String dorsalSpinesCount,
                          String estimatedPlAge,
                          String coefficientOfSizeVariation,
                          String sampleSalinity,
                          String salinitySressSurvival,
                          String formalinSressSurvival,
                          String gillDevStatus,
                          String muscleGutRation,
                          String ectoparasiteattachments,
                          String endoParasite,
                          String necrosis,
                          String structuralDeformities,
                          String hepathopancreasLipid,
                          String mbvOcclusionBodies,
                          String hypherTropiedNucleiInHp,
                          String pcrResultWssv,
                          String wssvCtValueSeviority,
                          String pcrResultEhp,
                          String ehpCtValueSeviority,
                          String pcrResultIhhnv,
                          String ihhnvCtValueSeviority,
                          String pcrResultEms,
                          String emsCtValueSeviority,
                          String comments,
                          String createddate,
                          String modifieddate) {

        this.pcrobservationid = pcrobservationid;
        this.tankid = tankid;
        this.userid = userid;
        this.culturecode = culturecode;
        this.cultureloc = cultureloc;
        this.obsvdate = obsvdate;
        this.physicalActivity = physicalActivity;
        this.meanBodyLeangth = meanBodyLeangth;
        this.dorsalSpinesCount = dorsalSpinesCount;
        this.estimatedPlAge = estimatedPlAge;
        this.coefficientOfSizeVariation = coefficientOfSizeVariation;
        this.sampleSalinity = sampleSalinity;
        this.salinitySressSurvival = salinitySressSurvival;
        this.formalinSressSurvival = formalinSressSurvival;
        this.gillDevStatus = gillDevStatus;
        this.muscleGutRation = muscleGutRation;
        this.ectoparasiteattachments = ectoparasiteattachments;
        this.endoParasite = endoParasite;
        this.necrosis = necrosis;
        this.structuralDeformities = structuralDeformities;
        this.hepathopancreasLipid = hepathopancreasLipid;
        this.mbvOcclusionBodies = mbvOcclusionBodies;
        this.hypherTropiedNucleiInHp = hypherTropiedNucleiInHp;
        this.pcrResultWssv = pcrResultWssv;
        this.wssvCtValueSeviority = wssvCtValueSeviority;
        this.pcrResultEhp = pcrResultEhp;
        this.ehpCtValueSeviority = ehpCtValueSeviority;
        this.pcrResultIhhnv = pcrResultIhhnv;
        this.ihhnvCtValueSeviority = ihhnvCtValueSeviority;
        this.pcrResultEms = pcrResultEms;
        this.emsCtValueSeviority = emsCtValueSeviority;
        this.comments = comments;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCulturecode() {
        return culturecode;
    }

    public void setCulturecode(String culturecode) {
        this.culturecode = culturecode;
    }

    public String getCultureloc() {
        return cultureloc;
    }

    public void setCultureloc(String cultureloc) {
        this.cultureloc = cultureloc;
    }

    public String getObsvdate() {
        return obsvdate;
    }

    public void setObsvdate(String obsvdate) {
        this.obsvdate = obsvdate;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public String getMeanBodyLeangth() {
        return meanBodyLeangth;
    }

    public void setMeanBodyLeangth(String meanBodyLeangth) {
        this.meanBodyLeangth = meanBodyLeangth;
    }

    public String getDorsalSpinesCount() {
        return dorsalSpinesCount;
    }

    public void setDorsalSpinesCount(String dorsalSpinesCount) {
        this.dorsalSpinesCount = dorsalSpinesCount;
    }

    public String getEstimatedPlAge() {
        return estimatedPlAge;
    }

    public void setEstimatedPlAge(String estimatedPlAge) {
        this.estimatedPlAge = estimatedPlAge;
    }

    public String getCoefficientOfSizeVariation() {
        return coefficientOfSizeVariation;
    }

    public void setCoefficientOfSizeVariation(String coefficientOfSizeVariation) {
        this.coefficientOfSizeVariation = coefficientOfSizeVariation;
    }

    public String getSampleSalinity() {
        return sampleSalinity;
    }

    public void setSampleSalinity(String sampleSalinity) {
        this.sampleSalinity = sampleSalinity;
    }

    public String getSalinitySressSurvival() {
        return salinitySressSurvival;
    }

    public void setSalinitySressSurvival(String salinitySressSurvival) {
        this.salinitySressSurvival = salinitySressSurvival;
    }

    public String getFormalinSressSurvival() {
        return formalinSressSurvival;
    }

    public void setFormalinSressSurvival(String formalinSressSurvival) {
        this.formalinSressSurvival = formalinSressSurvival;
    }

    public String getGillDevStatus() {
        return gillDevStatus;
    }

    public void setGillDevStatus(String gillDevStatus) {
        this.gillDevStatus = gillDevStatus;
    }

    public String getMuscleGutRation() {
        return muscleGutRation;
    }

    public void setMuscleGutRation(String muscleGutRation) {
        this.muscleGutRation = muscleGutRation;
    }

    public String getEctoparasiteattachments() {
        return ectoparasiteattachments;
    }

    public void setEctoparasiteattachments(String ectoparasiteattachments) {
        this.ectoparasiteattachments = ectoparasiteattachments;
    }

    public String getEndoParasite() {
        return endoParasite;
    }

    public void setEndoParasite(String endoParasite) {
        this.endoParasite = endoParasite;
    }

    public String getNecrosis() {
        return necrosis;
    }

    public void setNecrosis(String necrosis) {
        this.necrosis = necrosis;
    }

    public String getStructuralDeformities() {
        return structuralDeformities;
    }

    public void setStructuralDeformities(String structuralDeformities) {
        this.structuralDeformities = structuralDeformities;
    }

    public String getHepathopancreasLipid() {
        return hepathopancreasLipid;
    }

    public void setHepathopancreasLipid(String hepathopancreasLipid) {
        this.hepathopancreasLipid = hepathopancreasLipid;
    }

    public String getMbvOcclusionBodies() {
        return mbvOcclusionBodies;
    }

    public void setMbvOcclusionBodies(String mbvOcclusionBodies) {
        this.mbvOcclusionBodies = mbvOcclusionBodies;
    }

    public String getHypherTropiedNucleiInHp() {
        return hypherTropiedNucleiInHp;
    }

    public void setHypherTropiedNucleiInHp(String hypherTropiedNucleiInHp) {
        this.hypherTropiedNucleiInHp = hypherTropiedNucleiInHp;
    }

    public String getPcrResultWssv() {
        return pcrResultWssv;
    }

    public void setPcrResultWssv(String pcrResultWssv) {
        this.pcrResultWssv = pcrResultWssv;
    }

    public String getWssvCtValueSeviority() {
        return wssvCtValueSeviority;
    }

    public void setWssvCtValueSeviority(String wssvCtValueSeviority) {
        this.wssvCtValueSeviority = wssvCtValueSeviority;
    }

    public String getPcrResultEhp() {
        return pcrResultEhp;
    }

    public void setPcrResultEhp(String pcrResultEhp) {
        this.pcrResultEhp = pcrResultEhp;
    }

    public String getEhpCtValueSeviority() {
        return ehpCtValueSeviority;
    }

    public void setEhpCtValueSeviority(String ehpCtValueSeviority) {
        this.ehpCtValueSeviority = ehpCtValueSeviority;
    }

    public String getPcrResultIhhnv() {
        return pcrResultIhhnv;
    }

    public void setPcrResultIhhnv(String pcrResultIhhnv) {
        this.pcrResultIhhnv = pcrResultIhhnv;
    }

    public String getIhhnvCtValueSeviority() {
        return ihhnvCtValueSeviority;
    }

    public void setIhhnvCtValueSeviority(String ihhnvCtValueSeviority) {
        this.ihhnvCtValueSeviority = ihhnvCtValueSeviority;
    }

    public String getPcrResultEms() {
        return pcrResultEms;
    }

    public void setPcrResultEms(String pcrResultEms) {
        this.pcrResultEms = pcrResultEms;
    }

    public String getEmsCtValueSeviority() {
        return emsCtValueSeviority;
    }

    public void setEmsCtValueSeviority(String emsCtValueSeviority) {
        this.emsCtValueSeviority = emsCtValueSeviority;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
