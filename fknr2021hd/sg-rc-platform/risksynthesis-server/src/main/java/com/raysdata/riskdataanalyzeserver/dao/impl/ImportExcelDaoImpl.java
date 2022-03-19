package com.raysdata.riskdataanalyzeserver.dao.impl;

import com.raysdata.riskdataanalyzeserver.dao.ImportExcelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImportExcelDaoImpl implements ImportExcelDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int importExcel(String sql) {
        return jdbcTemplate.update(sql);
    }

    @Override
    public String getPrimaryName(String sql) {
        return jdbcTemplate.queryForObject(sql,String.class);
    }

    @Override
    public Integer getCount(String sql) {
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }
}
