package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StEquipmentmanagement {
    private Integer id;

    //设备名称
    private String equipmentName;

    //设备编码
    private String equipmentCode;

    //运维餐厅
    private String restaurant;

    //采购批次
    private Integer purchaseBatch;

    //设备总数
    private Integer totalDevices;

    //在用数量
    private Integer useQuantity;

    //在检数量
    private Integer examineQuantity;

    //报废数量
    private Integer scrapQuantity;

    //备注
    private String remarks;

    private String delFlag;
}
