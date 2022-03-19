package com.raysdata.riskdataanalyzeserver.bean;

public class SrpWorkKdtbWorkplandayWithBLOBs extends SrpWorkKdtbWorkplanday {
    private String workPlanName;

    private String substationName;

    private String substationId;

    private String workPlace;

    private String workingGroup;

    private String workingGroupId;

    private String workOrg;

    private String workOrgId;

    public String getWorkPlanName() {
        return workPlanName;
    }

    public void setWorkPlanName(String workPlanName) {
        this.workPlanName = workPlanName == null ? null : workPlanName.trim();
    }

    public String getSubstationName() {
        return substationName;
    }

    public void setSubstationName(String substationName) {
        this.substationName = substationName == null ? null : substationName.trim();
    }

    public String getSubstationId() {
        return substationId;
    }

    public void setSubstationId(String substationId) {
        this.substationId = substationId == null ? null : substationId.trim();
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace == null ? null : workPlace.trim();
    }

    public String getWorkingGroup() {
        return workingGroup;
    }

    public void setWorkingGroup(String workingGroup) {
        this.workingGroup = workingGroup == null ? null : workingGroup.trim();
    }

    public String getWorkingGroupId() {
        return workingGroupId;
    }

    public void setWorkingGroupId(String workingGroupId) {
        this.workingGroupId = workingGroupId == null ? null : workingGroupId.trim();
    }

    public String getWorkOrg() {
        return workOrg;
    }

    public void setWorkOrg(String workOrg) {
        this.workOrg = workOrg == null ? null : workOrg.trim();
    }

    public String getWorkOrgId() {
        return workOrgId;
    }

    public void setWorkOrgId(String workOrgId) {
        this.workOrgId = workOrgId == null ? null : workOrgId.trim();
    }
}