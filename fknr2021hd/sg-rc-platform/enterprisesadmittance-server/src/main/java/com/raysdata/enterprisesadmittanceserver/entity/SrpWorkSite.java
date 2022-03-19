package com.raysdata.enterprisesadmittanceserver.entity;


import java.io.Serializable;

public class SrpWorkSite implements Serializable {
    public String siteinfo_id;
    public String basicinfoContractor;
    public String orgNature;
    public String basicinfoLegalrepresentative;
    public String datareportOrg;
    public String datareportIrgId;
    public int violationCnt;
    public int violationOrgPoints;
    public String datafillOrg;
    public String datafillOrgId;
    public String isEnableEnter;

    public SrpWorkSite() {
    }

    public SrpWorkSite(String siteinfo_id, String basicinfoContractor, String orgNature, String basicinfoLegalrepresentative, String datareportOrg, String datareportIrgId, int violationCnt, int violationOrgPoints, String datafillOrg, String datafillOrgId, String isEnableEnter) {
        this.siteinfo_id = siteinfo_id;
        this.basicinfoContractor = basicinfoContractor;
        this.orgNature = orgNature;
        this.basicinfoLegalrepresentative = basicinfoLegalrepresentative;
        this.datareportOrg = datareportOrg;
        this.datareportIrgId = datareportIrgId;
        this.violationCnt = violationCnt;
        this.violationOrgPoints = violationOrgPoints;
        this.datafillOrg = datafillOrg;
        this.datafillOrgId = datafillOrgId;
        this.isEnableEnter = isEnableEnter;
    }

    public String getSiteinfo_id() {
        return siteinfo_id;
    }

    public void setSiteinfo_id(String siteinfo_id) {
        this.siteinfo_id = siteinfo_id;
    }

    public String getBasicinfoContractor() {
        return basicinfoContractor;
    }

    public void setBasicinfoContractor(String basicinfoContractor) {
        this.basicinfoContractor = basicinfoContractor;
    }

    public String getOrgNature() {
        return orgNature;
    }

    public void setOrgNature(String orgNature) {
        this.orgNature = orgNature;
    }

    public String getBasicinfoLegalrepresentative() {
        return basicinfoLegalrepresentative;
    }

    public void setBasicinfoLegalrepresentative(String basicinfoLegalrepresentative) {
        this.basicinfoLegalrepresentative = basicinfoLegalrepresentative;
    }

    public String getDatareportOrg() {
        return datareportOrg;
    }

    public void setDatareportOrg(String datareportOrg) {
        this.datareportOrg = datareportOrg;
    }

    public String getDatareportIrgId() {
        return datareportIrgId;
    }

    public void setDatareportIrgId(String datareportIrgId) {
        this.datareportIrgId = datareportIrgId;
    }

    public int getViolationCnt() {
        return violationCnt;
    }

    public void setViolationCnt(int violationCnt) {
        this.violationCnt = violationCnt;
    }

    public int getViolationOrgPoints() {
        return violationOrgPoints;
    }

    public void setViolationOrgPoints(int violationOrgPoints) {
        this.violationOrgPoints = violationOrgPoints;
    }

    public String getDatafillOrg() {
        return datafillOrg;
    }

    public void setDatafillOrg(String datafillOrg) {
        this.datafillOrg = datafillOrg;
    }

    public String getDatafillOrgId() {
        return datafillOrgId;
    }

    public void setDatafillOrgId(String datafillOrgId) {
        this.datafillOrgId = datafillOrgId;
    }

    public String getIsEnableEnter() {
        return isEnableEnter;
    }

    public void setIsEnableEnter(String isEnableEnter) {
        this.isEnableEnter = isEnableEnter;
    }

    @Override
    public String toString() {
        return "SrpWorkSite{" +
                "siteinfo_id='" + siteinfo_id + '\'' +
                ", basicinfoContractor='" + basicinfoContractor + '\'' +
                ", orgNature='" + orgNature + '\'' +
                ", basicinfoLegalrepresentative='" + basicinfoLegalrepresentative + '\'' +
                ", datareportOrg='" + datareportOrg + '\'' +
                ", datareportIrgId='" + datareportIrgId + '\'' +
                ", violationCnt=" + violationCnt +
                ", violationOrgPoints=" + violationOrgPoints +
                ", datafillOrg='" + datafillOrg + '\'' +
                ", datafillOrgId='" + datafillOrgId + '\'' +
                ", isEnableEnter='" + isEnableEnter + '\'' +
                '}';
    }
}
