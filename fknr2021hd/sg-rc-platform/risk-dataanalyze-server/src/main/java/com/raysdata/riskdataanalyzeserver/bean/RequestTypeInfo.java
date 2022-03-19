package com.raysdata.riskdataanalyzeserver.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("用户实体类 RequestTypeInfo")
public class RequestTypeInfo {
  @ApiModelProperty(value ="请求信息的编码")
  private String requestTypeId;
  @ApiModelProperty(value ="请求信息描述")
  private String requestDesc;
  @ApiModelProperty(value ="是否记录日志 0：需要 1：不需要")
  private Integer isLog;
  @ApiModelProperty(value ="创建时间")
  private String createTime;
  @ApiModelProperty(value ="修改时间")
  private String updateTime;
}
