package com.raysdata.riskdataanalyzeserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author FangYF
 * @date 2021-08-05
 */
@Mapper
@Repository
public interface RiskWorkStaffInfoMapper {
    /**
     * 人员基本情况count
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getRiskWorkerInfosListCNT1(Map<String, Object> mapParam);

    /**
     * 人员基本情况list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerInfosList1(Map<String, Object> mapParam);

    /**
     * 人员基本情况list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerInfosList2(Map<String, Object> mapParam);

    /**
     * 人员总数查询
     *
     * @return Integer
     */
    Integer getAllPeopleNum();

    /**
     * 人员性质分类数量查询
     *
     * @param sql 动态条件
     * @return Integer
     */
    Integer getPeopleNumByWORKERNATURE(String sql);

    /**
     * 人员项目列表查询count
     *
     * @param workerId workerId
     * @return Integer
     */
    Integer getRiskWorkertoProjectListCNT1(String workerId);

    /**
     * 人员项目列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkertoProjectList1(Map<String, Object> mapParam);

    /**
     * 人员全网信息联动列表查询count
     *
     * @return Integer
     */
    Integer getRiskWorkerLinkageInfosListCNT();

    /**
     * 人员全网信息联动列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerLinkageInfosList(Map<String, Object> mapParam);

    /**
     * 人员单位变更记录列表查询count
     *
     * @param workerId workerId
     * @return Integer
     */
    Integer getRiskWorkerUnitChangeListCNT(String workerId);

    /**
     * 人员单位变更记录列表查询list
     *
     * @param workerId workerId
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerUnitChangeList(String workerId);

    /**
     * 人员违章记录列表查询count
     *
     * @param VIOLATION_STAFF_ID VIOLATION_STAFF_ID
     * @return Integer
     */
    Integer getRiskWorkerViolationListCNT(String VIOLATION_STAFF_ID);

    /**
     * 人员违章记录列表查询list
     *
     * @param VIOLATION_STAFF_ID VIOLATION_STAFF_ID
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerViolationList(String VIOLATION_STAFF_ID);

    /**
     * 人员安全资信列表查询count
     *
     * @return Integer
     */
    Integer getRiskWorkerSafeInfoListCNT();

    /**
     * 人员安全资信列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerSafeInfoList(Map<String, Object> mapParam);

    /**
     * 人员培训记录列表查询count
     *
     * @param workerId workerId
     * @return Integer
     */
    Integer getRiskWorkerSafeLearnListCNT(String workerId);

    /**
     * 人员培训记录列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerSafeLearnList(Map<String, Object> mapParam);

    /**
     * 人员考试记录列表查询count
     *
     * @param workerId workerId
     * @return Integer
     */
    Integer getRiskWorkerSafetyTestListCNT(String workerId);

    /**
     * 人员考试记录列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerSafetyTestList(Map<String, Object> mapParam);

    /**
     * 人员违章记录列表查询count
     *
     * @param PENALTY_STAFF_ID PENALTY_STAFF_ID
     * @return Integer
     */
    Integer getRiskWorkerViolationPointsListCNT(String PENALTY_STAFF_ID);

    /**
     * 人员违章记录列表查询list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getRiskWorkerViolationPointsList(Map<String, Object> mapParam);

    /**
     * 根据人员ID查询单位ID
     *
     * @param STAFF_ID STAFF_ID
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getOrgId(String STAFF_ID);

    /**
     * 根据单位ID查询单位数量
     *
     * @param mapParam 动态条件
     * @return Integer
     */
    Integer getOrgNumByOrgId(Map<String, Object> mapParam);

    /**
     * 查询准入情况list
     *
     * @param mapParam 动态条件
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getAccessList(Map<String, Object> mapParam);

    /**
     * 违章人数查询
     *
     * @return Integer
     */
    Integer getViolationPeopleNum();

    /**
     * 参加考试人数(通过/未通过)查询
     *
     * @param pass 条件
     * @return Integer
     */
    Integer getExaminationNum(String pass);
}
