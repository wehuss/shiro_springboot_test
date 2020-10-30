package com.project.shiro_test.entiy;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements Serializable {
    /**
     * user表的id字段
     */
    private Integer id;

    /**
     * 用户id 作为表主键 用于关联
     */
    private String userid;

    /**
     * 用户登录帐号
     */
    private String username;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 备注，预留字段
     */
    private String userremarks;

    private static final long serialVersionUID = 1L;
}