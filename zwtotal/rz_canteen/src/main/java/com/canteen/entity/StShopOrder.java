package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Auther: lilong
 * @Date: 2020/11/26 16:52
 * @Description:
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StShopOrder implements Serializable {

    private Integer pageSize;

    private Integer pageNum;

    private String  shopId;

    private String  goodsName;

    private String orderNo;

    private String userName;

    private String createDate;

    private String status;

    private String shopName;

    private String  phone;

    private String prices;




}