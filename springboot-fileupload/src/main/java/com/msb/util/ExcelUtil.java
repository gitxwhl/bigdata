//package com.msb.util;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.cglib.beans.BeanMap;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
///**
// * 解析excle表格工具
// */
//public class ExcelUtil {
//    /**
//     * 解析表格方法（通用）
//     *
//     * @param stream 文件输入流
//     * @param clazz  实体类类型
//     * @param <T>    任何数据类型
//     * @return 解析表格的结果
//     */
//    public static <T> List<T> readExcel(FileInputStream stream, Class<T> clazz) throws Exception {
//        //返回结果
//        List<T> result = new ArrayList<>();
//        //从输入流中获取工作薄
//        XSSFWorkbook workbook = new XSSFWorkbook(stream);
//        //在工作薄中获取目标工作表
//        Sheet sheet = workbook.getSheetAt(0);
//        //获取工作表中的行数
//        int rowNum = sheet.getPhysicalNumberOfRows();
//        //获取第一行数据，因为Excel表格中第一行数据包含对应要映射的属性
//        Row row = sheet.getRow(0);
//        //遍历第一行数据，遍历出的数据是当前实体类所需要的新增数据的所有属性，并且把这些数据放入到key集合中
//        List<String> key = new ArrayList<>();
//        //遍历第一行数据，所有要新增的数据属性，并把数据放入到key集合中
//        for (Cell cell : row) {
//            //数据设置为String
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//            //添加数据
//            key.add(cell.getStringCellValue());
//        }
//        //遍历所有行数据
//        for (int i = 2; i < rowNum; i++) {
//            //获取所有行
//            row = sheet.getRow(i);
//            //如果行数据不等于空
//            if (row != null) {
//                //遍历所有单元格数据，并把key和value，放到excelMap中进行映射
//                Map<String, String> excelmap = new HashMap<>();
//                //用于保存每条数据的map，并且在map中建立属性和数据的映射关系
//                int j = 0;
//                for (Cell cell : row) {
//                    if (cell != null) {
//                        //设置获取的单元格类型String
//                        cell.setCellType(Cell.CELL_TYPE_STRING);
//                        //获取数据
//                        String value = cell.getStringCellValue();
//                        if (value != null && value != "") {
//                            //将每个单元格的数据放入到集合中
//                            excelmap.put(key.get(j), value);
//                            j++;
//                        }
//                    }
//                }
//                //12:创建对应的实体类类型
//                T t = clazz.newInstance();
//                //spring 提供的beanMap，它可以通过反射的形势把map中的数据转化为实体类中
//                BeanMap beanMap = BeanMap.create(t);
//                beanMap.putAll(excelmap);
//                result.add(t);
//            }
//        }
//        return result;
//    }
//}
