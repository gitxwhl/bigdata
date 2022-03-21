package com.property.service.impl;

import com.property.entity.LysbGzGuzbxsq;
import com.property.mapper.LysbGzGuzbxsqMapper;
import com.property.service.LysbGzGuzbxsqService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class LysbGzGuzbxsqServiceImpl implements LysbGzGuzbxsqService {

    @Autowired
    private LysbGzGuzbxsqMapper lysbGzGuzbxsqMapper;

    /**
     * 新添加维修申请单（状态默认未提交）
     * @param lysbGzGuzbxsq
     * @return
     */
    @Override
    public Object addSq(LysbGzGuzbxsq lysbGzGuzbxsq) {
        lysbGzGuzbxsq.setID(UUID.randomUUID().toString().replace("-",""));
        //获取工单号
        String gdh = lysbGzGuzbxsqMapper.getGdh();
        lysbGzGuzbxsq.setWEIXGDH(gdh);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        String date = sdf.format(new Date());
        lysbGzGuzbxsq.setSHENQSJ(date);
        //设置状态为 01：未提交
        lysbGzGuzbxsq.setSHENHZT("01");
        lysbGzGuzbxsqMapper.addSq(lysbGzGuzbxsq);
        return "添加成功";
    }

    /**
     * 更改状态
     * @param lysbGzGuzbxsq
     * @return
     */
    @Override
    public Object updateState(LysbGzGuzbxsq lysbGzGuzbxsq) {
        lysbGzGuzbxsqMapper.updateState(lysbGzGuzbxsq);
        return "操作成功";
    }


}
