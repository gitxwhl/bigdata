package com.property.service;

import com.property.entity.LysbGzGuzbxsq;

public interface LysbGzGuzbxsqService {

    //新添加维修申请单（状态默认未提交）
    Object addSq(LysbGzGuzbxsq lysbGzGuzbxsq);

    //更改状态
    Object updateState(LysbGzGuzbxsq lysbGzGuzbxsq);
}
