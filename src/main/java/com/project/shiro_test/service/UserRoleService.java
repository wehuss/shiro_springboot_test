package com.project.shiro_test.service;

import com.project.shiro_test.dao.UserRoleDao;
import com.project.shiro_test.entiy.User;
import com.project.shiro_test.entiy.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleService {
    @Resource
    UserRoleDao userRoleDao;

    public int updateUserRole(String userId, String roleId) {
        return userRoleDao.updateUserRole(userId, roleId);
    }
}
