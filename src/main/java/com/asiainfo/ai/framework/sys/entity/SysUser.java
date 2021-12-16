package com.asiainfo.ai.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.asiainfo.ai.framework.web.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 用户信息
 *
 * @author zhangls
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户信息")
public class SysUser extends BaseBean {

    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("研发云用户名")
    private String userId;

    @ApiModelProperty("登陆密码")
    private String passWord;

    @ApiModelProperty("盐值")
    private String passSalt;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证")
    private String cardId;

    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("手机号码")
    private String phoneNum;

    @ApiModelProperty("所属项目ID")
    private Integer proId;

    @ApiModelProperty("用户类型 0:未知; 1:项目经理; 2:需求分析; 3:方案设计; 4:开发; 5:测试; 6:运维")
    private Boolean userType;

    @ApiModelProperty("状态 1:在职状态; 2:锁定状态; 3:离职状态")
    private Boolean state;

}
