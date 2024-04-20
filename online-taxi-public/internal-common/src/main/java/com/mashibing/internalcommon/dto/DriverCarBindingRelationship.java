package com.mashibing.internalcommon.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class DriverCarBindingRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 司机id
     */
    private Long driverId;

    /**
     * 车辆id
     */
    private Long carId;

    /**
     * 绑定状态(1: 绑定   2: 解绑)
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;
}
