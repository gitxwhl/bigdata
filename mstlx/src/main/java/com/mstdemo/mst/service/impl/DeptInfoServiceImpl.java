package com.mstdemo.mst.service.impl;

import com.mstdemo.mst.bean.DeptInfo;
import com.mstdemo.mst.bean.Permission;
import com.mstdemo.mst.mapper.DeptInfoMapper;
import com.mstdemo.mst.service.DeptInfoService;
import com.mstdemo.mst.util.StreamZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    @Autowired
    private DeptInfoMapper deptInfoMapper;

    /**
     * 返回添加返回id
     * @param deptInfo
     * @return
     */
    @Override
    public int addDept(DeptInfo deptInfo) {
        int fla = deptInfoMapper.addDept(deptInfo);
        Integer id = deptInfo.getDeptId();
        return fla;
    }


    /**
     * 递归调用
     * @return
     */
    @Override
    public List<Permission> findPermission(){
        Permission parent = new Permission();
        parent.setId("0");
        queryChildPermissions(parent);
        return parent.getChildren();
    }
    /**
     *递归
     * 自己调用自己
     * 方法一定要存在跳出逻辑
     * 方法调用时，参数之间应该有规律
     * @param parent
     * @return
     */
    public void queryChildPermissions(Permission parent){
        List<Permission> childPermissions = deptInfoMapper.findPermission(parent.getId());
        for (Permission permission:childPermissions){
            queryChildPermissions(permission);
        }
        parent.setChildren(childPermissions);
    }

    /**
     * 处理时间循环遍历出现数组越界
     * @param
     * @param
     */
    public static void getTime(String starttime, String endtime){
       int starttimey = Integer.valueOf(starttime.substring(0,4));
       int starttimem = Integer.valueOf(starttime.substring(5));
       int endtimey = Integer.valueOf(endtime.substring(0,4));
       int endtimem = Integer.valueOf(endtime.substring(5));
       int l = 0;
       String xAxis [] = new String[10000];
       for (int i=0;starttimey <= endtimey;i++){
           if(starttimey == endtimey && starttimem <= endtimem){
               if(starttimem < 10){
                   xAxis[l] = starttimey + "-0" + starttimem;
                   starttimem = starttimem+1;
                   System.out.println(xAxis[l]);
                   l= l+1;
               }else if(starttimem < 13 && starttimem >= 10){

                   xAxis[l] = starttimey + "-" + starttimem;
                   starttimem = starttimem+1;
                   System.out.println(xAxis[l]);
                   l= l+1;
               }
           }

           if(starttimey < endtimey)
            {
               if(starttimem < 10){
                   xAxis[l] = starttimey + "-0" + starttimem;
                   starttimem = starttimem+1;
                   System.out.println(xAxis[l]);
                   l= l+1;
               }else if(starttimem < 13 && starttimem >= 10){
                   xAxis[l] = starttimey + "-" + starttimem;
                   starttimem = starttimem+1;
                   System.out.println(xAxis[l]);
                   l= l+1;
               }else {
                   starttimey= starttimey+1;
                   starttimem = starttimem-12;
               }
           }
       }
    }


    public static void main(String[] args) {
        //日期之间的调用
        getTime("1993-08" ,"2022-11");
        //解压测试
//        decompression("D:\\sss.zip","D:\\xjzh\\");
    }

}
