package com.msb.poitest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用poi读取exl表格数据
 */
public class ReadDamo {
    public static void main(String[] args) {
        try {
            List<Product> list = read("D:\\ideaxlsx\\a.xlsx");
            for(Product product :list){
                System.out.println(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Product> read(String path) throws Exception{
        List<Product> products = new ArrayList<>();
        //1.创建输入流
        FileInputStream fileInputStream = new FileInputStream(path);
        //2.在输入流中获取工作薄(excel表)
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        //3.在工作薄中获取工作表
        Sheet sheet = workbook.getSheetAt(0);
        //获取工作表中的行数（有数据的）
        int rowNum = sheet.getPhysicalNumberOfRows();
        //遍历行数,因为第一行数据不需要，所以要跳过第一行
        for (int i=1;i < rowNum;i++){
        //5.获取第一行以外的所有的行
            Row row = sheet.getRow(i);
            if(row !=null){
                //保存每条数据的集合
                List<String> list = new ArrayList<>();
                for(Cell cell : row){
                        if(cell !=null){
                            //把单元格中所有的数据设置为String
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            //获取所有单元格中的数据
                             String value = cell.getStringCellValue();
                            //将每一个单元格数据保存到集合中
                            if(value !=null && value != ""){
                                list.add(value);
                            }
                        }
                }
                //把获取的每一条数据封装成一个product类型
                if(list.size() > 0){
                    Product product = new Product(Integer.parseInt(list.get(0)),list.get(1),Double.parseDouble(list.get(2)),Integer.parseInt(list.get(3)));
                    products.add(product);
                }
            }
        }
        return products;
    }




}
