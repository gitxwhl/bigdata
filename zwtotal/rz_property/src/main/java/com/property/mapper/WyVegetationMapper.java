package com.property.mapper;

import com.property.entity.WyVegetation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WyVegetationMapper {

    @Insert("INSERT INTO wy_vegetation  VALUES (#{id},#{vegetationname},#{number},#{company},#{introduction},#{spacemanagementId})")
    int gwtNewlyVegetation(WyVegetation wyVegetation);
}
