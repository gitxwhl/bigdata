//package com.raysdata.riskmanagementserver.utils;
//
///**
// * @author zyyy
// */
//public class Const {
//
//    /**
//     * iom_tabl_col_value表地市编码检索
//     */
//    public static final String COLUMN_KEY_AREA_CODE = "area_code";
//    public static final String COLUMN_KEY = "zxzd_result_code";
//    /**
//     * 安全风险综合可视化展现
//     * 查询sql
//     */
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDWARNCNT = "select count(1) GRIDWARN_CNT from SRP_RISK_GRIDWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now()) AND WARNINGLEVEL != '09'";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDCONSTWARNCNT = "SELECT count(1) GRIDCONSTWARN_CNT FROM (select * from SRP_RISK_GRIDCONSTWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now()) ) a WHERE a.WARNINGLEVEL = '01'\n" +
//            "OR a.WARNINGLEVEL = '02' OR a.WARNINGLEVEL = '03' OR a.WARNINGLEVEL = '04'";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKINDUSRISKWARNCNT = "select count(1) INDUSRISKWARN_CNT from SRP_RISK_INDUSRISKWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now())";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKEVENTWARNINGCNT = "select count(1) EVENTEWARNING_CNT from SRP_RISK_EVENTEWARNING where to_days(WARNBEGINTIME) = to_days(now()) AND WARNINGLEVEL != '06'";
//
//
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPWORKPLANVOLTAGELEVELCNT = "select " +
//            " count(case when VOLTAGE_LEVEL in ('06','07','08','09','10','11','12','13') then 1 else NULL END) LEVEL110," +
//            " count(case when VOLTAGE_LEVEL in ('04','05') then 1 else NULL END) LEVEL220," +
//            " count(case when VOLTAGE_LEVEL in ('01','02','03','14','15','16','17') then 1 else NULL END) LEVEL500" +
//            " from SRP_WORK_WORKPLANDAYS WHERE to_days( BEGIN_TIME ) = to_days( now( ) ) ";
//
//
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDWARNLEVELCNT = "select " +
//            " count(CASE WHEN WARNINGLEVEL in ('01','02','03','04') THEN 1 ELSE NULL END) LEVEL4," +
//            " count(CASE WHEN WARNINGLEVEL = '05' THEN 1 ELSE NULL END) LEVEL5," +
//            " count(CASE WHEN WARNINGLEVEL = '06' THEN 1 ELSE NULL END) LEVEL6," +
//            " count(CASE WHEN WARNINGLEVEL = '07' THEN 1 ELSE NULL END) LEVEL7," +
//            " count(CASE WHEN WARNINGLEVEL = '08' THEN 1 ELSE NULL END) LEVEL8" +
//            " from SRP_RISK_GRIDWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now())";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDCONSTWARNLEVELCNT = "select " +
//            " count(CASE WHEN WARNINGLEVEL = '01' THEN 1 ELSE NULL END) LEVEL1," +
//            " count(CASE WHEN WARNINGLEVEL = '02' THEN 1 ELSE NULL END) LEVEL2," +
//            " count(CASE WHEN WARNINGLEVEL = '03' THEN 1 ELSE NULL END) LEVEL3," +
//            " count(CASE WHEN WARNINGLEVEL = '04' THEN 1 ELSE NULL END) LEVEL4" +
//            " from SRP_RISK_GRIDCONSTWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now())";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKINDUSRISKWARNLEVELCNT = "select " +
//            " count(CASE WHEN WARNINGLEVEL = '01' THEN 1 ELSE NULL END) LEVEL1," +
//            " count(CASE WHEN WARNINGLEVEL = '02' THEN 1 ELSE NULL END) LEVEL2," +
//            " count(CASE WHEN WARNINGLEVEL = '03' THEN 1 ELSE NULL END) LEVEL3" +
//            " from SRP_RISK_INDUSRISKWARNNOTICE where to_days(WARNBEGINTIME) = to_days(now())";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKEVENTWARNINGLEVELCNT = "select " +
//            " count(CASE WHEN WARNINGLEVEL = '01' THEN 1 ELSE NULL END) LEVEL1," +
//            " count(CASE WHEN WARNINGLEVEL = '02' THEN 1 ELSE NULL END) LEVEL2," +
//            " count(CASE WHEN WARNINGLEVEL = '03' THEN 1 ELSE NULL END) LEVEL3, " +
//            " count(CASE WHEN WARNINGLEVEL = '04' THEN 1 ELSE NULL END) LEVEL4, " +
//            " count(CASE WHEN WARNINGLEVEL = '05' THEN 1 ELSE NULL END) LEVEL5 " +
//            " from SRP_RISK_EVENTEWARNING where to_days(WARNBEGINTIME) = to_days(now())";
//
//
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDWARNAREACNT = "SELECT " +
//            " count( b.GRIDWARNNOTICE_ID ) CNT, " +
//            " a.column_type_name DATAREPORT_ORG, " +
//            " a.column_type_code DATAREPORT_ORG_ID  " +
//            " FROM " +
//            " ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) a " +
//            " LEFT JOIN ( SELECT * FROM SRP_RISK_GRIDWARNNOTICE WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) AND WARNINGLEVEL != '09' %param%) b ON a.column_type_code = b.DATAREPORT_ORG_ID  " +
//            " GROUP BY " +
//            " a.column_type_name, " +
//            " a.column_type_code  " +
//            " ORDER BY " +
//            " a.column_type_code +0";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDCONSTWARNAREACNT = "SELECT " +
//            " count( b.GRIDCONSTWARNNOTICE_ID ) CNT, " +
//            " a.column_type_name DATAREPORT_ORG, " +
//            " a.column_type_code DATAREPORT_ORG_ID  " +
//            " FROM " +
//            " ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) a " +
//            " LEFT JOIN ( SELECT * FROM SRP_RISK_GRIDCONSTWARNNOTICE WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) AND WARNINGLEVEL != '06' %param%) b ON a.column_type_code = b.DATAREPORT_ORG_ID  " +
//            " GROUP BY " +
//            " a.column_type_name, " +
//            " a.column_type_code  " +
//            " ORDER BY " +
//            " a.column_type_code +0";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKINDUSRISKWARNAREACNT = "SELECT " +
//            " count( b.INDUSRISKNOTICE_ID ) CNT, " +
//            " a.column_type_name DATAREPORT_ORG, " +
//            " a.column_type_code DATAREPORT_ORG_ID  " +
//            " FROM " +
//            " ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) a " +
//            " LEFT JOIN ( SELECT * FROM SRP_RISK_INDUSRISKWARNNOTICE WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) %param%) b ON a.column_type_code = b.DATAREPORT_ORG_ID  " +
//            " GROUP BY " +
//            " a.column_type_name, " +
//            " a.column_type_code  " +
//            " ORDER BY " +
//            " a.column_type_code +0";
//    public static final String SQL_RISKMANAGEMENTDAO_GETSRPRISKEVENTWARNINGAREACNT = "SELECT " +
//            " count( b.INDUSRISKNOTICE_ID ) CNT, " +
//            " a.column_type_name DATAREPORT_ORG, " +
//            " a.column_type_code DATAREPORT_ORG_ID  " +
//            " FROM " +
//            " ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) a " +
//            " LEFT JOIN ( SELECT * FROM SRP_RISK_EVENTEWARNING WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) AND WARNINGLEVEL != '06' %param%) b ON a.column_type_code = b.DATAREPORT_ORG_ID  " +
//            " GROUP BY " +
//            " a.column_type_name, " +
//            " a.column_type_code  " +
//            " ORDER BY " +
//            " a.column_type_code +0";
//
//    /**
//     * 电网运行风险预警可视化展现
//     */
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNCNT = "SELECT " +
//            " srst.column_type_name DATAREPORT_ORG, " +
//            " srst.column_type_code DATAREPORT_ORG_ID, " +
//            " count( srg.DATAREPORT_ORG_ID ) AS GRIDWARN_CNT  " +
//            " FROM " +
//            " srp_risk_sys_tab srst " +
//            " LEFT JOIN ( SELECT DATAREPORT_ORG_ID FROM srp_risk_gridwarnnotice WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) and  WARNINGLEVEL <> '09' %param% ) srg ON srst.column_type_code = srg.DATAREPORT_ORG_ID " +
//            " WHERE " +
//            " srst.column_code = 'DATAREPORT_ORG'  " +
//            "GROUP BY " +
//            " srst.column_type_name  " +
//            " ORDER BY " +
//            " srst.column_type_code + 0";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNLEVELCNT = "select count(1) as WARNINGLEVEL, " +
//            " count(CASE WHEN WARNINGLEVEL = '01' THEN 1 else NULL END) as WARNINGLEVEL1, " +
//            " count(CASE WHEN WARNINGLEVEL = '02' THEN 1 else NULL END) as WARNINGLEVEL2, " +
//            " count(CASE WHEN WARNINGLEVEL = '03' THEN 1 else NULL END) as WARNINGLEVEL3, " +
//            " count(CASE WHEN WARNINGLEVEL = '04' THEN 1 else NULL END) as WARNINGLEVEL4, " +
//            " count(CASE WHEN WARNINGLEVEL = '05' THEN 1 else NULL END) as WARNINGLEVEL5, " +
//            " count(CASE WHEN WARNINGLEVEL = '06' THEN 1 else NULL END) as WARNINGLEVEL6, " +
//            " count(CASE WHEN WARNINGLEVEL = '07' THEN 1 else NULL END) as WARNINGLEVEL7, " +
//            " count(CASE WHEN WARNINGLEVEL = '08' THEN 1 else NULL END) as WARNINGLEVEL8 " +
//            " from srp_risk_gridwarnnotice a " +
//            " where a.WARNINGLEVEL != '09' " +
//            " %param%";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNWORKCNT = "SELECT " +
//            " count( 1 ) WORK_PLAN_WARN_CNT " +
//            "FROM " +
//            " srp_risk_gridwarnnotice a, " +
//            " srp_risk_gridwarntoworkplan b, " +
//            " SRP_WORK_WORKPLANDAYS c  " +
//            " WHERE " +
//            " a.GRIDWARNNOTICE_ID = b.GRIDWARNNOTICE_ID  " +
//            " AND b.WORK_PLAN_ID = c.WORK_PLAN_ID " +
//            " %param% ";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNLISTCNT = "SELECT count(1) CNT FROM srp_risk_gridwarnnotice selw WHERE 1=1  %param% ";
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNLIST = "SELECT " +
//            " selw.GRIDWARNNOTICE_ID, " +
//            " selw.WARNNUM, " +
//            " selw.TITLE, " +
//            " selc.column_type_name AS WARNINGLEVEL, " +
//            " selw.WARNBEGINTIME, " +
//            " selw.PUBLISH_ORG, " +
//            " srst.column_type_name AS WARNSTATUS" +
//            " FROM " +
//            " srp_risk_gridwarnnotice selw " +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'GRIDWARNLEVEL' ) selc ON selw.WARNINGLEVEL = selc.column_type_code " +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'WARNSTATUS') srst ON selw.WARNSTATUS = srst.column_type_code " +
//            " WHERE 1=1 %param% ";
//
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNINFO = "select \n" +
//            " selw.TITLE,\n" +
//            " selw.WARNNUM,\n" +
//            " selc.column_type_name AS WARNINGLEVEL, \n" +
//            " selw.WARNBEGINTIME,\n" +
//            " selw.WARNENDTIME,\n" +
//            " selw.PUBLISH_ORG,\n" +
//            " selw.PUBLISH_STAFF,\n" +
//            " srst.column_type_name AS WARNSTATUS,\n" +
//            " selw.ANNEX\n" +
//            " from SRP_RISK_GRIDWARNNOTICE selw\n" +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'GRIDWARNLEVEL' ) selc ON selw.WARNINGLEVEL = selc.column_type_code  \n" +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'WARNSTATUS') srst ON selw.WARNSTATUS = srst.column_type_code \n" +
//            " where GRIDWARNNOTICE_ID = ? ";
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNFEEDBACKINFO = "select ANNEX AS WARNFEEDBACK from SRP_RISK_GRIDWARNFEEDBACK where GRIDWARNNOTICE_ID = ? ";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNREPORTINFO = "select ANNEX AS WARNREPORT from SRP_RISK_GRIDWARNREPORT where GRIDWARNNOTICE_ID = ? ";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNFORMINFO = "select ANNEX AS WARNINFORM from SRP_RISK_GRIDWARNINFORM where GRIDWARNNOTICE_ID = ? ";
//
//    public static final String SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNRELINFO = "select \n" +
//            " WARNREL,\n" +
//            " WARNREMOVE_STAFF,\n" +
//            " PHONENUMBER,\n" +
//            " REMOVEDATE\n" +
//            " from SRP_RISK_GRIDWARNRELINFO where GRIDWARNNOTICE_ID = ?";
//
//
////---------------------------------------------------------------产业安全风险预警可视化展现----------------------------------------------------------------------------------
//
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKINDUSRISKWARNAREACNT = "SELECT \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.INDUSRISKNOTICE_ID ) AS GRIDWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_INDUSRISKWARNNOTICE a, SRP_RISK_DKYCOMPANY b \n" +
//            " WHERE %param% and a.PUBLISH_ORG_ID = b.ENTERPRISE_NUM and INDUSTRY_TYPE = %param1% ) srg ON  srst.column_type_code = srg.DATAREPORT_ORG_ID\t\n" +
//            "  WHERE \n" +
//            "  srst.column_code = 'DATAREPORT_ORG'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKDKYCOMPANYCNT = "select \n" +
//            " COUNT(1) COMPANY_CNT,\n" +
//            " COUNT(DISTINCT PROVINCIAL_COMPANY) PROVINCIAL_COMPANY_CNT,\n" +
//            " COUNT(CASE WHEN INDUSTRY_TYPE = '02' THEN 1 else NULL END) as INDUSTRY_SL,\n" +
//            " COUNT(CASE WHEN INDUSTRY_TYPE = '03' THEN 1 else NULL END) as INDUSTRY_XS,  \n" +
//            " COUNT(CASE WHEN INDUSTRY_TYPE = '06' THEN 1 else NULL END) as INDUSTRY_FL, \n" +
//            " COUNT(CASE WHEN INDUSTRY_TYPE = '07' THEN 1 else NULL END) as INDUSTRY_TY, \n" +
//            " COUNT(CASE WHEN INDUSTRY_TYPE = '08' THEN 1 else NULL END) as INDUSTRY_CN\t\n" +
//            " from SRP_RISK_DKYCOMPANY ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKDKYCOMPANYSTAFFCNT = "select \n" +
//            " SUM(CONTRACT_NUM + 0)+SUM(DISPATCHEDPERSONNEL_NUM + 0)+SUM(OUTSOURCING_LABOR_SERVICES + 0) AS STAFF_CNT,\n" +
//            " SUM(CONTRACT_NUM + 0) AS CONTRACT_CNT,\n" +
//            " SUM(DISPATCHEDPERSONNEL_NUM + 0) AS DISPATCHEDPERSONNEL_CNT,\n" +
//            " SUM(OUTSOURCING_LABOR_SERVICES + 0) AS OUTSOURCING_LABOR_SERVICES_CNT,\n" +
//            " SUM(SECUR_GUARD_NUM + 0) AS SECUR_GUARD_CNT \n" +
//            " from SRP_RISK_DKYCOMPANY";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETRISKINDUSRISKWARNCNT = "select \n" +
//            "  count(1) WARNINGLEVEL_CNT,\n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '01' THEN 1 else NULL END) as WARNINGLEVEL1,\n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '02' THEN 1 else NULL END) as WARNINGLEVEL2,  \n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '03' THEN 1 else NULL END) as WARNINGLEVEL3\n" +
//            " from SRP_RISK_INDUSRISKWARNNOTICE  WHERE DATE_FORMAT( WARNBEGINTIME, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKRESERVOIRSTATIONINFO = "SELECT  \n" +
//            " COUNT(1) RESERVOI_CNT,\n" +
//            " COUNT(CASE WHEN RESER_SIZE >= 100000000 THEN 1 ELSE NULL END) as RESERVOI_SUM\n" +
//            " FROM SRP_RISK_RESERVOIR  ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKDAMINFORSTATIONINFO = "SELECT  \n" +
//            " COUNT(1) RESERVOI_CNT,\n" +
//            " COUNT(CASE WHEN WHETHER_TO_PASS = '01' THEN 1 else NULL END) as WHETHER_TO_PASS_CNT\n" +
//            " FROM SRP_RISK_DAMINFOR  ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKHYDROPOWERSTATIONINFO = "SELECT  \n" +
//            " COUNT(1) HYDROPOWERSTATION_CNT,\n" +
//            " SUM(INSTALLRD_CAPACITY) INSTALLRD_CAPACITY_SUM\n" +
//            " FROM SRP_RISK_HYDROPOWERSTATION  ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETRISKHYDROPOWERSTATIONINFO = "SELECT \n" +
//            " SUM(CAVERN_MILEAGE) INSTALLRD_CAPACITY_SUM,\n" +
//            " SUM(EARTHWORK_VOLUME) CAVERN_MILEAGE_SUM,\n" +
//            " SUM(EXPLOSIVES_REQUIRED) EARTHWORK_VOLUME_SUM\n" +
//            " FROM SRP_RISK_HYDROPOWERSTATION ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETRISKBIOLOGICALMATERINFO = "SELECT  \n" +
//            " COUNT(1) BIOLOGICALMATER_CNT,\n" +
//            " SUM(STORAGE_NUM) STORAGE_NUM\n" +
//            " FROM SRP_RISK_BIOLOGICALMATER  ";
//
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKINDUSRISKWARNLISTCNT = "SELECT count(1) CNT FROM SRP_RISK_INDUSRISKWARNNOTICE selw WHERE 1=1  %param% ";
//    public static final String SQL_RISKINDUSRISKWARNDAO_GETSRPRISKINDUSRISKWARNLIST = "SELECT \n" +
//            " selw.INDUSRISKNOTICE_ID, \n" +
//            " selw.WARNNUM, \n" +
//            " selw.TITLE, \n" +
//            " selc.column_type_name AS WARNINGLEVEL,  \n" +
//            " selw.WARNBEGINTIME, \n" +
//            " selw.PUBLISH_ORG, \n" +
//            " srst.column_type_name AS WARNSTATUS \n" +
//            " FROM  \n" +
//            " SRP_RISK_INDUSRISKWARNNOTICE selw \n" +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'INDUSRISKWARNLEVEL' ) selc ON selw.WARNINGLEVEL = selc.column_type_code \n" +
//            " LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'WARNSTATUS') srst ON selw.WARNSTATUS = srst.column_type_code \n" +
//            " WHERE 1=1 %param%  ";
//
////------------------------------------------------------------------------------------------------输变电工程施工风险预警---------------------------------------------------------------------------
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNAREACNT = "SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.GRIDCONSTWARNNOTICE_ID ) AS GRIDWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_GRIDCONSTWARNNOTICE a\n" +
//            "  WHERE to_days( a.WARNBEGINTIME ) = to_days( now( ) )  %param1%   ) srg ON  srst.column_type_code = %param2% \n" +
//            "  WHERE \n" +
//            "  srst.column_code = '%param3%'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNLEVELCNT = "SELECT COUNT(case when WARNINGLEVEL = '03' then 1 else NULL end) AS  LEVEL3,\n" +
//            "COUNT(case when WARNINGLEVEL = '04' then 1 else NULL end) AS  LEVEL4\n" +
//            "FROM SRP_RISK_GRIDCONSTWARNNOTICE \n" +
//            "WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) %param% ";
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNWORKCNT ="SELECT\n" +
//            " COUNT(case when c.WORK_TYPE = '01' then 1 else NULL end) AS  WORK_TYPE1,\n" +
//            " COUNT(case when c.WORK_TYPE = '02' then 1 else NULL end) AS  WORK_TYPE2,\n" +
//            " COUNT(case when c.WORK_TYPE = '03' then 1 else NULL end) AS  WORK_TYPE3,\n" +
//            " COUNT(case when c.WORK_TYPE = '04' then 1 else NULL end) AS  WORK_TYPE4,\n" +
//            " COUNT(case when c.WORK_TYPE = '05' then 1 else NULL end) AS  WORK_TYPE5,\n" +
//            " COUNT(case when c.WORK_TYPE = '06' then 1 else NULL end) AS  WORK_TYPE6,\n" +
//            " COUNT(case when c.WORK_TYPE = '07' then 1 else NULL end) AS  WORK_TYPE7,\n" +
//            " COUNT(case when c.WORK_TYPE = '08' then 1 else NULL end) AS  WORK_TYPE8\n" +
//            " FROM\n" +
//            " SRP_RISK_GRIDCONSTWARNNOTICE a,\n" +
//            " SRP_RISK_TRANSFORWORKPLAN b,\n" +
//            " SRP_WORK_WORKPLANDAYS c \n" +
//            " WHERE\n" +
//            " a.GRIDCONSTWARNNOTICE_ID = b.GRIDCONSTWARNNOTICE_ID \n" +
//            " AND b.WORK_PLAN_ID = c.WORK_PLAN_ID\n" +
//            " and to_days( a.WARNBEGINTIME ) = to_days( now( ) )  %param% ";
//
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNBEGINORENDCNT ="SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.GRIDCONSTWARNNOTICE_ID ) AS GRIDWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_GRIDCONSTWARNNOTICE a\n" +
//            "  WHERE YEARWEEK(date_format(%param1%,'%Y-%m-%d'),1) = YEARWEEK(now(),1) %param% ) srg ON  srst.column_type_code = %param2% \n" +
//            "  WHERE \n" +
//            "  srst.column_code = '%param3%'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNYEARCNT ="select count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-01' then 1 else NULL end ) as month1,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-02' then 1 else NULL end ) as month2,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-03' then 1 else NULL end ) as month3,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-04' then 1 else NULL end ) as month4,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-05' then 1 else NULL end ) as month5,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-06' then 1 else NULL end ) as month6,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-07' then 1 else NULL end ) as month7,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-08' then 1 else NULL end ) as month8,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-09' then 1 else NULL end ) as month9,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-10' then 1 else NULL end ) as month10,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-11' then 1 else NULL end ) as month11,\n" +
//            " count(case when date_format(WARNBEGINTIME,'%Y-%m') = '%param1%-12' then 1 else NULL end ) as month12\n" +
//            " from SRP_RISK_GRIDCONSTWARNNOTICE where 1=1 %param% ";
//
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWORKLIST ="SELECT\n" +
//            " c.WORK_PLAN_ID,\n" +
//            " c.WORK_PLAN_CODE_DAY,\n" +
//            " c.WORK_PLAN_NAME,\n" +
//            " c.WORK_PLACE,\n" +
//            " case when END_TIME <= now( ) then '已完工' else '已开工' end as isEnd\n" +
//            "  FROM\n" +
//            "  SRP_RISK_GRIDCONSTWARNNOTICE a,\n" +
//            "  SRP_RISK_TRANSFORWORKPLAN b,\n" +
//            "  SRP_WORK_WORKPLANDAYS c \n" +
//            "  WHERE\n" +
//            "  a.GRIDCONSTWARNNOTICE_ID = b.GRIDCONSTWARNNOTICE_ID \n" +
//            "  AND b.WORK_PLAN_ID = c.WORK_PLAN_ID\n" +
//            "  and to_days( a.WARNBEGINTIME ) = to_days( now( ) )  \n" +
//            "  and a.city_id = ? ";
//
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWORKLISTCNT ="SELECT\n" +
//            "count(1) as list_cnt\n" +
//            " FROM\n" +
//            " SRP_RISK_GRIDCONSTWARNNOTICE a,\n" +
//            " SRP_RISK_TRANSFORWORKPLAN b,\n" +
//            " SRP_WORK_WORKPLANDAYS c \n" +
//            " WHERE\n" +
//            " a.GRIDCONSTWARNNOTICE_ID = b.GRIDCONSTWARNNOTICE_ID \n" +
//            " AND b.WORK_PLAN_ID = c.WORK_PLAN_ID\n" +
//            " and to_days( a.WARNBEGINTIME ) = to_days( now( ) )  \n" +
//            " and a.city_id = ? ";
//
//    public static final String SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWORKPLANINFO ="select a.PROJECT_ID,\n" +
//            "a.PROJECT_NAME,\n" +
//            "a.WORK_PLAN_CODE_DAY,\n" +
//            "(MAJORWORKER_NUM+0)+(OUTWORKER_NUM+0)+(INDUSTRIALWORKER_NUM+0) as staff_cnt,\n" +
//            "a.WORK_ORG,\n" +
//            "c.column_type_name,\n" +
//            "a.WORKCONTENTS,\n" +
//            "a.WORK_MANAGER,\n" +
//            "a.WORK_MANAGER_ID,\n" +
//            "a.WORK_MANAGER_CONTACT,\n" +
//            "b.PHOTO\n" +
//            "from SRP_WORK_WORKPLANDAYS a\n" +
//            "LEFT JOIN SRP_WORK_SITEWORKERINFOS b on a.WORK_ORG_ID = b.ORG_ID\n" +
//            "LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'WORK_TYPE') c on a.WORK_TYPE= c.column_type_code\n" +
//            "where a.WORK_PLAN_ID = ? ";
//
//
////----------------------------------------------------------网络安全风险预警可视化--------------------------------------------------------------
//
//    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTWARNAREACNT ="SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG,  \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.INDUSRISKNOTICE_ID ) AS GRIDWARN_CNT  \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_EVENTEWARNING a \n" +
//            " WHERE  %param% ) srg ON  srst.column_type_code = srg.DATAREPORT_ORG_ID  \n" +
//            " WHERE  \n" +
//            " srst.column_code = 'DATAREPORT_ORG'  \n" +
//            " GROUP BY   \n" +
//            " srst.column_type_name   \n" +
//            " ORDER BY  \n" +
//            " srst.column_type_code + 0";
//
//
//    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTTYPECNT ="select\n" +
//            "count(SERVER_ID)  SERVER_CNT,\n" +
//            "sum(STORAGE_CAPACITY) STORAGE_CAPACITY_SUM,\n" +
//            "COUNT(case when THREATENED_ATTACK = '01' then 1 else NULL end) AS  ATTACK_THREAT_CNT,\n" +
//            "COUNT(case when VIRUS_DISPOSAL = '01' then 1 \n"+
//                "when VIOLATION_TYPE = '02'	then 1\n"+
//                "when VIOLATION_TYPE = '03'	then 1\n"+
//            "when VIOLATION_TYPE = '04'	then 1\n"+
//                "when VIOLATION_TYPE = '05'	then 1\n"+
//                "else NULL end) AS  VIRUS_DISPOSAL_CNT,\n" +
//            "COUNT(case when EQUIPSUPPORT_ENDTIME <=  now( )  then 1 else NULL end) AS  EQUIPSUPPORT_CNT,\n" +
//            "COUNT(case when ATTACK_THREAT_TYPE = '01' then 1\n"+
//                "when ATTACK_THREAT_TYPE = '02'	then 1\n"+
//                "when ATTACK_THREAT_TYPE = '03'	then 1\n"+
//                "when ATTACK_THREAT_TYPE = '04'	then 1\n"+
//                "when ATTACK_THREAT_TYPE = '05'	then 1\n"+
//                "when ATTACK_THREAT_TYPE = '06'	then 1\n"+
//                "when ATTACK_THREAT_TYPE = '07'	then 1\n"+
//                "else NULL end) AS  ILLEGAL_CNT\n" +
//            "from SRP_RISK_EVENTEWARNING where to_days( WARNBEGINTIME ) = to_days( now( ) )";
//
////    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTTYPECNT ="select count(SERVER_ID)  SERVER_CNT,\n" +
////            " sum(STORAGE_CAPACITY) STORAGE_CAPACITY_SUM,\n" +
////            " COUNT(case when THREATENED_ATTACK = '01' then 1 else NULL end) AS  ATTACK_THREAT_CNT,\n" +
////            " COUNT(case when VIRUS_DISPOSAL = '01' then 1 else NULL end) AS  VIRUS_DISPOSAL_CNT,\n" +
////            " COUNT(case when EQUIPSUPPORT_ENDTIME <=  now( )  then 1 else NULL end) AS  EQUIPSUPPORT_CNT,\n" +
////            " COUNT(case when ILLEGAL = '01' then 1 else NULL end) AS  ILLEGAL_CNT\n" +
////            " from SRP_RISK_EVENTEWARNING\n" +
////            " where to_days( WARNBEGINTIME ) = to_days( now( ) )";
//
//    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTWARNATTACKTHREATCNT ="select \n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '01' then 1 else NULL end) AS  ATTACK_THREAT1,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '02' then 1 else NULL end) AS  ATTACK_THREAT2,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '03' then 1 else NULL end) AS  ATTACK_THREAT3,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '04' then 1 else NULL end) AS  ATTACK_THREAT4,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '05' then 1 else NULL end) AS  ATTACK_THREAT5,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '06' then 1 else NULL end) AS  ATTACK_THREAT6,\n" +
//            " COUNT(case when ATTACK_THREAT_TYPE = '07' then 1 else NULL end) AS  ATTACK_THREAT7\n" +
//            " from SRP_RISK_EVENTEWARNING where to_days( WARNBEGINTIME ) = to_days( now( ) )";
//
//
//    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTWARNVIOLATIONCNT ="select \n" +
//            " COUNT(case when VIOLATION_TYPE = '01' then 1 else NULL end) AS  VIOLATION1,\n" +
//            " COUNT(case when VIOLATION_TYPE = '02' then 1 else NULL end) AS  VIOLATION2,\n" +
//            " COUNT(case when VIOLATION_TYPE = '03' then 1 else NULL end) AS  VIOLATION3,\n" +
//            " COUNT(case when VIOLATION_TYPE = '04' then 1 else NULL end) AS  VIOLATION4,\n" +
//            " COUNT(case when VIOLATION_TYPE = '05' then 1 else NULL end) AS  VIOLATION5\n" +
//            " from SRP_RISK_EVENTEWARNING where to_days( WARNBEGINTIME ) = to_days( now( ) )";
//
//
//    public static final String SQL_RISKEVENTWARNDAO_GETSRPRISKEVENTWARNCNT ="SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.INDUSRISKNOTICE_ID ) AS EVENTEWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_EVENTEWARNING a\n" +
//            "  WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) ) srg ON  srst.column_type_code = srg.DATAREPORT_ORG_ID \n" +
//            "  WHERE \n" +
//            "  srst.column_code = 'DATAREPORT_ORG'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//
//    public static final String  RISKWORKWARNDAO_GETSRPRISKWORKWARNAREACNT = "select srst.column_type_name DATAREPORT_ORG,\n" +
//            "srst.column_type_code DATAREPORT_ORG_ID,\n" +
//            "count( srg.WORK_PLAN_ID ) AS GRIDWARN_CNT \n" +
//            "from  srp_risk_sys_tab srst \n" +
//            "LEFT JOIN ( SELECT a.*  FROM SRP_WORK_WORKPLANDAYS a WHERE to_days( a.BEGIN_TIME ) = to_days( now( ) ) %param% ) srg ON  srst.column_type_code = srg.DATAREPORT_ORG_ID \n" +
//            "WHERE srst.column_code = 'DATAREPORT_ORG'\n" +
//            "GROUP BY srst.column_type_name \n" +
//            "ORDER BY  \n" +
//            "srst.column_type_code + 0 ";
//
//
//    public static final String SQL_RISKWORKWARNDAO_GETSRPRISKINDUSRISKWARNAREACNT = "SELECT \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.INDUSRISKNOTICE_ID ) AS GRIDWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_RISK_INDUSRISKWARNNOTICE a, SRP_RISK_DKYCOMPANY b \n" +
//            " WHERE to_days( a.WARNBEGINTIME ) = to_days( now( ) ) and a.PUBLISH_ORG_ID = b.ENTERPRISE_NUM %param% ) srg ON  srst.column_type_code = srg.DATAREPORT_ORG_ID\t\n" +
//            "  WHERE \n" +
//            "  srst.column_code = 'DATAREPORT_ORG'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//
//    public static final String SQL_RISKWORKWARNDAO_GETSRPRISKGRIDCONSTWARNLEVELCNT = "SELECT \n" +
//            " COUNT(case when WARNINGLEVEL = '01' then 1 else NULL end) AS  LEVEL1,\n" +
//            " COUNT(case when WARNINGLEVEL = '02' then 1 else NULL end) AS  LEVEL2,\n" +
//            " COUNT(case when WARNINGLEVEL = '03' then 1 else NULL end) AS  LEVEL3, \n" +
//            " COUNT(case when WARNINGLEVEL = '04' then 1 else NULL end) AS  LEVEL4 \n" +
//            " FROM SRP_RISK_GRIDCONSTWARNNOTICE  \n" +
//            " WHERE to_days( WARNBEGINTIME ) = to_days( now( ) )";
//
//
//    public static final String SQL_RISKWORKWARNDAO_GETRISKINDUSRISKWARNCNT = "select \n" +
//            "  count(1) WARNINGLEVEL_CNT,\n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '01' THEN 1 else NULL END) as WARNINGLEVEL1,\n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '02' THEN 1 else NULL END) as WARNINGLEVEL2,  \n" +
//            "  COUNT(CASE WHEN WARNINGLEVEL = '03' THEN 1 else NULL END) as WARNINGLEVEL3\n" +
//            " from SRP_RISK_INDUSRISKWARNNOTICE  WHERE to_days( WARNBEGINTIME ) = to_days( now( ) ) ";
//
//
//
////-------------------------------------------------------------------风险作业全景化展示-------------------------------------------------------------------------------------------------------------
//
//
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNTS = "SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.WORK_PLAN_ID ) AS PLANWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_WORK_WORKPLANDAYS a\n" +
//            "  WHERE   %param1%   ) srg ON  srst.column_type_code = %param2% \n" +
//            "  WHERE \n" +
//            "  srst.column_code = '%param3%'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLAREACNT = "SELECT  \n" +
//            " srst.column_type_name DATAREPORT_ORG, \n" +
//            " srst.column_type_code DATAREPORT_ORG_ID, \n" +
//            " count( srg.WORK_PLAN_ID ) AS PLANWARN_CNT \n" +
//            " FROM  \n" +
//            " srp_risk_sys_tab srst  \n" +
//            " LEFT JOIN ( SELECT a.*  FROM SRP_WORK_WORKPLANDAYS a\n" +
//            "  WHERE to_days( a.BEGIN_TIME ) = to_days( now( ) )  %param1%   ) srg ON  srst.column_type_code = %param2% \n" +
//            "  WHERE \n" +
//            "  srst.column_code = '%param3%'  \n" +
//            "  GROUP BY  \n" +
//            "  srst.column_type_name   \n" +
//            "  ORDER BY  \n" +
//            "  srst.column_type_code + 0 ";
//
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNFORALLCNT ="SELECT \n" +
//            " COUNT(case when c.WORK_TYPE = '01' then 1 else NULL end) AS  WORK_TYPE1, \n" +
//            " COUNT(case when c.WORK_TYPE = '02' then 1 else NULL end) AS  WORK_TYPE2, \n" +
//            " COUNT(case when c.WORK_TYPE = '03' then 1 else NULL end) AS  WORK_TYPE3, \n" +
//            " COUNT(case when c.WORK_TYPE = '04' then 1 else NULL end) AS  WORK_TYPE4, \n" +
//            " COUNT(case when c.WORK_TYPE = '05' then 1 else NULL end) AS  WORK_TYPE5, \n" +
//            " COUNT(case when c.WORK_TYPE = '06' then 1 else NULL end) AS  WORK_TYPE6, \n" +
//            " COUNT(case when c.WORK_TYPE = '07' then 1 else NULL end) AS  WORK_TYPE7, \n" +
//            " COUNT(case when c.WORK_TYPE = '08' then 1 else NULL end) AS  WORK_TYPE8 \n" +
//            " FROM SRP_WORK_WORKPLANDAYS c \n" +
//            " WHERE to_days( c.BEGIN_TIME ) = to_days( now( ) )  %param% ";
//
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKWARNWORKTYPECNT ="SELECT \n" +
//            " COUNT(case when c.WORK_STATE = '01' then 1 else NULL end) AS  WORK_STATE1, \n" +
//            " COUNT(case when c.WORK_STATE = '02' then 1 else NULL end) AS  WORK_STATE2, \n" +
//            " COUNT(case when c.WORK_STATE = '03' then 1 else NULL end) AS  WORK_STATE3, \n" +
//            " COUNT(case when c.WORK_STATE = '04' then 1 else NULL end) AS  WORK_STATE4, \n" +
//            " COUNT(case when c.WORK_STATE = '05' then 1 else NULL end) AS  WORK_STATE5\n" +
//            " FROM SRP_WORK_WORKPLANDAYS c \n" +
//            " WHERE to_days( c.BEGIN_TIME ) = to_days( now( )  ) %param% ";
//
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKPLANLISTCNT ="SELECT count(1) cnt " +
//            " FROM \n" +
//            " SRP_WORK_WORKPLANDAYS c  \n" +
//            " WHERE to_days( c.BEGIN_TIME ) = to_days( now( ) )   \n" +
//            " and c.city_id = ? ";
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKPLANLIST ="SELECT \n" +
//            " c.WORK_PLAN_ID, \n" +
//            " c.WORK_PLAN_CODE_DAY, \n" +
//            " c.WORK_PLAN_NAME, \n" +
//            " c.WORK_PLACE, \n" +
//            " case when END_TIME <= now( ) then '已完工' else '已开工' end as isEnd \n" +
//            "  FROM \n" +
//            "  SRP_WORK_WORKPLANDAYS c  \n" +
//            "  WHERE to_days( c.BEGIN_TIME ) = to_days( now( ) )   \n" +
//            "  and c.city_id = ? ";
//
//
//    public static final String SQL_RISKWORKWARNFORALLDAO_GETSRPRISKWORKPLANINFO =" select a.WORKCONTENTS,\n" +
//            " a.BEGIN_TIME,\n" +
//            " a.END_TIME,\n" +
//            " b.column_type_name WORK_TYPE,\n" +
//            " a.WORK_PLACE,\n" +
//            " a.PROJECT_NAME,\n" +
//            " c.column_type_name POWER_CUT,\n" +
//            " a.SUBCONTRACT_ORG,\n" +
//            " a.SUPERVISORORG,\n" +
//            " d.column_type_name WORKRISK_LEVEL,\n" +
//            " e.column_type_name GRIDRISK_LEVEL,\n" +
//            " f.column_type_name VOLTAGE_LEVEL,\n" +
//            " g.column_type_name LIVE_WORK_FLAG,\n" +
//            " a.WORK_MANAGER,\n" +
//            " a.WORK_MANAGER_CONTACT,\n" +
//            " h.VIOLATION_CNT\n" +
//            " from SRP_WORK_WORKPLANDAYS a \n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'WORK_TYPE') b on a.WORK_TYPE= b.column_type_code\n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'POWER_CUT') c on a.POWER_CUT= c.column_type_code\n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'WORKRISK_LEVEL') d on a.WORKRISK_LEVEL= d.column_type_code\n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'GRIDRISK_LEVEL') e on a.GRIDRISK_LEVEL= e.column_type_code\n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'VOLTAGE_LEVEL') f on a.VOLTAGE_LEVEL= f.column_type_code\n" +
//            " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'LIVE_WORK_FLAG') g on a.LIVE_WORK_FLAG= g.column_type_code\n" +
//            " LEFT JOIN (select VIOLATION_STAFF_ID, count(1) VIOLATION_CNT from SRP_WORK_VIOLATIONFILE GROUP BY VIOLATION_STAFF_ID ) h on a.WORK_MANAGER_ID= h.VIOLATION_STAFF_ID\n" +
//            " where a.WORK_PLAN_ID = ? ";
//
//
//
//    //保存系统日志列表
//    public static final String SQL_SYS_INSSERTLOG ="INSERT INTO sys_log(ID,LOG_CONTENT,MODULE_NAME,LOG_IP,OPERATION_TYPE,LOG_ON,LOG_USER_NAME,LOG_LEVEL,DATA_TYPE,ERROR_TYPE,LOG_TIME)\n" +
//            "VALUES(?,?,?,?,?,?,?,?,?,?,NOW());";
//
//
//    //省份查询
//    public static final String SQL_SELECT_SQR_RISK_SYS_TAB ="SELECT * FROM  srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG'";
//
//
//}
