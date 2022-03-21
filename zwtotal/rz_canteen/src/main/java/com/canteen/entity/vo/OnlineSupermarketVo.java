package com.canteen.entity.vo;

import com.canteen.entity.StOrderDishes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName OnlineSupermarketVo
 * @Date 2020/9/9
 * @Auther tiandejiang
 * @Description:
 * @Vserion 1.0.0
 */
@Data
public class OnlineSupermarketVo {

    @ApiModelProperty(value="所选物品列表")
    private List<StOrderDishes> list;
    @ApiModelProperty(value="订单必要的一些字段")
    private  OrderDetailVo   order;


}
