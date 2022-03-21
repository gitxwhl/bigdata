package com.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.WyFrequencyDetails;
import com.property.entity.Wyinspectionplan;
import com.property.entity.Wyperson;
import com.property.mapper.WypersonMapper;
import com.property.mapper.WyworkorganizationMapper;
import com.property.service.WypersonService;
import com.property.utils.MySFTP;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WypersonServiceImpl implements WypersonService {

    @Autowired
    private WypersonMapper wypersonMapper;

    @Autowired
    private WyworkorganizationMapper wyworkorganizationMapper;

    //-----------------------------------------------排班计划-----------------------------------------------------
    /**
     * 查询班次明细
     * @param paramJson
     * {"date":"","day":""}
     * @return
     */
    @Override
    public Object getFrequency(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String date = rowData.getString("date");
        String day = rowData.getString("day");
        date = date + "-" + day;
        List frequency = wypersonMapper.getFrequency(date);
        return frequency;
    }

    /**
     * 添加班次明细
     * @param paramJson
     * {"date":"","day":"","msg":[{"frequency":"","startTime":"","endTime":""}]}
     * @return
     */
    @Transactional
    @Override
    public Object addFrequency(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String date = rowData.getString("date");
        String day = rowData.getString("day");
        date = date + "-" + day;
        List msg = rowData.getJSONArray("msg");
        for (Object o : msg) {
            JSONObject jsonObject = (JSONObject) o;
            String frequency = jsonObject.getString("frequency");
            String startTime = jsonObject.getString("startTime");
            String endTime = jsonObject.getString("endTime");
            WyFrequencyDetails wyFrequencyDetails = new WyFrequencyDetails(0,date,frequency,startTime,endTime);
            wypersonMapper.addFrequency(wyFrequencyDetails);
        }
        return "添加成功";
    }

    /**
     * 修改班次明细
     * @param paramJson
     * [{"id":"","frequency":"","startTime":"","endTime":""}]
     * @return
     */
    @Transactional
    @Override
    public Object updateFrequency(String paramJson) {
        List array = JSONArray.parseArray(paramJson);
        for (Object o : array) {
            JSONObject rowData = (JSONObject)o;
            int id = Integer.parseInt(rowData.getString("id"));
            String frequency = rowData.getString("frequency");
            String startTime = rowData.getString("startTime");
            String endTime = rowData.getString("endTime");
            WyFrequencyDetails wyFrequencyDetails = new WyFrequencyDetails(id,null,frequency,startTime,endTime);
            wypersonMapper.updateFrequency(wyFrequencyDetails);
        }
        return "修改成功";
    }

    //-----------------------------------------------巡检计划-----------------------------------------------------

    /**
     * 巡检计划列表
     * @param paramJson
     * {"pageNum":"","pageSize":""}
     * @return
     */
    @Override
    public Object inspectionList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        Integer totalSize = wypersonMapper.inspectionplanListCnt();
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = wypersonMapper.inspectionplanList(startIndex, pageSize);
            for (Map map : list) {
                List ids = new ArrayList();
                Object personnel = map.get("personnel");
                if(personnel != null && !personnel.equals("")){
                    StringBuilder sb = new StringBuilder();
                    List<Map> person = wypersonMapper.getPersonById(personnel.toString());
                    for (Map person1 : person) {
                        Object name = person1.get("name");
                        sb.append(name).append(",");
                    }
                    String s = sb.substring(0, sb.length() - 1);
                    map.put("personnel",s);
                    String[] split = personnel.toString().split(",");
                    for (String s1 : split) {
                        ids.add(Integer.parseInt(s1));
                    }
                }
                map.put("ids",ids);
            }
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 新增巡检计划
     * @param paramJson
     * {"place":"","inspectiontask":"","schedule":"","personnel":[],"content":""}
     * @return
     */
    @Override
    public Object addInspection(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String place = rowData.getString("place");
        String inspectiontask = rowData.getString("inspectiontask");
        String schedule = rowData.getString("schedule");
        String content = rowData.getString("content");
        List personnel = rowData.getJSONArray("personnel");
        StringBuilder sb = new StringBuilder();
        String str = null;
        if(personnel != null && personnel.size() != 0){
            for (Object o : personnel) {
                sb.append(o).append(",");
            }
            str = sb.substring(0,sb.length()-1);
        }
        Wyinspectionplan wyinspectionplan = new Wyinspectionplan(0,place,inspectiontask,schedule,str,content,null);
        wypersonMapper.addInspectionplan(wyinspectionplan);
        return "新增成功";
    }

    /**
     * 修改巡检计划
     * @param paramJson
     * {"id":"","place":"","inspectiontask":"","schedule":"","personnel":[],"content":""}
     * @return
     */
    @Override
    public Object updateInspection(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.parseInt(rowData.getString("id"));
        String place = rowData.getString("place");
        String inspectiontask = rowData.getString("inspectiontask");
        String schedule = rowData.getString("schedule");
        String content = rowData.getString("content");
        List personnel = rowData.getJSONArray("personnel");
        StringBuilder sb = new StringBuilder();
        String str = null;
        if(personnel != null && personnel.size() != 0){
            for (Object o : personnel) {
                sb.append(o).append(",");
            }
            str = sb.substring(0,sb.length()-1);
        }
        Wyinspectionplan wyinspectionplan = new Wyinspectionplan(id,place,inspectiontask,schedule,str,content,null);
        wypersonMapper.updateInspectionplan(wyinspectionplan);
        return "修改成功";
    }

    /**
     * 删除巡检计划
     * @param paramJson
     * {"id":""}
     * @return
     */
    @Override
    public Object deleteInspection(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.parseInt(rowData.getString("id"));
        wypersonMapper.deleteInspectionplan(id);
        return "删除成功";
    }

    /**
     * 下拉框
     * @return
     */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        //门岗人员
        List person = wypersonMapper.getPerson();
        //班次
        List<JSONObject> frequency = new ArrayList();

        List<Map> dictionaries = wyworkorganizationMapper.getDictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'" + map.get("parakey") + "','text':'" + map.get("paravalue") + "'}");
            if(map.get("paraname").equals("frequency")){
                frequency.add(jsonObject);
            }
        });
        resultMap.put("person",person);
        resultMap.put("frequency",frequency);
        return resultMap;
    }

    //-----------------------------------------------岗位资料-----------------------------------------------------
    /**
     * 新增
     * @param paramJson
     * {"name":"","post":"","telephone":"","photo":""}
     * @return
     */
    @Override
    public Object insertWyPerson(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String name = rowData.getString("name");//姓名
        String post = rowData.getString("post");//岗位
        String telephone = rowData.getString("telephone");//电话
        String photo = rowData.getString("photo");//照片
        String s = MySFTP.uploadFile(photo);
        Wyperson wyperson = new Wyperson();
        wyperson.setName(name);
        wyperson.setPost(post);
        wyperson.setTelephone(telephone);
        wyperson.setPhoto(s);
        wyperson.setIsgate("0");
        wypersonMapper.insertWyPerson(wyperson);
        return "新增成功";
    }

    /**
     * 查询列表
     * @param paramJson
     * {"pageNum":"","pageSize":""}
     * @return
     */
    @Override
    public PageBean wypersonList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int totalSize = wypersonMapper.getPersonCnt();
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List<Map> list = wypersonMapper.getPersonList(startIndex, pageSize);
            for (Map map : list) {
                Object photo = map.get("photo");
                if(photo != null && !photo.equals("")){
                    String path = MySFTP.getPath(photo.toString());
                    map.put("photo",path);
                }
            }
            pageBean.setList(list);
        }
        return pageBean;
    }



}
