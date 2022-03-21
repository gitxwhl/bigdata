package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wytoolsconsumables;
import com.property.mapper.WytoolsconsumablesMapper;
import com.property.service.WyToolsconsumablesService;
import com.property.utils.ExportExcel;
import com.property.utils.ImportExcelUtil;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyToolsconsumablesServiceImpl implements WyToolsconsumablesService {

    @Autowired
    private WytoolsconsumablesMapper wytoolsconsumablesMapper;

//    @Autowired
//    private StFileNameService stFileNameService;

    /**
     * 保洁工具消耗管理列表
     * @param paramJson
     * @return
     */
    @Override
    public Object getToolConsumption(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String name = param.getString("name");
        String type = param.getString("type");
        String departmentId = param.getString("departmentId");

        int totalSize = wytoolsconsumablesMapper.getToolConsumptionCnt(name, type, departmentId);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list = wytoolsconsumablesMapper.getToolConsumptionList(startIndex, pageSize, name, type, departmentId);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 新建材料及人员信息
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object insertMaterialsStaff(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String name = param.getString("name");//工具/耗材名称
        String classification = param.getString("classification");//分类(1:工具,2:材料)
        String type = param.getString("type");//工具/耗材种类(1:大型工具,2:小型工具)
        String no = param.getString("no");//编号
        String inventory = param.getString("inventory");//库存数量
        String received = param.getString("received");//领用数量
        String recipients = param.getString("recipients");//领用人
        String collectiontime = param.getString("collectiontime");//领用时间
        String returntime = param.getString("returntime");//归还时间
        String usenum = param.getString("usenum");//耗材使用数量
        String departmentId = param.getString("departmentId");//耗材使用数量

        int i = wytoolsconsumablesMapper.insertMaterialsStaff(name, classification, type, no, inventory, received, recipients, collectiontime, returntime, usenum, departmentId);
        if (i > 0) {
            return "新增成功";
        }
        return "新增失败";
    }

    @Override
    public Object updateMaterialsStaff(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        Integer id = Integer.valueOf(param.getString("id"));//工具/耗材名称
        String name = param.getString("name");//工具/耗材名称
        String classification = param.getString("classification");//分类(1:工具,2:材料)
        String type = param.getString("type");//工具/耗材种类(1:大型工具,2:小型工具)
        String no = param.getString("no");//编号
        String inventory = param.getString("inventory");//库存数量
        String received = param.getString("received");//领用数量
        String recipients = param.getString("recipients");//领用人
        String collectiontime = param.getString("collectiontime");//领用时间
        String returntime = param.getString("returntime");//归还时间
        String usenum = param.getString("usenum");//耗材使用数量

        int i = wytoolsconsumablesMapper.updateMaterialsStaff(id, name, classification, type, no, inventory, received, recipients, collectiontime, returntime, usenum);
        if (i > 0) {
            return "修改成功";
        }
        return "修改失败";
    }


    /**
     * 查询数据详情
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object insertDetails(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        Integer id = Integer.valueOf( param.getString("id"));
        Wytoolsconsumables wytoolsconsumables = wytoolsconsumablesMapper.insertDetails(id);
        return wytoolsconsumables;
    }

    @Override
    public Object deleteMaterialsStaf(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String id = param.getString("id");
        int i = wytoolsconsumablesMapper.deleteMaterialsStaf(id);
        if (i > 0) {
            return "删除成功";
        }
        return "删除失败";
    }


    /**
     * 文件导出
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object fileImport(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        int j = 1;
        StringBuilder param = new StringBuilder();
        //文件名称
        String fileName = "保洁工具耗材管理" + ".xls";
        //查询表数据关系表
        List<Map<String, Object>> relationin = wytoolsconsumablesMapper.select();
        //第一行标题
        List<String> titles = new ArrayList<String>();
        titles.add("耗材名称");
        titles.add("分类");
        titles.add("耗材种类");
        titles.add("编号");
        titles.add("库存数量");
        titles.add("领用数量");
        titles.add("领用人");
        titles.add("领用时间");
        titles.add("归还时间");
        titles.add("耗材使用数量");
        titles.add("关联部门");
        //表格数据数据
        List<Map> varList = new ArrayList<Map>();
        for (Map<String, Object> map : relationin) {
            Map vpd = new HashMap();
            vpd.put("var1", map.get("name"));
            vpd.put("var2", map.get("classification"));
            vpd.put("var3", map.get("type"));
            vpd.put("var4", map.get("no"));
            vpd.put("var5", map.get("inventory"));
            vpd.put("var6", map.get("received"));
            vpd.put("var7", map.get("recipients"));
            vpd.put("var8", map.get("collectiontime"));
            vpd.put("var9", map.get("returntime"));
            vpd.put("var10", map.get("usenum"));
            vpd.put("var11", map.get("departmentId"));
            varList.add(vpd);
        }
        ExportExcel ex = new ExportExcel();
        ex.export(response, fileName, titles, varList);
        return j;
    }

    @Override
    public Object fileExample(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int j = 0;
        String t1 = null, t2 = null, t3 = null, t4 = null, t5 = null, t6 = null, t7 = null, t8 = null, t9 = null, t10 = null, t11 = null;
        StringBuilder param = new StringBuilder();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        InputStream in = null;
        List<List<Object>> listob = null;
        MultipartFile file = multipartRequest.getFile("file");
        //获取文件名
        String fileName = file.getOriginalFilename();
        //存到到本地
//        stFileNameService.instName(fileName);
        try {
            if (file.isEmpty()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        in = file.getInputStream();
        listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
        in.close();

        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            t1 = String.valueOf(lo.get(0));
            t2 = String.valueOf(lo.get(1));
            t3 = String.valueOf(lo.get(2));
            t4 = String.valueOf(lo.get(3));
            t5 = String.valueOf(lo.get(4));
            t6 = String.valueOf(lo.get(5));
            t7 = String.valueOf(lo.get(6));
            t8 = String.valueOf(lo.get(7));
            t9 = String.valueOf(lo.get(8));
            t10 = String.valueOf(lo.get(9));
            t11 = String.valueOf(lo.get(10));
            Wytoolsconsumables toolsconsumables = new Wytoolsconsumables();
            int i1 = wytoolsconsumablesMapper.insert(toolsconsumables);
            if (i1 > 0) {
                j++;
            }
        }
        return "成功导入" + j + "条数据";
    }


}
