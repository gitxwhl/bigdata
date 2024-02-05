package com.mashibing.easypoi_boot;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.mashibing.easypoi_boot.pojo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public  class EasypoiBootApplicationTests {

    @Test
    void contextLoads() {
    }

    public List<User> getUser(){
        List<User> userList=new ArrayList<>();
        for(int i=0;i< 10;i++){
            User user=new User();
            user.setId(String.valueOf(i));
            user.setName("文员");
            user.setAge(16+i);
            user.setBir(new Date());
            user.setStatus(String.valueOf(i%2));
            user.setHobby(Arrays.asList("抽烟","喝酒","烫头"));
            user.setCard(new Card("320321XXXXXXXXX","江苏"));
//            user.setOrderList(Arrays.asList(new Order("000000","水果"),new Order("111111", "蔬菜")));
            user.setPhoto("D:\\cssc\\a.png");
            userList.add(user);
        }
        return userList;
    }
    @Test
    public void testExportExcle() throws Exception {
        //导出大批量的数据使用
         //ExcelExportUtil.exportBigExcel()
        //1：配置对象:2：导出类:3:导出数据的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户列表","测试"), User.class,getUser());
        //输出位置
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\mb\\user.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
    @Test
    public void testImportExcel()throws Exception{
        //设置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//标题占几行
        params.setHeadRows(1);//列名占几行
        //将Excel导入到指定的位置
        params.setNeedSave(true);
        params.setSaveUrl("D:\\github\\bigdata\\easypoi_boot\\src\\main\\resources\\static");
        List<Emp> empList = ExcelImportUtil.importExcel(new FileInputStream("D:\\mb\\user.xlsx"), Emp.class, params);
        empList.forEach(System.out::println);
    }





    //    ==========================================多sheet页的导入============================================

    /**
     *接收Excle文件导入的多个sheet
     * @param filePath 文件路径
     * @param sheetIndex sheet页下标
     * @param titleRows  标题行数
     * @param headerRows 表头行数
     * @param pojoClass excle实体
     * @param <T>
     * @return
     */
    public static <T> List<T> importMultSheet(String filePath,int sheetIndex,Integer titleRows,Integer headerRows,Class<T> pojoClass){
        //导入相关参数
        ImportParams params  = new ImportParams();
        //设置参数下标
        params.setStartSheetIndex(sheetIndex);
        params.setHeadRows(headerRows);
        params.setTitleRows(titleRows);

        //表头必须包含的字段，不包含，就报错
        params.setImportFields(new String[]{"用户ID"});

        //
        List<T> list=null;
        try {
           list= ExcelImportUtil.importExcel(new FileInputStream(filePath), pojoClass, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    //测试导入多个sheet页
    @Test
    public void testImportMultiSheet(){
        String exclepath="D:\\mb\\login.xlsx";
        //拿到第一页数据遍历
        List<LoginUsr> loginUsrList = importMultSheet(exclepath, 0, 1, 1, LoginUsr.class);
        loginUsrList.forEach(System.out::println);
        //拿到第二页数据遍历
        System.out.println("第二页数据");
        List<LoginUrl> loginLoginUrlList = importMultSheet(exclepath, 1, 1, 1, LoginUrl.class);
        loginLoginUrlList.forEach(System.out::println);
    }






}
