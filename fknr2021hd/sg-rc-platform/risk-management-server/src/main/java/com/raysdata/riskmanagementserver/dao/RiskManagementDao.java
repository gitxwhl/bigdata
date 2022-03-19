package com.raysdata.riskmanagementserver.dao;

import com.raysdata.riskmanagementserver.bean.vo.SrpRiskVisualizationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Mapper
public interface RiskManagementDao {
    /**
     *电网风险总数
     * @return
     */
    Map<String, Object> getSrpriskgridwarncnt();
    /**
     *电网建设风险总数
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarncnt();
    /**
     *产业风险总数
     * @return
     */
    Map<String, Object> getSrpriskindusriskwarncnt();
    /**
     *网络风险总数
     * @return
     */
    Map<String, Object> getSrpriskeventwarningcnt();
    /**
     *各电压等级作业风险数
     * @return
     */
    Map<String, Object> getSrpworkplanvoltagelevelcnt();
    /**
     *电网运行风险等级
     * @return
     */
    Map<String, Object> getSrpriskgridwarnlevelcnt();
    /**
     *电网建设风险等级
     * @return
     */
    Map<String, Object> getSrpriskgridconstwarnlevelcnt();
    /**
     *产业风险等级
     * @return
     */
    Map<String, Object> getSrpriskindusriskwarnlevelcnt();
    /**
     *网络安全风险等级
     * @return
     */
    Map<String, Object> getSrpriskeventwarninglevelcnt();
    /**
     *电网运行风险
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridwarnareacnt(@Param("srpRiskVisualizationVo") SrpRiskVisualizationVo srpRiskVisualizationVo);
    /**
     *电网建设风险
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskgridconstwarnareacnt(@Param("srpRiskVisualizationVo") SrpRiskVisualizationVo srpRiskVisualizationVo);
    /**
     *产业风险
     * @param srpRiskVisualizationVo
     * @return
     */
    List<Map<String, Object>> getSrpriskindusriskwarnareacnt(@Param("srpRiskVisualizationVo") SrpRiskVisualizationVo srpRiskVisualizationVo);
    /**
     *网络风险
     * @param
     * @return
     */
    List<Map<String, Object>> getSrpriskeventwarningareacnt(@Param("srpRiskVisualizationVo") SrpRiskVisualizationVo srpRiskVisualizationVo);
}
