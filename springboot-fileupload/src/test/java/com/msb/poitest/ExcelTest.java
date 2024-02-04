package com.msb.poitest;


import com.msb.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ExcelTest {

    @Test
    public void Test05() throws IOException {
        //读取excle
        Workbook workbook =new XSSFWorkbook("D:\\mb\\员工表 .xlsx");
        //获取工作簿
       Sheet sheet = workbook.getSheetAt(0);
        //获取最后一行
        int lastRowNum= sheet.getLastRowNum();

        for (int i=0;i <= lastRowNum;i++){
            //获取当前行
            Row row = sheet.getRow(i);
            //获取最后一个单元格
            int lastCellNum = row.getLastCellNum();
            StringBuilder sb=new StringBuilder();
            //遍历当前行的每一列
            for (int j=0; j< lastCellNum;j++){
                Cell cell = row.getCell(j);
                Object value = ExcelUtils.getValue(cell);
                sb.append(value + " ");
            }
            System.out.println(sb.toString());
        }

    }
}
