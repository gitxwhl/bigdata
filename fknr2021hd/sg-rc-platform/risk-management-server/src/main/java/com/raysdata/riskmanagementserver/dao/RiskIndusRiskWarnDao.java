package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.SrpRiskIndusriskWarnnoticeVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVoBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Mapper
public interface RiskIndusRiskWarnDao {


    /**
     * 电网建设风险预警情况
     * industryType:产业类型
     * @param
     * @return
     * */
    List<Map<String, Object>> getSrpriskindusriskwarnareacnt(@Param("srpRiskSysTabVoBo") SrpRiskSysTabVoBo srpRiskSysTabVoBo);


    /**
     * 企业分布情况汇总
     * @return
     * */
    Map<String, Object> getSrpriskdkycompanycnt();


    /**
     * 人员构成汇总统计
     * @return
     * */
    Map<String, Object> getSrpriskdkycompanystaffcnt();


    /**
     * 产业安全风险预警
     * @return
     * */
    Map<String, Object> getRiskindusriskwarncnt();

    /**
     * 获取
     *@return
     * */
    Map<String, Object> getSrpriskreservoirstationinfo();
    /**
     * 获取
     *@return
     * */
    Map<String, Object> getSrpriskdaminforstationinfo();
    /**
     * 获取
     *@return
     * */
    Map<String, Object> getSrpriskhydropowerstationinfo();

    /**
     * 抽水蓄能电站洞室施工建设情况汇总
     * @return
     * */
    Map<String, Object> getRiskhydropowerstationinfo();


    /**
     * 生物质料场情况汇总
     * @return
     * */
    Map<String, Object> getRiskbiologicalmaterinfo();
    /**
     * 获取
     * @param
     * @return
     * */
    Integer getSrpriskindusriskwarnlistcnt(@Param("srpRiskIndusriskWarnnoticeVo") SrpRiskIndusriskWarnnoticeVo srpRiskIndusriskWarnnoticeVo);

    /**
     * 产业风险分页查询
     * param :json参数
     * @param
     * @return
     * */
    List<Map<String, Object>> getSrpriskindusriskwarnlist(@Param("srpRiskIndusriskWarnnoticeVo") SrpRiskIndusriskWarnnoticeVo srpRiskIndusriskWarnnoticeVo);
}
