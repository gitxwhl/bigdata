package com.canteen.service;

import com.canteen.entity.AppTFlow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
public interface IAppTFlowService {
    /**
     * 添加流程节点
     * @param appTFlow
     */
    void saveAppTFlow(AppTFlow appTFlow);
}
