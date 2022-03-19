package com.raysdata.riskdataanalyzeserver.service.impl;
import org.apache.commons.lang3.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.bean.Instanceinfo;
import com.raysdata.riskdataanalyzeserver.bean.Knowledge;
import com.raysdata.riskdataanalyzeserver.bean.SrpModeling;
import com.raysdata.riskdataanalyzeserver.mapper.AtlasMapper;
import com.raysdata.riskdataanalyzeserver.mapper.RelationinfoMapper;
import com.raysdata.riskdataanalyzeserver.service.AtlasService;
import com.raysdata.riskdataanalyzeserver.utils.BusinessLog;
import com.raysdata.riskdataanalyzeserver.utils.ObjectUtils;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import com.raysdata.riskdataanalyzeserver.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@SuppressWarnings
@Service
public class AtlasServiceImpl implements AtlasService {

//    @Autowired
//    private AtlasDao atlasDao;

    @Autowired
    private AtlasMapper mapper;
    @Autowired
    private RelationinfoMapper relationinfoMapper;

    //------------------------------------------------安全风险知识图谱------------------------------------------------------------
    /*
     *页面加载接口
     *知识图谱建模查询
     * paramJson:{"typeName":""}
     * */
    @Override
    public PageBean<Map<String, Object>> getModeling(String paramJson) {
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        String typeName = JSONObject.parseObject(paramJson).getString("typeName");
        StringBuilder param = new StringBuilder();
        Map mapParam=new HashMap();
//        mapParam.put("typeName", typeName);
         int i= "作业计划".indexOf(typeName);
        if("作业计划".indexOf(typeName)>=0&& !typeName.isEmpty()){
            mapParam.put("typeName", "1");
        }
        if("隐患".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "5");
        }
        if("人员".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "3");
        }
        if("风险".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "4");
        }
        if("外包单位".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "2");
        }
        if("违章信息".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "7");
        }
        if("事件".indexOf(typeName)>=0&&!typeName.isEmpty()){
            mapParam.put("typeName", "6");
        }
        if("事件".indexOf(typeName)==-1&&"违章信息".indexOf(typeName)==-1&&"外包单位".indexOf(typeName)==-1&&"风险".indexOf(typeName)==-1&&"人员".indexOf(typeName)==-1&&"隐患".indexOf(typeName)==-1&&"作业计划".indexOf(typeName)==-1&&!typeName.isEmpty()){
            mapParam.put("typeName", "8");
        }
//        if("事件".indexOf(typeName)>=0&&"违章信息".indexOf(typeName)>=0&&"外包单位".indexOf(typeName)>=0&&"风险".indexOf(typeName)>=0&&"人员".indexOf(typeName)>=0&&"隐患".indexOf(typeName)>=0&&"作业计划".indexOf(typeName)>=0){
//
//        }




        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            int totalSize = mapper.getListCount(mapParam);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            } else {
                int offset = (page - 1) * size;
                mapParam.put("offset", offset);
                mapParam.put("size", size);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> manageList = this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder().
//                        .content(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString())))
                        content(mapper.getList(mapParam))
//                        .elementTotalSize(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(mapper.getList(mapParam).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }








    /*
     *删除接口
     *知识图谱建模删除
     * paramJson:{"typeId":""}
     * */
    @Transactional
    @Override
    public String deleteModeing(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String typeId = JSONObject.parseObject(paramJson).getString("typeId");

        SrpModeling srpModeling = mapper.findSrpModeling(typeId);
        if(srpModeling==null){
            return "数据已被删除，请刷新重试!";
        }
        StringBuilder param = new StringBuilder();
        param.append("and TYPE_ID = '").append(typeId).append("'");
        //根据建模ID查询编码
//        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODE.replaceAll("%param%", typeId));
        String typeCode = mapper.getTypeId(typeId);
            String typename= mapper.getModelTyteName(typeCode);
            String typenames=mapper.getModelName(typename);
            BusinessLog.Import_TYPENAME=typenames;




        // 通过ID删除建模
//        int i = atlasDao.rowsNum(Const.SQL_DATA_DELETEMODELING.replaceAll("%param%",param.toString()));
        int i = mapper.rowsNum(typeId);

        if(i > 0){
            // 查询实例信息表中对应建模ID的实例ID
//            List<Map<String, Object>> instanceIdList = atlasDao.getList(Const.SQL_DATA_GETINSTANCEID.replaceAll("%param%", typeCode));
            List<Map<String, Object>> instanceIdList = mapper.getList1(typeCode);
            if(instanceIdList != null && !instanceIdList.isEmpty()){
                List idList = new ArrayList();
                for (Map<String, Object> stringObjectMap : instanceIdList) {
                    idList.add(stringObjectMap.get("INSTANCE_ID"));
                }
                param = new StringBuilder();
                List<String> instanceIds=new ArrayList<>();
                for (Object o : idList) {
                    param.append("'").append(o).append("',");
                    instanceIds.add(String.valueOf(o));
                }
//                String sql = param.substring(0, param.length() - 1);
                // 删除实例关系信息表中对应实例ID的数据
//                atlasDao.rowsNum(Const.SQL_DATA_DELETEINSTANCERELATIONINFO.replaceAll("%param%",sql));
                mapper.rowsNum1(instanceIds);
            }
            param = new StringBuilder();
            param.append("INSTANCE_SOURCE = '").append(typeCode).append("'");
            // 删除实例信息表中对应建模编码的数据
//            atlasDao.rowsNum(Const.SQL_DATA_DELETEINSTANCEINFO.replaceAll("%param%", param.toString()));
            mapper.rowsNum2(typeCode);
            param = new StringBuilder();
            param.append("MODELING_GRAPH_ID = '").append(typeId).append("'");
            // 删除关系表中对应建模ID的数据
//            atlasDao.rowsNum(Const.SQL_DATA_DELETERELATIONINFO.replaceAll("%param%", param.toString()));
            mapper.rowsNum3(typeId);
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    /*
     *新增接口
     *知识图谱建模新增
     * paramJson:{"params":{"typeName":"","typeCode":"","typeDescribe":"","typeIcon":""}}
     * */
    @Transactional
    @Override
    public String insertModeing(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String typeName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeName");
        String typeCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeCode");
        String typeDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeDescribe");
        String typeIcon = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeIcon");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        BusinessLog.SAVE_PARAMTER="新增";
        // 获取主键
        StringBuilder param = new StringBuilder();
        param.append("'").append(typeName).append("','").append(typeCode).append("','").append(typeIcon).append("','").append(typeDescribe).append("','").append(dateString).append("'");
//        int i = atlasDao.rowsNum(Const.SQL_DATA_INSERTMODELING.replaceAll("%param%", param.toString()));
        List modeingList=null;
        try{
             modeingList = mapper.getModeingList();
        }catch (Exception e){
            e.printStackTrace();
        }
        Map map=null;
        for (Object modeing : modeingList) {
            if (typeName.equalsIgnoreCase(modeing.toString())){
                return "新增失败";
            }
        }
            map=new HashMap();
            map.put("typeName", typeName);
            map.put("typeCode",typeCode );
            map.put("typeIcon", typeIcon);
            map.put("typeDescribe", typeDescribe);
            map.put("dateString",dateString );
        int i = mapper.addModeing(map);

        if( i > 0){
            return "新增成功";
        }else {
            return  "新增失败";
        }
    }









    /*
     *生成编号接口
     * */
    @Override
    public String generatedNumber() {
            String number = "ZS";
            NumberFormat f=new DecimalFormat("00000");
            // 查询数据库中的编号
//            if(atlasDao.getCount(Const.SQL_DATA_GETMODELINGCNT) == 0){
            if(mapper.getModeingCount() == 0){
                String format = f.format(1);
                number = number + format;
            }else {
//                String typeId = atlasDao.getTypeId(Const.SQL_DATA_GETMAXTYPEID);
                String typeId = mapper.getMaxId();
                Integer a = Integer.parseInt(typeId) ;
                String format = f.format(a+1);
                number = number + format;
            }
        return number;
    }

    /*
     *编辑接口
     *知识图谱建模编辑
     * paramJson:{"params":{"typeId":"","typeName":"","typeCode":"","typeDescribe":"","typeIcon":""}}
     * */
    @Override
    public String updateModeling(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String typeId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeId");
        String typeName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeName");
        String typeCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeCode");
        String typeDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeDescribe");
        String typeIcon = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeIcon");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        //查询知识图谱建模表
        Map<String,Object> mapZhi = new LinkedHashMap<>();
        mapZhi.put("typeName",typeName);
        int codelength =typeCode.length();
        int declength  =typeDescribe.length();
        if(codelength>50){
            return "编码长度不能超过50！";
        }
        if(declength>200){
            return "描述长度不能超过200！";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("typeName", typeName);
        map.put("typeCode", typeCode);
        map.put("typeIcon", typeIcon);
        map.put("typeDescribe", typeDescribe);
        map.put("dateString", dateString);
        map.put("typeId", typeId);
        //根据编号查询知识图谱建模
//        List<Map<String,Object>> srpModelings= mapper.getSrpModelings(map);
        SrpModeling srpModelings= mapper.getSrpModelings(typeCode);

        Object obj = JSONArray.toJSON(srpModelings);
        BusinessLog.modify=obj.toString();



        int i = mapper.updateModeling(map);
        if( i > 0){
            return "编辑成功";
        }else {
            return "编辑失败";
        }
    }

    /*
     *查看详情接口
     *知识图谱建模查看
     * paramJson:{"typeId":""}
     * */
    @Override
    public Map<String, Object> getModenlingDetails(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String typeId = JSONObject.parseObject(paramJson).getString("typeId");
        StringBuilder param = new StringBuilder();
        param.append("and TYPE_ID = '").append(typeId).append("'");
//        List<Map<String, Object>> list = atlasDao.getList(Const.SQL_DATA_MODELING.replaceAll("%param%", param.toString()));
        Map paramMap=new HashMap();
        paramMap.put("typeId", typeId);
        List<Map<String, Object>> list = mapper.getListdetails(paramMap);
        Map map = list.get(0);
        return map;
    }



    //----------------------------------------------知识获取------------------------------------------------------------
    /*
     *知识获取
     *关系表查询
     * */
    @Override
    public PageBean<Map<String, Object>>getRelationInfo(String paramJson) {
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        StringBuilder param = new StringBuilder();
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            Integer totalSize = mapper.getRelationInfoCount();
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            } else {
                int offset = (page - 1) * size;
                param.append(" limit " + size + " offset " + offset);
                Map paramMap=new HashMap();
                paramMap.put("limit", size);
                paramMap.put("offset", offset);
                List<Map<String, Object>> list= mapper.getRelationInfoList(paramMap);
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

    /*
     *知识获取
     *关系表编号生成
     * */
    @Override
    public String relationNumber() {
        String number = "GX";
        NumberFormat f=new DecimalFormat("000000");
        if(mapper.getRelationInfoCount() == 0){
            String format = f.format(1);
            number = number + format;
        }else {
            String typeId = mapper.getRelationNumber();
            Integer a = Integer.parseInt(typeId) ;
            String format = f.format(a+1);
            number = number + format;
        }
        return number;
    }

    /*
     *新增接口
     *知识获取新增关系
     * paramJson:{"params":{"relationName":"","relationCode":"","sourceType":"","targetType":"","relationType":"","relationDescribe":""}}
     * */
    @Transactional
    @Override
    public Map<String,String> insertRelationInfo(String paramJson) {
        HashMap<String,String> resultMap = new HashMap<>();
        String relationName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationName");
        String relationCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationCode");
        String sourceType = JSONObject.parseObject(paramJson).getJSONObject("params").getString("sourceType");
        String targetType = JSONObject.parseObject(paramJson).getJSONObject("params").getString("targetType");
        String relationType = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationType");
        String relationDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationDescribe");
        String modelingGraphId = mapper.getModelingGraphId(sourceType);
         int a=relationDescribe.length();
         int b=relationCode.length();
        if(a>200){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:描述长度不能超过200!");
            return resultMap;
        }
         if(b>50){
             resultMap.put("code","500");
             resultMap.put("msg","新增失败:编码长度不能超过50");
             return resultMap;
         }

        if(StringUtils.isNotEmpty(relationCode)){
            int codeCount = mapper.checkRelationCode(relationCode);
            if(codeCount>0){
                resultMap.put("code","500");
                resultMap.put("msg","新增失败:新增编码已存在");
                return resultMap;
            }
        }
        if(StringUtils.isEmpty(relationName)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增参数名称不能为空");
            return resultMap;
        }else if(StringUtils.isEmpty(relationCode)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增参数编码不能为空");
            return resultMap;
        }else if(StringUtils.isEmpty(sourceType)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增参数源类别不能为空");
            return resultMap;
        }else if(StringUtils.isEmpty(targetType)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增参数目标类别不能为空");
            return resultMap;
        }else if(StringUtils.isEmpty(relationType)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增参数关联关系类别不能为空");
            return resultMap;
        }else if(StringUtils.isEmpty(modelingGraphId)){
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:新增关联知识图谱建模获取失败");
            return resultMap;
        }
        Map paramMap=new HashMap();
        paramMap.put("modelingGraphId", modelingGraphId);
        paramMap.put("relationName",relationName );
        paramMap.put("sourceType", sourceType);
        paramMap.put("targetType", targetType);
        paramMap.put("relationType", relationType);
        paramMap.put("relationDescribe", relationDescribe);
        paramMap.put("relationCode", relationCode);
        //更新前数据条数
        Integer beforCount= mapper.getRelationInfoCount();

        int j = mapper.insertRelationInfo(paramMap);

        //更新之后数据条数
        Integer afterCount= mapper.getRelationInfoCount();
        //添加知识融合列表
        Map<String,Object> map1=new HashMap<>();
//        LocalDateTime.now()
        map1.put("k1", "关系");
        map1.put("k2", "1");
        map1.put("k3", beforCount);
        map1.put("k4",afterCount);
        map1.put("k5","srp_bd_kg_relationinfo");
        try {
            relationinfoMapper.instKnowlwdgeFusion(map1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        if( j > 0){
            resultMap.put("code","200");
            resultMap.put("msg","新增成功");
            return resultMap;
        }else {
            resultMap.put("code","500");
            resultMap.put("msg","新增失败:系统异常");
            return resultMap;
        }
    }

    /*
     *知识获取界面
     *实例信息查询
     * paramJson:{"page":"", "size":"", "params":{"modelingId":"","instanceName":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getInstanceInfo(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        //根据类别名称查询编码
//        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODEBYID.replaceAll("%param%", modelingId));

        String typeCode = mapper.getTypeCode(modelingId);



        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else{
            Map paramMap=new HashMap();
            paramMap.put("typeCode", typeCode);
            paramMap.put("instanceName",instanceName );
            StringBuilder param = new StringBuilder();
            param.append("WHERE INSTANCE_SOURCE = '").append(typeCode).append("'");
            if(ObjectUtils.isNotEmpty(instanceName)){
                param.append("and  INSTANCE_NAME like '%").append(instanceName).append("%'");
            }
//            int totalSize = atlasDao.getCount(Const.SQL_DATA_GETINSTANCEINFOCNT.replaceAll("%param%", param.toString()));
            int totalSize = mapper.getInstanceInfoCount(paramMap);
            if( totalSize == 0){
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
//                List<Map<String, Object>> list = atlasDao.getList(Const.SQL_DATA_INSTANCEINFO.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(atlasDao.getList(Const.SQL_DATA_INSTANCEINFO.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(atlasDao.getList(Const.SQL_DATA_INSTANCEINFO.replaceAll("%param%", param.toString())).size())
                        .content(mapper.getInstanceInfoList(paramMap))
                        .elementTotalSize(mapper.getInstanceInfoList(paramMap).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /*
     *知识获取
     *实例表编号生成
     * */
    @Override
    public String instanceNumber() {
        String number = "SL";
        NumberFormat f=new DecimalFormat("000000");
        StringBuilder param = new StringBuilder();
        //查询数据总数
//        if(atlasDao.getCount(Const.SQL_DATA_GETINSTANCEINFOCNT.replaceAll("%param%",param.toString())) == 0){
        if(mapper.getInstanceInfoCount(null) == 0){
            //总数为0，编号为SL000001
            String format = f.format(1);
            number = number + format;
        }else {
            //总数不为0，查询数据库中最大的主键
//            String typeId = atlasDao.getTypeId(Const.SQL_DATA_GETMAXINSTANCEID);
            String typeId = mapper.instanceNumber();
            Integer a = Integer.parseInt(typeId) ;
            String format = f.format(a+1);
            number = number + format;
        }
        return number;
    }


    /*
     *知识获取
     *删除实例
     *paramJson:{"params":[{"instanceId":""}]}
     * */
    @Transactional
    @Override
    public String deleteInstance(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONArray jsonArray  = JSONObject.parseObject(paramJson).getJSONArray("params");
       for(int i=0;i < jsonArray.size();i++){
           JSONObject jsonData = (JSONObject) JSONObject.parseObject(paramJson).getJSONArray("params").get(i);
           String instanceId = jsonData.getString("instanceId");
           Instanceinfo nstanceinfo = mapper.findInsta(instanceId);
           if(nstanceinfo==null){
            return "数据已删除，请刷新重试!";
           }
       }



        StringBuilder param = new StringBuilder();
        StringBuilder param1 = new StringBuilder();
        param.append("INSTANCE_ID in (");
        //遍历JSONArray
        Map paramMap=new HashMap();
        List<String> instanceIds=new ArrayList<>();
        if(JSONObject.parseObject(paramJson).getJSONArray("params")!=null && !JSONObject.parseObject(paramJson).getJSONArray("params").isEmpty()){
            for (int i = 0; i < JSONObject.parseObject(paramJson).getJSONArray("params").size() ; i++) {
                JSONObject jsonData = (JSONObject) JSONObject.parseObject(paramJson).getJSONArray("params").get(i);
                //拿到用户选择的实例ID
                String instanceId = jsonData.getString("instanceId");
                BusinessLog.INSTANCEID =instanceId;
                BusinessLog.DEL_EXAMPLE = mapper.findInstanceInfoName(instanceId);
                instanceIds.add(instanceId);
                param.append("'").append(instanceId).append("',");
                param1.append("'").append(instanceId).append("',");
            }
        }
        //更新前的条数
        int foreCout= mapper.getIntanceCout();
        int i = mapper.deleteInstanceinfo(instanceIds);
        //更新后的条数
        Map<String,Object> map1=new HashMap<>();
        map1.put("k1","");
        map1.put("k3", "实例");
        map1.put("k4", "1");
        map1.put("k5", foreCout);
        map1.put("k6", foreCout-1);
        map1.put("k7", "srp_bd_kg_instanceinfo");
        relationinfoMapper.saveExample(map1);
        if( i > 0){
//            String sql1 = param1.substring(0,param1.length()-1);
            //删除含有所选ID的实例关系
//            atlasDao.rowsNum(Const.SQL_DATA_DELETEINSTANCERELATIONINFO.replaceAll("%param%",sql1));
            mapper.rowsNum1(instanceIds);
            return "删除成功";
        }else {
            return "删除失败";
        }
    }



    /*
     *知识获取
     *新增实例   未去掉list
     *paramJson:{"params":{"modelingId":"","instanceName":"","instanceCode":"","instanceDescribe":""},"idList":[{"instanceId":"","relationId":""}]}
     *
     * */
//    @Transactional
//    @Override
//    public Map<String, List> insertInstanceInfo(String paramJson){
////        JSONObject rowData = JSONObject.parseObject(paramJson);
////        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
//        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
//        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
//        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
//        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//
//        Map map = new LinkedHashMap();
//        int t = instanceName.length();
//        int p= instanceDescribe.length();
//        if(t>10){
//            BusinessLog.csts="实例名称不能大于10";
//            map.put("实例名称不能大于10",t);
//         return map;
//        }
//        int b= instanceDescribe.length();
//        if(p>200){
//            map.put("code","500");
//            BusinessLog.csts1="描述不能大于200";
//            map.put("描述不能大于200",p);
//            return map;
//        }
//
//        int cunt = mapper.findInstanceCount(instanceName);
//        String type = mapper.getTypeName(instanceName);
//         if(type != null){
//
//
//
////             if(len>50){
////                 resultMap.put("code","500");
////                 resultMap.put("msg","导入失败:描述内容不能超过50");
////             }
//
//        if(type.equals("1")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与作业计划中实例名称重复");
////                BusinessLog.csts11="实例名称与作业计划中实例名称重复";
////                map.put("实例名称与作业计划中实例名称重复",cunt);
//                return map;
//            }
//        }
//
//
//
//        if(type.equals("2")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与外包单位中实例名称重复");
////                BusinessLog.csts12="实例名称与外包单位中实例名称重复";
////                map.put("实例名称与外包单位中实例名称重复",cunt);
//                return map;
//            }
//        }
//        if(type.equals("3")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与人员中实例名称重复");
////                BusinessLog.csts13="实例名称与人员中实例名称重复";
////                map.put("实例名称与人员中实例名称重复",cunt);
//                return map;
//            }
//        }
//
//        if(type.equals("4")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与风险中实例名称重复");
////                BusinessLog.csts14="实例名称与风险中实例名称重复";
////                map.put("实例名称与风险中实例名称重复",cunt);
//                return map;
//            }
//        }
//
//        if(type.equals("5")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与隐患中实例名称重复");
////                BusinessLog.csts15="实例名称与隐患中实例名称重复";
////                map.put("实例名称与隐患中实例名称重复",cunt);
//                return map;
//            }
//        }
//
//        if(type.equals("6")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与事件中实例名称重复");
////                BusinessLog.csts16="实例名称与事件中实例名称重复";
////                map.put("实例名称与事件中实例名称重复",cunt);
//                return map;
//            }
//        }
//        if(type.equals("7")){
//            if(cunt>0){
//                map.put("code","500");
//                map.put("msg","实例名称与事件中实例名称重复");
//                BusinessLog.csts17="实例名称与违章信息中实例名称重复";
//                map.put("实例名称与违章信息中实例名称重复",cunt);
//                return map;
//            }
//        }
//
//         }
//
//        //通过建模ID查询类别名称
//        //String typeName = atlasDao.getTypeId(Const.SQL_DATA_GETTYPENAMEBYID.replaceAll("%param%",modelingId));
//        //获取类别编码
////        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODEBYID.replaceAll("%param%",modelingId));
//        String typeCode = mapper.getTypeCode( modelingId);
//        StringBuilder param = new StringBuilder();
//        param.append("'").append(modelingId).append("','").append(instanceName)
//                .append("','").append(instanceDescribe).append("','").append(typeCode).append("','")
//                .append(instanceCode).append("'");
//
//        //新增实例
//        Map paramMap=new HashMap();
//        paramMap.put("modelingId", modelingId);
//        paramMap.put("instanceName", instanceName);
//        paramMap.put("instanceDescribe", instanceDescribe);
//        paramMap.put("typeCode", typeCode);
//        paramMap.put("instanceCode", instanceCode);
//        String id =null;
//        id = mapper.getId(instanceCode);
//        int a=-1;
//        if(id==null){
//             a = mapper.insertInstanceInfo(paramMap);
//        }
//
//        //获取新增实例ID
////        String id = atlasDao.getTypeId(Const.SQL_DATA_GETIDBYCODE.replaceAll("%param%", instanceCode));
//         id = mapper.getId(instanceCode);
//        map.put("id",id);
//        //获取关联实例集合
//        JSONArray list = JSONObject.parseObject(paramJson).getJSONArray("idList");
//
////        Map<String,Object> map1 =new HashMap<>();
////        map1.put("relationId",relationId);
////        map1.put("relationId",relationId);
////        List<Map<String,Object>> selectList = mapper.getInstanceRelationInfo(map1);
//
//
//
//        //判断集合是否为空
//        if(list != null && !list.isEmpty()){
//            for (int j = 0; j < list.size(); j++) {
//                JSONObject jsonData = (JSONObject) list.get(j);
//                //获取实例ID
//                String instanceId = jsonData.getString("instanceId");
//                //通过实例ID查询实例名称
////                String name = atlasDao.getTypeId(Const.SQL_DATA_GETINSTANCENAMEBYID.replaceAll("%param%",instanceId));
//                String name = mapper.getName(instanceId);
//                //获取关系ID
//                String relationId = jsonData.getString("relationId");
//                //通过关系ID查询关系名称
////                String relationName = atlasDao.getTypeId(Const.SQL_DATA_GETRELATIONNAME.replaceAll("%param%", relationId));
//                String relationName = mapper.getRelationName(relationId);
//                //获取实例关系表主键
//                /*String instanceRelationId;
//                if( atlasDao.getCount(Const.SQL_DATA_GETINSTANCERELATIONINFOCNT) == 0){
//                    instanceRelationId = "1";
//                }else {
//                    String typeId = atlasDao.getTypeId(Const.SQL_DATA_GETMAXINSTANCERELATIONINFID);
//                    Integer m = Integer.parseInt(typeId) + 1;
//                    instanceRelationId = m.toString();
//                }*/
//                StringBuilder param1 = new StringBuilder();
//                param1.append("'").append(id).append("','").append(instanceName).append("','")
//                        .append(instanceId).append("','").append(name).append("','").append(relationId).append("','").append(relationName)
//                        .append("'");
//                //新增实例关系
////              a = atlasDao.rowsNum(Const.SQL_DATA_INSERTINSTANCERELATIONINFO.replaceAll("%param%",param1.toString()));
//                Map paramMap1=new HashMap();
//                paramMap1.put("id", id);
//                paramMap1.put("instanceName", instanceName);
//                paramMap1.put("instanceId", instanceId);
//                paramMap1.put("name", name);
//                paramMap1.put("relationId", relationId);
//                paramMap1.put("relationName", relationName);
//                //更新前的条数
//                int foreCout= mapper.getIntanceCout();
//                a = mapper.insert(paramMap1);
//                //更新后的条数
//                Map<String,Object> map1=new HashMap<>();
//                map1.put("k1",modelingId);
//                map1.put("k3", "实例");
//                map1.put("k4", "1");
//                map1.put("k5", foreCout);
//                map1.put("k6", foreCout+1);
//                map1.put("k7", "srp_bd_kg_instanceinfo");
//                relationinfoMapper.saveExample(map1);
//                if(a > 0){
//                    map.put("新增成功",a);
//                }else {
//                    map.put("新增失败",a);
//                }
//            }
//        }
//
//        return map;
//    }







    /*
     *知识获取
     *新增实例 修改版去掉List
     *paramJson:{"params":{"modelingId":"","instanceName":"","instanceCode":"","instanceDescribe":""},"idList":[{"instanceId":"","relationId":""}]}
     *
     * */
    @Transactional
    @Override
    public Map<String, String> insertInstanceInfo(String paramJson){
//        JSONObject rowData = JSONObject.parseObject(paramJson);
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");

        Map<String, String> map = new LinkedHashMap();
        int t = instanceName.length();
        int p= instanceDescribe.length();
        if(t>10){
            BusinessLog.csts="实例名称不能大于10";
//            map.put("实例名称不能大于10",t);
            return map;
        }
        int b= instanceDescribe.length();
        if(p>200){
//            map.put("code","500");
            BusinessLog.csts1="描述不能大于200";
//            map.put("描述不能大于200",p);
            return map;
        }
        if(instanceName.equals("null")){
            map.put("code","500");
            map.put("msg","实例名称不能为null");
            return map;
        }


        int cunt = mapper.findInstanceCount(instanceName);
        String type = mapper.getTypeName(instanceName);
        if(type != null){

            if(type.equals("1")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与作业计划中实例名称重复");
                    return map;
                }
            }



            if(type.equals("2")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与外包单位中实例名称重复");
                    return map;
                }
            }
            if(type.equals("3")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与人员中实例名称重复");
                    return map;
                }
            }

            if(type.equals("4")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与风险中实例名称重复");
                    return map;
                }
            }

            if(type.equals("5")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与隐患中实例名称重复");
                    return map;
                }
            }

            if(type.equals("6")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与事件中实例名称重复");
                    return map;
                }
            }
            if(type.equals("7")){
                if(cunt>0){
                    map.put("code","500");
                    map.put("msg","实例名称与违章信息中实例名称重复");
                    return map;
                }
            }
        }

        //通过建模ID查询类别名称
        //String typeName = atlasDao.getTypeId(Const.SQL_DATA_GETTYPENAMEBYID.replaceAll("%param%",modelingId));
        //获取类别编码
//        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODEBYID.replaceAll("%param%",modelingId));
        String typeCode = mapper.getTypeCode( modelingId);
        StringBuilder param = new StringBuilder();
        param.append("'").append(modelingId).append("','").append(instanceName)
                .append("','").append(instanceDescribe).append("','").append(typeCode).append("','")
                .append(instanceCode).append("'");

        //新增实例
        Map paramMap=new HashMap();
        paramMap.put("modelingId", modelingId);
        paramMap.put("instanceName", instanceName);
        paramMap.put("instanceDescribe", instanceDescribe);
        paramMap.put("typeCode", typeCode);
        paramMap.put("instanceCode", instanceCode);
        String id =null;
        id = mapper.getId(instanceCode);
        int a=-1;
        if(id==null){
            a = mapper.insertInstanceInfo(paramMap);
        }

        //获取新增实例ID
//        String id = atlasDao.getTypeId(Const.SQL_DATA_GETIDBYCODE.replaceAll("%param%", instanceCode));
        id = mapper.getId(instanceCode);
        map.put("id",id);
        //获取关联实例集合
        JSONArray list = JSONObject.parseObject(paramJson).getJSONArray("idList");

//        Map<String,Object> map1 =new HashMap<>();
//        map1.put("relationId",relationId);
//        map1.put("relationId",relationId);
//        List<Map<String,Object>> selectList = mapper.getInstanceRelationInfo(map1);



        //判断集合是否为空
        if(list != null && !list.isEmpty()){
            for (int j = 0; j < list.size(); j++) {
                JSONObject jsonData = (JSONObject) list.get(j);
                //获取实例ID
                String instanceId = jsonData.getString("instanceId");
                //通过实例ID查询实例名称
//                String name = atlasDao.getTypeId(Const.SQL_DATA_GETINSTANCENAMEBYID.replaceAll("%param%",instanceId));
                String name = mapper.getName(instanceId);
                //获取关系ID
                String relationId = jsonData.getString("relationId");
                //通过关系ID查询关系名称
//                String relationName = atlasDao.getTypeId(Const.SQL_DATA_GETRELATIONNAME.replaceAll("%param%", relationId));
                String relationName = mapper.getRelationName(relationId);
                //获取实例关系表主键
                /*String instanceRelationId;
                if( atlasDao.getCount(Const.SQL_DATA_GETINSTANCERELATIONINFOCNT) == 0){
                    instanceRelationId = "1";
                }else {
                    String typeId = atlasDao.getTypeId(Const.SQL_DATA_GETMAXINSTANCERELATIONINFID);
                    Integer m = Integer.parseInt(typeId) + 1;
                    instanceRelationId = m.toString();
                }*/
                StringBuilder param1 = new StringBuilder();
                param1.append("'").append(id).append("','").append(instanceName).append("','")
                        .append(instanceId).append("','").append(name).append("','").append(relationId).append("','").append(relationName)
                        .append("'");
                //新增实例关系
//              a = atlasDao.rowsNum(Const.SQL_DATA_INSERTINSTANCERELATIONINFO.replaceAll("%param%",param1.toString()));
                Map paramMap1=new HashMap();
                paramMap1.put("id", id);
                paramMap1.put("instanceName", instanceName);
                paramMap1.put("instanceId", instanceId);
                paramMap1.put("name", name);
                paramMap1.put("relationId", relationId);
                paramMap1.put("relationName", relationName);
                //更新前的条数
                int foreCout= mapper.getIntanceCout();
                a = mapper.insert(paramMap1);
                //更新后的条数
                Map<String,Object> map1=new HashMap<>();
                map1.put("k1",modelingId);
                map1.put("k3", "实例");
                map1.put("k4", "1");
                map1.put("k5", foreCout);
                map1.put("k6", foreCout+1);
                map1.put("k7", "srp_bd_kg_instanceinfo");
                relationinfoMapper.saveExample(map1);
                if(a > 0){
                    map.put("新增成功",Integer.toString(a));
                }else {
                    map.put("新增失败",Integer.toString(a));
                }
            }
        }

        return map;
    }



    /*
     *知识获取
     *根据源类别名称查询关系
     *paramJson:{"modelingId":""}
     * */
    @Override
    public List<Map<String, Object>> getRelationInfoById(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
        StringBuilder param = new StringBuilder();
        param.append("and SOURCE_TYPE = '").append(modelingId).append("'");
//        List<Map<String, Object>> list = atlasDao.getList(Const.SQL_DATA_RELATIONINFOBYMODELINGID.replaceAll("%param%",param.toString()));
        List<Map<String, Object>> list = mapper.getRelationInfoById(modelingId);
        return list;
    }

    /*
     *知识获取
     *根据类别查询实例
     *paramJson:{"targetType":"","relationId":""}
     * */
    @Override
    public List<Map<String, Object>> getInstanceInfoByType(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String targetType = JSONObject.parseObject(paramJson).getString("targetType");
        String relationId = JSONObject.parseObject(paramJson).getString("relationId");

        StringBuilder param = new StringBuilder();
        param.append("where TYPE_NAME = '").append(targetType).append("'");
        List<Map<String, Object>> list = mapper.getInstanceInfoListByType(targetType);
        for (Map<String, Object> stringObjectMap : list) {
            stringObjectMap.put("RELATION_ID",relationId);
        }
        return list;
    }


    /*
     *知识获取
     *根据id查询实例详情
     *paramJson:{"instanceId":""}
     * */
    @Override
    public List<Map<String, Object>> getInstanceInfoById(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String instanceId = JSONObject.parseObject(paramJson).getString("instanceId");
//        List<Map<String, Object>> list = atlasDao.getList(Const.SQL_DATA_GETINSTANCEINFOBYID.replaceAll("%param%", instanceId));
        List<Map<String, Object>> list = mapper.getInstanceInfoById(instanceId);
        return list;
    }

    /*
     *知识获取
     *通过ID查询实例关系
     *paramJson:{"instanceId":"","targetType":"","relationId":""}
     * */
    @Override
    public Map<String, List> getInstancerelationinfoById(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String instanceId = JSONObject.parseObject(paramJson).getString("instanceId");
        String targetType = JSONObject.parseObject(paramJson).getString("targetType");
        String relationId = JSONObject.parseObject(paramJson).getString("relationId");
        String glsxName = JSONObject.parseObject(paramJson).getString("glsxName");
        BusinessLog.glgxName =glsxName;
        List<Map<String, Object>> selectList = new ArrayList();
        List<Map<String, Object>> noSelectList = new ArrayList();
        Map<String,Object> map1=new HashMap<>();
        map1.put("instanceId", instanceId);
        map1.put("relationId", relationId);
        int count = mapper.getInstancerelationinfoByIdCount(map1);

        if(count == 0){
            StringBuilder param = new StringBuilder();
            param.append("where TYPE_NAME = '").append(targetType).append("'");
            noSelectList = mapper.getInstanceInfoListByType(targetType);
            BusinessLog.Instance="";
        }else {
            selectList = mapper.getInstanceRelationInfo(map1);
            Knowledge kn =new Knowledge();
            for (Map<String,Object> mt:selectList){
//                BusinessLog.INSTANCE_NAME = mt.get("INSTANCE_NAME").toString();
                kn.getInstanceNameList().add(mt.get("INSTANCE_NAME").toString());
                BusinessLog.RELATION_ID = mt.get("RELATION_ID").toString();
                BusinessLog.INSTANCE_ID = mt.get("INSTANCE_ID").toString();
            }
            Object obj = JSONArray.toJSON(kn);
            BusinessLog.Instance=obj.toString();


            StringBuilder param = new StringBuilder();
//            List<Map<String, Object>> list = new ArrayList();
            for (Map<String, Object> stringObjectMap : selectList) {
                Object id = stringObjectMap.get("INSTANCE_ID");
//                param.append("'").append(id).append("',");
            }

//            String substring = param.substring(0, param.length() - 1);
//            String substrings [] = substring.split(",");

            Map<String,Object> map2=new HashMap<>();
            map2.put("targetType",targetType);
            map2.put("selectList",selectList);
//            map2.put("substring",substring.replace("'",""));

            noSelectList = mapper.getExample(map2);
//            noSelectList = mapper.getExample(targetType,substrings);
        }

        for (Map<String, Object> stringObjectMap : noSelectList) {
            stringObjectMap.put("RELATION_ID",relationId);
        }
        Map map = new LinkedHashMap();
        map.put("selected",selectList);
        map.put("noSelected",noSelectList);
        return map;
    }










    /**
     * 不含关联关系新增（不含关联关系）
     * @param paramJson
     * @return
     */
    /*@Transactional
    @Override
    public String saveInstanceInfoInclude(String paramJson) {
        String instanceId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
        Map<String,Object> map=new HashMap<>();
        map.put("instanceName", instanceName);
        map.put("instanceDescribe", instanceDescribe);
        map.put("instanceId", instanceId);
        int b= instanceName.length();
        int c=instanceDescribe.length();
        if(b>200){
            return "名称长度不能超过100！";
        }
        if(c>200){
            return "描述长度不能超过200！";
        }
        int a = mapper.updateInstanceinfo(map);
        Map<String,Object> map1=new HashMap<>();
        map1.put("instanceName", instanceName);
        map1.put("instanceId", instanceId);

        //更新前的条数
        int foreCout= mapper.getIntanceCout();
        mapper.updateInstanceRelationInfo(map1);
        mapper.updateInstanceInfo(map1);
        Map<String,Object> map3=new HashMap<>();
        map3.put("k1","");
        map3.put("k3", "实例");
        map3.put("k4", "1");
        map3.put("k5", foreCout);
        map3.put("k6", foreCout);
        map3.put("k7", "srp_bd_kg_instanceinfo");
        relationinfoMapper.saveExample(map3);

        if(a > 0){
            return "新增成功";
        }else {
            return "新增失败";
        }
    }*/







    /**
     * 新增表单
     * @param paramJson
     * @return
     */
    @Transactional
    @Override
    public String insertInstanceInfobd(String paramJson){
//        JSONObject rowData = JSONObject.parseObject(paramJson);
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
        String typeCode = mapper.getTypeCode( modelingId);
        StringBuilder param = new StringBuilder();
        //新增实例
        Map paramMap=new HashMap();
        paramMap.put("modelingId", modelingId);
        paramMap.put("instanceName", instanceName);
        paramMap.put("instanceDescribe", instanceDescribe);
        paramMap.put("typeCode", typeCode);
        paramMap.put("instanceCode", instanceCode);
        int a = mapper.insertInstanceInfo(paramMap);
        if(a > 0){
            return "新增成功";
        }else {
            return "新增失败";
        }
    }







    @Override
    public Instanceinfo findinstancexq(Instanceinfo Instanceinfo) {
        Instanceinfo nstanceinfos = mapper.findinstancexq(Instanceinfo);
         String nstanceinfoName  =  nstanceinfos.getInstanceName();
        BusinessLog.ylinsName= nstanceinfoName;
        return nstanceinfos;
    }





    /*
     *知识获取
     *编辑实例
     *paramJson:{"params":{"instanceId":"","instanceName":"","instanceDescribe":""},"idList":[{"instanceId":"","relationId":""}]}
     *
     * */
    /*@Override
    public String updateInstanceInfo(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String instanceId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//        StringBuilder param = new StringBuilder();
//        StringBuilder param2 = new StringBuilder();
//        param.append("INSTANCE_NAME = '").append(instanceName).append("',INSTANCE_DESCRIBE = '").append(instanceDescribe).append("'");
//        param2.append("INSTANCE_ID = '").append(instanceId).append("'");
        //编辑实例
//        int a = atlasDao.rowsNum(Const.SQL_DATA_UPDATEINSTANCEINFO.replaceAll("%param%", param.toString()).replaceAll("%param1%", param2.toString()));
        Map<String,Object> map=new HashMap<>();
        map.put("instanceName", instanceName);
        map.put("instanceDescribe", instanceDescribe);
        map.put("instanceId", instanceId);
        int a = mapper.updateInstanceinfo(map);
        //编辑实例下的关系
//        atlasDao.rowsNum(Const.SQL_DATA_UPDATESOURCE.replaceAll("%param%",instanceName).replaceAll("%param1%",instanceId));
//        atlasDao.rowsNum(Const.SQL_DATA_UPDATETARGET.replaceAll("%param%",instanceName).replaceAll("%param1%",instanceId));
        Map<String,Object> map1=new HashMap<>();
        map1.put("instanceName", instanceName);
        map1.put("instanceId", instanceId);
        mapper.updateInstanceRelationInfo(map1);
        mapper.updateInstanceInfo(map1);
        //删除实例关系
        //atlasDao.rowsNum(Const.SQL_DATA_DELETEINSTANCERELATIONINFOBYID.replaceAll("%param%",instanceId));
        //获取关联实例集合
        JSONArray list = JSONObject.parseObject(paramJson).getJSONArray("idList");
        //判断集合是否为空
        if(list != null && !list.isEmpty()){
            StringBuilder param3 = new StringBuilder();
            List<String> sqls=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonData = (JSONObject) list.get(i);
                //获取实例ID
                String instanceId1 = jsonData.getString("instanceId");
                param3.append("'").append(instanceId1).append("',");
                sqls.add(instanceId1);
            }
//            String sql = param3.substring(0, param3.length() - 1);
            //删除掉未被选择的实例关系
//            atlasDao.rowsNum(Const.SQL_DELETE_NOSELECTID.replaceAll("%param%",instanceId).replaceAll("%param1%",sql));
            Map<String,Object> map2=new HashMap<>();
            map2.put("sqls", sqls);
            map2.put("instanceId", instanceId);
            mapper.deleteInstanceRelationInfo(map2);

            for (int j = 0; j < list.size(); j++) {
                JSONObject jsonData = (JSONObject) list.get(j);
                //获取实例ID
                String instanceId1 = jsonData.getString("instanceId");
                //通过源实例ID和目标实例ID查询该数据是否存在
//                int count = atlasDao.getCount(Const.SQL_GETCOUNTBYID.replaceAll("%param%", instanceId).replaceAll("%param1%", instanceId1));
                Map<String,Object> map3=new HashMap<>();
                map3.put("instanceId1", instanceId1);
                map3.put("instanceId", instanceId);
                int count = mapper.getRelationCount(map3);
                if(instanceId.isEmpty()){

                }
                if(count == 0){
                    //通过实例ID查询实例名称
//                    String name = atlasDao.getTypeId(Const.SQL_DATA_GETINSTANCENAMEBYID.replaceAll("%param%",instanceId1));
                    String name = mapper.getName(instanceId1);
                    //获取关系ID
                    String relationId = jsonData.getString("relationId");
                    //通过关系ID查询关系名称
//                    String relationName = atlasDao.getTypeId(Const.SQL_DATA_GETRELATIONNAME.replaceAll("%param%", relationId));
                    String relationName = mapper.getRelationName(relationId);;
                    Map<String,Object> insertMap=new HashMap<>();
                    insertMap.put("instanceId",instanceId);
                    insertMap.put("instanceName",instanceName);
                    insertMap.put("instanceId1",instanceId1);
                    insertMap.put("name",name);
                    insertMap.put("relationId",relationId);
                    insertMap.put("relationName",relationName);
                    //新增实例关系
                    mapper.insertTopology(insertMap);
//                    atlasDao.rowsNum(Const.SQL_DATA_INSERTINSTANCERELATIONINFO.replaceAll("%param%",param1.toString()));
//                    mapper.insert(param1.toString());
                }else if(count > 0){
                    String name = mapper.getName(instanceId1);
                    //获取关系ID
                    String relationId = jsonData.getString("relationId");
                    //通过关系ID查询关系名称
                    String relationName = mapper.getRelationName(relationId);
                    Map<String,Object> updateMap=new HashMap<>();
                    updateMap.put("instanceId",instanceId);
                    updateMap.put("instanceName",instanceName);
                    updateMap.put("instanceId1",instanceId1);
                    updateMap.put("name",name);
                    updateMap.put("relationId",relationId);
                    updateMap.put("relationName",relationName);
                    mapper.updateTopology(updateMap);
                }
            }
        }else {
            Map<String,Object> delMap=new HashMap<>();
            delMap.put("instanceId",instanceId);
            mapper.delTopology(delMap);
        }
        if(a > 0){
            return "编辑成功";
        }else {
            return "编辑失败";
        }
    }*/









    /**
     * 不含关联关系实例编辑
     * @param paramJson
     * @return
     */
    @Transactional
    @Override
    public String updateInstanceInfoInclude(String paramJson) {
        String instanceId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceId");
        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
        JSONArray newIdList = JSONObject.parseObject(paramJson).getJSONArray("newIdList");
        JSONArray oldIdList = JSONObject.parseObject(paramJson).getJSONArray("oldIdList");
        String relationIds="";
        String relationIdsy="";
        List<String> ls= new ArrayList<>();
        if(oldIdList.size()>0){
            for (int i = 0; i < oldIdList.size(); i++) {
                if(i==0){
                    JSONObject jsonData = (JSONObject) oldIdList.get(i);
                    //获取实例ID
                   String re = jsonData.getString("relationId");
                    ls.add(re);
                }
                JSONObject jsonDatas = (JSONObject) oldIdList.get(i);
                //获取实例ID
                relationIds = jsonDatas.getString("relationId");
                String instanceIds = jsonDatas.getString("instanceId");
                if(i>0){
                    JSONObject jsonData = (JSONObject) oldIdList.get(i-1);
                    relationIdsy = jsonData.getString("relationId");
                    if(!relationIds.equals(relationIdsy)){
                        ls.add(relationIds);
                    }
                }
            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("instanceName", instanceName);
        map.put("instanceDescribe", instanceDescribe);
        map.put("instanceId", instanceId);
        //编辑

        int b= instanceName.length();
        int c=instanceDescribe.length();
        if(b>200){
            return "名称长度不能超过100！";
        }
        if(c>200){
            return "描述长度不能超过200！";
        }
        if(instanceName.equals("null")){
            return "实例名称不能为null";
        }
        Instanceinfo ins =  mapper.findInstanceinfobj1(instanceName);
        String ylinName = BusinessLog.ylinsName;
        int a=0;
        if(ylinName.equals(instanceName)){
            Knowledge knowLedge = mapper.getInName(instanceId);
            knowLedge.getInstanceDescribe();
            BusinessLog.oldName =knowLedge.getInstanceNames();
            BusinessLog.oldDsc=knowLedge.getInstanceDescribe();
            //执行编辑操作
            a = mapper.updateInstanceinfo(map);

            Map<String,Object> map1=new HashMap<>();
            map1.put("instanceName", instanceName);
            map1.put("instanceId", instanceId);
            //        更新关联关系
            mapper.updateInstanceRelationInfo(map1);
            mapper.updateInstanceInfo(map1);
            //根据编号获取修改之前的信息
            Instanceinfo instanceinfos = mapper.findInstanceinfo(instanceId);
            instanceinfos.setTypeName(instanceName);
            Object obj = JSONArray.toJSON(instanceinfos);
            BusinessLog.instanceInfos=obj.toString();
            //判断关联关系集合是否为空
            if(newIdList != null && !newIdList.isEmpty()){
                List<String> lists= new ArrayList<>();
                List<String> listsInstance= new ArrayList<>();
                if(oldIdList.size()>0){
                for (int p=0; p< oldIdList.size();p++){
                    JSONObject jsonData = (JSONObject) oldIdList.get(p);
                    String instanceIdnei = jsonData.getString("instanceId");
                    String relation = jsonData.getString("relationId");
                    listsInstance.add(instanceIdnei);
                    lists.add(relation);
                }
                    Map<String,Object> map2=new HashMap<>();
                    map2.put("sqls", lists);
                    map2.put("instanceId", instanceId);
                    map2.put("listsInstance", listsInstance);
                   mapper.deleteInstanceRelationInfo(map2);
                }

                for (int j = 0; j < newIdList.size(); j++) {
                    JSONObject jsonData = (JSONObject) newIdList.get(j);
                    //获取实例ID
                    String instanceId1 = jsonData.getString("instanceId");
                    String relation = jsonData.getString("relationId");
                    //通过源实例ID和目标实例ID查询该数据是否存在
                    Map<String,Object> map3=new HashMap<>();
                    map3.put("instanceId1", instanceId1);
                    map3.put("instanceId", instanceId);
                    map3.put("relationId", relation);
                    int count = mapper.getRelationCount(map3);
                    if(instanceId.isEmpty()){
                    }
                    if(count == 0){
                        //通过实例ID查询实例名称
                        String name = mapper.getName(instanceId1);
                        //获取关系ID
                        String relationId = jsonData.getString("relationId");
                        //通过关系ID查询关系名称
                        String relationName = mapper.getRelationName(relationId);;
                        Map<String,Object> insertMap=new HashMap<>();
                        insertMap.put("instanceId",instanceId);
                        insertMap.put("instanceName",instanceName);
                        insertMap.put("instanceId1",instanceId1);
                        insertMap.put("name",name);
                        insertMap.put("relationId",relationId);
                        insertMap.put("relationName",relationName);
                        //新增实例关系
                        a = mapper.insertTopology(insertMap);
                    }else if(count > 0){
                        String name = mapper.getName(instanceId1);
                        //获取关系ID
                        String relationId = jsonData.getString("relationId");
                        //通过关系ID查询关系名称
                        String relationName = mapper.getRelationName(relationId);
                        Map<String,Object> updateMap=new HashMap<>();
                        updateMap.put("instanceId",instanceId);
                        updateMap.put("instanceName",instanceName);
                        updateMap.put("instanceId1",instanceId1);
                        updateMap.put("name",name);
                        updateMap.put("relationId",relationId);
                        updateMap.put("relationName",relationName);
                        a= mapper.updateTopology(updateMap);
                    }
                }
            }
            else {
                    Map<String,Object> delMap=new HashMap<>();
                    delMap.put("instanceId",instanceId);
                    delMap.put("relationIds",ls);
                    a= mapper.delTopology(delMap);
            }


//            //更新前的条数
//            int foreCout= mapper.getIntanceCout();
////        更新关联关系
//            mapper.updateInstanceRelationInfo(map1);
//            mapper.updateInstanceInfo(map1);
//
//            Map<String,Object> map3=new HashMap<>();
//            map3.put("k1","");
//            map3.put("k3", "实例");
//            map3.put("k4", "1");
//            map3.put("k5", foreCout);
//            map3.put("k6", foreCout);
//            map3.put("k7", "srp_bd_kg_instanceinfo");
//            //保存到知识融合表
//            relationinfoMapper.saveExample(map3);
        }else {
            if(ins!=null){
                return "实例名称重复";
            }
            Knowledge knowLedge = mapper.getInName(instanceId);
            knowLedge.getInstanceDescribe();
            BusinessLog.oldName =knowLedge.getInstanceNames();
            BusinessLog.oldDsc=knowLedge.getInstanceDescribe();
            a = mapper.updateInstanceinfo(map);
            Map<String,Object> map1=new HashMap<>();
            map1.put("instanceName", instanceName);
            map1.put("instanceId", instanceId);
            //        更新关联关系
            mapper.updateInstanceRelationInfo(map1);
            mapper.updateInstanceInfo(map1);

            //根据编号获取修改之前的信息
            Instanceinfo instanceinfos = mapper.findInstanceinfo(instanceId);
            instanceinfos.setTypeName(instanceName);
            Object obj = JSONArray.toJSON(instanceinfos);
            BusinessLog.instanceInfos=obj.toString();


            //判断之前关联关系是否为空
            if(newIdList != null && !newIdList.isEmpty()){

                if(oldIdList.size()>0){
                    List<String> lists= new ArrayList<>();
                    List<String> listsInstance= new ArrayList<>();
                    for (int p=0; p< oldIdList.size();p++){
                        JSONObject jsonData = (JSONObject) oldIdList.get(p);
                        String instanceIdnei = jsonData.getString("instanceId");
                        String relation = jsonData.getString("relationId");
                        listsInstance.add(instanceIdnei);
                        lists.add(relation);
                    }
                    Map<String,Object> map2=new HashMap<>();
                    map2.put("sqls", lists);
                    map2.put("instanceId", instanceId);
                    map2.put("listsInstance", listsInstance);
                    mapper.deleteInstanceRelationInfo(map2);
                }



                for (int j = 0; j < newIdList.size(); j++) {
                    JSONObject jsonData = (JSONObject) newIdList.get(j);
                    //获取实例ID
                    String instanceId1 = jsonData.getString("instanceId");
                    String relation = jsonData.getString("relationId");
                    //通过源实例ID和目标实例ID查询该数据是否存在
//                int count = atlasDao.getCount(Const.SQL_GETCOUNTBYID.replaceAll("%param%", instanceId).replaceAll("%param1%", instanceId1));
                    Map<String,Object> map3=new HashMap<>();
                    map3.put("instanceId1", instanceId1);
                    map3.put("instanceId", instanceId);
                    map3.put("relationId", relation);
                    int count = mapper.getRelationCount(map3);
                    if(instanceId.isEmpty()){
                    }
                    if(count == 0){
                        //通过实例ID查询实例名称
                        String name = mapper.getName(instanceId1);
                        //获取关系ID
                        String relationId = jsonData.getString("relationId");
                        //通过关系ID查询关系名称
                        String relationName = mapper.getRelationName(relationId);;
                        Map<String,Object> insertMap=new HashMap<>();
                        insertMap.put("instanceId",instanceId);
                        insertMap.put("instanceName",instanceName);
                        insertMap.put("instanceId1",instanceId1);
                        insertMap.put("name",name);
                        insertMap.put("relationId",relationId);
                        insertMap.put("relationName",relationName);
                        //新增实例关系
                        a = mapper.insertTopology(insertMap);
                    }else if(count > 0){
                        String name = mapper.getName(instanceId1);
                        //获取关系ID
                        String relationId = jsonData.getString("relationId");
                        //通过关系ID查询关系名称
                        String relationName = mapper.getRelationName(relationId);
                        Map<String,Object> updateMap=new HashMap<>();
                        updateMap.put("instanceId",instanceId);
                        updateMap.put("instanceName",instanceName);
                        updateMap.put("instanceId1",instanceId1);
                        updateMap.put("name",name);
                        updateMap.put("relationId",relationId);
                        updateMap.put("relationName",relationName);
                        a= mapper.updateTopology(updateMap);
                    }
                }
            }
            else {
                Map<String,Object> delMap=new HashMap<>();
                delMap.put("instanceId",instanceId);
                delMap.put("relationIds",ls);
                a= mapper.delTopology(delMap);
            }





            //更新前的条数
//            int foreCout= mapper.getIntanceCout();
//            mapper.updateInstanceRelationInfo(map1);
//            mapper.updateInstanceInfo(map1);
//            Map<String,Object> map3=new HashMap<>();
//            map3.put("k1","");
//            map3.put("k3", "实例");
//            map3.put("k4", "1");
//            map3.put("k5", foreCout);
//            map3.put("k6", foreCout);
//            map3.put("k7", "srp_bd_kg_instanceinfo");
//            relationinfoMapper.saveExample(map3);


        }
        if(a >= 0){
            return "编辑成功";
        }else {
            return "编辑失败";
        }
    }



    public static List<String> listrem(List<String> listA,List<String> listB){
        for (Iterator<String> itA = listA.iterator(); itA.hasNext();)
        {
            String temp = itA.next();
            // itA.next() 只能在外层循环里面调用1次
            for (int i = 0; i < listB.size(); i++)
            {
                if (temp.equals(listB.get(i)))
                // 你不该在这里多次调用itA.next()的
                {
                    itA.remove();
                }
            }
        }
        return listA;
    }







//    @Override
//    public String InsertInstanceInfoInclude(String paramJson) {
//        String instanceId = JSONObject.parseObject(paramJson).getString("instanceId");
//        String instanceName = JSONObject.parseObject(paramJson).getString("instanceName");
//        JSONArray list = JSONObject.parseObject(paramJson).getJSONArray("idList");
//        JSONArray oldlist = JSONObject.parseObject(paramJson).getJSONArray("oldIdList");
//        String relationIds="";
//        if(oldlist.size()>0){
//            for (int i = 0; i < oldlist.size(); i++) {
//                JSONObject jsonData = (JSONObject) oldlist.get(i);
//                //获取实例ID
//                 relationIds = jsonData.getString("relationId");
//                String instanceIds = jsonData.getString("instanceId");
//            }
//        }
//        int a=0;
//        //判断集合是否为空
//        if(list != null && !list.isEmpty()){
//            StringBuilder param3 = new StringBuilder();
//            List<String> sqls=new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                JSONObject jsonData = (JSONObject) list.get(i);
//                //获取实例ID
//                String instanceId1 = jsonData.getString("instanceId");
//                param3.append("'").append(instanceId1).append("',");
//                sqls.add(instanceId1);
//            }
//
//            Map<String,Object> map2=new HashMap<>();
//            map2.put("sqls", sqls);
//            map2.put("instanceId", instanceId);
////            mapper.deleteInstanceRelationInfo(map2);
//
//            for (int j = 0; j < list.size(); j++) {
//                JSONObject jsonData = (JSONObject) list.get(j);
//                //获取实例ID
//                String instanceId1 = jsonData.getString("instanceId");
//                //通过源实例ID和目标实例ID查询该数据是否存在
////                int count = atlasDao.getCount(Const.SQL_GETCOUNTBYID.replaceAll("%param%", instanceId).replaceAll("%param1%", instanceId1));
//                Map<String,Object> map3=new HashMap<>();
//                map3.put("instanceId1", instanceId1);
//                map3.put("instanceId", instanceId);
//                int count = mapper.getRelationCount(map3);
//                if(instanceId.isEmpty()){
//                }
//                if(count == 0){
//                    //通过实例ID查询实例名称
//                    String name = mapper.getName(instanceId1);
//                    //获取关系ID
//                    String relationId = jsonData.getString("relationId");
//                    //通过关系ID查询关系名称
//                    String relationName = mapper.getRelationName(relationId);;
//                    Map<String,Object> insertMap=new HashMap<>();
//                    insertMap.put("instanceId",instanceId);
//                    insertMap.put("instanceName",instanceName);
//                    insertMap.put("instanceId1",instanceId1);
//                    insertMap.put("name",name);
//                    insertMap.put("relationId",relationId);
//                    insertMap.put("relationName",relationName);
//                    //新增实例关系
//                    a = mapper.insertTopology(insertMap);
//                }else if(count > 0){
//                    String name = mapper.getName(instanceId1);
//                    //获取关系ID
//                    String relationId = jsonData.getString("relationId");
//                    //通过关系ID查询关系名称
//                    String relationName = mapper.getRelationName(relationId);
//                    Map<String,Object> updateMap=new HashMap<>();
//                    updateMap.put("instanceId",instanceId);
//                    updateMap.put("instanceName",instanceName);
//                    updateMap.put("instanceId1",instanceId1);
//                    updateMap.put("name",name);
//                    updateMap.put("relationId",relationId);
//                    updateMap.put("relationName",relationName);
//                    a= mapper.updateTopology(updateMap);
//                }
//            }
//        }else {
//            Map<String,Object> delMap=new HashMap<>();
//            delMap.put("instanceId",instanceId);
//            delMap.put("relationIds",relationIds);
//            a= mapper.delTopology(delMap);
//        }
//        if(a > 0){
//            return "编辑成功";
//        }else {
//            return "编辑失败";
//        }
//
//    }
















    /*
     *知识获取
     *拓扑图
     *paramJson:{"instanceId":""}
     * */
    @Override
    public Map<String, List> getGplot(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String instanceId = JSONObject.parseObject(paramJson).getString("instanceId");
//        List<Map<String, Object>> list1 = atlasDao.getList(Const.SQL_DATA_GETINSTANCERELATIONINFOBYID.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list1 = mapper.getImage(instanceId);

        List<String> targetIds=new ArrayList<>();

        for (Map<String, Object> stringObjectMap : list1) {
            Object targetId = stringObjectMap.get("TARGET_INSTANCE_ID");
            targetIds.add(String.valueOf(targetId));
        }
//        List<Map<String, Object>> list2 = mapper.getImage2(targetIds);
//        list2.clear();

        Map<String,List> map = new HashMap<>();
        map.put("principalLine",list1);
//        map.put("branch",list2);
        return map;
    }


    /**
     * 获取实例关联关系
     * @return
     */
    @Override
    public List<Map<String, String>> findTopology() {
        return mapper.findTopology();
    }

    /**
     * 通过实例名称查询拓扑图
     * @param paramJson
     * @return
     */
    @Override
    public Object getGplotName(String paramJson) {
        String instanceName = JSONObject.parseObject(paramJson).getString("instanceName");
//        String  instanceIdss  = mapper.getInstanceName(instanceName);
        List<String> instanceId=  mapper.getInstanceNameTp(instanceName);
        List<Map<String, Object>> list1 = mapper.getImageTuoPu(instanceId);

        List<String> targetIds=new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list1) {
            Object targetId = stringObjectMap.get("TARGET_INSTANCE_ID");
            targetIds.add(String.valueOf(targetId));
        }
        List<Map<String, Object>> list2 = mapper.getImage2(targetIds);
        Map<String,List> map = new HashMap<>();
        map.put("principalLine",list1);
        map.put("branch",list2);
        if(!instanceName.isEmpty()){
            List<String>  instanceIds  = mapper.getInstanceNameTp(instanceName);
            if(instanceIds.size()<=0){
                list1=null;
                list2=null;
                map.put("principalLine",list1);
                map.put("branch",list2);
            }
        }
        return map;
    }




   /* @Override
    public Object getGplotName(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String instanceName = JSONObject.parseObject(paramJson).getString("instanceName");
//        StringBuilder param = new StringBuilder();
//        param.append("'").append(instanceName).append("'");
//        BusinessLog.INSTANCEID = atlasDao.getInstanceName(Const.SQL_DATA_GETINSTANCERELATIONINFOBYIDNAME.replaceAll("%param%", param.toString()));
        BusinessLog.INSTANCEID = mapper.getInstanceName(instanceName);
        param = new StringBuilder();
        param.append("'").append(BusinessLog.INSTANCEID).append("'");
//        List<Map<String, Object>> list1 = atlasDao.getList(Const.SQL_DATA_GETINSTANCERELATIONINFOBYID.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list1 = mapper.getImage(BusinessLog.INSTANCEID);;
        param = new StringBuilder();
        List<String> targetIds=new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list1) {
            Object targetId = stringObjectMap.get("TARGET_INSTANCE_ID");
            param.append("'").append(targetId).append("',");
            targetIds.add(String.valueOf(targetId));
        }
//        String sql = param.substring(0, param.length() - 1);
//        List<Map<String, Object>> list2 = atlasDao.getList(Const.SQL_DATA_GETBRANCH.replaceAll("%param%", sql));
        List<Map<String, Object>> list2 = mapper.getImage2(targetIds);
        Map<String,List> map = new HashMap<>();
        map.put("principalLine",list1);
        map.put("branch",list2);
        return map;
    }*/


}
