package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.mapper.StquantitypriceMapper;
import com.canteen.service.StquantitypriceService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StquantitypriceServiceImpl implements StquantitypriceService {

    @Autowired
    private StquantitypriceMapper stquantitypriceMapper;

    @Autowired
    private StOperationrestaurantServiceImpl service;

    /**
     * 商品管理列表
     * @paramJson paramJson
     * @return
     */
    @Override
    public PageBean getCommodityList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:"+param);
        String dishname = param.getString("dishname");
        String str = param.getString("restaurant");
        String restaurant = service.getIds(str);
        int totalSize =  stquantitypriceMapper.getCommodityCnt(dishname,restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            int startIndex = pageBean.getStartIndex();
            List list = stquantitypriceMapper.getCommodityList(startIndex, pageSize,dishname,restaurant);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 修改列表价格数据
     * @paramJson paramJson
     * @return
     */
    @Override
    public String amendPrice(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        Integer id = Integer.parseInt(rowData.getString("id"));
        String timepice = rowData.getString("timepice");
        String Outtimepice = rowData.getString("Outtimepice");
        String employeecardprice = rowData.getString("employeecardprice");
        String temporarycardPrice = rowData.getString("temporarycardPrice");
        int i = 0 ;
            i =  stquantitypriceMapper.amendPrice(id,timepice,Outtimepice,employeecardprice,temporarycardPrice);
        if( i > 0 ){
            return "修改价格成功";
        }
        return "修改价格失败";
    }
}
