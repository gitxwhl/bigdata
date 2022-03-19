package com.raysdata.riskdataanalyzeserver.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface ImportExcelDao {
    int importExcel(String sql);

    //获取主键信息
    String getPrimaryName(String sql);

    //获取表中数据数量
    Integer getCount(String sql);
}
