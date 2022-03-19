package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVo;
import com.raysdata.riskmanagementserver.dao.RiskWorkWarnDao;
import com.raysdata.riskmanagementserver.service.RiskEventWarnServer;
import com.raysdata.riskmanagementserver.service.RiskGridConstWarnServer;
import com.raysdata.riskmanagementserver.service.RiskGridWarnService;
import com.raysdata.riskmanagementserver.service.RiskWorkWarnService;
import com.raysdata.riskmanagementserver.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zyyy
 */
@Service
public class RiskWorkWarnServiceImpl implements RiskWorkWarnService {

    @Autowired
    private RiskWorkWarnDao riskWorkWarnDao;
    @Autowired
    private RiskGridConstWarnServer riskGridConstWarnServer;
    @Autowired
    private RiskEventWarnServer riskEventWarnServer;
    @Autowired
    private RiskGridWarnService riskGridWarnService;
    @Autowired
    RedisTemplate redisTemplate;

//    @Autowired
//    private RiskIndusRiskWarnService riskIndusRiskWarnService;




    /*
    *风险预警情况{"warningLevel":"01","warnType":"1"}
    *warnType:风险类别
    * warnType：风险级别
    * */

    @Override
    public List<Map<String, Object>> getSrpriskworkwarnareacnt(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String warningLevel = JSONObject.parseObject(paramJson).getString("warningLevel");
        int warnType =  (ObjectUtils.isNotEmpty(JSONObject.parseObject(paramJson).getString("warnType"))? Integer.parseInt(JSONObject.parseObject(paramJson).getString("warnType")):0);
        List<Map<String, Object>> resultList = new ArrayList<>();
        SrpRiskSysTabVo srpRiskSysTabVo = new SrpRiskSysTabVo();
        srpRiskSysTabVo.setNetoprationStatus(warningLevel);
        switch(warnType) {
            //电网风险
            case 1:
                if(ObjectUtils.isNotEmpty(warningLevel)){
//                    resultList = riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("dwkey");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("dwkey");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
                                redisTemplate.opsForValue().set("dwkey",resultList);
                            }else {
                            }
                        }
//                    }
                }else{
//                    resultList = riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("dwkey1");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("dwkey1");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
                                redisTemplate.opsForValue().set("dwkey1",resultList);
                            }else {
                            }
                        }

//                    }
                }
                break;
            //作业风险
            case 2:
                if(ObjectUtils.isNotEmpty(warningLevel)){
//                    resultList = riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zykey1");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zykey1");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
                                redisTemplate.opsForValue().set("zykey1",resultList);
                            }else {
                            }
                        }
//                    }

                }else{
//                    resultList = riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zykey2");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zykey2");
                            if(null== resultList ){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
                                redisTemplate.opsForValue().set("zykey2",resultList);
                            }else {
                            }
                        }
//                    }


                }
                break;
            //电网建设风险
            case 3:
                if(ObjectUtils.isNotEmpty(warningLevel)){
//                    resultList = riskGridConstWarnServer.getSrpriskgridconstwarnareacnt("{\"warningLevel\":"+warningLevel+",\"areaId\":\"\"}");
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("jianshekey2");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("jianshekey2");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskGridConstWarnServer.getSrpriskgridconstwarnareacnt("{\"warningLevel\":"+warningLevel+",\"areaId\":\"\"}");
                                redisTemplate.opsForValue().set("jianshekey2",resultList);
                            }else {
                            }
                        }
//                    }


                }else{
//                    resultList = riskGridConstWarnServer.getSrpriskgridconstwarnareacnt("{\"warningLevel\":\"\",\"areaId\":\"\"}");
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("jianshekey3");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("jianshekey3");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskGridConstWarnServer.getSrpriskgridconstwarnareacnt("{\"warningLevel\":\"\",\"areaId\":\"\"}");
                                redisTemplate.opsForValue().set("jianshekey3",resultList);
                            }else {
                            }
                        }
//                    }
                }
                break;
            //产业风险
            case 4:
                if(ObjectUtils.isNotEmpty(warningLevel)){
//                    resultList = riskWorkWarnDao.getSrpriskindusriskwarnareacnt(warningLevel);
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("cykey1");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("cykey1");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskindusriskwarnareacnt(warningLevel);
                                redisTemplate.opsForValue().set("cykey1",resultList);
                            }else {
                            }
                        }
//                    }
                }else{
//                    resultList = riskWorkWarnDao.getSrpriskindusriskwarnareacnt(warningLevel);
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("cykey2");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("cykey2");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskWorkWarnDao.getSrpriskindusriskwarnareacnt(warningLevel);
                                redisTemplate.opsForValue().set("cykey2",resultList);
                            }else {
                            }
                        }
//                    }
                }
                break;
            //网络风险
            case 5:
                if(ObjectUtils.isNotEmpty(warningLevel)){
//                    resultList = riskEventWarnServer.getSrpriskeventwarnareacnt("{\"warningLevel\":"+warningLevel+", \"systremApplication\":\"\", \"netoprationStatus\":\"\"}");
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("wlkey2");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("wlkey2");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskEventWarnServer.getSrpriskeventwarnareacnt("{\"warningLevel\":"+warningLevel+", \"systremApplication\":\"\", \"netoprationStatus\":\"\"}");
                                redisTemplate.opsForValue().set("wlkey2",resultList);
                            }else {
                            }
                        }
//                    }
                }else{
//                    resultList = riskEventWarnServer.getSrpriskeventwarnareacnt("{\"warningLevel\":\"\", \"systremApplication\":\"\", \"netoprationStatus\":\"\"}");
//                    List<Map<String, Object>> resultList = new ArrayList<>();
                    //序列化
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("wlkey3");
//                    if(null==resultList){
                        //双重检测锁
                        synchronized (this){
                            //再读redis
                            resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("wlkey3");
                            if(null== resultList){
                                //查询数据库
                                resultList=riskEventWarnServer.getSrpriskeventwarnareacnt("{\"warningLevel\":\"\", \"systremApplication\":\"\", \"netoprationStatus\":\"\"}");
                                redisTemplate.opsForValue().set("wlkey3",resultList);
                            }else {
                            }
                        }
//                    }
                }
                break;
            //作业计划数
            default:
//                resultList = riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));

//                List<Map<String, Object>> resultList = new ArrayList<>();
                //序列化
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zyjhkey2");
//                if(null==resultList){
                    //双重检测锁
                    synchronized (this){
                        //再读redis
                        resultList= (List<Map<String, Object>>)redisTemplate.opsForValue().get("zyjhkey2");
                        if(null== resultList){
                            //查询数据库
                            resultList=riskWorkWarnDao.getSrpriskworkwarnareacnt(warningLevel,Integer.toString(warnType));
                            redisTemplate.opsForValue().set("zyjhkey2",resultList);
                        }else {
                        }
                    }
//                }

        }
        return resultList;
    }


    /**
     * 电网运行风险等级汇总
     * dateType：时间类型;1当天；2当月；3当年
     * */
    @Override
    public List<Map<String, Object>> getSrpriskgridwarnlevelcnt() {
        List<Map<String, Object>> list=null;
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("yxkey");
//        if(null==list){
            //双重检测锁
            synchronized (this){
                //再读redis
                list= (List<Map<String, Object>>)redisTemplate.opsForValue().get("yxkey");
                if(null== list){
                    //查询数据库
                    list=riskGridWarnService.getSrpriskgridwarnlevelcnt(1);
                    redisTemplate.opsForValue().set("yxkey",list);
                }else {
                }
                return list;
            }
//        }

    }

    /**
     * 产业安全风险预警
     * */
    @Override
    public Map<String, Object> getRiskindusriskwarncnt() {
        Map<String, Object> list=null;
//序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        list= (Map<String, Object>)redisTemplate.opsForValue().get("cyyjkey");
//        if(null==list){
            //双重检测锁
            synchronized (this){
                //再读redis
                list= (Map<String, Object>)redisTemplate.opsForValue().get("cyyjkey");
                if(null== list){
                    //查询数据库
                    list=riskWorkWarnDao.getRiskindusriskwarncnt();
                    redisTemplate.opsForValue().set("cyyjkey",list);
                }else {
                }
                return list;
            }
//        }

    }


    /*
     * 网络预警各维度数量统计
     * */

    @Override
    public Map<String, Object> getSrpriskeventtypecnt() {
        Map<String, Object> list=null;
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        list= (Map<String, Object>)redisTemplate.opsForValue().get("wlkeys");
//        if(null==list){
            //双重检测锁
            synchronized (this){
                //再读redis
                list= (Map<String, Object>)redisTemplate.opsForValue().get("wlkeys");
                if(null== list){
                    //查询数据库
                    list= riskEventWarnServer.getSrpriskeventtypecnt();
                    redisTemplate.opsForValue().set("wlkeys",list);
                }else {
                }
                return list;
            }
//        }

    }


    /**
    * 电网建设风险等级
    * */
    @Override
    public Map<String, Object> getSrpriskgridconstwarnlevelcnt() {
        Map<String, Object> list=null;
        //序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        list= (Map<String, Object>)redisTemplate.opsForValue().get("jskey");
//        if(null==list){
            //双重检测锁
            synchronized (this){
                //再读redis
                list= (Map<String, Object>)redisTemplate.opsForValue().get("jskey");
                if(null== list){
                    //查询数据库
                    list=riskWorkWarnDao.getSrpriskgridconstwarnlevelcnt();
                    redisTemplate.opsForValue().set("jskey",list);
                }else {
                }
                return list;
            }
//        }

    }

}
