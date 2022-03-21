package com.canteen.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel(value="异地就餐列表参数",description="异地就餐列表对象")
public class RemoteDiningListVO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "页码")
    private Integer pageIndex;
    @ApiModelProperty(value = "页数")
    private Integer pageSize;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "流程节点")
    private String flowstatus;
    @ApiModelProperty(value = "流程单号")
    private String flowId;

}
