package com.property.mapper;

import com.property.entity.LysbGzGuzbxsq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LysbGzGuzbxsqMapper {

    //调用函数获取工单号
    String getGdh();

    //新添加维修申请单（状态默认未提交）
    void addSq(LysbGzGuzbxsq lysbGzGuzbxsq);

    //更改状态
    void updateState(LysbGzGuzbxsq lysbGzGuzbxsq);
}
