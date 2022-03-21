package com.canteen.mapper;

import com.canteen.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface StSupplierMapper {

    //供应商数量
    Integer getSupplierCnt(@Param("name")String name,@Param("number")String number,
                           @Param("code")String code,@Param("count")String count);

    //供应商列表
    List<Map> getSupplier(@Param("name")String name, @Param("number")String number,
                          @Param("code")String code, @Param("count")String count,
                          @Param("index")int index, @Param("pageSize")int pageSize,
                          @Param("pageMark")String pageMark);

    //通过供应商id查询项目法人
    List<Map> getPerson(@Param("id")Integer id);

    //新增准入供应商
    Integer addSupplier(StSupplier stSupplier);

    //新增项目法人
    Integer addCertificate(StCertificate stCertificate);

    //编辑供应商
    Integer updateSupplier(StSupplier stSupplier);

    //查询供应商评分
    Integer getScoreCnt(@Param("id")int id,@Param("timeType")int timeType);
    List<Map> getScore(@Param("id")int id,@Param("timeType")int timeType);

    //查询供应商扣分
    Integer deductScoreCnt(@Param("id")int id,@Param("timeType")int timeType);
    List<Map> deductScore(@Param("id")int id,@Param("timeType")int timeType);

    //供应商遴选列表
    List<Map> getEligible(@Param("name")String name,@Param("number")String number,@Param("address")String address,
                     @Param("establishTime")String establishTime,@Param("startMoney")String startMoney,
                     @Param("endMoney")String endMoney,@Param("lastPenalty")String lastPenalty,
                     @Param("thisPenalty")String thisPenalty,@Param("state")String state);

    //供应商遴选
    Integer choose(StSupplier stSupplier);

    //修改入选次数
    Integer updateSelection(StSupplier stSupplier);

    String getSelection(StSupplier stSupplier);

    //评分历史记录
    List<Map> getHistory(@Param("id")int id);

    //本年度黑名单
    List<Map> getBlacklist(@Param("id")int id,@Param("timeType")int timeType);

    //添加评分
    Integer addScore(StSupplierScore supplierScore);

    //惩罚详情
    List<Map> getPunishment(@Param("id")int id);

    //添加惩罚
    Integer addPunishment(StPenaltyList stPenaltyList);

    //添加黑名单
    Integer addBlacklist(StBlacklist blacklist);

    //通过id查询供应商附件
    String getCertificates(@Param("id")Integer id);
}
