package com.raysdata.riskdataanalyzeserver.bean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("日志实体类查询类型 RequestTypeToOption")
public class RequestTypeToOption {
  @ApiModelProperty(value ="请求信息的编码")
  private String requestTypeId;
  @ApiModelProperty(value ="操作类型：1新增、2查询、3修改、4删除、5上传、6下载、7登录、8登出、9统计、10调阅、11其它")
  private String optType;
  @ApiModelProperty(value ="创建时间")
  private String createTime;
  @ApiModelProperty(value ="更新时间")
  private String updateTime;
}
