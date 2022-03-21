package com.canteen.util;


public class ExcelUtil {


    public static boolean isExcelFile(String fileName){
        if (fileName == null || !(isExcel2003(fileName) || isExcel2007(fileName))){
            return false;
        }
        return true;
    }


    public static boolean isExcel2003(String fileName){
        return fileName.matches("^.+\\.(?i)(xls)$");
    }


    public static boolean isExcel2007(String fileName){
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }


    public static boolean isCsv(String fileName){
        return fileName.matches("^.+\\.(?i)(csv)$");
    }

}
