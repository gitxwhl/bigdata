package com.canteen.service.impl;


import com.canteen.entity.AppTFlow;
import com.canteen.mapper.AppTFlowMapper;
import com.canteen.service.IAppTFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Transactional(rollbackFor = { Exception.class })
@Service
public class AppTFlowServiceImpl  implements IAppTFlowService {
    AppTFlowMapper appTFlowMapper;

    /**
     * 添加流程节点
     * @param appTFlow
     */
    @Override
    public void saveAppTFlow(AppTFlow appTFlow) {
        appTFlowMapper.saveAppTFlow(appTFlow);
    }



}
