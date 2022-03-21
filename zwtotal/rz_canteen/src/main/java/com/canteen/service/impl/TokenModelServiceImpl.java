package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StfoodManagement;
import com.canteen.entity.StnutritionalComponents;
import com.canteen.entity.TokenModel;
import com.canteen.mapper.StfoodManagementMapper;
import com.canteen.service.StfoodManagementService;
import com.canteen.service.TokenModelService;
import com.canteen.utils.SigGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenModelServiceImpl implements TokenModelService {

    private String url = "http://183.129.209.98:8221/kuaishou/prod";    //请求地址
    private String key = "8779f024eafd4a6dbb4fbc76202780a2";

    //营业流水
    @Override
    public String openWater(TokenModel tokenModel) {
        StringBuilder param = new StringBuilder();
        param.append("v="+tokenModel.getV()+"&d="+tokenModel.getD()+"&format="+tokenModel.getFormat()+"&shop="+tokenModel.getShop()
                +"&term="+tokenModel.getTerm()+"&oper="+tokenModel.getOper()+"&t="+tokenModel.getT()+"&pi="+tokenModel.getPi()
                +"&ps="+tokenModel.getPs()+"&sd="+tokenModel.getSd()+"&ed="+tokenModel.getEd());
        String[] split = param.toString().split("&");
        String sig = SigGenerate.getSig(key, split);
        tokenModel.setSig(sig);
        //参数设置
        org.apache.commons.httpclient.NameValuePair[] data = {
                    new org.apache.commons.httpclient.NameValuePair("v",tokenModel.getV().toString()),
                    new org.apache.commons.httpclient.NameValuePair("d",tokenModel.getD()),
                    new org.apache.commons.httpclient.NameValuePair("format",tokenModel.getFormat()),
                    new org.apache.commons.httpclient.NameValuePair("shop",tokenModel.getShop().toString()),
                    new org.apache.commons.httpclient.NameValuePair("term",tokenModel.getTerm().toString()),
                    new org.apache.commons.httpclient.NameValuePair("oper",tokenModel.getOper()),
                    new org.apache.commons.httpclient.NameValuePair("t",tokenModel.getId().toString()),
                    new org.apache.commons.httpclient.NameValuePair("pi",tokenModel.getPi().toString()),
                    new org.apache.commons.httpclient.NameValuePair("ps",tokenModel.getPs().toString()),
                    new org.apache.commons.httpclient.NameValuePair("sd",tokenModel.getSd()),
                    new org.apache.commons.httpclient.NameValuePair("ed",tokenModel.getEd()),
                    new org.apache.commons.httpclient.NameValuePair("sig",tokenModel.getSig())
            };
        String token = SigGenerate.getToken(url, tokenModel, data);
        return token;
    }

    //营业流水明细
    @Override
    public String openWaterInfo(TokenModel tokenModel) {
        StringBuilder param = new StringBuilder();
        param.append("v="+tokenModel.getV()+"&d="+tokenModel.getD()+"&format="+tokenModel.getFormat()+"&shop="+tokenModel.getShop()
                +"&term="+tokenModel.getTerm()+"&oper="+tokenModel.getOper()+"&t="+tokenModel.getT()+"&seq="+tokenModel.getSeq());
        String[] split = param.toString().split("&");
        String sig = SigGenerate.getSig(key, split);
        tokenModel.setSig(sig);
        //参数设置
        org.apache.commons.httpclient.NameValuePair[] data = {
                new org.apache.commons.httpclient.NameValuePair("v",tokenModel.getV().toString()),
                new org.apache.commons.httpclient.NameValuePair("d",tokenModel.getD()),
                new org.apache.commons.httpclient.NameValuePair("format",tokenModel.getFormat()),
                new org.apache.commons.httpclient.NameValuePair("shop",tokenModel.getShop().toString()),
                new org.apache.commons.httpclient.NameValuePair("term",tokenModel.getTerm().toString()),
                new org.apache.commons.httpclient.NameValuePair("oper",tokenModel.getOper()),
                new org.apache.commons.httpclient.NameValuePair("t",tokenModel.getId().toString()),
                new org.apache.commons.httpclient.NameValuePair("seq",tokenModel.getSeq().toString()),
                new org.apache.commons.httpclient.NameValuePair("sig",tokenModel.getSig())
        };
        String token = SigGenerate.getToken(url, tokenModel, data);
        return token;
    }
}
