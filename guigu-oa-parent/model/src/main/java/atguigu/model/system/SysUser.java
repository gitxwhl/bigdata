package atguigu.model.system;

import atguigu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zpp
 * @since 2024-06-02
 */
@Data
@ApiModel(description = "用户")
@TableName("sys_user")
public class SysUser {

	private static final long serialVersionUID = 1L;

	/**
	 * 会员id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;


	@ApiModelProperty(value = "用户名")
	private String username;


	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	private String phone;

	/**
	 * 头像地址
	 */
	@ApiModelProperty(value = "头像地址")
	private String headUrl;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	private Long deptId;

	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "岗位id")
	private Long postId;

	/**
	 * 微信openId
	 */
	@ApiModelProperty(value = "微信openId")
	private String openId;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 状态（1：正常 0：停用）
	 */
	@ApiModelProperty(value = "状态（1：正常 0：停用）")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标记（0:不可用 1:可用）
	 */
	@ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
	private Integer isDeleted;


}

