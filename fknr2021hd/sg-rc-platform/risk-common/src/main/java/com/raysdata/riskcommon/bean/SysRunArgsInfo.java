package com.raysdata.riskcommon.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("日志实体类 SysRunArgsInfo")
public class SysRunArgsInfo {
  @ApiModelProperty(value ="主键")
  private String id;
  @ApiModelProperty(value ="参数编码")
  private String paraCode;
  @ApiModelProperty(value ="参数说明")
  private String paraDesc;
  @ApiModelProperty(value ="当前值")
  private String currentValue;
  @ApiModelProperty(value ="建议最大值")
  private String maxValue;
  @ApiModelProperty(value ="最小值")
  private String minValue;
  @ApiModelProperty(value ="参数类型ID")
  private String typeId;
  @ApiModelProperty(value ="创建时间")
  private String createTime;
  @ApiModelProperty(value ="更新时间")
  private String updateTime;
}
