package com.canteen.mapper;


import com.canteen.entity.StFlowdetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StFlowdetailMapper   {
    void saveBatch(@Param("list") List<StFlowdetail> appTFlowdetailList);

    void insert(StFlowdetail stFlowdetail);
}
