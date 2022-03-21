package com.canteen.service.impl;


import com.canteen.entity.AppTFlowdetail;
import com.canteen.mapper.AppTFlowdetailMapper;
import com.canteen.service.IAppTFlowdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Service
public class AppTFlowdetailServiceImpl implements IAppTFlowdetailService {

    @Autowired
    private AppTFlowdetailMapper appTFlowdetailMapper;

    @Override
    public void saveBatch(List<AppTFlowdetail> appTFlowdetailList) {
        appTFlowdetailMapper.saveBatch(appTFlowdetailList);
    }
}
