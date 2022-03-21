package com.canteen.service;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StBlacklist;
import com.canteen.entity.StPenaltyList;
import com.canteen.entity.StSupplier;
import com.canteen.entity.StSupplierScore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

public interface StSupplierService {

    //供应商列表
    Object getSupplier(String paramJson) ;

    //导出供应商
    Object exportSupplier(HttpServletResponse response,Map<String,Object> map) throws UnsupportedEncodingException;

    //新增准入供应商
    Object addSupplier(String paramJson);

    //采购食材详情
    Object getIngredient(Map<String,Object> map);

    //添加食材
    Object addIngredient(Map<String,Object> map);

    //编辑供应商
    //Object updateSupplier(String paramJson);

    //供应商遴选列表
    //Object getChooseList(String paramJson);

    //供应商遴选
    //Object choose(StSupplier stSupplier);

    //供应商评价
    //Object getEvaluate(String paramJson);

    //添加评分
    //Object addScore(String paramJson);

    //惩罚详情
    //Object getPunishment(String paramJson);

    //添加惩罚
    //Object addPunishment(String paramJson);

    //黑名单详情
    //Object getBlacklist(String paramJson);

    //添加黑名单
    //Object addBlacklist(String paramJson);

}
