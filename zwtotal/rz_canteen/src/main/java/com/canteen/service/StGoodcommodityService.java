package com.canteen.service;

import com.canteen.entity.StGoodcommodity;
import com.canteen.entity.vo.OnlineSupermarketVo;
import com.canteen.utils.PageBean;
import com.canteen.utils.Results;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 网上超市
 */
public interface StGoodcommodityService {

    /**
     * 超市类别查询
     * @param
     * @return
     */
    PageBean<StGoodcommodity> getStGoodcommodityPage(Map<String,Object> map);

    /**
     * 售卖商品列表查询
     * @param
     * @param
     * @return
     */
    PageBean<StGoodcommodity> findGoodListPage(Map<String,Object> map);



    /**
     *
     * @param userId 登录用户id
     * @param order  订单详情
     * @return
     */
    Results<Boolean> insertOnlineSupermarketOrder(String userId, OnlineSupermarketVo order);

    /**
     *网上超市一级类别查询
     * @param
     * @param
     * @return
     */
    List<Map> findStGoodcommodity();

    /**
     *网上超市二级类别查询
     * @param
     * @param
     * @return
     */
    Object findStGoodcommodityTwo(String param);
    /**
     * 新增商品
     */
    Results<Boolean> saveGood(StGoodcommodity stGoodcommodity);
    /**
     * 商品上下架
     */
    Results<Boolean> updateEnable(StGoodcommodity stGoodcommodity);

    void importTaskExcel(MultipartFile file) throws Exception;

    /**
     * 入库并上架:修改
     */
    Results<Boolean> updateWarehousing(StGoodcommodity stGoodcommodity);

}
