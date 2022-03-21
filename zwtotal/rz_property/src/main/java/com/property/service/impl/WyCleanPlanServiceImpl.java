package com.property.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wycleaningplan;
import com.property.mapper.WyCleanPlanMapper;
import com.property.service.WyCleanPlanService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyCleanPlanServiceImpl implements WyCleanPlanService {
    @Autowired
    WyCleanPlanMapper wyCleanPlanMapper;

    /*
    查询保洁计划
     */
    @Override
    public PageBean selectCleanPlan(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String company = paramDate.getString("company");
        String planname = paramDate.getString("planname");
        String plantype = paramDate.getString("plantype");
        String cleaningarea = paramDate.getString("cleaningarea");
        String cycle = paramDate.getString("cycle");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = wyCleanPlanMapper.getPlanSize(company,planname,plantype,cleaningarea,cycle);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        List dislist = new ArrayList();
        if(totalSize == 0){
            pageBean.setMap(new HashMap());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Map> list = wyCleanPlanMapper.selectCleanPlan(company,planname,
                    plantype,cleaningarea,cycle,pageSize,startIndex);
            for(Map map1 : list){
                Map map = new HashMap();
                int id = (int) map1.get("id");
                map.put("plan",map1);
                map.put("content", wyCleanPlanMapper.selectCleanContent(id));
                dislist.add(map);
            }
            pageBean.setList(dislist);
        }
        return pageBean;
    }

    /*
    删除保洁计划
     */
    @Override
    public void deleteCleanPlan(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        wyCleanPlanMapper.deleteCleanPlan(id);
    }

    /*
    修改-根据id返回相关信息
     */
    @Override
    public Map updateOfContent(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        Map map = new HashMap();
        map.put("updateCleanPlan",wyCleanPlanMapper.updateCleanPlan(id));
        map.put("updateContent",wyCleanPlanMapper.updateContent(id));
        map.put("updateProgram",wyCleanPlanMapper.updateProgram(id));
        return map;
    }

    /*
    修改保洁计划
     */
    @Override
    public void updateCleanPlan(String param) {
        //修改保洁方案
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        String planname = paramDate.getString("planname");
        String cleaningtime = paramDate.getString("cleaningtime");
        String plantype = paramDate.getString("plantype");
        wyCleanPlanMapper.alterPlan(id,planname,cleaningtime,plantype);
        //保洁内容
        List<Map> content = (List<Map>)paramDate.get("content");
        for(Map map : content){
            int cid = (int)map.get("id");
            String standards = (String) map.get("standards");
            String contentrange = (String) map.get("contentrange");
            String cleaningarea = (String) map.get("cleaningarea");
            String cycle = (String) map.get("cycle");
           wyCleanPlanMapper.alterContent(cid,standards,contentrange,cleaningarea,cycle);
        }
        //保洁方案
        List<Map> program = (List<Map>)paramDate.get("program");
        for(Map map : program){
            int pid = (int)map.get("id");
            String cleaningarea = (String) map.get("cleaningarea");
            String personnel = (String) map.get("personnel");
            String remarks = (String) map.get("remarks");
            wyCleanPlanMapper.alterProgram(pid,cleaningarea,personnel,remarks);
        }
    }

    /*
    新建保洁计划
     */
    @Override
    public void insertCleanPlan(String param) {
        //新增保洁方案
        JSONObject paramDate = JSONObject.parseObject(param);
        String planname = paramDate.getString("planname");
        String cleaningtime = paramDate.getString("cleaningtime");
        String plantype = paramDate.getString("plantype");
        int company = paramDate.getIntValue("company");
        Wycleaningplan wycleaningplan = new Wycleaningplan(0,planname,cleaningtime,null,null,
                null,null,null,null,plantype,
                null,null,company,"0");
        wyCleanPlanMapper.insertPlan(wycleaningplan);
        int id = wycleaningplan.getId();

        //保洁内容
        List<Map> content = (List<Map>)paramDate.get("content");
        for(Map map : content){
            String standards = (String) map.get("standards");
            String contentrange = (String) map.get("contentrange");
            String cleaningarea = (String) map.get("cleaningarea");
            String cycle = (String) map.get("cycle");
            wyCleanPlanMapper.insertContent(id,standards,contentrange,cleaningarea,cycle);
        }
        //保洁方案
        List<Map> program = (List<Map>)paramDate.get("program");
        for(Map map : program){
            String cleaningarea = (String) map.get("cleaningarea");
            String personnel = (String) map.get("personnel");
            String remarks = (String) map.get("remarks");
            wyCleanPlanMapper.insertProgram(id,cleaningarea,personnel,remarks);
        }

    }

    /*
    检查计划查询
     */
    @Override
    public PageBean selectInspectionPlan(String param) {
        PageBean pageBean = new PageBean();
        return pageBean;
    }

    /*
    检查计划删除
     */
    @Override
    public void deleteInspectionPlan(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        wyCleanPlanMapper.deleteInspectionPlan(id);
    }

    /*
    修改-根据id查询信息
     */
    @Override
    public Map selectInspectionPlanById(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
       return wyCleanPlanMapper.selectInspectionPlanById(id);

    }

    /*
    修改检查计划
     */
    @Override
    public void updateInspectionPlan(String param) {

    }

    /*
    新建检查计划
     */
    @Override
    public void insertInspectionPlan(String param) {

    }
}
