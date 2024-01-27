package com.msb.controller;

import com.msb.pojo.ZhCustomer;
import com.msb.util.ExcelUtil;
import com.msb.util.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/ZhCustomerController.do")
public class ZhCustomerController {


    /**
     * 根据前端传递Excel表格文件和对应的公司信息，解析业主信息，存入到数据库
     * @param file excel表格文件（固定格式）
     * @param company 对应公司编号
     * @return   是否新增成功
     */
    @PostMapping("/uploadExcel")
    public ResponseResult uploadExcel(MultipartFile file,String company){
        try {
            if(file !=null&&file.getSize()>0){
                List<ZhCustomer> zhCustomers=ExcelUtil.readExcel((FileInputStream) file.getInputStream(), ZhCustomer.class);
                //调用业务层，批量添加到数据库


                for(ZhCustomer zhCustomer :zhCustomers){
                    System.out.println(zhCustomer);
                }
                System.out.println("uploadExcel");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return ResponseResult.success();
    }




}
