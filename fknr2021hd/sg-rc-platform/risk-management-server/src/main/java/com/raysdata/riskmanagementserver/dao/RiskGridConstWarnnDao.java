package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.AreaVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskGridconstwarnnoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RiskGridConstWarnnDao<getSrpriskgridconstwarnlevelcnt> {
    /**
     * 各地区电网建设风险预警数
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridconstwarnareacnt(@Param("warningLevel") String warningLevel,@Param("areaId") String areaId);
    /**
     *电网建设风险等级3、4级汇总警数
     * @param
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt(@Param("areaId")String areaId);
    /**
     *电网建设作业类型风险数量统计数
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridconstwarnworkcnt(@Param("areaVo") AreaVo areaVo);
    /**
     *电网建设各市公司本周开始及解除风险数量统计数
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridconstwarnbeginorendcnt(@Param("areaVo")AreaVo areaVo);
    /**
     *电网建设年度历史同期值风险数量统计数   今年
     * @param
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnyearcnt(@Param("areaVo")AreaVo areaVo,@Param("yearList")List<String> yearList);
    /**
     *电网建设年度历史同期值风险数量统计数   上一年
     * @param
     * @return
     */
//    Map<String, Object> getSrpriskgridconstwarnyearcntLast(@Param("srpRiskGridconstwarnnoticeVo") SrpRiskGridconstwarnnoticeVo srpRiskGridconstwarnnoticeVo);

    /**
     *根据id获取
     * @param
     * @return
     */
    Integer getSrpriskgridconstworklistcnt(@Param("srpRiskGridconstwarnnoticeVo") SrpRiskGridconstwarnnoticeVo srpRiskGridconstwarnnoticeVo);
    /**
     *电网建设地市作业计划列表
     * @param
     *  @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridconstworklist(@Param("srpRiskGridconstwarnnoticeVo") SrpRiskGridconstwarnnoticeVo srpRiskGridconstwarnnoticeVo);
    /**
     *电网建设年作业计划详情查询
     * @param workPlanId
     * @return
     */
    Map<String, Object> getSrpriskgridconstworkplaninfo(String workPlanId);
}
