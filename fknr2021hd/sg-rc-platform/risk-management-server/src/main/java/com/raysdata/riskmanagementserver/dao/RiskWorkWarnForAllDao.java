package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.AreaVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVoPo;
import com.raysdata.riskmanagementserver.bean.vo.SrpWorkWorkplandaysVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Mapper
public interface RiskWorkWarnForAllDao {
    /**
     * 地图各单风险作业预警情况
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskworkwarnforallareacnt(@Param("srpRiskSysTabVoPo") SrpRiskSysTabVoPo srpRiskSysTabVoPo);
    /**
     * 地图各单风险作业预警情况s分开查询 getSrpriskworkwarnforallareacnt 区分
     * @param
     * @return
     */
    List<Map<String, Object>> findSrpriskworkwarnforallareacnt(@Param("warnType") String warnType , @Param("areaId") String areaId);


    /**
     *作业类型风险数量统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskworkwarnforallworktypecnt(String areaId);
    /**
     *风险作业状态数量统计
     * @param
     * @return
     */
    Map<String, Object> getSrpriskworkwarnworkstatecnt(@Param("areaVo") AreaVo areaVo);
    /**
     *根据id获取
     * @param
     * @return
     */
    Integer getSrpriskworkplanlistcnt(@Param("srpWorkWorkplandaysVo") SrpWorkWorkplandaysVo srpWorkWorkplandaysVo);

    /**
     *根据id获取
     * @param
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskworkplanlist(@Param("srpWorkWorkplandaysVo") SrpWorkWorkplandaysVo srpWorkWorkplandaysVo);
    /**
     *作业计划详情查询
     * @param workPlanId
     * @return
     */
    Map<String, Object> getSrpriskworkplaninfo(String workPlanId);

}
