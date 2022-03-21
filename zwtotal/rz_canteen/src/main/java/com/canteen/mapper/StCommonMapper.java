package com.canteen.mapper;

import com.canteen.entity.StOperationrestaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface StCommonMapper {
    List<Map<String,String>> getCompanyOrDeptList(@Param("type") String type, @Param("parentId") String id);

    List<Map<String,String>> getRestaurantList( @Param("id")  String id);


    List<Map<String,String>> getPersonList( @Param("parentId")  String id);

    List<StOperationrestaurant> getStOperationList(@Param("type")String type);

    List<StOperationrestaurant> getGoodType();

    List<Map<String,String>> findCompanyId(@Param("account") String account);

    List<Map<String,String>> findDeparmentId(@Param("account") String account);

    List<Map<String,String>> findCompany();

    List<Map<String,String>> finddeparmentIds(String bid);

    List<Map<String,String>> findShiCompany();


}
