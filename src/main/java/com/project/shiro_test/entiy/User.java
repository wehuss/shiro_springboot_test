package com.project.shiro_test.entiy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends SysUser {
    private String userRole;

//    private List<String> userPermissions;
}
