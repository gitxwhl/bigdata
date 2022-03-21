package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StPurchaseorder;
import com.canteen.mapper.StPurchaseorderMapper;
import com.canteen.service.StPurchaseorderService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StPurchaseorderServiceImpl implements StPurchaseorderService {

    @Autowired
    private StPurchaseorderMapper StPurchaseorderMapper;

    /**
     * 采购订单列表
     *
     * @return
     * @paramJson paramJson
     */
    @Override
    public PageBean getPurchaseIndentList(String paramJson) {

        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String name = param.getString("name");
        String no = param.getString("no");
        String timestamp = param.getString("timestamp");
        String type = param.getString("type");

        int totalSize = StPurchaseorderMapper.getPurchaseIndentCnt(name, no, timestamp, type);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list = StPurchaseorderMapper.getPurchaseIndentList(startIndex, pageSize, name, no, timestamp, type);
            pageBean.setList(list);
        }
        return pageBean;
    }
}
