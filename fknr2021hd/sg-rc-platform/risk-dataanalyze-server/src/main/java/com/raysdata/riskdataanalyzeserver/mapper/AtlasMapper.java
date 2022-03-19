package com.raysdata.riskdataanalyzeserver.mapper;

import com.raysdata.riskdataanalyzeserver.bean.Instanceinfo;
import com.raysdata.riskdataanalyzeserver.bean.Knowledge;
import com.raysdata.riskdataanalyzeserver.bean.SrpModeling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface AtlasMapper {
    //------------------------------------------------安全风险知识图谱------------------------------------------------------------
    /*
     *页面加载接口
     *知识图谱建模查询
     * paramJson:{"typeName":""}
     * */
    List<Map<String,Object>> getList(Map typeName);
    List<Map<String,Object>> getListdetails(Map typeId);

     List getModeingList();

    Integer getListCount(Map typeName);

   String getExampleName(String typeName);



    /*
     *删除接口
     *知识图谱建模删除
     * paramJson:{"typeId":""}
     * */
    String getTypeId(String typeId);
    /*
     *根据id获取知识建模
     * paramJson:{"typeId":""}
     * */
    SrpModeling findSrpModeling(String typeId);







    int rowsNum(String typeId);

    String getModelTyteName(@Param("typeCode")String typeCode);

    String getModelName(String columnnum);

    List<Map<String,Object>> getList1(String typeCode);

    int rowsNum1(@Param("instanceIds") List<String> instanceIds);

    int rowsNum2(String typeCode);

    int rowsNum3(String typeId);

    /*
     *新增接口
     *知识图谱建模新增
     * paramJson:{"params":{"typeName":"","typeCode":"","typeDescribe":"","typeIcon":""}}
     * */
    int addModeing(Map param);

    String getsrgMing(String typeCode);

    /*
     *生成编号接口
     * */
    String getMaxId();

    int getModeingCount();

    /*
     *编辑接口
     *知识图谱建模编辑
     * paramJson:{"params":{"typeId":"","typeName":"","typeCode":"","typeDescribe":"","typeIcon":""}}
     * */
    int updateModeling(Map<String, Object> map);

    //----------------------------------------------知识获取------------------------------------------------------------
    /*
     *知识获取
     *关系表查询
     * */
    Integer getRelationInfoCount();

    List<Map<String, Object>> getRelationInfoList(Map param);

    /*
     *知识获取
     *关系表编号生成
     * */
    String getRelationNumber();

    /*
     *新增接口
     *知识获取新增关系
     * paramJson:{"params":{"relationName":"","relationCode":"","sourceType":"","targetType":"","relationType":"","relationDescribe":""}}
     * */
    String getModelingGraphId(String sourceType);

    int insertRelationInfo(Map param);
    //实例关系根据编码获取关系id
    String findRelationInfo(String relationCode);



    /*
     *知识获取界面
     *实例信息查询
     * paramJson:{"page":"", "size":"", "params":{"modelingId":"","instanceName":""}}
     * */
    String getTypeCode(String modelingId);

    int getInstanceInfoCount(Map param);

    List<Map<String, Object>> getInstanceInfoList(Map param);

    /*
     *知识获取
     *实例表编号生成
     * */
    String instanceNumber();

    /*
     *知识获取
     *删除实例
     *paramJson:{"params":[{"instanceId":""}]}
     * */
    String findInstanceInfoName(String instanceId);

    /*
     *
     *根据实例id获取实例
     *paramJson:{"params":[{"instanceId":""}]}
     * */
    Instanceinfo findInsta(String instanceId);

    int deleteInstanceinfo(@Param("instanceIds") List<String> instanceIds);


    /*
     *知识获取
     *新增实例
     *paramJson:{"params":{"modelingId":"","instanceName":"","instanceCode":"","instanceDescribe":""},"idList":[{"instanceId":"","relationId":""}]}
     *
     * */
    int insertInstanceInfo(Map param);

    //根据名称获取实例
    int findInstanceCount(@Param("instanceName") String instanceName);

    //根据名称获取TYEP_NAME
    String getTypeName(String instanceName);



    String getId(@Param("instanceCode") String instanceCode);



    String getName(String instanceId);

    String getRelationName(String relationId);

    int insert(Map param);
    //根据编码查询实例id
    String findInscebyid(String instaceCode);



   /**
    * 查询知识获取实例列表条数
    * @param
    * @return
    */
   int getIntanceCout();








 /*
     *知识获取
     *根据源类别名称查询关系
     *paramJson:{"modelingId":""}
     * */
    List<Map<String,Object>> getRelationInfoById(String modelingId);

    /*
     *知识获取
     *根据类别查询实例
     *paramJson:{"targetType":"","relationId":""}
     * */
    List<Map<String,Object>> getInstanceInfoListByType(String targetType);

    /*
     *知识获取
     *根据实例id获取实例
     *paramJson:{"targetType":"","relationId":""}
     * */
    String getInstanceInfoListName(String instanceId);







    /*
     *知识获取
     *根据id查询实例详情
     *paramJson:{"instanceId":""}
     * */
    List<Map<String,Object>> getInstanceInfoById(String instanceId);

    /*
     *知识获取
     *通过ID查询实例关系
     *paramJson:{"instanceId":"","targetType":"","relationId":""}
     * */
    int getInstancerelationinfoByIdCount(Map<String, Object> map);


    //获取关联关系绑定关系和名称
    List<Map<String,Object>> getInstanceRelationInfo(Map<String, Object> map1);
    //新增根据关系id获取关联关系
    List<Map<String,Object>> getInstanceRelationInfoInser(Map<String, Object> map1);












    List<Map<String,Object>> getExample(Map<String, Object> map1);

//    List<Map<String,Object>> getExample(@Param("targetType") String targetType,@Param("substrings") String [] substrings);

    /*
     *知识获取
     *编辑实例
     *paramJson:{"params":{"instanceId":"","instanceName":"","instanceDescribe":""},"idList":[{"instanceId":"","relationId":""}]}
     *
     * */
    int updateInstanceinfo(Map<String, Object> map);


    //根据编号获取名称
    Knowledge getInName(String instanceId);

    //根据名称和类别获取
    List<Instanceinfo> findInstanceinfobj();
    Instanceinfo findInstanceinfobj1(@Param("instanceName")String instanceName);


    Instanceinfo findinstancexq(Instanceinfo instanceinfo);

    int updateInstanceRelationInfo(Map<String, Object> map1);

    Instanceinfo findInstanceinfo(String instanceId);




    int updateInstanceInfo(Map<String, Object> map1);

    int deleteInstanceRelationInfo(Map<String, Object> map2);

    int getRelationCount(Map<String, Object> map3);



    /*
     *知识获取
     *拓扑图
     *paramJson:{"instanceId":""}
     * */
    List<Map<String,Object>> getImage(String instanceId);

    List<Map<String,Object>> getImageTuoPu(@Param("instanceIds") List<String> instanceIds);

    List<Map<String,Object>> getImage2(@Param("targetIds") List<String> targetIds);

    /**
     * 通过实例名称查询拓扑图
     * @param
     * @return
     */
    String getInstanceName(String instanceName);

    List<String> getInstanceNameTp(String instanceName);
    /**
     * 校验编码重复
     * @param relationCode
     * @return
     */
    int checkRelationCode(String relationCode);

    /**
     * 新增关联关系
     */

    int insertTopology(Map<String, Object> map2);
    /**
     * 修改关联关系
     */
    int updateTopology(Map<String, Object> map2);
   /**
    * 实例id关系id等于空 即idList为空清空实例id下所有的关系数据
    */
   int delTopology(Map<String, Object> map2);

 /**
  * 实例关联关系新增（单独保存）
  * @param paramJson
  * @return
  */
 int InsertInstanceInfoInclude(String paramJson);


 /**
  * 获取实例关联关系列表
  */
  List<Map<String, String>> findTopology();
    /**
     * 根据id获取实例名称
     */
    String getSrpModeling(String typeId);
    /**
     * 根据编号查询知识图谱建模
     */
//    List<Map<String,Object>> getSrpModelings(Map map);

    SrpModeling getSrpModelings(String typeCode);

}
