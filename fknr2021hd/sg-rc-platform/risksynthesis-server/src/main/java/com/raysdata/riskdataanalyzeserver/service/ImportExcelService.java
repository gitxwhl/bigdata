package com.raysdata.riskdataanalyzeserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ImportExcelService {
    Object importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
