package com.mashibing.easypoi_boot;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.mashibing.easypoi_boot.pojo.Card;
import com.mashibing.easypoi_boot.pojo.Order;
import com.mashibing.easypoi_boot.pojo.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
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



}
