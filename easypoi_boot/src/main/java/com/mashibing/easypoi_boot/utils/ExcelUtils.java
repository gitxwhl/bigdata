package com.mashibing.easypoi_boot.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {

    private static final Logger log=  LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出的文件名
     * @param response     相应对象
     * @param exportParams 导出参数（标题，sheet名称，是否创建表头，表格类型）
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        //导出
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, response, workbook);


    }

    /**
     * 下载
     *
     * @param fileName 文件名
     * @param response
     * @param workbook excle数据对象
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        ServletOutputStream out = null;
        try {
            //设置字符集
            response.setCharacterEncoding("UTF-8");
            //设置下载方式设置文件名
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            //下载文件
            workbook.write(out);
        } catch (Exception e) {
            log.error("导出excel异常：{}",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                out.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
