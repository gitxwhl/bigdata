package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.mapper.WyspacemanagementMapper;
import com.property.service.WySpaceManagementService;
import com.property.utils.MySFTP;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WySpaceManagementServiceImpl implements WySpaceManagementService {
    @Autowired
    WyspacemanagementMapper stSpaceManagementMapper;
    /*
        空间查询
         */
    @Override
    public PageBean selectSpace(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String corporatid = paramDate.getString("id");
        String name = paramDate.getString("name");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = stSpaceManagementMapper.getTotalSize(corporatid,name);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            pageBean.setList(stSpaceManagementMapper.getSpace(corporatid,name,startIndex,pageSize));
        }

        return pageBean;
    }

    /*
   空间信息修改
    */
    //查询出需要修改的信息
    @Override
    public Map updateSpaceById(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getInteger("id");
        return stSpaceManagementMapper.updateSpaceById(id);
    }
    //修改空间信息
    @Override
    public void updateSpace(String param){
        JSONObject paramDate = JSONObject.parseObject(param);
        Integer id = paramDate.getInteger("id");
        String spacename = paramDate.getString("spacename");
        String builtarea = paramDate.getString("builtarea");
        String greeningratio = paramDate.getString("greeningratio");
        String detailedaddress = paramDate.getString("detailedaddress");
        String description = paramDate.getString("description");
        Integer corporatid = paramDate.getInteger("corporatid");
        //获取附件base64码
        String dataFile = paramDate.getString("dataFile");
        //调用上传附件工具类，传入base64码，返回新的附件名
        String enclosure = "";
        if(!dataFile.equals("")){
            enclosure = MySFTP.uploadFile(dataFile);
        }
        stSpaceManagementMapper.updateSpace(id,spacename,builtarea,greeningratio,detailedaddress,description,enclosure,corporatid);
    }

   /*
   空间信息删除
    */
    @Override
    public void deleteSpace(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id  = paramDate.getIntValue("id");
        stSpaceManagementMapper.deleteSpace(id);
    }

    /*
    新增空间信息
     */
    @Override
    @Transactional
    public void insertSpace(String param){
        JSONObject paramDate = JSONObject.parseObject(param);
        String spacename = paramDate.getString("spacename");
        String builtarea = paramDate.getString("builtarea");
        String greeningratio = paramDate.getString("greeningratio");
        String detailedaddress = paramDate.getString("detailedaddress");
        String description = paramDate.getString("description");
        Integer corporatid = paramDate.getInteger("corporatid");
        //获取附件base64码
        String dataFile = paramDate.getString("dataFile");
        //调用上传附件工具类，传入base64码，返回附件名
        String enclosure = "";
        if(!"".equals(dataFile)){
            enclosure = MySFTP.uploadFile(dataFile);
        }
        stSpaceManagementMapper.insertSpace(spacename,builtarea,greeningratio,detailedaddress,description,enclosure,corporatid);
    }

    /*
    查看附件信息
     */
    @Override
    public Map getSpaceFile(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        Integer id = paramDate.getInteger("id");
        //根据id获取文件名称
        String enclosure = stSpaceManagementMapper.getSpaceFile(id);
        //通过文件名称转换base64码
        String dataFile = MySFTP.getPath(enclosure);
        Map map =new HashMap();
        map.put("dataFile",dataFile);
        return map;
    }

}
