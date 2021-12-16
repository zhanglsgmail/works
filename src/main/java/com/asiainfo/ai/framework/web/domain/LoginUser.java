package com.asiainfo.ai.framework.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 登陆用户信息
 *
 * @author zhangls
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色列表 # Set ->> roleKey
     */
    private Set<String> roleSet;

    /**
     * 权限列表 # Set ->> permKey
     */
    private Set<String> permissionsSet;

    /**
     * 用户信息
     */
    private UserInfo user;

}
