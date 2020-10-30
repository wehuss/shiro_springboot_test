package com.project.shiro_test.entiy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends SysUser {
    private String userRole;

    public List<String> userPermissions;
}
