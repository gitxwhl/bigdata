package com.raysdata.enterprisesadmittanceserver.util;


import com.raysdata.enterprisesadmittanceserver.entity.PersonnePoi;
import com.raysdata.enterprisesadmittanceserver.entity.PoiFileds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
    关于excel 的生成和解析
    工具类  通用
 */
public class ExcelUtils {

    /*private static  final Logger log= LoggerFactory.getLogger(ExcelUtils.class);*/
    /*
        根据传入的数据 生成XSSFWorkbook
     */
    public static XSSFWorkbook downExcel(List<PersonnePoi> list, String[] titles, String[] attrs) throws NoSuchFieldException, IllegalAccessException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            XSSFRow row1 = sheet.createRow(i + 1);
            for (int j = 0; j < attrs.length; j++) {
                XSSFCell cell = row1.createCell(j);
                Class<?> aClass = o.getClass();
                Field declaredField = aClass.getDeclaredField(attrs[j]);
                declaredField.setAccessible(true);
                Object o1 = declaredField.get(o);
                if (declaredField.getType() == String.class) {
                    cell.setCellValue((String) o1);
                } else if (declaredField.getType() == Date.class) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String format = sdf.format(o1);
                    cell.setCellValue((String) format);
                } else if (declaredField.getType() == Integer.class) {
                    String sex = declaredField.getName();
                    cell.setCellValue(String.valueOf(o1));
                }
            }
        }
        return workbook;
    }

    /**
     * 反射和注解导出
     * @param list
     * @return
     */
    public static XSSFWorkbook createExcel(List list) throws NoSuchFieldException, IllegalAccessException {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        Object o = list.get(0);
        Class<?> aClass = o.getClass();
        //获取所有的属性
        Field[] declaredFields = aClass.getDeclaredFields();
        //用来存放所有属性名
        List<String> attrNames=new ArrayList();
        //用来存放类型
        List<Integer> types=new ArrayList<>();
        int count=0;
        for (int i = 0; i < declaredFields.length; i++) {
            //获取第一个属性
            Field field=declaredFields[i];
            //判断属性上有么有注解
            boolean annotationPresent = field.isAnnotationPresent(PoiFileds.class);
            //如果有,获取注解,并且获取注解的值
            if (annotationPresent==true){
                XSSFCell cell = row.createCell(count);
                PoiFileds annotation = field.getAnnotation(PoiFileds.class);
                String name = annotation.name();
                attrNames.add(annotation.title());
                types.add(annotation.type());
                cell.setCellValue(name);
                count++;
            }
        }
        int a=1;
        for (int i = 0; i < list.size(); i++) {
            XSSFRow row1 = sheet.createRow(i + 1);
            Object o1 = list.get(i);

            for (int j = 0; j < attrNames.size(); j++) {
                Class<?> aClass1 = o1.getClass();
                Field declaredField = aClass1.getDeclaredField(attrNames.get(j));
                String name = declaredField.getName();
                declaredField.setAccessible(true);
                Object value = declaredField.get(o1);
                XSSFCell cell = row1.createCell(j);
                Integer integer = types.get(j);
                if (integer==0){
                    if(value==null){
                        cell.setCellValue(a++);
                    }else {
                        cell.setCellValue(String.valueOf(value));
                    }
                }else if (integer==1){
                    cell.setCellValue((Long)value);
                }else if (integer==2){
                    cell.setCellValue((Double) value);
                }else if (integer==3){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    cell.setCellValue(sdf.format(value));
                }
            }
        }
        return workbook;
    }

}
