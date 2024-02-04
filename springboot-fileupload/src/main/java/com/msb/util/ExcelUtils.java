package com.msb.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
//excle  单元格数据类型处理工具类
public class ExcelUtils {

    //处理excle数据类型
    public static Object getValue(Cell cell){
        Object value=null;
        switch (cell.getCellType()){
            case STRING:
                value=cell.getStringCellValue();
                break;
            case BOOLEAN:
                value=cell.getBooleanCellValue();
                break;
            case NUMERIC://数字类型包含日期和普通数字
                if(DateUtil.isCellDateFormatted(cell)){
                    value=cell.getDateCellValue();
                }else {
                    value=cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                    value=cell.getCellFormula();
                    break;
            default:
                break;
        }
        return value;
    }

}
