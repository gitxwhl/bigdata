package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.mapper.RiskAccidentAnalysisMapper;
import com.raysdata.riskdataanalyzeserver.service.RiskAccidentAnalysisService;
import com.raysdata.riskdataanalyzeserver.utils.ObjectUtils;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RiskAccidentAnalysisServiceImpl implements RiskAccidentAnalysisService {
//    @Autowired
//    private RiskAccidentAnalysisDao riskAccidentAnalysisDao;

    @Autowired
    private RiskAccidentAnalysisMapper mapper;

    /*
     *设备事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","voltageCapacity":"","gridcorp":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getEquipAccidentList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String accidentGrade = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentGrade");
        String voltageCapacity = JSONObject.parseObject(paramJson).getJSONObject("params").getString("voltageCapacity");
        String gridcorp = JSONObject.parseObject(paramJson).getJSONObject("params").getString("gridcorp");
        String deviceClass = JSONObject.parseObject(paramJson).getJSONObject("params").getString("deviceClass");
        String startTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endTime");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else{
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(accidentGrade)) {
//                param.append(" and ACCIDENTGRADE = '").append(accidentGrade).append("'");
//            }
            List<String> columnTypeCodes=new ArrayList<>();
            if (ObjectUtils.isNotEmpty(voltageCapacity)) {
                //查询字典表
                List<Map<String,Object>> dataFromDictionaries = mapper.getDataFromDictionaries(voltageCapacity);
                if(dataFromDictionaries.size()==0){
                    columnTypeCodes.add("voltageCapacity");
                }else {
                    Object columnTypeCode = "";
                    for (int i=0 ; i < dataFromDictionaries.size() ;i++){
                        columnTypeCode += dataFromDictionaries.get(i).get("column_type_code") +",";
                        columnTypeCodes.add(String.valueOf(dataFromDictionaries.get(i).get("column_type_code")));
                    }
                }
            }
            String dataFromDictionaries=null;
            if (ObjectUtils.isNotEmpty(gridcorp)) {
                //查询字典表
//                String sql = "where column_code = 'DATAREPORT_ORG' and column_type_name like '%" + gridcorp + "%'";
//                Object dataFromDictionaries = riskAccidentAnalysisDao.getDataFromDictionaries(Const.SQL_DATA_DICTIONARIES.replaceAll("%param%", sql));
                 dataFromDictionaries = mapper.getDataFromDictionarie(gridcorp);
                 if(dataFromDictionaries==null){
                     dataFromDictionaries="gridcorp";
                 }
//                param.append(" and GRIDCORP = '").append(dataFromDictionaries).append("'");
            }
//            if (ObjectUtils.isNotEmpty(deviceClass)) {
////                param.append(" and DEVICECLASS = '").append(deviceClass).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(startTime) || ObjectUtils.isNotEmpty(endTime)) {
//                param.append(" and BGNTIME between STR_TO_DATE('").append(startTime).append("','%Y-%m-%d %H:%i:%s') and STR_TO_DATE('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
//            }

//            int totalSize = this.riskAccidentAnalysisDao.getEquipAccidentListCNT(Const.SQL_ACCIDENTDAO_GETEQUIPACCIDENTCNT.replaceAll("%param%",param.toString()));

            Map paramMap=new HashMap();
            paramMap.put("accidentGrade",accidentGrade);
            paramMap.put("gridcorp", gridcorp);
            paramMap.put("columnTypeCodes", columnTypeCodes);
            paramMap.put("dataFromDictionaries", dataFromDictionaries);
            paramMap.put("deviceClass", deviceClass);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            paramMap.put("voltageCapacity", voltageCapacity);

//            if(!voltageCapacity.isEmpty()&& !voltageCapacity.equals("±")){
//                paramMap.put("voltageCapacity",voltageCapacity);
//            }
//            int s = getCount(voltageCapacity,"±");
//            if(s >= 1){
//                paramMap.put("voltageCapacity", voltageCapacity);
//            }


            int totalSize = mapper.getEquipAccidentListCNT(paramMap);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            }else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
                paramMap.put("limit", size);
                paramMap.put("offset", offset);
//                List<Map<String, Object>> equipAccidentList = this.riskAccidentAnalysisDao.getEquipAccidentList(Const.SQL_ACCIDENTDAO_GETEQUIPACCIDENT.replaceAll("%param%", param.toString()));
                List<Map<String, Object>>  list= mapper.getEquipAccidentList(paramMap);
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskAccidentAnalysisDao.getEquipAccidentList(Const.SQL_ACCIDENTDAO_GETEQUIPACCIDENT.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(this.riskAccidentAnalysisDao.getEquipAccidentList(Const.SQL_ACCIDENTDAO_GETEQUIPACCIDENT.replaceAll("%param%", param.toString())).size())
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *人身事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","accidentClass":"","injuryDegree":"","startTime":"","endTime":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getPersonalAccidentList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String accidentGrade = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentGrade");
        String accidentClass = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentClass");
        String injuryDegree = JSONObject.parseObject(paramJson).getJSONObject("params").getString("injuryDegree");
        String startTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endTime");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else{
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(accidentGrade)) {
//                param.append(" and ACCIDENTGRADE = '").append(accidentGrade).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(accidentClass)) {
//                param.append(" and ACCIDENTCLASS = '").append(accidentClass).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(injuryDegree)) {
//                param.append(" and INJURY_DEGREE = '").append(injuryDegree).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(startTime) || ObjectUtils.isNotEmpty(endTime)) {
//                param.append(" and BGNTIME between STR_TO_DATE('").append(startTime).append("','%Y-%m-%d %H:%i:%s') and STR_TO_DATE('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
//            }
//            int totalSize = this.riskAccidentAnalysisDao.getPersonalAccidentListCNT(Const.SQL_ACCIDENTDAO_GETPERSONALACCIDENTCNT.replaceAll("%param%",param.toString()));
            Map paramMap=new HashMap();
            paramMap.put("accidentGrade", accidentGrade);
            paramMap.put("accidentClass", accidentClass);
            paramMap.put("injuryDegree", injuryDegree);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            int totalSize = mapper.getPersonalAccidentListCNT(paramMap);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            }else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
                paramMap.put("limit", size);
                paramMap.put("offset", offset);
//                List<Map<String, Object>> personalAccidentList = this.riskAccidentAnalysisDao.getPersonalAccidentList(Const.SQL_ACCIDENTDAO_GETPERSONALACCIDENT.replaceAll("%param%", param.toString()));
                List<Map<String, Object>>  list= mapper.getPersonalAccidentList(paramMap);
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskAccidentAnalysisDao.getPersonalAccidentList(Const.SQL_ACCIDENTDAO_GETPERSONALACCIDENT.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(this.riskAccidentAnalysisDao.getPersonalAccidentList(Const.SQL_ACCIDENTDAO_GETPERSONALACCIDENT.replaceAll("%param%", param.toString())).size())
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }


    /*
     *电网事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","voltageCapacity":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getPowerGridAccidentList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String accidentGrade = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentGrade");
        String voltageCapacity = JSONObject.parseObject(paramJson).getJSONObject("params").getString("voltageCapacity");
        String deviceClass = JSONObject.parseObject(paramJson).getJSONObject("params").getString("deviceClass");
        String startTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endTime");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else{
            List<String> dataFromDictionaries=null;
            if (ObjectUtils.isNotEmpty(voltageCapacity)) {
                //查询字典表
                 dataFromDictionaries = mapper.getDataFromDictionaries2(voltageCapacity);
                 if(dataFromDictionaries.size()==0){
                     dataFromDictionaries.add("dataFromDictionaries");
                 }
            }

            Map paramMap=new HashMap();
            paramMap.put("accidentGrade", accidentGrade);
            paramMap.put("dataFromDictionaries", dataFromDictionaries);
            paramMap.put("deviceClass", deviceClass);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);

            int totalSize = mapper.getPowerGridAccidentListCNT(paramMap);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            }else {
                int offset = (page - 1) * size;
                paramMap.put("limit", size);
                paramMap.put("offset", offset);
                List<Map<String, Object>> list = mapper.getPowerGridAccidentList(paramMap);
                return PageBean.<Map<String, Object>>builder()
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }


    public static int getCount(String source, String sub) {
                int count = 0;
                int length = source.length() - sub.length();
                for (int i = 0; i < length; i++) {
            String sourceBak = source.substring(i, i + sub.length());
            int index = sourceBak.indexOf(sub);
                if (index != -1) {
                            count++;
                    }
                }
                return count;
    }


    /*
     *网络信息事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","accidentClass":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getInformAccidentList(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String accidentGrade = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentGrade");
        String accidentClass = JSONObject.parseObject(paramJson).getJSONObject("params").getString("accidentClass");
        String deviceClass = JSONObject.parseObject(paramJson).getJSONObject("params").getString("deviceClass");
        String startTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endTime");
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else{
            StringBuilder param = new StringBuilder();
            if (ObjectUtils.isNotEmpty(accidentGrade)) {
                param.append(" and ACCIDENTGRADE = '").append(accidentGrade).append("'");
            }
            if (ObjectUtils.isNotEmpty(accidentClass)) {
                param.append(" and ACCIDENTCLASS = '").append(accidentClass).append("'");
            }
            if (ObjectUtils.isNotEmpty(deviceClass)) {
                param.append(" and DEVICECLASS = '").append(deviceClass).append("'");
            }
            if (ObjectUtils.isNotEmpty(startTime) || ObjectUtils.isNotEmpty(endTime)) {
                param.append(" and BGNTIME between STR_TO_DATE('").append(startTime).append("','%Y-%m-%d %H:%i:%s') and STR_TO_DATE('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
            }
//            int totalSize = this.riskAccidentAnalysisDao.getInformAccidentListCNT(Const.SQL_ACCIDENTDAO_GETINFORMACCIDENTCNT.replaceAll("%param%",param.toString()));
            Map paramMap=new HashMap();
            paramMap.put("accidentGrade", accidentGrade);
            paramMap.put("accidentClass", accidentClass);
            paramMap.put("deviceClass", deviceClass);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            int totalSize = mapper.getInformAccidentListCNT(paramMap);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            }else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                param.append(" limit " + size + " offset " + offset);
                paramMap.put("limit", size);
                paramMap.put("offset", offset);
                List<Map<String, Object>> list= mapper.getInformAccidentList(paramMap);
//                List<Map<String, Object>> informAccidentList = this.riskAccidentAnalysisDao.getInformAccidentList(Const.SQL_ACCIDENTDAO_GETINFORMACCIDENT.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskAccidentAnalysisDao.getInformAccidentList(Const.SQL_ACCIDENTDAO_GETINFORMACCIDENT.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(this.riskAccidentAnalysisDao.getInformAccidentList(Const.SQL_ACCIDENTDAO_GETINFORMACCIDENT.replaceAll("%param%", param.toString())).size())
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *安全事故分类统计
     * paramJson:{"timeType":""}
     * */
    @Override
    public Map<String, Integer> getAccidentStatistics(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int timeType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("timeType"));
        Map<String,Integer> map =null;
        if(timeType == 0){//今日
            Integer equipAccidentCount = mapper.getEquipAccidentListCNT1();
            Integer personalAccidentCount = mapper.getPersonalAccidentListCNT1();
            Integer powerGridAccidentCount = mapper.getPowerGridAccidentListCNT1();
            Integer informAccidentCount = mapper.getInformAccidentListCNT1();

            map = new LinkedHashMap<>();
            map.put("informAccident",informAccidentCount);
            map.put("personalAccident",personalAccidentCount);
            map.put("powerGridAccident",powerGridAccidentCount);
            map.put("equipAccident",equipAccidentCount);
            return map;
        }else if(timeType == 1){//本周
            Integer equipAccidentCount = mapper.getEquipAccidentListCNT2();
            Integer personalAccidentCount = mapper.getPersonalAccidentListCNT2();
            Integer powerGridAccidentCount = mapper.getPowerGridAccidentListCNT2();
            Integer informAccidentCount = mapper.getInformAccidentListCNT2();

            map = new LinkedHashMap<>();
            map.put("informAccident",informAccidentCount);
            map.put("personalAccident",personalAccidentCount);
            map.put("powerGridAccident",powerGridAccidentCount);
            map.put("equipAccident",equipAccidentCount);

        }else if(timeType == 2){//本月
            Integer equipAccidentCount = mapper.getEquipAccidentListCNT3();
            Integer personalAccidentCount = mapper.getPersonalAccidentListCNT3();
            Integer powerGridAccidentCount = mapper.getPowerGridAccidentListCNT3();
            Integer informAccidentCount = mapper.getInformAccidentListCNT3();

            map = new LinkedHashMap<>();
            map.put("informAccident",informAccidentCount);
            map.put("personalAccident",personalAccidentCount);
            map.put("powerGridAccident",powerGridAccidentCount);
            map.put("equipAccident",equipAccidentCount);

        }else if(timeType == 3){//本年
            Integer equipAccidentCount = mapper.getEquipAccidentListCNT4();
            Integer personalAccidentCount = mapper.getPersonalAccidentListCNT4();
            Integer powerGridAccidentCount = mapper.getPowerGridAccidentListCNT4();
            Integer informAccidentCount = mapper.getInformAccidentListCNT4();

            map = new LinkedHashMap<>();
            map.put("informAccident",informAccidentCount);
            map.put("personalAccident",personalAccidentCount);
            map.put("powerGridAccident",powerGridAccidentCount);
            map.put("equipAccident",equipAccidentCount);

        }
        return map;
    }

    /*
     *按类型同比统计
     * paramJson:{"type":""}
     * */
    @Override
    public Map<String, Map> getAccidentCountByMonth(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int type = Integer.parseInt(JSONObject.parseObject(paramJson).getString("type"));
        Calendar now = Calendar.getInstance();
        StringBuffer param1 = new StringBuffer().append(now.get(Calendar.YEAR));
        StringBuffer param2 = new StringBuffer().append(now.get(Calendar.YEAR)-1);
        Map<String,Map> map = new LinkedHashMap<>();
        StringBuffer param = new StringBuffer();

        /*if(type == 0){//设备事件
            param.append("bd_inc_equipaccident");
        }else if(type == 1){//人身事件
            param.append("bd_inc_personalaccident");
        }else if(type == 2){//电网事件
            param.append("bd_inc_powergridaccident");
        }else if(type == 3){//网络信息事件
            param.append("bd_inc_informaccident");
        }*/




        Map<String,String> map3 = new LinkedHashMap<>();
        Map<String,String> map4 = new LinkedHashMap<>();

//        Map map1 = mapper.getMap(param.toString());
        Map map1 = mapper.getMap(Integer.toString(type));

        Map map2 = mapper.getMap2(Integer.toString(type));


        map3.put("month1",map1.get("month1").toString());
        map3.put("month2",map1.get("month2").toString());
        map3.put("month3",map1.get("month3").toString());
        map3.put("month4",map1.get("month4").toString());
        map3.put("month5",map1.get("month5").toString());
        map3.put("month6",map1.get("month6").toString());
        map3.put("month7",map1.get("month7").toString());
        map3.put("month8",map1.get("month8").toString());
        map3.put("month9",map1.get("month9").toString());
        map3.put("month10",map1.get("month10").toString());
        map3.put("month11",map1.get("month11").toString());
        map3.put("month12",map1.get("month12").toString());
        map4.put("month1",map2.get("month1").toString());
        map4.put("month2",map2.get("month2").toString());
        map4.put("month3",map2.get("month3").toString());
        map4.put("month4",map2.get("month4").toString());
        map4.put("month5",map2.get("month5").toString());
        map4.put("month6",map2.get("month6").toString());
        map4.put("month7",map2.get("month7").toString());
        map4.put("month8",map2.get("month8").toString());
        map4.put("month9",map2.get("month9").toString());
        map4.put("month10",map2.get("month10").toString());
        map4.put("month11",map2.get("month11").toString());
        map4.put("month12",map2.get("month12").toString());

        map.put("thisYear",map3);
        map.put("lastYear",map4);
        return map;
    }


    /*
     *按类型环比统计
     * */
    @Override
    public Map<String, Map> getAccidentCount() {
        Calendar now = Calendar.getInstance();
        StringBuffer param1 = new StringBuffer().append(now.get(Calendar.YEAR));
//        Map equipAccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNT.replaceAll("%param%","bd_inc_equipaccident").replaceAll("%param1%",param1.toString()));
//        Map informAccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNT.replaceAll("%param%","bd_inc_informaccident").replaceAll("%param1%",param1.toString()));
//        Map personalAccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNT.replaceAll("%param%","bd_inc_personalaccident").replaceAll("%param1%",param1.toString()));
//        Map powergridAccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNT.replaceAll("%param%","bd_inc_powergridaccident").replaceAll("%param1%",param1.toString()));
        Map<String,String> mapequipAccident = new LinkedHashMap<>();

        Map<String,String> mapinformAccident = new LinkedHashMap<>();

        Map<String,String> mappersonalAccident = new LinkedHashMap<>();

        Map<String,String> mappowergridAccident = new LinkedHashMap<>();
        Map equipAccident = mapper.getMap("0");

        mapequipAccident.put("month1",equipAccident.get("month1").toString());
        mapequipAccident.put("month2",equipAccident.get("month2").toString());
        mapequipAccident.put("month3",equipAccident.get("month3").toString());
        mapequipAccident.put("month4",equipAccident.get("month4").toString());
        mapequipAccident.put("month5",equipAccident.get("month5").toString());
        mapequipAccident.put("month6",equipAccident.get("month6").toString());
        mapequipAccident.put("month7",equipAccident.get("month7").toString());
        mapequipAccident.put("month8",equipAccident.get("month8").toString());
        mapequipAccident.put("month9",equipAccident.get("month9").toString());
        mapequipAccident.put("month10",equipAccident.get("month10").toString());
        mapequipAccident.put("month11",equipAccident.get("month11").toString());
        mapequipAccident.put("month12",equipAccident.get("month12").toString());
        Map informAccident = mapper.getMap("3");
        mapinformAccident.put("month1",informAccident.get("month1").toString());
        mapinformAccident.put("month2",informAccident.get("month2").toString());
        mapinformAccident.put("month3",informAccident.get("month3").toString());
        mapinformAccident.put("month4",informAccident.get("month4").toString());
        mapinformAccident.put("month5",informAccident.get("month5").toString());
        mapinformAccident.put("month6",informAccident.get("month6").toString());
        mapinformAccident.put("month7",informAccident.get("month7").toString());
        mapinformAccident.put("month8",informAccident.get("month8").toString());
        mapinformAccident.put("month9",informAccident.get("month9").toString());
        mapinformAccident.put("month10",informAccident.get("month10").toString());
        mapinformAccident.put("month11",informAccident.get("month11").toString());
        mapinformAccident.put("month12",informAccident.get("month12").toString());
        Map personalAccident = mapper.getMap("1");
        mappersonalAccident.put("month1",personalAccident.get("month1").toString());
        mappersonalAccident.put("month2",personalAccident.get("month2").toString());
        mappersonalAccident.put("month3",personalAccident.get("month3").toString());
        mappersonalAccident.put("month4",personalAccident.get("month4").toString());
        mappersonalAccident.put("month5",personalAccident.get("month5").toString());
        mappersonalAccident.put("month6",personalAccident.get("month6").toString());
        mappersonalAccident.put("month7",personalAccident.get("month7").toString());
        mappersonalAccident.put("month8",personalAccident.get("month8").toString());
        mappersonalAccident.put("month9",personalAccident.get("month9").toString());
        mappersonalAccident.put("month10",personalAccident.get("month10").toString());
        mappersonalAccident.put("month11",personalAccident.get("month11").toString());
        mappersonalAccident.put("month12",personalAccident.get("month12").toString());
        Map powergridAccident = mapper.getMap("2");
        mappowergridAccident.put("month1",powergridAccident.get("month1").toString());
        mappowergridAccident.put("month2",powergridAccident.get("month2").toString());
        mappowergridAccident.put("month3",powergridAccident.get("month3").toString());
        mappowergridAccident.put("month4",powergridAccident.get("month4").toString());
        mappowergridAccident.put("month5",powergridAccident.get("month5").toString());
        mappowergridAccident.put("month6",powergridAccident.get("month6").toString());
        mappowergridAccident.put("month7",powergridAccident.get("month7").toString());
        mappowergridAccident.put("month8",powergridAccident.get("month8").toString());
        mappowergridAccident.put("month9",powergridAccident.get("month9").toString());
        mappowergridAccident.put("month10",powergridAccident.get("month10").toString());
        mappowergridAccident.put("month11",powergridAccident.get("month11").toString());
        mappowergridAccident.put("month12",powergridAccident.get("month12").toString());
        Map<String,Map> map = new LinkedHashMap<>();
        map.put("equipAccident",mapequipAccident);
        map.put("informAccident",mapinformAccident);
        map.put("personalAccident",mappersonalAccident);
        map.put("powergridAccident",mappowergridAccident);
        return map;
    }

    //趋势预测分析
    @Override
    public Map<String, Object> getTendencyAnalyze() {
        //人身事故视图表 加s的是预测值
//        Map personalaccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTS.replaceAll("%param%","bd_inc_personalaccident"));
//        Map personalaccidents = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTSY.replaceAll("%param%","bd_inc_personalaccident"));
        Map personalaccident = mapper.getTendencyAnalyzeMap1();
        Map personalaccidents = mapper.getTendencyAnalyzeMaps1();

        //设备事故视图表
//        Map equipAccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTS.replaceAll("%param%","bd_inc_equipaccident"));
//        Map equipAccidents = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTSY.replaceAll("%param%","bd_inc_equipaccident"));
        Map equipAccident = mapper.getTendencyAnalyzeMap2();
        Map equipAccidents = mapper.getTendencyAnalyzeMaps2();

        //电网事故视图表
//        Map powergridaccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTS.replaceAll("%param%","bd_inc_powergridaccident"));
//        Map powergridaccidents = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTSY.replaceAll("%param%","bd_inc_powergridaccident"));
        Map powergridaccident = mapper.getTendencyAnalyzeMap3();
        Map powergridaccidents = mapper.getTendencyAnalyzeMaps3();

        //信息事故视图表
//        Map informaccident = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTS.replaceAll("%param%","bd_inc_informaccident"));
//        Map informaccidents = riskAccidentAnalysisDao.getMap(Const.SQL_ACCIDENTDAO_GETACCIDENTCNTSY.replaceAll("%param%","bd_inc_informaccident"));
        Map informaccident = mapper.getTendencyAnalyzeMap4();
        Map informaccidents = mapper.getTendencyAnalyzeMaps4();

        List list = new ArrayList();
        //获取当日以及前一个月内的时间
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        list.add(sdf.format(date));
        for (int i = 0; i < 30 ; i++) {
            calendar.add(Calendar.DATE,-1);
            list.add(sdf.format(calendar.getTime()));
        }
        Collections.reverse(list);

        Map<String,Object> map = new HashMap<>();
        map.put("date",list);
        map.put("personalaccident",personalaccident);
        map.put("personalaccidents",personalaccidents);
        map.put("equipAccident",equipAccident);
        map.put("equipAccidents",equipAccidents);
        map.put("powergridaccident",powergridaccident);
        map.put("powergridaccidents",powergridaccidents);
        map.put("informaccident",informaccident);
        map.put("informaccidents",informaccidents);
        return map;
    }
    //按等级统计---------------------------------------------------------------------------------------------------------------

    /*paramJson:{"type":""}*/
    @Override
    public List<Map<String, Object>> getAccidentGrade(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int  accidentType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("type"));
//        List<Map<String, Object>> equipAccidentGradeList = riskAccidentAnalysisDao.getEquipAccidentGrade(Const.SQL_EQUIPACCIDENTDAO_GETACCIDENTGRADE);
//        List<Map<String, Object>> informAccidentGradeList = riskAccidentAnalysisDao.getInformAccidentGrade(Const.SQL_INFORMACCIDENTDAO_GETACCIDENTGRADE);
//        List<Map<String,Object>> personalAccidentGradeList = riskAccidentAnalysisDao.getPersonalAccidentGrade(Const.SQL_PERSONALACCIDENTDAO_GETACCIDENTGRADE);
//        List<Map<String,Object>> powergridAccidentGradeList = riskAccidentAnalysisDao.getPowergridAccidentGrade(Const.SQL_POWERGRIDACCIDENTDAO_GETACCIDENTGRADE);

        if(accidentType ==0){
//            return equipAccidentGradeList;
            return mapper.getEquipAccidentGrade();
        }else if(accidentType ==1){
//            return powergridAccidentGradeList;
            return mapper.getPowergridAccidentGrade();
        }else if(accidentType ==2){
//            return personalAccidentGradeList;
            return mapper.getPersonalAccidentGrade();
        }else if(accidentType ==3){
//            return informAccidentGradeList;
            return mapper.getInformAccidentGrade();
        }
        return null;
    }

    //按单位统计----------------------------------------------------------------------------------------------------------------
    @Override
    public List<Map<String, Object>> getAccidentGridcrop() {
//        List<Map<String, Object>> accidentGridcropList = riskAccidentAnalysisDao.getAccidentGridcrop(Const.SQL_GETACCIDENTGRIDCROP);
//        return accidentGridcropList;
        return mapper.getAccidentGridcrop();
    }
    //按电压等级统计-----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Map<String, Object>> getAccidentVoltageLevel() {
//        List<Map<String, Object>> accidentVoltageLevelList = riskAccidentAnalysisDao.getAccidentVoltageLevel(Const.SQL_GETACCIDENTVOLTAGELEVEL);
//        return accidentVoltageLevelList;
        return mapper.getAccidentVoltageLevel();
    }

    //按事故原因TOP10---------------------------------------------------------------------------------------------------------------------------------
    /*paramJson:{"type":""}*/
    @Override
    public List<Map<String, Object>> getAccidentDesc(String paramJson){
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int  reasonType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("type"));
//        List<Map<String, Object>> equioAccidentDescList = riskAccidentAnalysisDao.getEquipAccidentDesc(Const.SQL_EQUIPACCIDENTDAO_GETACCIDENTDESC);
//        List<Map<String, Object>> powergridAccidentDescList = riskAccidentAnalysisDao.getPowergridAccidentDesc(Const.SQL_POWERGRIDACCIDENTDAO_GETACCIDENTDESC);
//        List<Map<String, Object>> personalAccidentDescList = riskAccidentAnalysisDao.getPersonalAccidentDesc(Const.SQL_PERSONALACCIDENTDAO_GETACCIDENTDESC);
//        List<Map<String, Object>> informAccidentDescList = riskAccidentAnalysisDao.getInformAccidentDesc(Const.SQL_INFORMALACCIDENTDAO_GETACCIDENTDESC);

        if(reasonType == 0){
//            return equioAccidentDescList;
            return mapper.getEquipAccidentDesc();
        }else if(reasonType == 1){
//            return powergridAccidentDescList;
            return mapper.getPowergridAccidentDesc();
        }else if(reasonType == 2){
//            return personalAccidentDescList;
            return mapper.getPersonalAccidentDesc();
        }else if(reasonType == 3){
//            return informAccidentDescList;
            return mapper.getInformAccidentDesc();
        }
        return null;
    }

    //获取字典定义
    @Override
    public Map<String, List<JSONObject>> getSrpRiskSysTab() {
        Map<String, List<JSONObject>> resultMap = new LinkedHashMap<>();

        //事故等级（设备事件，人身事件，电网事件，网络信息事件）
        List<JSONObject> accidentGrade = new ArrayList<>();

        //设备分类（设备事件，电网事件）
        List<JSONObject> deviceClass = new ArrayList<>();

        //事故类别（人身事件）
        List<JSONObject> accidentClass = new ArrayList<>();

        //伤害程度（人身事件）
        List<JSONObject> injuryDegree = new ArrayList<>();

        //事件分类（网络信息事件）
        List<JSONObject> informAccidentClass = new ArrayList<>();

        //设备分类（网络信息事件）
        List<JSONObject> informDeviceClass = new ArrayList<>();


//        List<Map> resultList = riskAccidentAnalysisDao.getSrpRiskSysTab(Const.SQL_DATA_GETDICTIONARIES);
        List<Map<String, Object>> resultList = mapper.getSrpRiskSysTab();
        resultList.stream().forEach(map -> {
            JSONObject jsonObject = JSON.parseObject("{'id':'" + map.get("column_type_code") + "','text':'" + map.get("column_type_name") + "'}");
            if ("ACCIDENTGRADE".equals(map.get("column_code"))) {

                accidentGrade.add(jsonObject);
            }
            if ("DEVICECLASS".equals(map.get("column_code"))) {
                deviceClass.add(jsonObject);
            }
            if ("ACCIDENTCLASS".equals(map.get("column_code"))) {
                accidentClass.add(jsonObject);
            }
            if ("INJURY_DEGREE".equals(map.get("column_code"))) {
                injuryDegree.add(jsonObject);
            }
            if ("INFORMACCIDENTCLASS".equals(map.get("column_code"))) {
                informAccidentClass.add(jsonObject);
            }
            if ("INFORMDEVICECLASS".equals(map.get("column_code"))) {
                informDeviceClass.add(jsonObject);
            }

        });
        resultMap.put("accidentGrade", accidentGrade);
        resultMap.put("deviceClass", deviceClass);
        resultMap.put("accidentClass", accidentClass);
        resultMap.put("injuryDegree", injuryDegree);
        resultMap.put("informAccidentClass", informAccidentClass);
        resultMap.put("informDeviceClass", informDeviceClass);
        return resultMap;
    }



    /*
     *事件数量统计
     * paramJson:{"timeType":""}
     * */
    @Override
    public Map<String, Integer> getAccidentStatistics1(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int timeType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("timeType"));
        Map<String,Integer> map =null;
        if(timeType == 0) {//今日
            Integer equipAccidentCount = mapper.getAllEquipAccidentListCNT4();

            Integer personalAccidentCount = mapper.getAllPersonalAccidentListCNT4();

            Integer powerGridAccidentCount = mapper.getAllPowerGridAccidentListCNT4();

            Integer informAccidentCount = mapper.getAllInformAccidentListCNT4();

            map = new LinkedHashMap<>();
            map.put("informAccident", informAccidentCount);
            map.put("personalAccident", personalAccidentCount);
            map.put("powerGridAccident", powerGridAccidentCount);
            map.put("equipAccident", equipAccidentCount);
        }
        return map;
    }



}
