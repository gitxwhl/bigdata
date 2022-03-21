package com.canteen.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderDetailVo
 * @Date 2020/9/9
 * @Auther tiandejiang
 * @Description:
 * @Vserion 1.0.0
 */
@Data
public class OrderDetailVo {

    @ApiModelProperty(value="配送地址")
    private  String address;
    @ApiModelProperty(value="用户电话")
    private  String telephone;
    @ApiModelProperty(value="用户姓名，非登录用户")
    private  String userName;
    @ApiModelProperty(value="下单餐厅(餐厅外键)")
    private  String restaurant;
    @ApiModelProperty(value="预定日期")
    private  String scheduled;
    @ApiModelProperty(value="费用合计")
    private  String costtotal;

}
