package com.canteen.service.impl;

import com.canteen.mapper.DocumentnumberMapper;
import com.canteen.service.DocumentnumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DocumentnumberServiceImpl implements DocumentnumberService {
    @Autowired
    private DocumentnumberMapper documentnumberMapper;

    @Override
    public String getOrderNo(String type) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String createDate = df.format(new Date());
        List<Map> orderNo = documentnumberMapper.getOrderNo(type);
        String no = "";
        String cd = "";
        String noString = "" ;
        if (orderNo!=null && orderNo.size()>0) {
            no = orderNo.get(0).get("NO") == null ? "" : String.valueOf(orderNo.get(0).get("NO"));
            cd = orderNo.get(0).get("CD") == null ? "" : String.valueOf(orderNo.get(0).get("CD"));
            if (null == no || "".equals(no) || !createDate.equals(cd)) {
                noString = createDate.replace("-", "") + "0001";
            } else {
                noString = (Long.parseLong(no) + 1) + "";
            }
            documentnumberMapper.updateOrderNo(noString,createDate,type);
        }else {
            noString = createDate.replace("-", "")+"0001";
            documentnumberMapper.insertOrderNo(noString,createDate,type);
        }
        return type+"-"+noString;
    }
}
