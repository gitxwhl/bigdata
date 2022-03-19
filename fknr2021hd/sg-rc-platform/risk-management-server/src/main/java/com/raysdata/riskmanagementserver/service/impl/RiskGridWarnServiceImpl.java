package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskGridwannoticeVo;
import com.raysdata.riskmanagementserver.dao.RiskGridWarnDao;
import com.raysdata.riskmanagementserver.service.RiskGridWarnService;
import com.raysdata.riskmanagementserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Service
public class RiskGridWarnServiceImpl implements RiskGridWarnService {
    @Autowired
    private RiskGridWarnDao riskGridWarnDao;


    /**
    * 电网运行风险预警情况
    * warningLevel：风险等级
    * */
    @Override
    public List<Map<String, Object>> getSrpriskgridwarncnt(String warningLevel) {
        return this.riskGridWarnDao.getSrpriskgridwarncnt(warningLevel);
    }


    /**
    * 电网运行风险等级汇总
    * dateType：时间类型;1当天；2当月；3当年
    * */
    @Override
    public List<Map<String, Object>> getSrpriskgridwarnlevelcnt(int dateType) {
//        StringBuilder param = new StringBuilder();
//        switch(dateType) {
//            case 1:
//                param.append(" AND to_days(a.WARNBEGINTIME) = to_days(now())");
//                break;
//            case 2:
//                param.append(" AND DATE_FORMAT( a.WARNBEGINTIME, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
//                break;
//            case 3:
//                param.append(" AND YEAR(a.WARNBEGINTIME) = YEAR(NOW()) ");
//            default:
//        }
        List<Map<String, Object>> levelcntList = this.riskGridWarnDao.getSrpriskgridwarnlevelcnt(dateType);
        levelcntList.add(this.riskGridWarnDao.getSrpriskgridwarnworkcnt(dateType));

        return levelcntList;
    }


    /**
    * 电网运行风险预警列表查询
    * paramJson：列表查询json串（    {"page":"", "size":"", "params":{"title":"", "warningLevel":"", "warnNum":"", "startTime":"", "endTime":"", "publishOrg":"", "warnStatus":""} }）
    * */
    @Override
    public PageBean<Map<String, Object>> getSrpriskgridwarnlist(String paramJson) {
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

        SrpRiskGridwannoticeVo srpRiskGridwannoticeVo = new SrpRiskGridwannoticeVo();

        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
//            StringBuilder param = new StringBuilder();

//            if (ObjectUtils.isNotEmpty(title)) {
                srpRiskGridwannoticeVo.setTitle(title);
//                param.append(" and selw.TITLE like '%" + title + "%' ");
//            }

//            if (ObjectUtils.isNotEmpty(warningLevel)) {
                srpRiskGridwannoticeVo.setWarningLevel(warningLevel);
//                param.append(" and selw.WARNINGLEVEL = '" + warningLevel + "' ");
//            }

//            if (ObjectUtils.isNotEmpty(warnStatus)) {
                srpRiskGridwannoticeVo.setWarnStatus(warnStatus);
//                param.append(" and selw.WARNSTATUS = '" + warnStatus + "' ");
//            }

//            if (ObjectUtils.isNotEmpty(warnNum)) {
                srpRiskGridwannoticeVo.setWarnNum(warnNum);
//                param.append(" and selw.WARNNUM like '%" + warnNum + "%' ");
//            }

//            if (ObjectUtils.isNotEmpty(publishOrg)) {
                srpRiskGridwannoticeVo.setPublishOrg(publishOrg);
//                param.append(" and selw.PUBLISH_ORG like '%" + publishOrg + "%' ");
//            }



//            if (ObjectUtils.isNotEmpty(startTime) && ObjectUtils.isNotEmpty(endTime)) {
                srpRiskGridwannoticeVo.setStartTime(startTime);
                srpRiskGridwannoticeVo.setEndTime(endTime);
//                param.append(" and selw.WARNBEGINTIME BETWEEN STR_TO_DATE('" + startTime + "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('" + endTime + "', '%Y-%m-%d %H:%i:%s') ");
//            }

            int totalSize = this.riskGridWarnDao.getSrpriskgridwarnlistcnt(srpRiskGridwannoticeVo);
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
                srpRiskGridwannoticeVo.setOffset(offset);
                srpRiskGridwannoticeVo.setSize(size);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskGridWarnDao.getSrpriskgridwarnlist(Const.SQL_RISKGRIDWARNDAO_GETSRPRISKGRIDWARNLIST.replaceAll("%param%", param.toString()));
                return PageBean.<Map<String, Object>>builder()
                        .content(this.riskGridWarnDao.getSrpriskgridwarnlist(srpRiskGridwannoticeVo))
                        .elementTotalSize(this.riskGridWarnDao.getSrpriskgridwarnlist(srpRiskGridwannoticeVo).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }

    /**
    * 电网运行风险预警详情查询
    * gridWarnNoticeId：电网运行风险预警通知单id
    * */
    @Override
    public Map<String, Map<String, Object>> getSrpriskgridwarninfobygridwarnnoticeid(String gridWarnNoticeId) {
        Map<String, Map<String, Object>> warnInfoMap = new HashMap<String, Map<String, Object>>(9);
        warnInfoMap.put("SrpRiskGridWarnInfo", this.riskGridWarnDao.getSrpriskgridwarninfo(gridWarnNoticeId));
        warnInfoMap.put("RiskGridWarnFeedBackInfo", this.riskGridWarnDao.getSrpriskgridwarnfeedbackinfo(gridWarnNoticeId));
        warnInfoMap.put("SrpRiskGridWarnReportInfo", this.riskGridWarnDao.getSrpriskgridwarnreportinfo(gridWarnNoticeId));
        warnInfoMap.put("SrpRiskGridWarnFormInfo", this.riskGridWarnDao.getSrpriskgridwarnforminfo(gridWarnNoticeId));
        warnInfoMap.put("SrpRiskGridWarnRelInfo", this.riskGridWarnDao.getSrpriskgridwarnrelinfo(gridWarnNoticeId));
        return warnInfoMap;
    }
}
