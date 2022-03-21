package com.canteen.mapper;


import com.canteen.entity.AppTFlowdetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdh
 * @since 2020-10-13
 */
@Repository
public interface AppTFlowdetailMapper{
    /**
     * 批量插入
     */
    void saveBatch(@Param("appTFlowdetailList") List<AppTFlowdetail> appTFlowdetailList);

}
