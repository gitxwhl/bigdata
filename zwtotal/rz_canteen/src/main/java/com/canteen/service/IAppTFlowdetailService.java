package com.canteen.service;


import com.canteen.entity.AppTFlowdetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
public interface IAppTFlowdetailService  {
    /**
     * 插入审批记录
     */
   void saveBatch(List<AppTFlowdetail> appTFlowdetailList);

}
