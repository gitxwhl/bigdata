package com.raysdata.riskdataanalyzeserver.bean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel("日志实体类 OpLogInfo")
public class OpLogInfo {
  @ApiModelProperty(value ="操作日志id")
  private String id;
  @ApiModelProperty(value ="用户id")
  private String userId;
  @ApiModelProperty(value ="操作者是使用那个业务系统登录的")
  private String businessCode;
  @ApiModelProperty(value ="时间")
  private String opTime;
  @ApiModelProperty(value ="用户ip")
  private String userIp;
  @ApiModelProperty(value ="请求编码")
  private String requestTypeId;
  @ApiModelProperty(value ="返回值")
  private String opResult;
  @ApiModelProperty(value ="日志详情")
  private String context;
  @ApiModelProperty(value ="开始时间")
  private String transactionBeginDate;
  @ApiModelProperty(value ="结束时间")
  private String transactionEndDate;
  @ApiModelProperty(value ="事件类型(0:业务级;1:系统级)")
  private String actionType;
  @ApiModelProperty(value ="事件级别(0:成功;1:严重;2:告警;3:一般)")
  private String actionLevel;
  @ApiModelProperty(value ="操作类型：1新增、2查询、3修改、4删除、5上传、6下载、7登录、8登出、9统计、10调阅、11其它")
  private String optType;
  @ApiModelProperty(value ="创建时间")
  private String createTime;
  @ApiModelProperty(value ="更新时间")
  private String updateTime;
  @ApiModelProperty(value ="日志类型（0：运行；1：错误；2：告警；3：调试 ）")
  private String logType;
  @ApiModelProperty(value ="返回值中文")
  private String opResultZw;

}
