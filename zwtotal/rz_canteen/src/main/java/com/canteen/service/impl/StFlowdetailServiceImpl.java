package com.canteen.service.impl;

import com.canteen.entity.StFlowdetail;
import com.canteen.mapper.StFlowdetailMapper;
import com.canteen.service.IStFlowdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: lilong
 * @Date: 2020/11/12 11:28
 * @Description:
 */
@Service
public class StFlowdetailServiceImpl implements IStFlowdetailService {
    @Autowired
    private StFlowdetailMapper mapper;

    @Override
    public void batchSave(List<StFlowdetail> appTFlowdetailList) {
        int len = appTFlowdetailList.size();
        for(int i =0 ;i<len;i++){
            mapper.insert(appTFlowdetailList.get(i));
        }
    }
}
