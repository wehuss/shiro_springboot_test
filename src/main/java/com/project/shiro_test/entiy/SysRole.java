package com.project.shiro_test.entiy;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_role
 * @author 
 */
@Data
public class SysRole implements Serializable {
    /**
     *  角色id 作为表主键 用于关联
     */
    private String roleid;

    /**
     * 角色名
     */
    private String rolename;

    /**
     * 备注，预留字段
     */
    private String roleremarks;

    private static final long serialVersionUID = 1L;
}