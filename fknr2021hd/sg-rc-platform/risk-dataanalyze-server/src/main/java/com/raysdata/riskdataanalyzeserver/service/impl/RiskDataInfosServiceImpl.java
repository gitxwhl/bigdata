package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.mapper.RiskDataInfosMapper;
import com.raysdata.riskdataanalyzeserver.service.RiskDataInfosService;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskDataInfosServiceImpl implements RiskDataInfosService {

    //    @Autowired
//    private RiskDataInfosDao riskDataInfosDao;
//    @Autowired
//    private BusinessLogUnifiedDao businessLogUnifiedDao;
    @Autowired
    private RiskDataInfosMapper riskDataInfosMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
     *数据治理信息表查询
     * paramJson:{"page":"", "size":"", "params":{"jobName":"","executionDateTime":"","endDateTime":"","processState":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getDataInfoManage(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String jobName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("jobName");
        String executionDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("executionDateTime");
        String endDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endDateTime");
        String processState = JSONObject.parseObject(paramJson).getJSONObject("params").getString("processState");
        Map<String, Object> mapParam = new HashMap<>();
        if(jobName.contains("_")){
            jobName = jobName.replace("_", "\\_");
        }
        mapParam.put("size", size);
        mapParam.put("jobName", jobName);
        mapParam.put("executionDateTime", executionDateTime);
        mapParam.put("endDateTime", endDateTime);
        mapParam.put("processState", processState);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {

            Integer totalSize = riskDataInfosMapper.getDataCountDataInfoManage(mapParam);
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

//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                mapParam.put("offset", offset);
                List<Map<String, Object>> list= riskDataInfosMapper.getTableDataDataInfoManage(mapParam);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> manageList = this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_MANAGE.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_MANAGE.replaceAll("%param%", param.toString())))
                        .content(list)
//                        .elementTotalSize(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_MANAGE.replaceAll("%param%", param.toString())).size())
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
     *数据清洗信息表查询
     * paramJson:{"page":"", "size":"", "params":{"jobName":"","executionDateTime":"","endDateTime":"","processState":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getDataInfoPurge(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String jobName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("jobName");
        String executionDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("executionDateTime");
        String endDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endDateTime");
        String processState = JSONObject.parseObject(paramJson).getJSONObject("params").getString("processState");
        Map<String, Object> mapParam = new HashMap<>();
        if(jobName.contains("_")){
            jobName = jobName.replace("_", "\\_");
        }
        mapParam.put("size", size);
        mapParam.put("jobName", jobName);
        mapParam.put("executionDateTime", executionDateTime);
        mapParam.put("endDateTime", endDateTime);
        mapParam.put("processState", processState);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            StringBuffer param = new StringBuffer();
//            if (ObjectUtils.isNotEmpty(jobName)) {
//                param.append("and JOBNAME like '%").append(jobName).append("%'");
//            }
//
//            //条件查询执行时间的时间区间
//            //开始时间
//            if (ObjectUtils.isNotEmpty(executionDateTime)) {
//                param.append(" AND SUBSTRING(EXECUTION_DATETIME,1,26) > STR_TO_DATE('").append(executionDateTime).append("','%Y-%m-%d %H:%i:%s')");
//            }
//            //结束时间
//            if (ObjectUtils.isNotEmpty(endDateTime)) {
//                param.append(" AND SUBSTRING(EXECUTION_DATETIME,1,26) < STR_TO_DATE('").append(endDateTime).append("','%Y-%m-%d %H:%i:%s')");
//            }
//
//
//            if (ObjectUtils.isNotEmpty(processState)) {
//                param.append("and PROCESS_STATE = '").append(processState).append("'");
//            }
//            int totalSize = this.riskDataInfosDao.getDataCount(Const.SQL_GETDATAINFO_PURGECNT.replaceAll("%param%", param.toString()));
            int totalSize = riskDataInfosMapper.getDataCountDataInfoPurge(mapParam);
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
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                mapParam.put("offset", offset);
                List<Map<String, Object>> list = riskDataInfosMapper.getTableDataDataInfoPurge(mapParam);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> manageList = this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString())))
                        .content(list)
//                        .elementTotalSize(this.riskDataInfosDao.getTableData(Const.SQL_GETDATAINFO_PURGE.replaceAll("%param%", param.toString())).size())
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
     *数据加载
     * paramJson:{"page":"", "size":"", "params":{"jobName":"","executionDateTime":"","endDateTime":"","processState":""}}
     * */
//    @Cacheable(cacheNames = "dept")
    @Override
    public PageBean<Map<String, Object>> getDataInfoLoadList(String paramJson) {
// JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        String jobName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("jobName");
        String executionDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("executionDateTime");
        String endDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endDateTime");
        String processState = JSONObject.parseObject(paramJson).getJSONObject("params").getString("processState");
        Map<String, Object> mapParam = new HashMap<>();
        if(jobName.contains("_")){
            jobName = jobName.replace("_", "\\_");
        }
        mapParam.put("size", size);
        mapParam.put("jobName", jobName);
        mapParam.put("executionDateTime", executionDateTime);
        mapParam.put("endDateTime", endDateTime);
        mapParam.put("processState", processState);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            Map<String, String> map = new HashMap<>();

            map.put("items", "作业标准化管控/入网施工队伍管理/负面清单");

            int totalSize = riskDataInfosMapper.getDataInfoLoadListCNT(mapParam);

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
                List<Map<String, Object>> list=riskDataInfosMapper.getDataInfoLoadList(mapParam);
                /*List<Map<String, Object>> list=null;
                //序列化
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                //取redis
//                String code = UUID.randomUUID().toString().substring(0, 4);
                 list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("key"+page);
                if(null==list){
                //双重检测锁
                    synchronized (this){
                        //再读redis
                       list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("key"+page);
                       if(null== list){
                        //查询数据库
                       list=riskDataInfosMapper.getDataInfoLoadList(mapParam);
                       redisTemplate.opsForValue().set("key"+page,list);
                       }else {
                       }
                    }
                }*/
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
     *数据转换
     * paramJson:{"page":"", "size":"", "params":{"jobName":"","executionDateTime":"","endDateTime":"","processState":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getDataInfoConversionList(HttpServletRequest request, String paramJson) {
        System.out.println("调用次数=========================================================");
        String ip = request.getRemoteAddr();
//        businessLogUnifiedDao.saveIp(ip);
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String jobName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("jobName");
        String executionDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("executionDateTime");
        String endDateTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endDateTime");
        String processState = JSONObject.parseObject(paramJson).getJSONObject("params").getString("processState");
        Map<String, Object> mapParam = new HashMap<>();
        if(jobName.contains("_")){
            jobName = jobName.replace("_", "\\_");
        }
        mapParam.put("size", size);
        mapParam.put("jobName", jobName);
        mapParam.put("executionDateTime", executionDateTime);
        mapParam.put("endDateTime", endDateTime);
        mapParam.put("processState", processState);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(jobName)) {
//                param.append("and JOBNAME like '%").append(jobName).append("%'");
//            }
//            if (ObjectUtils.isNotEmpty(executionDateTime) || ObjectUtils.isNotEmpty(endDateTime)) {
//                param.append("and EXECUTION_DATETIME > STR_TO_DATE('").append(executionDateTime).append("','%Y-%m-%d %H:%i:%s') and END_DATETIME < STR_TO_DATE('").append(endDateTime).append("','%Y-%m-%d %H:%i:%s')");
//            }

            //条件查询执行时间的时间区间
            //开始时间
//            if (ObjectUtils.isNotEmpty(executionDateTime)) {
//                param.append(" AND SUBSTRING(EXECUTION_DATETIME,1,26) > STR_TO_DATE('").append(executionDateTime).append("','%Y-%m-%d %H:%i:%s')");
//            }
            //结束时间
//            if (ObjectUtils.isNotEmpty(endDateTime)) {
//                param.append(" AND SUBSTRING(EXECUTION_DATETIME,1,26) < STR_TO_DATE('").append(endDateTime).append("','%Y-%m-%d %H:%i:%s')");
//            }
//
//            if (ObjectUtils.isNotEmpty(processState)) {
//                param.append("and PROCESS_STATE = '").append(processState).append("'");
//            }
//            int totalSize = this.riskDataInfosDao.getDataInfoConversionListCNT(Const.SQL_DATAINFODAO_GETDATAINFOCONVERSIONCNT.replaceAll("%param%", param.toString()));
            int totalSize = riskDataInfosMapper.getDataInfoConversionListCNT(mapParam);
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
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                mapParam.put("offset", offset);
                List<Map<String, Object>> list = riskDataInfosMapper.getDataInfoConversionList(mapParam);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> equipAccidentList = this.riskDataInfosDao.getDataInfoConversionList(Const.SQL_DATAINFODAO_GETDATAINFOCONVERSION.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
//                        .content(this.riskDataInfosDao.getDataInfoConversionList(Const.SQL_DATAINFODAO_GETDATAINFOCONVERSION.replaceAll("%param%", param.toString())))
                        .content(list)
//                        .elementTotalSize(this.riskDataInfosDao.getDataInfoConversionList(Const.SQL_DATAINFODAO_GETDATAINFOCONVERSION.replaceAll("%param%", param.toString())).size())
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

}
