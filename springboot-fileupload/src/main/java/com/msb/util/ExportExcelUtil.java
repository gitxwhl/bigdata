package com.msb.util;

import com.msb.anno.ExceelAttr;
import com.sun.istack.internal.NotNull;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExportExcelUtil<T> {


    public  void exportExcel(@NotNull Map<String, Object> params) throws Exception {
        //获取外面传过来的对象
        List<T> objs = (List<T>) params.get("objs");
        //获取外面传过来的路径
        String path = (String) params.get("path");
        //获取第二行的样式从外面传过来
        Integer rowIndex = (Integer) params.get("rowIndex");
        //外界传过来的response
        HttpServletResponse response = (HttpServletResponse) params.get("response");
        //工作簿对象
        Workbook workbook = new XSSFWorkbook(
                Objects.requireNonNull(this.getClass().getClassLoader()
                        .getResourceAsStream(path)));
        //sheet页
        Sheet sheet = workbook.getSheetAt(0);
        //获取第二行的样式
        Row row1 = sheet.getRow(rowIndex);
        CellStyle[] cellStyles = new CellStyle[row1.getLastCellNum()];
        for (int i = 0; i < cellStyles.length; i++) {
            cellStyles[i] = row1.getCell(i).getCellStyle();
        }
        for (int i = 0; i < objs.size(); i++) {
            //创建一行
           Row row = sheet.createRow(i + rowIndex);
            T t = objs.get(i);
            //拿到属性Filed,标有@ExceelAttr(sort = )这个注解的才处理
            //创建一列
            Cell useNamecell = row.createCell(0);
            //获取运行时类
            Class<?> clazz = t.getClass();
            //获取运行时类的属性
            Field[] fields = clazz.getDeclaredFields();
            //获取总列数
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < cellStyles.length; j++) {
                //创建一列
                Cell cellValue = row.createCell(j);
                //每一列设置样式
                cellValue.setCellStyle(cellStyles[j]);

                //每一列设置相应属性,
                //找出一个有声明ExceelAttr，并且sort=j的值,所以遍历Field
                for (Field field : fields) {
                    //获取ExceelAttr 注解
                    ExceelAttr annotation = field.getAnnotation(ExceelAttr.class);
                    if (annotation != null) {
                        //获取sort的值
                        int sort = annotation.sort();
                        //如果sort和j相等，设置属性值
                        if (sort == j) {
                            field.setAccessible(true);
                            //设置单元格属性的值
                                if(field.get(t) !=null){
                                    cellValue.setCellValue(field.get(t).toString());
                                }

                        }
                    }
                }
            }
        }
        String fileName = URLEncoder.encode("员工表.xlsx", "UTF-8");
        response.setContentType("applibation/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setHeader("fileName", fileName);
        workbook.write(response.getOutputStream());


    }


}




