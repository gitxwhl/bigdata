package com.raysdata.riskdataanalyzeserver.utils;
import com.raysdata.riskdataanalyzeserver.bean.SheetName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportExcelUtil {
    private final static String EXCEL_2003_L =".xls";    //2003- 版本的excel
    private final static String EXCEL_2007_U =".xlsx";   //2007+ 版本的excel

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     * @param in,fileName
     * @return
     * @throws
     */
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Throwable{
        List<List<Object>> list = null;
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in,fileName);
        if(null == work){
            throw new Throwable("创建Excel工作薄为空！");
        }

        if(work != null){
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;

            list = new ArrayList<List<Object>>();
            //遍历Excel中所有的sheet
            List<String> listsheet  = new ArrayList<>();
            SheetName.sheetList=listsheet;
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if(sheet==null){continue;}
                //遍历当前sheet中的所有行
                if(sheet !=null){

                   String sheetName = sheet.getSheetName();
                    listsheet.add(sheetName);


                   int rnum = sheet.getLastRowNum();
                    for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                        row = sheet.getRow(j);
                        if(row==null||row.getFirstCellNum()==j){continue;}
                        //遍历所有的列
                        List<Object> li = new ArrayList<Object>();
                        int ss = row.getLastCellNum();
                        if(ss==4){
                            ss = ss-1;
                        }
                        for (int y = row.getFirstCellNum(); y < ss; y++) {
                            cell = row.getCell(y);
                            li.add(this.getCellValue(cell));
                        }
                        list.add(li);
                    }
                }

            }
            in.close();
        }
        return list;
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Throwable{
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(EXCEL_2003_L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);  //2003-
        }else if(EXCEL_2007_U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);  //2007+
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }






    /**
     * 描述：对表格中数值进行格式化
     * @param cell
     * @return
     */
    public  Object getCellValue(Cell cell){
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }
}
