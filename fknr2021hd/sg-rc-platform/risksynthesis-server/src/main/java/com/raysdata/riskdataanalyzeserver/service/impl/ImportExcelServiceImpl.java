package com.raysdata.riskdataanalyzeserver.service.impl;



import com.raysdata.riskdataanalyzeserver.dao.ImportExcelDao;
import com.raysdata.riskdataanalyzeserver.service.ImportExcelService;
import com.raysdata.riskdataanalyzeserver.utils.Const;
import com.raysdata.riskdataanalyzeserver.utils.ImportExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.sql.Connection;


@Service
@Transactional
public class ImportExcelServiceImpl implements ImportExcelService {

    @Autowired
    ImportExcelDao importExcelDao;

    @Autowired
    DataSource dataSource;

    @Override
    public Object importExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int num = 0;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        InputStream in = null;
        MultipartFile file = multipartRequest.getFile("file");
        try {
            if (file.isEmpty()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        in = file.getInputStream();
        ImportExcelUtil ie = new ImportExcelUtil();
        Workbook work = ie.getWorkbook(in, file.getOriginalFilename());
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        //获取当前数据库连接
        Connection connection = DataSourceUtils.getConnection(dataSource);
        //获取当前数据库名称
        String databaseName = connection.getCatalog();
        //获取第一个sheet页
        sheet = work.getSheetAt(0);
        //获取sheet页的名字（sheet是表的名字）
        String sheetName = sheet.getSheetName();
        short firstCellNum = sheet.getRow(0).getFirstCellNum();
        short lastCellNum = sheet.getRow(0).getLastCellNum();
        //查询表的主键名称（通过数据库名称和表的名称）
        String primaryName = importExcelDao.getPrimaryName(Const.SQL_DATA_GETTABLEPRIMARY.replaceAll("%param%", sheetName).replaceAll("%param1%", databaseName));
        int totalNum = 0;
        int success = 0;
        int fail = 0;
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);

            if(sheet==null){continue;}
            //遍历当前sheet中的所有行
            for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                row = sheet.getRow(j);
                if(row==null||j == 0){continue;}

                /*// 获取主键id
                String primaryId = null;
                //获取表中数据数量
                int count = importExcelDao.getCount(Const.SQL_DATA_GETTABLECOUNT.replaceAll("%param%",sheetName));
                if( count == 0){
                    primaryId = "1";
                }else {
                    //获取表中最大主键id
                    String typeId = importExcelDao.getPrimaryName(Const.SQL_DATA_GETMAXPRIMARY.replaceAll("%param%",sheetName).replaceAll("%param1%",primaryName));
                    Integer a = Integer.parseInt(typeId) + 1;
                    primaryId = a.toString();
                }
                StringBuilder param = new StringBuilder();
                param.append("'").append(primaryId).append("',");*/

                StringBuilder param = new StringBuilder();
                //遍历所有的列
                for (int y = firstCellNum; y < lastCellNum; y++) {
                    cell = row.getCell(y);
                    if(cell == null || ie.getCellValue(cell) == ""){
                        param.append("null,");
                    }else {
                        Object s = ie.getCellValue(cell);
                        param.append("'").append(s.toString()).append("',");
                    }
                }
                String sql = param.substring(0, param.length() - 1);
                //获取主键
                cell = row.getCell(firstCellNum);
                Object id = ie.getCellValue(cell);
                param = new StringBuilder();
                param.append(primaryName).append("= '").append(id).append("'");
                //查询该主键是否存在
                int count = importExcelDao.getCount(Const.SQL_DATA_GETTABLECOUNT.replaceAll("%param%", sheetName).replaceAll("%param1%", param.toString()));
                if(count == 0){
                    //主键不存在，则导入
                    num = importExcelDao.importExcel(Const.SQL_DATA_INSERTBYEXCEL.replaceAll("%param1%", sheetName).replaceAll("%param%", sql));
                    success++;
                }
                totalNum++;
            }
        }
        fail = totalNum - success;
        in.close();
        if(success > 0){
            return "总导入"+totalNum+"条,导入成功"+success+"条,"+"导入失败"+fail+"条";
        }else {
            return "导入失败";
        }
    }
}
