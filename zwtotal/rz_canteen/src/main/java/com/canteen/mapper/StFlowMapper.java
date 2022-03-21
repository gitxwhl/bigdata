package com.canteen.mapper;

import com.canteen.entity.StFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface StFlowMapper   {
    void updateStatus(Map<String,String> map );

    void insert(StFlow entity);
}
