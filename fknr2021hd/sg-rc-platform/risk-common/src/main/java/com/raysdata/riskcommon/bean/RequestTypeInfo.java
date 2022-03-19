package com.raysdata.riskcommon.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("日志实体类 RequestTypeInfo")
public class RequestTypeInfo {
  @ApiModelProperty(value ="请求信息的编码")
  private String requestTypeId;
  @ApiModelProperty(value ="请求信息描述")
  private String requestDesc;
  @ApiModelProperty(value ="是否记录日志 0：需要 1：不需要")
  private double isLog;
  @ApiModelProperty(value ="创建时间")
  private java.sql.Timestamp createTime;
  @ApiModelProperty(value ="更新时间")
  private java.sql.Timestamp updateTime;
}
