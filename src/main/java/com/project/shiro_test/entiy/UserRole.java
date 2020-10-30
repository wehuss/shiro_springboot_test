package com.project.shiro_test.entiy;

import java.io.Serializable;
import lombok.Data;

/**
 * user_role
 * @author 
 */
@Data
public class UserRole implements Serializable {
    /**
     * 表主键id
     */
    private Integer id;

    /**
     * 帐号表的主键id
     */
    private String userid;

    /**
     * 角色表的主键id
     */
    private String roleid;

    private static final long serialVersionUID = 1L;
}