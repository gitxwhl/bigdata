package com.mashibing.internalcommon.response;

import lombok.Data;

@Data
public class DriverUserExistsResposonse {
    //手机号
   private String  driverPhone;
   //是否存在 0不存在 1存在
   private Integer isExists;
}
