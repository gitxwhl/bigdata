package com.raysdata.riskdataanalyzeserver.utils;

public class Const {


    //iom_tabl_col_value表地市编码检索
    public static final String COLUMN_KEY_AREA_CODE = "area_code";
    public static final String COLUMN_KEY = "zxzd_result_code";

    //-------------------------------------------------------------------人员基本情况-----------------------------------------------------------------------------------------------
    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERINFOSCNT = "SELECT  count(1) cnt " +
            " from SRP_WORK_MAINOCCUPATION_BASICFILEINFO a\n" +
            " left join (SELECT WORKER_ID,GROUP_CONCAT(PROJECT, \";\") AS PROJECT\n" +
            "     FROM\n" +
            "        SRP_WORK_SITEWORKERTOPROJECT\n" +
            "     GROUP BY\n" +
            "        WORKER_ID) b on a.BASICFILEINFO_ID = b.WORKER_ID\n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'DATAREPORT_ORG') c on a.DATAREPORT_ORG_ID = c.column_type_code" +
            " where 1=1 %param% ";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERINFOS = "SELECT\n" +
            "\ta.BASICFILEINFO_ID,\n" +
            "\ta.NAME,\n" +
            "\td.column_type_name SEX,\n" +
            "\ta.ID_CARD,\n" +
            "\tc.column_type_name DATAREPORT_ORG,\n" +
            "\ta.ORG_NAME,\n" +
            "\tb.PROJECT,\n" +
            "\ta.CONTACT,\n" +
            "\ta.POTO,\n" +
            "\ta.AGE \n" +
            "FROM\n" +
            "\tSRP_WORK_MAINOCCUPATION_BASICFILEINFO a\n" +
            "\tLEFT JOIN ( SELECT WORKER_ID, GROUP_CONCAT( PROJECT, \";\" ) AS PROJECT FROM SRP_WORK_SITEWORKERTOPROJECT GROUP BY WORKER_ID ) b ON a.BASICFILEINFO_ID = b.WORKER_ID\n" +
            "LEFT JOIN ( SELECT column_type_code, column_type_name FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) c ON a.DATAREPORT_ORG_ID = c.column_type_code\n" +
            "LEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'SEX' ) d ON a.SEX = d.column_type_code \n" +
            "WHERE\n" +
            "\t1 = 1 %param%";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERTOPROJECTCNT = "select count(1) cnt \n" +
            " from SRP_WORK_SITEWORKERTOPROJECT a,SRP_WORK_PROJECTMANAGEMENT b \n" +
            " where a.PROJECTG_ID = b.PROJECT_CODE \n" +
            " and a.WORKER_ID = ? ";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERTOPROJECT = "select \n" +
            "b.PROJECT_NAME,\n" +
            "b.PROJECT_CODE,\n" +
            "b.CONTRACTOR_MANAGER,\n" +
            "c.column_type_name STATE_PROJECT,\n" +
            "b.CREATE_TIME,\n" +
            "b.END_TIME\n" +
            " from SRP_WORK_SITEWORKERTOPROJECT a,SRP_WORK_PROJECTMANAGEMENT b,(SELECT * FROM srp_risk_sys_tab where column_code = 'STATE_PROJECT') c \n" +
            " where a.PROJECTG_ID = b.PROJECT_CODE and b.STATE_PROJECT = c.column_type_code %param% ";

    //人员总数查询
    public static final String SQL_DATA_GETSTAFFCNT = "SELECT count(1) cnt FROM SRP_WORK_MAINOCCUPATION_BASICFILEINFO";

    //人员性质分类数量查询
    public static final String SQL_DATA_GETWORKERNATURECNT = "select count(1) from SRP_WORK_SITEWORKERINFO where WORKER_NATURE = %param%";

    //违章人数查询
    public static final String SQL_DATA_GETVIOLATIONCNT = "select count(1) cnt from (select 1 from srp_work_kdtb_violationfile  group by VIOLATION_STAFF ) a";

    //参加考试人数(通过/未通过)查询
    public static final String SQL_DATA_GETEXAMINATION = "select count(1) cnt from SRP_WORK_SAFETYTEST where 1=1 %param%";


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERLINKAGEINFOSLISTCNT = "SELECT  count(1) cnt " +
            " from SRP_WORK_MAINOCCUPATION_BASICFILEINFO a\n" +
            " left join (select VIOLATION_STAFF_ID,count(1) VIOLATION_CNT from srp_work_kdtb_violationfile  group by VIOLATION_STAFF_ID) b on a.BASICFILEINFO_ID = b.VIOLATION_STAFF_ID\n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'DATAREPORT_ORG') c on a.DATAREPORT_ORG_ID = c.column_type_code \n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'ACCESS_STATE') d on a.ACCESS_STATE = c.column_type_code ";


    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERLINKAGEINFOSLIST = "SELECT  a.BASICFILEINFO_ID,a.NAME,e.column_type_name SEX,a.ID_CARD,c.column_type_name DATAREPORT_ORG,\n" +
            " a.ORG_NAME, d.column_type_name ACCESS_STATE, a.ACCESS_DATE,IFNULL(b.VIOLATION_CNT,0) VIOLATION_CNT,a.CONTACT,a.POTO,a.AGE\n" +
            " from SRP_WORK_MAINOCCUPATION_BASICFILEINFO a\n" +
            " left join (select VIOLATION_STAFF_ID,count(1) VIOLATION_CNT from srp_work_kdtb_violationfile  group by VIOLATION_STAFF_ID) b on a.BASICFILEINFO_ID = b.VIOLATION_STAFF_ID\n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'DATAREPORT_ORG') c on a.DATAREPORT_ORG_ID = c.column_type_code \n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'ACCESS_STATE') d on a.ACCESS_STATE = d.column_type_code \n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'SEX') e on a.SEX = e.column_type_code \n" +
            " where 1=1 %param% ";


    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERUNITCHANGELISTCNT = "select count(1) cnt from SRP_WORK_UNITCHANGE where WORKER_ID = ? ";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERUNITCHANGELIST = "select SITEWORKERTOPROJECT_ID,\n" +
            "WORKER,\n" +
            "WORKER_ID,\n" +
            "PROJECT,\n" +
            "CREATE_TIME,\n" +
            "UPDATE_TIME\n" +
            "from SRP_WORK_UNITCHANGE where WORKER_ID = ? ";


    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONLISTCNT = "select count(1) cnt from srp_work_kdtb_violationfile where VIOLATION_STAFF_ID = ? ";


    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONLIST = "select VIOLATION_LEVEL,VIOLATION_CONTENT,VIOLATION_ORG,VIOLATION_DATE,CHECK_LEVEL from srp_work_kdtb_violationfile where VIOLATION_STAFF_ID = ? ";







    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFEINFOLISTCNT = "SELECT count(1) cnt" +
            " from SRP_WORK_MAINOCCUPATION_BASICFILEINFO a \n" +
            " left join (select WORKER_ID,count(1) SAFELEARNING_CNT from SRP_WORK_SAFELEARNING group by WORKER_ID) b on a.BASICFILEINFO_ID = b.WORKER_ID \n" +
            " left JOIN (select WORKER_ID,count(1) SAFETYTEST_CNT from SRP_WORK_SAFETYTEST group by WORKER_ID) c on a.BASICFILEINFO_ID = c.WORKER_ID  \n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'DATAREPORT_ORG') d on a.DATAREPORT_ORG_ID = d.column_type_code\n" +
            " LEFT JOIN\t(select PENALTY_STAFF_ID,count(1) STAFFVIOLATIO_CNT,SUM(PENALTY_STAFF_POINTS+0) POINTS \n" +
            "     from SRP_WORK_STAFFVIOLATIONPENALTY GROUP BY PENALTY_STAFF_ID) e on a.BASICFILEINFO_ID = e.PENALTY_STAFF_ID";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFEINFOLIST = "SELECT  a.BASICFILEINFO_ID,a.NAME,f.column_type_name SEX,a.ID_CARD,d.column_type_name DATAREPORT_ORG, \n" +
            "a.ORG_NAME,IFNULL(b.SAFELEARNING_CNT,0) SAFELEARNING_CNT,IFNULL(c.SAFETYTEST_CNT,0) SAFETYTEST_CNT,IFNULL(e.POINTS,0) POINTS,IFNULL(e.STAFFVIOLATIO_CNT,0) STAFFVIOLATIO_CNT,a.CONTACT,a.POTO,a.AGE \n" +
            " from SRP_WORK_MAINOCCUPATION_BASICFILEINFO a \n" +
            " left join (select WORKER_ID,count(1) SAFELEARNING_CNT from SRP_WORK_SAFELEARNING group by WORKER_ID) b on a.BASICFILEINFO_ID = b.WORKER_ID \n" +
            " left JOIN (select WORKER_ID,count(1) SAFETYTEST_CNT from SRP_WORK_SAFETYTEST group by WORKER_ID) c on a.BASICFILEINFO_ID = c.WORKER_ID  \n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'DATAREPORT_ORG') d on a.DATAREPORT_ORG_ID = d.column_type_code\n" +
            " left JOIN (select column_type_code,column_type_name \n" +
            "     from srp_risk_sys_tab where column_code = 'SEX') f on a.SEX = f.column_type_code\n" +
            " LEFT JOIN\t(select PENALTY_STAFF_ID,count(1) STAFFVIOLATIO_CNT,SUM(PENALTY_STAFF_POINTS+0) POINTS \n" +
            "     from SRP_WORK_STAFFVIOLATIONPENALTY GROUP BY PENALTY_STAFF_ID) e on a.BASICFILEINFO_ID = e.PENALTY_STAFF_ID "+
             " where 1=1 %param% ";


    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFELEARNLISTCNT = "select count(1) cnt from SRP_WORK_SAFELEARNING where WORKER_ID = ? ";
    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFELEARNLIST = "select SAFELEARNING_NAME,SAFELEARNING_CONTENT,SAFELEARNING_DATE,SAFELEARNING_RESULT from SRP_WORK_SAFELEARNING where 1=1 %param% ";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFETYTESTLISTCNT = "select count(1) cnt from SRP_WORK_SAFETYTEST where WORKER_ID = ? ";
    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERSAFETYTESTLIST = "select TEST_NAME,TEST_SCORE,WHETHER_PASS from SRP_WORK_SAFETYTEST where 1=1 %param% ";

    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONPOINTSLISTCNT = "select count(1) cnt from SRP_WORK_STAFFVIOLATIONPENALTY a,srp_work_kdtb_violationfile b where a.VIOLATIONFILE_ID = b.VIOLATIONFILE_ID and a.PENALTY_STAFF_ID = ? ";
    public static final String SQL_RISKWORKSTAFFINFODAO_GETRISKWORKERVIOLATIONPOINTSLIST = "select b.VIOLATION_LEVEL,b.VIOLATION_CONTENT,b.VIOLATION_ORG,b.VIOLATION_DATE,CHECK_LEVEL,a.PENALTY_STAFF_POINTS from SRP_WORK_STAFFVIOLATIONPENALTY a,srp_work_kdtb_violationfile b where a.VIOLATIONFILE_ID = b.VIOLATIONFILE_ID %param% ";

    //------------------------------------------------------------------------安全事故事件统计分析---------------------------------------------------------------------------------------------------------------------------------------------------------
    //获取字段表定义
    public static final String SQL_DATA_GETDICTIONARIES = "select em_id,\n" +
            "            column_code,\n" +
            "            column_name,\n" +
            "            column_type_name,\n" +
            "            column_type_code,\n" +
            "            column_num\n" +
            "            from srp_risk_sys_tab";

    //查询字典表中数据
    public static final String SQL_DATA_DICTIONARIES = "select column_type_code from srp_risk_sys_tab %param%";

    //设备事件
    public  static  final String SQL_ACCIDENTDAO_GETEQUIPACCIDENTCNT = "select count(1) cnt from bd_inc_equipaccident where 1=1 %param%";
    public  static  final String SQL_ACCIDENTDAO_GETEQUIPACCIDENT = "SELECT\n" +
            "\td.column_type_name ACCIDENTGRADE,\n" +
            "\ta.TITLE,\n" +
            "\th.column_type_name VOLTAGE_CAPACITY,\n" +
            "\ti.column_type_name GRIDCORP,\n" +
            "\tg.column_type_name REASON,\n" +
            "\tf.column_type_name DUTY,\n" +
            "\tc.column_type_name DEVICECLASS,\n" +
            "\ta.ACCIDENTCORP,\n" +
            "\ta.BGNTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM bd_inc_equipaccident WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'YBCLASS' ) b ON a.YBCLASS = b.column_type_code \n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DEVICECLASS' ) c ON a.DEVICECLASS = c.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTGRADE' ) d ON a.ACCIDENTGRADE = d.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTCLASS' ) e ON a.ACCIDENTCLASS = e.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DUTY' ) f ON a.DUTY = f.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'VOLTAGE_LEVEL' ) h ON a.VOLTAGE_CAPACITY = h.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) i ON a.GRIDCORP = i.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'REASON') g ON a.REASON = g.column_type_code";


    //人身事件
    public  static  final String SQL_ACCIDENTDAO_GETPERSONALACCIDENTCNT = "select count(1) cnt from bd_inc_personalaccident where 1=1 %param%";
    public  static  final String SQL_ACCIDENTDAO_GETPERSONALACCIDENT = "SELECT\n" +
            "\td.column_type_name ACCIDENTGRADE,\n" +
            "\ta.TITLE,\n" +
            "\te.column_type_name ACCIDENTCLASS,\n" +
            "\ta.VICTIM,\n" +
            "\tb.column_type_name INJURY_DEGREE,\n" +
            "\ta.ACCIDENTCORP,\n" +
            "\ta.BGNTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM bd_inc_personalaccident WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTGRADE' ) d ON a.ACCIDENTGRADE = d.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTCLASS' ) e ON a.ACCIDENTCLASS = e.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'INJURY_DEGREE' ) b ON a.INJURY_DEGREE = b.column_type_code";

    //电网事件
    public  static  final String SQL_ACCIDENTDAO_GETPOWERGRIDACCIDENTCNT = "select count(1) cnt from bd_inc_powergridaccident where 1=1 %param%";
    public  static  final String SQL_ACCIDENTDAO_GETPOWERGRIDACCIDENT = "SELECT\n" +
            "\td.column_type_name ACCIDENTGRADE,\n" +
            "\ta.TITLE,\n" +
            "\tf.column_type_name VOLTAGE_CAPACITY,\n" +
            "\te.column_type_name REASON,\n" +
            "\tb.column_type_name DUTY,\n" +
            "\tc.column_type_name DEVICECLASS,\n" +
            "\ta.ACCIDENTCORP,\n" +
            "\ta.BGNTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM bd_inc_powergridaccident WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTGRADE' ) d ON a.ACCIDENTGRADE = d.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'REASON' ) e ON a.REASON = e.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DUTY' ) b ON a.DUTY = b.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'VOLTAGE_LEVEL' ) f ON a.VOLTAGE_CAPACITY = f.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DEVICECLASS' ) c ON a.DEVICECLASS = c.column_type_code";

    //网络信息事件
    public  static  final String SQL_ACCIDENTDAO_GETINFORMACCIDENTCNT = "select count(1) cnt from bd_inc_informaccident where 1=1 %param%";
    public  static  final String SQL_ACCIDENTDAO_GETINFORMACCIDENT = "SELECT\n" +
            "\td.column_type_name ACCIDENTGRADE,\n" +
            "\ta.TITLE,\n" +
            "\te.column_type_name ACCIDENTCLASS,\n" +
            "\tc.column_type_name DEVICECLASS,\n" +
            "\ta.ACCIDENTCORP,\n" +
            "\ta.BGNTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM bd_inc_informaccident WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENTGRADE' ) d ON a.ACCIDENTGRADE = d.column_type_code \n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'INFORMACCIDENTCLASS' ) e ON a.ACCIDENTCLASS = e.column_type_code\n" +
            "\tLEFT JOIN\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'INFORMDEVICECLASS' ) c ON a.DEVICECLASS = c.column_type_code";

    //按类型环比统计
    public  static  final String SQL_ACCIDENTDAO_GETACCIDENTCNT = "select " +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-01' then 1 else NULL end ) as month1, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-02' then 1 else NULL end ) as month2, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-03' then 1 else NULL end ) as month3, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-04' then 1 else NULL end ) as month4, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-05' then 1 else NULL end ) as month5, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-06' then 1 else NULL end ) as month6, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-07' then 1 else NULL end ) as month7, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-08' then 1 else NULL end ) as month8, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-09' then 1 else NULL end ) as month9, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-10' then 1 else NULL end ) as month10, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-11' then 1 else NULL end ) as month11, \n" +
            "             count(case when date_format(BGNTIME,'%Y-%m') = '%param1%-12' then 1 else NULL end ) as month12 \n" +
            "             from %param% where 1=1 ";

    //趋势预测分析 实际值
    public static final String SQL_ACCIDENTDAO_GETACCIDENTCNTS =
            "SELECT " +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 30 day),'%Y-%m-%d') then 1 else NULL end ) as day30,  \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 29 day),'%Y-%m-%d') then 1 else NULL end ) as day29, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 28 day),'%Y-%m-%d') then 1 else NULL end ) as day28, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 27 day),'%Y-%m-%d') then 1 else NULL end ) as day27, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 26 day),'%Y-%m-%d') then 1 else NULL end ) as day26, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 25 day),'%Y-%m-%d') then 1 else NULL end ) as day25, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 24 day),'%Y-%m-%d') then 1 else NULL end ) as day24, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 23 day),'%Y-%m-%d') then 1 else NULL end ) as day23, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 22 day),'%Y-%m-%d') then 1 else NULL end ) as day22, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 21 day),'%Y-%m-%d') then 1 else NULL end ) as day21, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 20 day),'%Y-%m-%d') then 1 else NULL end ) as day20, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 19 day),'%Y-%m-%d') then 1 else NULL end ) as day19, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 18 day),'%Y-%m-%d') then 1 else NULL end ) as day18, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 17 day),'%Y-%m-%d') then 1 else NULL end ) as day17, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 16 day),'%Y-%m-%d') then 1 else NULL end ) as day16, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 15 day),'%Y-%m-%d') then 1 else NULL end ) as day15, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 14 day),'%Y-%m-%d') then 1 else NULL end ) as day14, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 13 day),'%Y-%m-%d') then 1 else NULL end ) as day13, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 12 day),'%Y-%m-%d') then 1 else NULL end ) as day12, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 11 day),'%Y-%m-%d') then 1 else NULL end ) as day11, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 10 day),'%Y-%m-%d') then 1 else NULL end ) as day10, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 9 day),'%Y-%m-%d') then 1 else NULL end ) as day9, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 8 day),'%Y-%m-%d') then 1 else NULL end ) as day8, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 7 day),'%Y-%m-%d') then 1 else NULL end ) as day7, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 6 day),'%Y-%m-%d') then 1 else NULL end ) as day6, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 5 day),'%Y-%m-%d') then 1 else NULL end ) as day5, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 4 day),'%Y-%m-%d') then 1 else NULL end ) as day4, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 3 day),'%Y-%m-%d') then 1 else NULL end ) as day3, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 2 day),'%Y-%m-%d') then 1 else NULL end ) as day2, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 1 day),'%Y-%m-%d') then 1 else NULL end ) as day1, \n" +
                    "  count(case when date_format(BGNTIME, '%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL 0 day),'%Y-%m-%d') then 1 else NULL end ) as day0 \n" +
                    "  FROM %param% WHERE 1=1";
    //趋势预测分析 预测值
    public static final String SQL_ACCIDENTDAO_GETACCIDENTCNTSY = "select " +
            "   count(case when date_format(BGNTIME,'%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL -1 day),'%Y-%m-%d') then 1 else NULL end ) as day1, \n" +
            "   count(case when date_format(BGNTIME,'%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL -2 day),'%Y-%m-%d') then 1 else NULL end ) as day2, \n" +
            "   count(case when date_format(BGNTIME,'%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL -3 day),'%Y-%m-%d') then 1 else NULL end ) as day3, \n" +
            "   count(case when date_format(BGNTIME,'%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL -4 day),'%Y-%m-%d') then 1 else NULL end ) as day4, \n" +
            "   count(case when date_format(BGNTIME,'%Y-%m-%d') = DATE_FORMAT(date_sub(now(),INTERVAL -5 day),'%Y-%m-%d') then 1 else NULL end ) as day5  \n" +
            "    FROM %param% WHERE 1=1";


    //------------------------------------------------------------------------数据整合---------------------------------------------------------------------------------------------------------------------------------------------------------
    //数据治理
    public  static  final String SQL_GETDATAINFO_MANAGECNT = "select count(1) cnt from datainfo_manage where 1=1 %param%";
    public  static  final String SQL_GETDATAINFO_MANAGE = "SELECT\n" +
            "\tb.JOBNAME,\n" +
            "\tb.SERVER_IP,\n" +
            "\tb.DATA_TYPE,\n" +
            "\ta.column_type_name PROCESS_STATE,\n" +
            "\tb.OPERATION_INFO,\n" +
            "\tb.EXECUTION_DATETIME,\n" +
            "\tb.EXECUTION_TIME,\n" +
            "\tb.LOG_INFO \n" +
            "FROM\n" +
            "\t( SELECT * FROM datainfo_manage WHERE 1 = 1 %param%) b\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'PROCESS_STATE' ) a ON a.column_type_code = b.PROCESS_STATE";

    //数据清洗
    public  static  final String SQL_GETDATAINFO_PURGECNT = "select count(1) cnt from datainfo_purge where 1=1 %param%";
    public  static  final String SQL_GETDATAINFO_PURGE = "SELECT\n" +
            "\tb.JOBNAME,\n" +
            "\tb.SERVER_IP,\n" +
            "\tb.DATA_TYPE,\n" +
            "\ta.column_type_name PROCESS_STATE,\n" +
            "\tb.OPERATION_INFO,\n" +
            "\tb.EXECUTION_DATETIME,\n" +
            "\tb.EXECUTION_TIME,\n" +
            "\tb.LOG_INFO \n" +
            "FROM\n" +
            "\t( SELECT * FROM datainfo_purge WHERE 1 = 1 %param%) b\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'PROCESS_STATE' ) a ON a.column_type_code = b.PROCESS_STATE";

    //按等级统计
    //设备事件等级
    public static final String SQL_EQUIPACCIDENTDAO_GETACCIDENTGRADE = "select "+
            " count(CASE when ACCIDENTGRADE in ('01','02','03','04') THEN 1 ELSE NULL END) LEVEL4,"+
            " count(CASE when ACCIDENTGRADE in ('05') THEN 1 ELSE NULL END) LEVEL5,"+
            " count(CASE when ACCIDENTGRADE in ('06') THEN 1 ELSE NULL END) LEVEL6,"+
            " count(CASE when ACCIDENTGRADE in ('07') THEN 1 ELSE NULL END) LEVEL7,"+
            " count(CASE when ACCIDENTGRADE in ('08') THEN 1 ELSE NULL END) LEVEL8"+
            " from bd_inc_equipaccident where to_days(BGNTIME) = to_days(now())";
    //电网事件等级
    public static final String SQL_POWERGRIDACCIDENTDAO_GETACCIDENTGRADE = "select "+
            " count(CASE when ACCIDENTGRADE in ('01','02','03','04') THEN 1 ELSE NULL END) LEVEL4,"+
            " count(CASE when ACCIDENTGRADE in ('05') THEN 1 ELSE NULL END) LEVEL5,"+
            " count(CASE when ACCIDENTGRADE in ('06') THEN 1 ELSE NULL END) LEVEL6,"+
            " count(CASE when ACCIDENTGRADE in ('07') THEN 1 ELSE NULL END) LEVEL7,"+
            " count(CASE when ACCIDENTGRADE in ('08') THEN 1 ELSE NULL END) LEVEL8"+
            " from bd_inc_powergridaccident where to_days(BGNTIME) = to_days(now())";
    //人身事件等级
    public static final String SQL_PERSONALACCIDENTDAO_GETACCIDENTGRADE = "select "+
            " count(CASE when ACCIDENTGRADE in ('01','02','03','04') THEN 1 ELSE NULL END) LEVEL4,"+
            " count(CASE when ACCIDENTGRADE in ('05') THEN 1 ELSE NULL END) LEVEL5,"+
            " count(CASE when ACCIDENTGRADE in ('06') THEN 1 ELSE NULL END) LEVEL6,"+
            " count(CASE when ACCIDENTGRADE in ('07') THEN 1 ELSE NULL END) LEVEL7,"+
            " count(CASE when ACCIDENTGRADE in ('08') THEN 1 ELSE NULL END) LEVEL8"+
            " from bd_inc_personalaccident where to_days(BGNTIME) = to_days(now())";
    //信息事件等级
    public static final String SQL_INFORMACCIDENTDAO_GETACCIDENTGRADE = "select "+
            " count(CASE when ACCIDENTGRADE in ('01','02','03','04') THEN 1 ELSE NULL END) LEVEL4,"+
            " count(CASE when ACCIDENTGRADE in ('05') THEN 1 ELSE NULL END) LEVEL5,"+
            " count(CASE when ACCIDENTGRADE in ('06') THEN 1 ELSE NULL END) LEVEL6,"+
            " count(CASE when ACCIDENTGRADE in ('07') THEN 1 ELSE NULL END) LEVEL7,"+
            " count(CASE when ACCIDENTGRADE in ('08') THEN 1 ELSE NULL END) LEVEL8"+
            " from bd_inc_informaccident where to_days(BGNTIME) = to_days(now())";

    //按单位统计
    public static final String SQL_GETACCIDENTGRIDCROP = "SELECT\n"+
            "    b.EQUIPACCIDENT_cnt,\n"+
            "    c.POWERGRIDACCIDENT_cnt,\n"+
            "    d.PERSONALACCIDENT_cnt,\n"+
            "    e.INFORMACCIDENT_cnt,\n"+
            "    a.column_type_name DATAREPORT_ORG,\n"+
            "    a.column_type_code DATAREPORT_ORG_ID\n"+
            "    FROM\n"+
            "            ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) a\n"+
            "    left join (select GRIDCORP ,count(OBJID) EQUIPACCIDENT_cnt  from BD_INC_EQUIPACCIDENT WHERE to_days( BGNTIME ) = to_days( now( ) ) group by GRIDCORP) b on a.column_type_code = b.GRIDCORP\n"+
            "    left join (select GRIDCROP ,count(PKID) POWERGRIDACCIDENT_cnt  from BD_INC_POWERGRIDACCIDENT WHERE to_days( BGNTIME ) = to_days( now( ) ) group by GRIDCROP) c on a.column_type_code = c.GRIDCROP\n"+
            "    left join (select GRIDCROP ,count(OBJID) PERSONALACCIDENT_cnt  from BD_INC_PERSONALACCIDENT WHERE to_days( BGNTIME ) = to_days( now( ) ) group by GRIDCROP) d on a.column_type_code = d.GRIDCROP\n"+
            "    left join (select GRIDCROP ,count(OBJID) INFORMACCIDENT_cnt  from BD_INC_INFORMACCIDENT WHERE to_days( BGNTIME ) = to_days( now( ) ) group by GRIDCROP) e on a.column_type_code = e.GRIDCROP\n"+
            "    GROUP BY\n"+
            "    a.column_type_name,\n"+
            "    a.column_type_code\n"+
            "    ORDER BY\n"+
            "    a.column_type_code +0";

    //按电压等级统计
    public static final String SQL_GETACCIDENTVOLTAGELEVEL = "select\n" +
            "\tb.EQUIPACCIDENT_cnt, \n" +
            "\tc.POWERGRIDACCIDENT_cnt,\n" +
            "\ta.column_type_name DATAREPORT_ORG,  \n" +
            "\ta.column_type_code DATAREPORT_ORG_ID \n" +
            "\tfrom\n" +
            "\t( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'VOLTAGE_LEVEL' ) a \n" +
            "\tleft join (select VOLTAGE_CAPACITY ,count(OBJID) EQUIPACCIDENT_cnt from bd_inc_equipaccident where to_days( BGNTIME ) = to_days( now( )) group by VOLTAGE_CAPACITY)b on a.column_type_code = b.VOLTAGE_CAPACITY\n" +
            "\tleft join (select VOLTAGE_CAPACITY, count(PKID) POWERGRIDACCIDENT_cnt from bd_inc_powergridaccident where to_days( BGNTIME ) = to_days( now( )) group by VOLTAGE_CAPACITY)c on a.column_type_code = c.VOLTAGE_CAPACITY\n" +
            "\tgroup by\n" +
            "\ta.column_type_name,\n" +
            "\ta.column_type_code\n" +
            "\torder by\n" +
            "\ta.column_type_code +0";

    //按事故原因TOP10统计
    //设备事件
    public static final String SQL_EQUIPACCIDENTDAO_GETACCIDENTDESC = "select \n" +
            " b.EQUIPACCIDENT_cnt,\n" +
            " a.column_type_name DATAREPORT_ORG,  \n" +
            " a.column_type_code DATAREPORT_ORG_ID\n" +
            "\tfrom\n" +
            "\t(select * from srp_risk_sys_tab where column_code = 'REASON') a\n" +
            "\tleft join (select REASON,count(OBJID) EQUIPACCIDENT_cnt from bd_inc_equipaccident where to_days( BGNTIME ) = to_days( now( )) group by REASON)b on a.column_type_code = b.REASON\n" +
            "\tgroup by\n" +
            "\ta.column_type_name,\n" +
            "\ta.column_type_code\n" +
            "\torder by EQUIPACCIDENT_cnt DESC limit 10";
    //电网事件
    public static final String SQL_POWERGRIDACCIDENTDAO_GETACCIDENTDESC = "select \n" +
            " b.POWERGRIDACCIDENT_cnt,\n" +
            " a.column_type_name DATAREPORT_ORG,  \n" +
            " a.column_type_code DATAREPORT_ORG_ID\n" +
            "\tfrom\n" +
            "\t(select * from srp_risk_sys_tab where column_code = 'REASON') a\n" +
            "\tleft join (select REASON,count(PKID) POWERGRIDACCIDENT_cnt from bd_inc_powergridaccident where to_days( BGNTIME ) = to_days( now( )) group by REASON)b on a.column_type_code = b.REASON\n" +
            "\tgroup by\n" +
            "\ta.column_type_name,\n" +
            "\ta.column_type_code\n" +
            "\torder by POWERGRIDACCIDENT_cnt DESC limit 10";
    //人身事件
    public static final String SQL_PERSONALACCIDENTDAO_GETACCIDENTDESC = "select \n" +
            " b.PERSONALACCIDENT_cnt,\n" +
            " a.column_type_name DATAREPORT_ORG,  \n" +
            " a.column_type_code DATAREPORT_ORG_ID\n" +
            "\tfrom\n" +
            "\t(select * from srp_risk_sys_tab where column_code = 'DIRECTREASON') a\n" +
            "\tleft join (select DIRECTREASON,count(OBJID) PERSONALACCIDENT_cnt from bd_inc_personalaccident where to_days( BGNTIME ) = to_days( now( )) group by DIRECTREASON)b on a.column_type_code = b.DIRECTREASON\n" +
            "\tgroup by\n" +
            "\ta.column_type_name,\n" +
            "\ta.column_type_code\n" +
            "\torder by PERSONALACCIDENT_cnt DESC limit 10";
    //信息事件
    public static final String SQL_INFORMALACCIDENTDAO_GETACCIDENTDESC = "select \n" +
            " b.INFORMACCIDENT_cnt,\n" +
            " a.column_type_name DATAREPORT_ORG,  \n" +
            " a.column_type_code DATAREPORT_ORG_ID\n" +
            "\tfrom\n" +
            "\t(select * from srp_risk_sys_tab where column_code = 'REASON') a\n" +
            "\tleft join (select IREASON,count(OBJID) INFORMACCIDENT_cnt from bd_inc_informaccident where to_days( BGNTIME ) = to_days( now( )) group by IREASON)b on a.column_type_code = b.IREASON\n" +
            "\tgroup by\n" +
            "\ta.column_type_name,\n" +
            "\ta.column_type_code\n" +
            "\torder by INFORMACCIDENT_cnt DESC limit 10";


    //---------------------------------------------------数据整合-------------------------------------------------------------------------------------
    public static final String SQL_DATAINFODAO_GETDATAINFOLOADCNT = "select count(1) cnt from datainfo_load where 1=1 %param%";
    public static final String SQL_DATAINFODAO_GETDATAINFOLOAD = "select \n" +
            " b.INSTYPE_ID,\n" +
            " b.JOBNAME,\n" +
            " b.SERVER_IP,\n" +
            " b.DATA_TYPE,\n" +
            "a.column_type_name,\n" +
            " b.OPERATION_INFO,\n" +
            " b.EXECUTION_DATETIME,\n" +
            " b.EXECUTION_TIME,\n" +
            " b.LOG_INFO \n" +
            " \n" +
            "\tfrom\n" +
            "\t(select * from datainfo_load where 1=1 %param%) b\n" +
            "\tleft JOIN (select * from srp_risk_sys_tab where column_code = 'PROCESS_STATE')a on a.column_type_code = b.PROCESS_STATE";

    public static final String SQL_DATAINFODAO_GETDATAINFOCONVERSIONCNT = "select count(1) cnt from datainfo_conversion where 1=1 %param%";
    public static final String SQL_DATAINFODAO_GETDATAINFOCONVERSION = "select \n" +
            " b.JOBNAME,\n" +
            " b.SERVER_IP,\n" +
            " b.DATA_TYPE,\n" +
            "a.column_type_name,\n" +
            " b.OPERATION_INFO,\n" +
            " b.EXECUTION_DATETIME,\n" +
            " b.EXECUTION_TIME,\n" +
            " b.LOG_INFO \n" +
            " \n" +
            "\tfrom\n" +
            "\t(select * from datainfo_conversion where 1=1 %param%) b\n" +
            "\tleft JOIN (select * from srp_risk_sys_tab where column_code = 'PROCESS_STATE')a on a.column_type_code = b.PROCESS_STATE";


    //---------------------------------------------------外包单位作业风险画像-------------------------------------------------------------------------------------
    //外包单位风险画像列表
    public static final String SQL_DATA_GETRISKPORTRAITLIST= "SELECT\n" +
            "\ta.CONSTRUCTION_ORG_ID,\n" +
            "\tb.column_type_name DATAREPORT_ORG,\n" +
            "\ta.CONSTRUCTION_ORG,\n" +
            "\ta.WORK_MANAGER,\n" +
            "\ta.OUTWORKER_NUM\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_kdtb_workplanday WHERE 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) b ON a.DATAREPORT_ORG = b.column_type_code\n";

    public static final String SQL_DATA_GETRISKPORTRAITCNT = "SELECT count(*) cnt FROM srp_work_kdtb_workplanday where 1=1 %param%";
    //------------------------------------------------------单位基本情况------------------------------------------------------------------------------------------
    //承接项目总数查询
    public static final String SQL_DATA_PROJECTMANAGEMENTCNT = "\n" +
            "SELECT count(*) cnt FROM `srp_work_projectmanagement` where 1=1 %param%";

    //承接项目情况查询
    public static final String SQL_DATA_PROJECTMANAGEMENT = "SELECT\n" +
            "\ta.PROJECT_NAME,\n" +
            "\ta.PROJECT_CODE,\n" +
            "\ta.ISSUING_ORG,\n" +
            "\ta.IMPLEMENTATION_SITE,\n" +
            "\ta.CREATE_TIME,\n" +
            "\ta.END_TIME,\n" +
            "\tb.column_type_name STATE_PROJECT\n" +
            "FROM\n" +
            "\t( SELECT * FROM `srp_work_projectmanagement` where 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'STATE_PROJECT' ) b ON a.STATE_PROJECT = b.column_type_code";

    //基本信息查询
    public static final String SQL_DATA_OUTSOURCINGUNIT ="SELECT\n" +
            "\ta.BASICINFO_CONTRACTOR,\n" +
            "\ta.BASICINFO_ESTABLISHDATE,\n" +
            "\ta.BASICINFO_BUSINESSLICENSE,\n" +
            "\ta.PERSONNEL_NUMBER,\n" +
            "\ta.BASICINFO_LEGALREPRESENTATIVE,\n" +
            "\tb.column_type_name DATAREPORT_ORG,\n" +
            "\ta.BASICINFO_REGISTEREDCAPITAL,\n" +
            "\ta.CERTIFICATION_NAME,\n" +
            "\ta.BASICINFO_CONTACTNUMBER,\n" +
            "\ta.BASICINFO_ORGADDRESS \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_outsourcingunit WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) b ON a.DATAREPORT_ORG = b.column_type_code";

    //------------------------------------------------------单位安全资信------------------------------------------------------------------------------------------
    //查询一般违章、严重违章的数量
    public static final String SQL_DATA_VIOLATIONCNTBYLEVEL = "SELECT\n" +
            "\tcount( CASE WHEN VIOLATION_LEVEL = '01' THEN 1 ELSE NULL END ) AS general,\n" +
            "\tcount( CASE WHEN VIOLATION_LEVEL = '02' THEN 1 ELSE NULL END ) AS serious \n" +
            "FROM\n" +
            "\tsrp_work_kdtb_violationfile \n" +
            "WHERE\n" +
            "\t1 = 1 %param%";

    //查询违章数
    public static final String SQL_DATA_VIOLATIONCNT = "SELECT count(*) cnt from srp_work_kdtb_violationfile where 1=1 %param%";

    //查询负面清单数
    public static final String SQL_DATA_NEGATIVELISTCNT = "SELECT count(*) cnt from srp_work_negativelistorg where 1=1 %param%";

    //查询黑名单数
    public static final String SQL_DATA_BLACKLISTCNT = "SELECT count(*) cnt from srp_work_blacklistorg where 1=1 %param%";

    //查询本年违章情况
    public static final String SQL_DATA_VIOLATION = "SELECT\n" +
            "\tb.column_type_name DATAREPORT_ORG,\n" +
            "\tc.PROJECT_NAME,\n" +
            "\te.column_type_name VIOLATION_LEVEL,\n" +
            "\ta.VIOLATION_CONTENT,\n" +
            "\td.VIOLATION_ORG_POINTS,\n" +
            "\ta.VIOLATION_DATE,\n" +
            "\ta.CHECK_LEVEL,\n" +
            "\ta.VIOLATION_STAFF, \n" +
            "\t(@i:=@i) PERSON_NUM\n" +
            "FROM\n" +
            "\t(SELECT @i:=1) as i,( SELECT * FROM srp_work_kdtb_violationfile WHERE YEAR ( VIOLATION_DATE ) = YEAR ( now( ) ) %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) b ON a.DATAREPORT_ORG = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_work_projectmanagement ) c ON a.PROJECT_CODE = c.PROJECT_CODE\n" +
            "\tLEFT JOIN ( SELECT * FROM SRP_WORK_ORGVIOLATIONPENALTY ) d ON a.VIOLATION_ORG_ID = d.VIOLATION_ORG_ID\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'VIOLATION_LEVEL' ) e ON a.VIOLATION_LEVEL = e.column_type_code\n";

    //查询负面清单
    public static final String SQL_DATA_NEGATIVELIST = "SELECT\n" +
            "\tb.column_type_name DATAREPORT_ORG,\n" +
            "\ta.ITEMS_UNDER,\n" +
            "\ta.PUBLISH_ORG,\n" +
            "\ta.INCLUSION_CONDITION,\n" +
            "\ta.RELEASE_DATE,\n" +
            "\ta.NEGATIVELISTREL_DATE \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_negativelistorg WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) b ON a.DATAREPORT_ORG = b.column_type_code\n";

    //查询黑名单
    public static final String SQL_DATA_BLACKLIST = "SELECT\n" +
            "\tb.column_type_name DATAREPORT_ORG,\n" +
            "\ta.ITEMS_UNDER,\n" +
            "\ta.PUBLISH_ORG,\n" +
            "\ta.INCLUSION_CONDITION,\n" +
            "\tc.column_type_name BLACKLIST_LEVEL,\n" +
            "\ta.RELEASE_DATE,\n" +
            "\ta.BLACKLISTREL_DATE\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_blacklistorg WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) b ON a.DATAREPORT_ORG = b.column_type_code \n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'BLACKLIST_LEVEL') c ON a.BLACKLIST_LEVEL = c.column_type_code";

    //项目安全情况
    public static final String SQL_DATA_SAFETYSITUATION = "SELECT\n" +
            "\td.PROJECT_NAME,\n" +
            "\td.PROJECT_CODE,\n" +
            "\td.ISSUING_ORG,\n" +
            "\td.IMPLEMENTATION_SITE,\n" +
            "\te.column_type_name ACCIDENT_LEVEL,\n" +
            "\td.cnt,\n" +
            "\tf.column_type_name ACCIDENT_HANDLING \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\t*,\n" +
            "\tcount( c.ACCIDENT_LEVEL ) cnt \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\ta.project_name,\n" +
            "\ta.project_code,\n" +
            "\ta.ISSUING_ORG,\n" +
            "\ta.IMPLEMENTATION_SITE,\n" +
            "\tb.ACCIDENT_LEVEL,\n" +
            "\tb.ACCIDENT_HANDLING \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_projectmanagement WHERE 1=1 %param% ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_work_projectaccident ) b ON a.PROJECT_CODE = b.PROJECT_CODE \n" +
            "ORDER BY\n" +
            "\tb.ACCIDENT_LEVEL \n" +
            "\t) c \n" +
            "GROUP BY\n" +
            "\tc.PROJECT_CODE \n" +
            "\t) d\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENT_LEVEL' ) e ON d.ACCIDENT_LEVEL = e.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENT_HANDLING' ) f ON d.ACCIDENT_HANDLING = f.column_type_code";

    //------------------------------------------------------全网信息联动------------------------------------------------------------------------------------------
    //查询准入情况
    public static final String SQL_DATA_ACCESS = "SELECT\n" +
            "\td.column_type_name  DATAREPORT_ORG,\n" +
            "\ta.ACCEPT_ORG,\n" +
            "\ta.BASICINFO_CONTRACTOR,\n" +
            "\ta.REPORT_CARD_BEGINTIME,\n" +
            "\ta.REPORT_CARD_ENDTIME,\n" +
            "\tb.column_type_name ACCESS_STATE,\n" +
            "\tc.column_type_name BREACH_FAITH\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_outsourcingunit WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCESS_STATE' ) b ON a.ACCESS_STATE = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'BREACH_FAITH' ) c ON a.BREACH_FAITH = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) d ON a.DATAREPORT_ORG = d.column_type_code";


    //根据人员ID查询单位ID
    public static final String SQL_DATA_GETORGID = "select ORG_ID from srp_work_staff_org where STAFF_ID = '%param%'";

    //根据单位ID查询单位数量
    public static final String SQL_DATA_ACCESSCNT = "select count(1) from srp_work_outsourcingunit where 1=1 %param%";

    //查询违章情况
    public static final String SQL_DATA_ILLEGALSITUATION = "SELECT\n" +
            "\tc.column_type_name DATAREPORT_ORG,\n" +
            "\ta.ACCEPT_ORG,\n" +
            "\ta.VIOLATION_ORG,\n" +
            "\td.column_type_name VIOLATION_LEVEL,\n" +
            "\ta.VIOLATION_CONTENT,\n" +
            "\tb.VIOLATION_ORG_POINTS\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_kdtb_violationfile WHERE 1 = 1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_work_orgviolationpenalty ) b ON a.VIOLATION_ORG_ID = b.VIOLATION_ORG_ID\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) c ON a.DATAREPORT_ORG = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'VIOLATION_LEVEL' ) d ON a.VIOLATION_LEVEL = d.column_type_code";

    //查询事故情况
    public static final String SQL_DATA_ACCIDENTCONDITIONS = "SELECT\n" +
            "\tg.column_type_name DATAREPORT_ORG,\n" +
            "\td.PROJECT_NAME,\n" +
            "\td.PROJECT_CODE,\n" +
            "\td.cnt,\n" +
            "\te.column_type_name ACCIDENT_LEVEL,\n" +
            "\tf.column_type_name ACCIDENT_HANDLING \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\t*,\n" +
            "\tcount( c.ACCIDENT_LEVEL ) cnt \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\ta.DATAREPORT_ORG,\n" +
            "\ta.project_name,\n" +
            "\ta.project_code,\n" +
            "\tb.ACCIDENT_LEVEL,\n" +
            "\tb.ACCIDENT_HANDLING \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_work_projectmanagement WHERE 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_work_projectaccident ) b ON a.PROJECT_CODE = b.PROJECT_CODE \n" +
            "ORDER BY\n" +
            "\tb.ACCIDENT_LEVEL \n" +
            "\t) c \n" +
            "GROUP BY\n" +
            "\tc.PROJECT_CODE \n" +
            "\t) d\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENT_LEVEL' ) e ON d.ACCIDENT_LEVEL = e.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'ACCIDENT_HANDLING' ) f ON d.ACCIDENT_HANDLING = f.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) g ON d.DATAREPORT_ORG = g.column_type_code";

    //------------------------------------------------------安全能力分析------------------------------------------------------------------------------------------
    //查询故障数
    public static final String SQL_DATA_ACCIDENTCNT = "SELECT\n" +
            "\tCOUNT( * ) cnt \n" +
            "FROM\n" +
            "\tsrp_work_projectaccident \n" +
            "WHERE\n" +
            "\tPROJECT_CODE IN ( SELECT PROJECT_CODE FROM srp_work_projectmanagement WHERE 1=1 %param%)";

    //查询故障评分
    public static final String SQL_DATA_ACCIDENTSCORE = "SELECT BASICINFO_POINTS FROM srp_work_safety_capability where 1=1 %param%";

    //查询违章评分
    public static final String SQL_DATA_VIOLATIONSCORE = "SELECT CERTIFICATE_PHOTO_POINTS FROM srp_work_safety_capability where 1=1 %param%";

    //查询负面清单评分
    public static final String SQL_DATA_NEGATIVELISTSCORE = "SELECT SAFELEARNING_POINTS FROM srp_work_safety_capability where 1=1 %param%";

    //查询黑名单评分
    public static final String SQL_DATA_BLACKLISTSCORE = "SELECT SAFETYTEST_POINTS FROM srp_work_safety_capability where 1=1 %param%";

    //---------------------------------------------------安全风险知识图谱-------------------------------------------------------------------------------------
    //------------------------------------------------------知识图谱建模------------------------------------------------------------------------------------------
    //查询知识图谱建模
    public static final String SQL_DATA_MODELING = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\ta.TYPE_ID,\n" +
            "\tb.column_type_name TYPE_NAME,\n" +
            "\ta.TYPE_CODE,\n" +
            "\ta.TYPE_ICON,\n" +
            "\ta.TYPE_DESCRIBE,\n" +
            "\ta.UPDATE_TIME \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_modeling ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) b ON a.TYPE_NAME = b.column_type_code \n" +
            "\t) c \n" +
            "WHERE 1=1 %param%";

    public static final String SQL_DATA_MODELINGGRAPH = "SELECT * FROM srp_modeling where TYPE_NAME = '%param%'";

    //查询知识图谱建模数量
    public static final String SQL_DATA_GETMODELINGCNT = "SELECT COUNT(*) FROM srp_modeling";

    //查询最大id
    public static final String SQL_DATA_GETMAXTYPEID = "SELECT MAX(TYPE_ID) FROM srp_modeling";

    //删除知识图谱建模
    public static final String SQL_DATA_DELETEMODELING = "DELETE FROM srp_modeling where 1=1 %param%";
    //删除关系表中对应建模ID的数据
    public static final String SQL_DATA_DELETERELATIONINFO = "DELETE FROM srp_bd_kg_relationinfo where %param%";
    //通过建模编码查询该建模下的所有实例ID
    public static final String SQL_DATA_GETINSTANCEID = "SELECT INSTANCE_ID FROM srp_bd_kg_instanceinfo WHERE INSTANCE_SOURCE = '%param%'";

    //删除实例关系信息表中对应建模ID的数据
    public static final String SQL_DATA_DELETEINSTANCERELATIONINFO = "DELETE FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID in (%param%) or TARGET_INSTANCE_ID in(%param%)";

    //新增知识图谱建模
    public static final String SQL_DATA_INSERTMODELING = "insert into srp_modeling(TYPE_NAME,TYPE_CODE,TYPE_ICON,TYPE_DESCRIBE,UPDATE_TIME) VALUES (%param%)";

    //编辑知识图谱建模
    public static final String SQL_DATA_UPDATEMODELING = "UPDATE srp_modeling SET %param% WHERE %param1%";

    //根据类别名称查询编码
    public static final String SQL_DATA_GETTYPECODEBYID = "SELECT TYPE_CODE FROM srp_modeling WHERE TYPE_NAME = '%param%'";

    //根据建模ID查询编码
    public static final String SQL_DATA_GETTYPECODE = "SELECT TYPE_CODE FROM srp_modeling WHERE TYPE_ID = '%param%'";
    //------------------------------------------------------知识获取------------------------------------------------------------------------------------------
    //查询关系列表

    public static final String SQL_DATA_RELATIONINFO = "SELECT\n" +
            "\ta.RELATION_ID,\n" +
            "\tb.column_type_name RELATION_NAME,\n" +
            "\ta.RELATION_CODE,\n" +
            "\td.column_type_name SOURCE_TYPE,\n" +
            "\te.column_type_name TARGET_TYPE,\n" +
            "\tc.column_type_name RELATION_TYPE,\n" +
            "\ta.RELATION_DESCRIBE,\n" +
            "\ta.PROCESSENDTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_relationinfo where 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_NAME' ) b ON a.RELATION_NAME = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_TYPE' ) c ON a.RELATION_TYPE = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) d ON a.SOURCE_TYPE = d.column_type_code \n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) e ON a.TARGET_TYPE = e.column_type_code ";

    //根据建模ID查询关系
    public static final String SQL_DATA_RELATIONINFOBYMODELINGID = "SELECT\n" +
            "\ta.RELATION_ID,\n" +
            "\td.column_type_name SOURCE_TYPE,\n" +
            "\tb.column_type_name RELATION_NAME,\n" +
            "\te.column_type_name TARGET_TYPE,\n" +
            "\ta.TARGET_TYPE TYPE_NAME\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_relationinfo where 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_NAME' ) b ON a.RELATION_NAME = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_TYPE' ) c ON a.RELATION_TYPE = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) d ON a.SOURCE_TYPE = d.column_type_code \n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) e ON a.TARGET_TYPE = e.column_type_code ";

    //查询关系表内数据数量
    public static final String SQL_DATA_GETRELATIONINFOCNT = "SELECT count(*) FROM srp_bd_kg_relationinfo";

    //根据编码查询关系id
    public static final String SQL_DATA_FINDELATIONSHIPID = "SELECT RELATION_ID FROM srp_bd_kg_relationinfo WHERE RELATION_CODE = 'param'";

    //根据编码查询知识建模id
    public static final String SQL_DATA_KNOWLEDGE_MODELING = "SELECT TYPE_ID FROM srp_modeling WHERE TYPE_CODE = 'param'";
    //查询关系表最大主键ID
    public static final String SQL_DATA_GETMAXRELATIONID = "SELECT max(RELATION_ID) FROM srp_bd_kg_relationinfo";

    //通过实例编码查询ID
    public static final String SQL_DATA_GETIDBYCODE = "SELECT INSTANCE_ID FROM srp_bd_kg_instanceinfo WHERE INSTANCE_CODE = '%param%'";

    //通过关系ID查关系名称
    public static final String SQL_DATA_GETRELATIONNAME = "SELECT RELATION_NAME FROM srp_bd_kg_relationinfo WHERE RELATION_ID = '%param%'";

    //通过源类别查询建模ID
    public static final String SQL_DATA_GETTYPEIDBYTYPENAME = "SELECT TYPE_ID FROM srp_modeling WHERE 1=1 %param%";

    //新增关系
    public static final String SQL_DATA_INSERTRELATIONINFO = "INSERT INTO srp_bd_kg_relationinfo ( MODELING_GRAPH_ID, RELATION_NAME, SOURCE_TYPE, TARGET_TYPE, RELATION_TYPE, RELATION_DESCRIBE, RELATION_CODE, PROCESSENDTIME )\n" +
            "VALUES\n" +
            "\t( %param%, NOW() )";



    //查询实例信息列表
    public static final String SQL_DATA_INSTANCEINFO = "SELECT INSTANCE_ID,INSTANCE_NAME,INSTANCE_CODE,INSTANCE_DESCRIBE,UPDATE_TIME FROM srp_bd_kg_instanceinfo %param%";
    //根据实例id获取实例名称
    public static final String SQL_DATA_EXAMPLE = "SELECT INSTANCE_NAME FROM srp_bd_kg_instanceinfo WHERE INSTANCE_ID = 'param';";


    //查询未被选择的实例
    public static final String SQL_DATA_NOSELECT = "select a.INSTANCE_ID,a.TYPE_NAME,a.INSTANCE_NAME from (select * from srp_bd_kg_instanceinfo where TYPE_NAME = '%param%') a where a.INSTANCE_ID not in(%param1%)";

    //通过ID查询实例名称
    public static final String SQL_DATA_GETINSTANCENAMEBYID = "SELECT INSTANCE_NAME FROM srp_bd_kg_instanceinfo WHERE INSTANCE_ID = '%param%'";
    //通过ID实例查询
    public static final String SQL_DATA_EXAMPLE_ALL = "SELECT INSTANCE_ID,INSTANCE_NAME,INSTANCE_NAME,INSTANCE_DESCRIBE,INSTANCE_SOURCE,INSTANCE_CODE,UPDATE_TIME FROM srp_bd_kg_instanceinfo WHERE INSTANCE_ID = 'param';";


    //查询实例信息列表数量
    public static final String SQL_DATA_GETINSTANCEINFOCNT = "SELECT COUNT(*) FROM srp_bd_kg_instanceinfo  %param%";

    //查询实例表中最大ID
    public static final String SQL_DATA_GETMAXINSTANCEID = "SELECT MAX(INSTANCE_ID) FROM srp_bd_kg_instanceinfo" ;

    //通过建模ID查询类别名称
    public static final String SQL_DATA_GETTYPENAMEBYID = "SELECT TYPE_NAME FROM srp_modeling WHERE TYPE_ID = '%param%'";

    //新增实例
    public static final String SQL_DATA_INSERTINSTANCEINFO = "INSERT INTO srp_bd_kg_instanceinfo(TYPE_NAME,INSTANCE_NAME,INSTANCE_DESCRIBE,INSTANCE_SOURCE,INSTANCE_CODE,UPDATE_TIME) VALUES(%param%,NOW())";

    //新增实例关系
    public static final String SQL_DATA_INSERTINSTANCERELATIONINFO = "INSERT INTO srp_bd_kg_instancerelationinfo(SOURCE_INSTANCE_ID,SOURCE_INSTANCE_NAME,TARGET_INSTANCE_ID,TARGET_INSTANCE_NAME,RELATION_ID,RELATION_NAME,UPDATE_TIME) VALUES (%param%,NOW())";

    //删除实例
    public static final String SQL_DATA_DELETEINSTANCEINFO = "DELETE FROM srp_bd_kg_instanceinfo where %param%";


    //生成实例关系拓扑图
    //根据实例名称查询实例id
    public static final String SQL_DATA_GETINSTANCERELATIONINFOBYIDNAME="SELECT " +
            "INSTANCE_ID FROM srp_bd_kg_instanceinfo WHERE INSTANCE_NAME = %param%";

    //根据实例ID查询实例关系
    public static final String SQL_DATA_GETINSTANCERELATIONINFOBYID = "SELECT\n" +
            "\ta.SOURCE_INSTANCE_NAME,\n" +
            "\tb.column_type_name RELATION_NAME,\n" +
            "\ta.TARGET_INSTANCE_NAME,\n" +
            "\ta.TARGET_INSTANCE_ID\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = %param% ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_NAME' ) b ON a.RELATION_NAME = b.column_type_code";

    //根据目标实例ID查询目标实例之间的关系
    public static final String SQL_DATA_GETBRANCH = "SELECT\n" +
            "\ta.SOURCE_INSTANCE_NAME,\n" +
            "\tb.column_type_name RELATION_NAME,\n" +
            "\ta.TARGET_INSTANCE_NAME \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID IN ( %param% ) AND TARGET_INSTANCE_ID IN ( %param% ) ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_NAME' ) b ON a.RELATION_NAME = b.column_type_code";

    //删除未被选中的实例
    public static final String SQL_DELETE_NOSELECTID = "DELETE FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = %param% and TARGET_INSTANCE_ID not in(%param1%) ";

    //通过源实例ID查询是否存在该数据
    public static final String SQL_GETCOUNTBYID = "SELECT COUNT(1) FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = %param% and TARGET_INSTANCE_ID = %param1%";
    //------------------------------------------------------知识融合---------------------------------------------------------------
  /*  public static final String SQL_DATA_KNOWLWDGEFUSION = "SELECT\n" +
            "\tb.column_type_name TYPE,\n" +
            "\tc.column_type_name TYPE_NAME,\n" +
            "\ta.UPDATE_TIME,\n" +
            "\ta.NUMBER_DATA_UPDATE,\n" +
            "\ta.UPDATE_PREVIOUS,\n" +
            "\ta.UPDATE_AFTER,\n" +
            "\ta.STORAGE_LOCATION \n" +
            "FROM\n" +
            "\t( SELECT * FROM `srp_bd_kg_knowlwdge_fusion` ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE' ) b ON a.TYPE = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME') c ON a.TYPE_NAME = c.column_type_code";*/

    //------------------------------------------------------知识图谱应用---------------------------------------------------------------
    public static final String SQL_DATA_KNOWLWDGEAPPLICATION = "SELECT sum(TYPE_NAME) ,sum(TYPE_ID),sum(TYPE),sum(NUMBER_DATA_UPDATE),sum(UPDATE_PREVIOUS),sum(UPDATE_AFTER),sum(STORAGE_LOCATION) FROM srp_bd_kg_knowlwdge_application ";


    //--------------------------------------------------全网安全工器具预警及分析----------------------------------------------
    //-------------------------------------------------------数据统计---------------------------------------------------------
    //按照类型统计数量
    public static final String SQL_DATA_TOOLCNTBYTYPE = "SELECT\n" +
            "\tb.column_type_name TOOL_TYPE,\n" +
            "\ta.CNT \n" +
            "FROM\n" +
            "\t( SELECT TOOL_TYPE, COUNT( 1 ) CNT FROM srp_ins_basic_kdtb_info GROUP BY TOOL_TYPE ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TOOL_TYPE' ) b ON a.TOOL_TYPE = b.column_type_code";

    //工器具采购曲线
    public static final String SQL_DATA_PURPLAN = "SELECT\n" +
            "\'%param1%' time,\n" +
            "\tcount( CASE WHEN TOOL_TYPE = '0' THEN 1 ELSE NULL END ) as protective ,\n" +
            "\tcount( CASE WHEN TOOL_TYPE = '1' THEN 1 ELSE NULL END ) as ascend,\n" +
            "\tcount( CASE WHEN TOOL_TYPE = '2' THEN 1 ELSE NULL END ) as insulation \n" +
            "FROM\n" +
            "\t%param% \n" +
            "WHERE\n" +
            "\tdate_format(CREATE_TIME,'%Y-%m') = '%param1%' ";


    public static final String SQL_DATA_TESTDETAIL = "SELECT\n" +
            "\'%param1%' time,\n" +
            "\tcount( CASE WHEN b.TOOL_TYPE = '0' THEN 1 ELSE NULL END ) AS protective,\n" +
            "\tcount( CASE WHEN b.TOOL_TYPE = '1' THEN 1 ELSE NULL END ) AS ascend,\n" +
            "\tcount( CASE WHEN b.TOOL_TYPE = '2' THEN 1 ELSE NULL END ) AS insulation \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_ins_testdetail_kdtb_info ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_ins_basic_kdtb_info ) b ON a.srp_ins_basic_info_ID = b.INSBASICINFO_ID \n" +
            "WHERE\n" +
            "\tdate_format( a.CREATE_TIME, '%Y-%m' ) = '%param1%' ";

    //------------------------------------------------------实时预警----------------------------------------------------
    public static final String SQL_DATA_REALWARNING = "SELECT\n" +
            "\tc.column_type_name WARING_LEVEL,\n" +
            "\te.column_type_name DATAREPORT_ORG,\n" +
            "\ta.MANAGEMENT_CODE,\n" +
            "\tb.column_type_name TOOL_TYPE,\n" +
            "\ta.NAME,\n" +
            "\td.column_type_name INS_STATE \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_ins_basic_kdtb_info WHERE 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TOOL_TYPE' ) b ON a.TOOL_TYPE = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'WARING_LEVEL' ) c ON a.WARING_LEVEL = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'INS_STATE' ) d ON a.INS_STATE = d.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) e ON a.DATAREPORT_ORG = e.column_type_code";

    public static final String SQL_DATA_REALWARNINGCNT = "SELECT count(*) cnt FROM srp_ins_basic_kdtb_info WHERE 1=1 %param%";

    //查询生产日期和保质期
    public static final String SQL_DATA_DATEOFMANUFACTURE = "SELECT INS_RELEASEDATE,INS_SHELFLIFE FROM srp_ins_basic_kdtb_info WHERE 1=1 %param%";

    //-----------------------------------------------------供应商评价---------------------------------------------------
    //查询评价TOP 10 （BOTTOM 10 替换%param%为desc）
    public static final String SQL_DATA_GETTENGRADE = "SELECT\n" +
            "\tb.MAN_NAME,\n" +
            "\tROUND( a.fail / a.num * 100, 1 ) percent \n" +
            "FROM\n" +
            "\t( SELECT MAN_CODE, sum( SAM_FAIL_NUM ) fail, sum( SAM_NUM ) num FROM srp_ins_accept_info GROUP BY MAN_CODE ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_ins_man_info ) b ON a.MAN_CODE = b.MAN_CODE \n" +
            "ORDER BY\n" +
            "\tpercent %param%\n" +
            "\tLIMIT 10";

    //查询评价列表
    public static final  String SQL_DATA_GETGRADELIST = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "SELECT\n" +
            "\tc.column_type_name DATAREPORT_ORG,\n" +
            "\tb.MAN_NAME,\n" +
            "\ta.MAN_CODE,\n" +
            "\tROUND( a.fail / a.num * 100, 1 ) percent \n" +
            "FROM\n" +
            "\t( SELECT *, sum( SAM_FAIL_NUM ) fail, sum( SAM_NUM ) num FROM srp_ins_accept_info GROUP BY MAN_CODE ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_ins_man_info ) b ON a.MAN_CODE = b.MAN_CODE\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG' ) c ON a.DATAREPORT_ORG = c.column_type_code \n" +
            "WHERE\n" +
            "\t1 = 1  %param%\n" +
            "\t) c \n" +
            "ORDER BY\n" +
            "\tc.percent %param1%";

    //查询评价列表的数据数量
    public static final  String SQL_DATA_GETGRADELISTCNT = "SELECT\n" +
            "\tcount( * ) \n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_ins_accept_info GROUP BY MAN_CODE ) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_ins_man_info ) b ON a.MAN_CODE = b.MAN_CODE \n" +
            "WHERE\n" +
            "\t1 = 1 %param%";

    //---------------------------------------------------安全风险知识图谱-------------------------------------------------------------------------------------
    //------------------------------------------------------知识图谱建模------------------------------------------------------------------------------------------
    //查询知识图谱建模

    //通过实例ID查询实例详情
    public static final String SQL_DATA_GETINSTANCEINFOBYID = "SELECT\n" +
            "\ta.INSTANCE_ID,\n" +
            "\ta.INSTANCE_NAME,\n" +
            "\ta.INSTANCE_CODE,\n" +
            "\ta.INSTANCE_DESCRIBE,\n" +
            "\tb.column_type_name TYPE_NAME\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_instanceinfo WHERE INSTANCE_ID = '%param%') a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) b ON a.TYPE_NAME = b.column_type_code";
    //通过实例ID和关系ID查询实例关系的数量
    public static final String SQL_DATA_GETINSTANCERELATIONINFOCNT = "SELECT count(1) cnt FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = '%param%' AND RELATION_ID = '%param1%'";
    //通过实例ID和关系ID查询实例关系
    public static final String SQL_DATA_GETINSTANCERELATIONINFO = "SELECT TARGET_INSTANCE_ID INSTANCE_ID,TARGET_INSTANCE_NAME INSTANCE_NAME,RELATION_ID FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = '%param%' AND RELATION_ID = '%param1%'";
    //编辑实例基本属性
    public static final String SQL_DATA_UPDATEINSTANCEINFO = "UPDATE srp_bd_kg_instanceinfo SET %param% WHERE %param1% ";
    //编辑实例关联属性内的信息
    public static final String SQL_DATA_UPDATESOURCE = "update srp_bd_kg_instancerelationinfo set SOURCE_INSTANCE_NAME = '%param%' where SOURCE_INSTANCE_ID = '%param1%'";
    public static final String SQL_DATA_UPDATETARGET = "update srp_bd_kg_instancerelationinfo set TARGET_INSTANCE_NAME = '%param%' where TARGET_INSTANCE_ID = '%param1%'";

    //根据实例ID删除实例关系
    //public static final String SQL_DATA_DELETEINSTANCERELATIONINFOBYID = "DELETE FROM srp_bd_kg_instancerelationinfo WHERE SOURCE_INSTANCE_ID = '%param%'";
    //通过
  //-----------------------------------------------------查询关系数据---------------------------------------------------
    //查询表数据关系表数据
    public static final String SQL_SRP_BD_KG_RELATIONINFO = "SELECT\n" +
            "\ta.RELATION_ID,\n" +
            "\ta.MODELING_GRAPH_ID,\n" +
            "\tb.column_type_name RELATION_NAME,\n" +
            "\ta.RELATION_CODE,\n" +
            "\td.column_type_name SOURCE_TYPE,\n" +
            "\te.column_type_name TARGET_TYPE,\n" +
            "\tc.column_type_name RELATION_TYPE,\n" +
            "\ta.RELATION_DESCRIBE,\n" +
            "\ta.PROCESSENDTIME\n" +
            "FROM\n" +
            "\t( SELECT * FROM srp_bd_kg_relationinfo where 1=1 %param%) a\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_NAME' ) b ON a.RELATION_NAME = b.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'RELATION_TYPE' ) c ON a.RELATION_TYPE = c.column_type_code\n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) d ON a.SOURCE_TYPE = d.column_type_code \n" +
            "\tLEFT JOIN ( SELECT * FROM srp_risk_sys_tab WHERE column_code = 'TYPE_NAME' ) e ON a.TARGET_TYPE = e.column_type_code ";
    ///查询关系数据库中的编号
    public static final String SQL_SRP_BD_KG_RELATIONINFOCOUNT = "select count(RELATION_CODE) from srp_bd_kg_relationinfo";
    ///查询关系数据库中的总条数
    public static final String SQL_SRP_BD_KG_RELATIONINFOCOUNTS = "select count(*) from srp_bd_kg_relationinfo";
    ///查询实例数据库中的总条数
    public static final String SQL_SRP_BD_KG_KNOWLWDGE_FUSION = "select count(*) from srp_bd_kg_instanceinfo";
    //查询实例编号
    public static final String SQL_SRP_BD_KG_INSTANCEINFO = "select count(INSTANCE_CODE) from srp_bd_kg_instanceinfo";
    //关系文件导入
    public static final String SQL_SRP_BD_KG_RELATIONINFOS = "INSERT INTO srp_bd_kg_relationinfo (\n" +
            "\tMODELING_GRAPH_ID,\n" +
            "\tRELATION_NAME,\n" +
            "\tSOURCE_TYPE,\n" +
            "\tTARGET_TYPE,\n" +
            "\tRELATION_TYPE,\n" +
            "\tRELATION_DESCRIBE,\n" +
            "\tRELATION_CODE\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?\n" +
            "\t)";
    //实例文件导入 TYPE_NAME
    public static final String SQL_SRP_BD_KG_INSTANCEINFOS = "INSERT INTO srp_bd_kg_instanceinfo (\n" +
            "\tTYPE_NAME,\n" +
            "\tINSTANCE_NAME,\n" +
            "\tINSTANCE_DESCRIBE,\n" +
            "\tINSTANCE_SOURCE,\n" +
            "\tINSTANCE_CODE\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?\n" +
            "\t)\n";
    //保存关系到知识表
    public static final String SQL_SRP_BD_KG_KNOWLWDGE_FUSIONI ="INSERT INTO SRP_BD_KG_KNOWLWDGE_FUSION (\n" +
            "\tTYPE,\n" +
            "\tNUMBER_DATA_UPDATE,\n" +
            "\tUPDATE_PREVIOUS,\n" +
            "\tUPDATE_AFTER,\n" +
            "\tSTORAGE_LOCATION\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?,\n" +
            "\t\t?\n" +
            "\t)";

    //保存到实例表
    public static final String SQL_SRP_BD_KG_Example ="INSERT INTO srp_bd_kg_instanceinfo(TYPE_NAME,INSTANCE_NAME,INSTANCE_DESCRIBE,INSTANCE_SOURCE,INSTANCE_CODE,UPDATE_TIME)\n" +
            "VALUES(?,?,?,?,?,NOW())";
    //查询知识融合列表
    public static final String SQL_SRP_BD_KG_KNOWLWDGE_FUSIONS = "select count(1) cnt from srp_bd_kg_knowlwdge_fusion where 1=1 %param%";
    //查询知识融合列表
    public static final String SQL_SRP_BD_KG_KNOWLWDGE_FUSIONSA = "select * from srp_bd_kg_knowlwdge_fusion where 1=1 %param%";


    //------------------------------------------------------导入Excel模板-------------------------------------------------
    //获取表内数量
    public static final String SQL_DATA_GETTABLECOUNT = "select count(1) cnt from %param%";

    //获取表中最大主键ID
    public static final String SQL_DATA_GETMAXPRIMARY = "select max(%param1%) from %param%";

    //获取表的主键信息
    public static final String SQL_DATA_GETTABLEPRIMARY = "SELECT\n" +
            "\tk.column_name \n" +
            "FROM\n" +
            "\tinformation_schema.table_constraints t\n" +
            "\tJOIN information_schema.key_column_usage k USING ( constraint_name, table_schema, table_name ) \n" +
            "WHERE\n" +
            "\tt.constraint_type = 'PRIMARY KEY' \n" +
            "\tAND t.table_schema = '%param1%' \n" +
            "\tAND t.table_name = '%param%'";

    //插入数据
    public static final String SQL_DATA_INSERTBYEXCEL = "insert into %param1% VALUES (%param%)";

    //保存系统日志列表
    public static final String SQL_SYS_INSSERTLOG ="INSERT INTO sys_log(ID,LOG_CONTENT,MODULE_NAME,LOG_IP,OPERATION_TYPE,LOG_ON,LOG_USER_NAME,LOG_LEVEL,DATA_TYPE,ERROR_TYPE,LOG_TIME)\n" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,NOW());";

    //根据id查询日志信息
    public static final String SQL_SYS_FINDLOG ="SELECT * FROM sys_log WHERE %param%";
    //根据id查询日志信息数量
    public static final String SQL_SYS_FINDLOGCUT ="SELECT count(*) FROM sys_log WHERE %param%";
    //插入当前ip
    public static final String SQL_DATA_INSERTIp ="INSERT INTO datainfo_conversion (SERVER_IP) VALUES (?)";
    //省份查询
    public static final String SQL_SELECT_SQR_RISK_SYS_TAB ="SELECT * FROM  srp_risk_sys_tab WHERE column_code = 'DATAREPORT_ORG'";



}