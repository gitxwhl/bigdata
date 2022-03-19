package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskIndusriskWarnnoticeVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVoBo;
import com.raysdata.riskmanagementserver.dao.RiskIndusRiskWarnDao;
import com.raysdata.riskmanagementserver.service.RiskIndusRiskWarnService;
import com.raysdata.riskmanagementserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Service
public class RiskIndusRiskWarnServiceImpl implements RiskIndusRiskWarnService {
    @Autowired
    RiskIndusRiskWarnDao riskIndusRiskWarnDao;

    /**
     * {"industryType":"","startTime":"","endTime":""}
     * */
    @Override
    public List<Map<String, Object>> getSrpriskindusriskwarnareacnt(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String industryType = JSONObject.parseObject(paramJson).getString("industryType");
        String startTime = JSONObject.parseObject(paramJson).getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getString("endTime");
        SrpRiskSysTabVoBo srpRiskSysTabVoBo = new SrpRiskSysTabVoBo();
//        StringBuilder param = new StringBuilder();
//        if(ObjectUtils.isNotEmpty(startTime) && ObjectUtils.isNotEmpty(endTime)){
//            srpRiskSysTabVoBo.setStartTime(startTime);
//            srpRiskSysTabVoBo.setEndTime(endTime);
//            param.append("a.WARNBEGINTIME between STR_TO_DATE('").append(startTime).append("','%Y-%m-%d %H:%i:%s') and STR_TO_DATE('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
//        }else {
            srpRiskSysTabVoBo.setStartTime(startTime);
            srpRiskSysTabVoBo.setEndTime(endTime);
            srpRiskSysTabVoBo.setIndustryType(industryType);

//            param.append("to_days( a.WARNBEGINTIME ) = to_days( now( ) )");
//        }
        return riskIndusRiskWarnDao.getSrpriskindusriskwarnareacnt(srpRiskSysTabVoBo);
//        return riskIndusRiskWarnDao.getSrpriskindusriskwarnareacnt(Const.SQL_RISKINDUSRISKWARNDAO_GETSRPRISKINDUSRISKWARNAREACNT.replaceAll("%param%",param.toString()).replaceAll("%param1%",industryType));
    }


    @Override
    public Map<String, Object> getSrpriskdkycompanycnt() {
        return riskIndusRiskWarnDao.getSrpriskdkycompanycnt();
    }

    @Override
    public Map<String, Object> getSrpriskdkycompanystaffcnt() {
        return riskIndusRiskWarnDao.getSrpriskdkycompanystaffcnt();
    }

    @Override
    public Map<String, Object> getRiskindusriskwarncnt() {
        return riskIndusRiskWarnDao.getRiskindusriskwarncnt();
    }

    @Override
    public Map<String, Object> getSrpriskstationinfo() {
        Map<String, Object> srpRiskStationMap = new HashMap<>(9);
        srpRiskStationMap.put("reservoirStationInfo",riskIndusRiskWarnDao.getSrpriskreservoirstationinfo());

        Map<String, Object> map = riskIndusRiskWarnDao.getSrpriskdaminforstationinfo();
        //大坝总数
        int reservoiCnt = Integer.parseInt(String.valueOf(map.get("RESERVOI_CNT")));
        //通过安全性注册数
        int count = Integer.parseInt(String.valueOf(map.get("WHETHER_TO_PASS_CNT")));
        //创建一个数值格式化对象
        NumberFormat nf = NumberFormat.getInstance();
        //设置精确到小数点后2位
        nf.setMaximumFractionDigits(2);
        //占比
        String format = nf.format((float) count / (float) reservoiCnt * 100) + "%";
        map.put("proportion",format);
        srpRiskStationMap.put("daminforStationInfo",map);

        srpRiskStationMap.put("hydroPowerStationInfo",riskIndusRiskWarnDao.getSrpriskhydropowerstationinfo());
        return srpRiskStationMap;
    }

    @Override
    public Map<String, Object> getRiskhydropowerstationinfo() {
        return riskIndusRiskWarnDao.getRiskhydropowerstationinfo();
    }

    @Override
    public Map<String, Object> getRiskbiologicalmaterinfo() {
        return riskIndusRiskWarnDao.getRiskbiologicalmaterinfo();
    }

    @Override
    public PageBean<Map<String, Object>> getSrpriskindusriskwarnlist(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String title = JSONObject.parseObject(paramJson).getJSONObject("params").getString("title");
        String warningLevel = JSONObject.parseObject(paramJson).getJSONObject("params").getString("warningLevel");
        String warnNum = JSONObject.parseObject(paramJson).getJSONObject("params").getString("warnNum");
        String publishOrg = JSONObject.parseObject(paramJson).getJSONObject("params").getString("publishOrg");
        String warnStatus = JSONObject.parseObject(paramJson).getJSONObject("params").getString("warnStatus");
        String startTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getJSONObject("params").getString("endTime");
        SrpRiskIndusriskWarnnoticeVo srpRiskIndusriskWarnnoticeVo =new SrpRiskIndusriskWarnnoticeVo();
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            StringBuilder param = new StringBuilder();
//            if (ObjectUtils.isNotEmpty(title)) {
                srpRiskIndusriskWarnnoticeVo.setTitle(title);
//                param.append(" and selw.TITLE like '%" + title + "%' ");
//            }

//            if (ObjectUtils.isNotEmpty(warningLevel)) {
                srpRiskIndusriskWarnnoticeVo.setWarningLevel(warningLevel);
//                param.append(" and selw.WARNINGLEVEL = '" + warningLevel + "' ");
//            }

//            if (ObjectUtils.isNotEmpty(warnStatus)) {
                srpRiskIndusriskWarnnoticeVo.setWarnStatus(warnStatus);
//                param.append(" and selw.WARNSTATUS = '" + warnStatus + "' ");
//            }

//            if (ObjectUtils.isNotEmpty(warnNum)) {
                srpRiskIndusriskWarnnoticeVo.setWarnNum(warnNum);
//                param.append(" and selw.WARNNUM like '%" + warnNum + "%' ");
            }

//            if (ObjectUtils.isNotEmpty(publishOrg)) {
                srpRiskIndusriskWarnnoticeVo.setPublishOrg(publishOrg);
//                param.append(" and selw.PUBLISH_ORG like '%" + publishOrg + "%' ");
//            }



//            if (ObjectUtils.isNotEmpty(startTime) && ObjectUtils.isNotEmpty(endTime)) {
                srpRiskIndusriskWarnnoticeVo.setStartTime(startTime);
                srpRiskIndusriskWarnnoticeVo.setEndTime(endTime);
//                param.append(" and selw.WARNBEGINTIME BETWEEN STR_TO_DATE('" + startTime + "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('" + endTime + "', '%Y-%m-%d %H:%i:%s') ");
//            }

            int totalSize = this.riskIndusRiskWarnDao.getSrpriskindusriskwarnlistcnt(srpRiskIndusriskWarnnoticeVo);
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
                int offset = page -1;
//                int offset=0;
//                if (totalSize <= 10){
//                     offset = page -1;
//                }else {
//                      offset = (int) Math.floor(totalSize)/10-1;
//                }

                srpRiskIndusriskWarnnoticeVo.setOffset(offset);
                srpRiskIndusriskWarnnoticeVo.setSize(size);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskIndusRiskWarnDao.getSrpriskindusriskwarnlist(srpRiskIndusriskWarnnoticeVo);
                return PageBean.<Map<String, Object>>builder()
                        .content(this.riskIndusRiskWarnDao.getSrpriskindusriskwarnlist(srpRiskIndusriskWarnnoticeVo))
                        .elementTotalSize(this.riskIndusRiskWarnDao.getSrpriskindusriskwarnlist(srpRiskIndusriskWarnnoticeVo).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

