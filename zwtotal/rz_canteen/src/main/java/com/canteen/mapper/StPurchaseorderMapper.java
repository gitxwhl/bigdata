package com.canteen.mapper;

import com.canteen.entity.StPurchaseorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StPurchaseorderMapper {

    int getPurchaseIndentCnt(@Param("name") String name,
                             @Param("no") String no,
                             @Param("timestamp") String timestamp,
                             @Param("type") String type);

    List getPurchaseIndentList(@Param("startIndex") int startIndex,
                               @Param("pageSize") int pageSize,
                               @Param("name") String name,
                               @Param("no") String no,
                               @Param("timestamp") String timestamp,
                               @Param("type") String type);
}
