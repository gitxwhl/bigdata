package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.mapper.ToolMapper;
import com.raysdata.riskdataanalyzeserver.service.ToolService;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ToolServiceImpl implements ToolService {

//    @Autowired
//    ToolDao toolDao;
    @Autowired
    ToolMapper toolMapper;

    /*
     * 数据统计页面
     * */
    @Override
    public Map<String, List> dataStatistics() {
        Map map = new HashMap();
        //工器具数量统计
        List<Map<String, Object>> count = toolMapper.getToolListByType();
        map.put("count", count);
        String time1, time2, time3, time4, time5, time6;
        List<String> listTime = new ArrayList();
        List l1 = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String time = sdf.format(date);
        time1 = time;
        listTime.add(time1);
        for (int i = 0; i < 5; i++) {
            calendar.add(Calendar.MONTH, -1);
            date = calendar.getTime();
            time = sdf.format(date);
            l1.add(time);
        }
        time2 = (String) l1.get(0);
        listTime.add(time2);
        time3 = (String) l1.get(1);
        listTime.add(time3);
        time4 = (String) l1.get(2);
        listTime.add(time4);
        time5 = (String) l1.get(3);
        listTime.add(time5);
        time6 = (String) l1.get(4);
        listTime.add(time6);
        //工器具采购曲线
        List PURPLAN = new ArrayList<>();
        //按类型统计库存工器具数量
        List STORAGE = new ArrayList<>();
        //按类型统计报废数量
        List DISUSE = new ArrayList<>();
        //按类型统计检测工器具数量
        List TESTDETAIL = new ArrayList<>();
        for (int i = 0; i < listTime.size(); i++) {
            //工器具采购曲线
            String j = listTime.get(i);
            PURPLAN.add(toolMapper.getCurveMap1(j));
            //按类型统计库存工器具数量
            STORAGE.add(toolMapper.getCurveMap2(j));

            //按类型统计报废数量
            DISUSE.add(toolMapper.getCurveMap3(j));
            //按类型统计检测工器具数量
            TESTDETAIL.add(toolMapper.getCurveMapByType(j));
        }
        map.put("PURPLAN", PURPLAN);
        map.put("STORAGE", STORAGE);
        map.put("DISUSE", DISUSE);
        map.put("TESTDETAIL", TESTDETAIL);
        return map;
    }


    /*
     *实时预警
     * paramJson:{"pageNo":"", "pageSize":"", "params":{"dataReportOrg":"","managementCode":"","toolType":"","name":"","insState":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> realWarning(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNo = Integer.parseInt(JSONObject.parseObject(paramJson).getString("pageNo"));
        int pageSize = Integer.parseInt(JSONObject.parseObject(paramJson).getString("pageSize"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String dataReportOrg = JSONObject.parseObject(paramJson).getJSONObject("params").getString("dataReportOrg");
        String managementCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("managementCode");
        String toolType = JSONObject.parseObject(paramJson).getJSONObject("params").getString("toolType");
        String name = JSONObject.parseObject(paramJson).getJSONObject("params").getString("name");
        String insState = JSONObject.parseObject(paramJson).getJSONObject("params").getString("insState");
        if (pageNo <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (pageSize <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(dataReportOrg)) {
//                param.append("and DATAREPORT_ORG = '").append(dataReportOrg).append("' ");
//            }
//            if (ObjectUtils.isNotEmpty(managementCode)) {
//                param.append("and MANAGEMENT_CODE like '%").append(managementCode).append("%' ");
//            }
//            if (ObjectUtils.isNotEmpty(toolType)) {
//                param.append("and TOOL_TYPE = '").append(toolType).append("' ");
//            }
//            if (ObjectUtils.isNotEmpty(name)) {
//                param.append("and NAME like '%").append(name).append("%' ");
//            }
//            if (ObjectUtils.isNotEmpty(insState)) {
//                param.append("and INS_STATE = '").append(insState).append("' ");
//            }
//            Integer totalSize = toolDao.getCount(Const.SQL_DATA_REALWARNINGCNT.replaceAll("%param%", param.toString()));
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("pageSize", pageSize);
            int offset = (pageNo - 1) * pageSize;
            mapParam.put("offset", offset);
            mapParam.put("dataReportOrg", dataReportOrg);
            mapParam.put("managementCode", managementCode);
            mapParam.put("toolType", toolType);
            mapParam.put("name", name);
            mapParam.put("insState", insState);

            Integer totalSize = toolMapper.getWarningCNT(mapParam);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder().content(new ArrayList<>()).elementTotalSize(0).page(0).size(0).totalPage(0).totalSize(0)
                        .build();
            } else {
//                int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
//                int offset = (pageNo - 1) * pageSize;
//                param.append(" limit " + pageSize + " offset " + offset);
//                List<Map<String, Object>> realWarningList = toolDao.getList(Const.SQL_DATA_REALWARNING.replaceAll("%param%", param.toString()));
                List<Map<String, Object>> realWarningList = toolMapper.getWarningList(mapParam);
//                List<Map<String, Object>> dateList = toolDao.getList(Const.SQL_DATA_DATEOFMANUFACTURE.replaceAll("%param%", param.toString()));
                List<Map<String, Object>> dateList = toolMapper.getProductionAndShelfList(mapParam);
                int i = 0;
                for (Map<String, Object> stringObjectMap : dateList) {
                    String releaseDate = stringObjectMap.get("INS_RELEASEDATE").toString();//生产日期
                    String shelfLife = stringObjectMap.get("INS_SHELFLIFE").toString();//保质期
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    releaseDate = releaseDate.substring(0, 10);
                    try {
                        Date relDate = sdf.parse(releaseDate);
                        calendar.setTime(relDate);
                        calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(shelfLife));
                        relDate = calendar.getTime();
                        String expirationTime = sdf.format(relDate);//到期时间
                        Date nowDate = new Date();//获取当前时间

                        long timeRemaining = (nowDate.getTime()-relDate.getTime()) / (24 * 3600 * 1000);//剩余时间

                        Map<String, Object> stringObjectMap1 = realWarningList.get(i);
                        stringObjectMap1.put("expirationTime", expirationTime);
                        stringObjectMap1.put("timeRemaining", timeRemaining);
                        i++;
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                return PageBean.<Map<String, Object>>builder()
                        .content(realWarningList)
                        .elementTotalSize(realWarningList.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1)
                        .page(pageNo)
                        .size(pageSize)
                        .build();
            }
        }
    }

    /*
     *供应商评价
     * paramJson:{"pageNo":"", "pageSize":"", "params":{"dataReportOrg":"","manName":""}}
     * */
    @Override
    public Map<String, List> getGradeList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNo = Integer.parseInt(JSONObject.parseObject(paramJson).getString("pageNo"));
        int pageSize = Integer.parseInt(JSONObject.parseObject(paramJson).getString("pageSize"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String dataReportOrg = JSONObject.parseObject(paramJson).getJSONObject("params").getString("dataReportOrg");
        String manName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("manName");
        List list3 = new ArrayList();
        if (pageNo <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (pageSize <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("pageSize", pageSize);
            int offset = (pageNo - 1) * pageSize;
            mapParam.put("offset", offset);
            mapParam.put("dataReportOrg", dataReportOrg);
            mapParam.put("manName", manName);
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(dataReportOrg)) {
//                param.append(" and a.DATAREPORT_ORG = '").append(dataReportOrg).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(manName)) {
//                param.append(" and b.MAN_NAME like '%").append(manName).append("%'");
//            }
//            Integer totalSize = toolDao.getCount(Const.SQL_DATA_GETGRADELISTCNT.replaceAll("%param%", param.toString()));
            Integer totalSize = toolMapper.getAppraiseCNT( mapParam);
            if (totalSize == 0) {
                PageBean<Map<String, Object>> build = PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
                list3.add(build);
            } else {
//                int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
//                int offset = (pageNo - 1) * pageSize;
//                StringBuilder param1 = new StringBuilder();
//                param1.append(" limit " + pageSize + " offset " + offset);
//                List<Map<String, Object>> list = toolDao.getList(Const.SQL_DATA_GETGRADELIST.replaceAll("%param%", param.toString()).replaceAll("%param1%", param1.toString()));
                List<Map<String, Object>> list = toolMapper.getAppraiseList(mapParam);
                int i = offset + 1;
                for (Map<String, Object> stringObjectMap : list) {
                    stringObjectMap.put("ranking ", i);
                    i++;
                }
                PageBean<Map<String, Object>> build = PageBean.<Map<String, Object>>builder()
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1)
                        .page(pageNo)
                        .size(pageSize)
                        .build();
                list3.add(build);
            }
        }
//        StringBuilder param = new StringBuilder();
        //综合评价TOP 10
//        List<Map<String, Object>> list1 = toolDao.getList(Const.SQL_DATA_GETTENGRADE.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list1 = toolMapper.getTop10List();

        //综合评价BOTTOM 10
//        param.append("desc");
//        List<Map<String, Object>> list2 = toolDao.getList(Const.SQL_DATA_GETTENGRADE.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list2 = toolMapper.getTop10BOTTOMList();

        Map<String, List> map = new HashMap<>();
        map.put("TOP", list1);
        map.put("BOTTOM", list2);
        map.put("LIST", list3);
        return map;
    }
}
