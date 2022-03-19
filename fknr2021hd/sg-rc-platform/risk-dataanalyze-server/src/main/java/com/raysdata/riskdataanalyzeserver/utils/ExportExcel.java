package com.raysdata.riskdataanalyzeserver.utils;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ClassName ExportExcel
 * @Description 导出excel表格
 * @param <T>
 */

public class ExportExcel<T>  {


    /**
     *
     * @param response
     * @param fileName excel 表格名称
     * @param titles excel 第一行标题
     * @param varList excel 表格数据数据   Map中的数据中key名称为var+int i,i从1开始，value 表格数据
     */

    public void export(HttpServletResponse response, String fileName, List<String> titles, List<Map> varList) throws UnsupportedEncodingException {
        String filename=this.data2Str(new Date(), "yyyyMMddHHmmss");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("utf-8"), "iso8859-1")+".xls");
        response.setContentType("application/json;charset=utf-8");

        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
//        HSSFSheet sheet = new HSSFWorkbook().createSheet(fileName);
        // 设置表格默认列宽度为15个字节
        new HSSFWorkbook().createSheet(fileName).setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = new HSSFWorkbook().createCellStyle();
        // 设置这些样式   style.setFillBackgroundColor(HSSFColor.RED.index);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
//        HSSFFont font = new HSSFWorkbook().createFont();
        new HSSFWorkbook().createFont().setColor(HSSFColor.HSSFColorPredefined.VIOLET.getIndex());
        new HSSFWorkbook().createFont().setFontHeightInPoints((short) 12);

        // 生成并设置另一个样式
//        HSSFCellStyle style2 = new HSSFWorkbook().createCellStyle();
        new HSSFWorkbook().createCellStyle().setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        new HSSFWorkbook().createCellStyle().setFillPattern(FillPatternType.SOLID_FOREGROUND);
        new HSSFWorkbook().createCellStyle().setBorderBottom(BorderStyle.THIN);
        new HSSFWorkbook().createCellStyle().setBorderLeft(BorderStyle.THIN);
        new HSSFWorkbook().createCellStyle().setBorderRight(BorderStyle.THIN);
        new HSSFWorkbook().createCellStyle().setBorderTop(BorderStyle.THIN);
        new HSSFWorkbook().createCellStyle().setAlignment(HorizontalAlignment.CENTER);
        new HSSFWorkbook().createCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        HSSFFont font2 = new HSSFWorkbook().createFont();
        // 把字体应用到当前的样式
        new HSSFWorkbook().createCellStyle().setFont(font2);
        // 声明一个画图的顶级管理器
//        HSSFPatriarch patriarch = new HSSFWorkbook().createSheet(fileName).createDrawingPatriarch();
        // 定义注释的大小和位置
//        HSSFComment comment = new HSSFWorkbook().createSheet(fileName).createDrawingPatriarch().createComment(new HSSFClientAnchor(0,
//                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        new HSSFWorkbook().createSheet(fileName).createDrawingPatriarch().createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5)).setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("yang");
        int len = titles.size();
        // 产生表格标题行
        HSSFRow row = new HSSFWorkbook().createSheet(fileName).createRow(0);
        for (short i = 0; i < titles.size(); i++)
        {
//            HSSFCell cell = row.createCell(i);
            row.createCell(i).setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(titles.get(i));
            row.createCell(i).setCellValue(text);
        }
        for(int i=0; i<varList.size(); i++){
//            row=new HSSFWorkbook().createSheet(fileName).createRow(i+1);
//            Map vpd = varList.get(i);
            for(int j=0;j<len;j++){
                String varstr = varList.get(i).get("var"+(j+1)) != null ? String.valueOf(varList.get(i).get("var"+(j+1))) : "";
                new HSSFWorkbook().createSheet(fileName).createRow(i+1).createCell(j).setCellValue(varstr);
            }
        }
        OutputStream out;
        try {
//            out = new FileOutputStream("D:\\关系数据导出.xls");
            out = response.getOutputStream();
            new HSSFWorkbook().write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
//            e.printStackTrace();
            e.getMessage();
        }
    }

    /**
     * 日期格式化
     * @param date
     * @param format
     * @return String
     * @create on 2017-7-11
     */
    public String  data2Str(Date date,String format){
        if(date!=null){
            SimpleDateFormat sdf=new SimpleDateFormat(format);
            return sdf.format(date);
        }else{
            return "";
        }
    }



}