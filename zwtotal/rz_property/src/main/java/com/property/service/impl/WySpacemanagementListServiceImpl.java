package com.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.WyVegetation;
import com.property.entity.Wyspacemanagement;
import com.property.mapper.WyspacemanagementMapper;
import com.property.service.WySpacemanagementListService;
import com.property.service.WyVegetationService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class WySpacemanagementListServiceImpl implements WySpacemanagementListService {

    @Autowired
    WyspacemanagementMapper WyspacemanagementMapper;

    @Autowired
    WyVegetationService wyVegetationService;

    @Override
    public PageBean WySpacemanagementList(String paramJson) {

        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String suppliername = param.getString("suppliername");
        String attr1 = param.getString("attr1"); //部门关联字段

        int totalSize = WyspacemanagementMapper.getWyspacemanagementCnt(suppliername,attr1);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list = WyspacemanagementMapper.getWyspacemanagementList(startIndex,pageSize,suppliername,attr1);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 植被列表
     * @param paramJson
     * @return
     */
    @Override
    public PageBean WyvegetationtList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String id = param.getString("id");

        int totalSize = WyspacemanagementMapper.getvegetationtCnt(id);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list = WyspacemanagementMapper.getvegetationtList(startIndex,pageSize,id);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 新增供应商管理及植被
     * @param paramJson
     * @return
     */
    @Override
    public Object getSupplierManagement(String paramJson) {
        int i = 0;
        JSONObject rowData = JSONObject.parseObject(paramJson);
        //供应商信息
        String suppliername = rowData.getString("suppliername");
        String code = rowData.getString("code");
        String contacts = rowData.getString("contacts");
        String contactperson = rowData.getString("contactperson");

        //自动生成供应商主键id 及保存供应商信息
        String id = UUID.randomUUID().toString().replace("-", "");
        Wyspacemanagement  wyspacemanagement = new Wyspacemanagement();
        wyspacemanagement.setId(id);
        wyspacemanagement.setSuppliername(suppliername);
        wyspacemanagement.setCode(code);
        wyspacemanagement.setContacts(contacts);
        wyspacemanagement.setContactperson(contactperson);
        i =  WyspacemanagementMapper.getSupplierManagement(wyspacemanagement);
        //植被信息
        JSONObject param = rowData.getJSONObject("param");
        //植被数据
        JSONArray vegetations = param.getJSONArray("vegetations");
        for (Object vegetation : vegetations) {
            JSONObject jsonData = (JSONObject)vegetation;
            String vegetationname = jsonData.getString("vegetationname");
            String number = jsonData.getString("number");
            String company = jsonData.getString("company");
            String introduction = jsonData.getString("introduction");
            //保存植被数据
            WyVegetation wyVegetation = new WyVegetation(0,vegetationname,number,company,introduction,id);
            //保存植被信息
//            System.out.println("wyVegetation___"+wyVegetation);
            i =  wyVegetationService.gwtNewlyVegetation(wyVegetation);
        }
        if (i > 0) {
            return "新增成功";
        }
        return "新增失败";
    }


}
