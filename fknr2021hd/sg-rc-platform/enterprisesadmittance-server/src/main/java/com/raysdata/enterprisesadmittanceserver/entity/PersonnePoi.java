package com.raysdata.enterprisesadmittanceserver.entity;

public class PersonnePoi {
    @PoiFileds(name = "序号",title = "columntypecode",type = 0)
    private String columntypecode;
    @PoiFileds(name = "名称",title = "columntypename",type = 0)
    private String columntypename;
    @PoiFileds(name = "总数",title = "natureall",type = 0)
    private Long natureall;

    public PersonnePoi() {
    }



    public PersonnePoi(String columntypecode, String columntypename, Long natureall) {
        this.columntypecode = columntypecode;
        this.columntypename = columntypename;
        this.natureall = natureall;
    }

    public String getColumntypecode() {
        return columntypecode;
    }

    public void setColumntypecode(String columntypecode) {
        this.columntypecode = columntypecode;
    }

    public String getColumntypename() {
        return columntypename;
    }

    public void setColumntypename(String columntypename) {
        this.columntypename = columntypename;
    }

    public Long getNatureall() {
        return natureall;
    }

    public void setNatureall(Long natureall) {
        this.natureall = natureall;
    }
}
