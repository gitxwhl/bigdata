package com.raysdata.riskdataanalyzeserver.utils;


public class Const {

    //------------------------------------------------------导入Excel模板-------------------------------------------------
    //获取表内数量
    public static final String SQL_DATA_GETTABLECOUNT = "select count(1) cnt from %param% where %param1%" ;

    //获取表中最大主键ID
    public static final String SQL_DATA_GETMAXPRIMARY = "select max(CAST(%param1% as SIGNED)) from %param%";

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
}