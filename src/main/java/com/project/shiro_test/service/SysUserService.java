package com.project.shiro_test.service;

import com.project.shiro_test.dao.SysUserDao;
import com.project.shiro_test.entiy.SysUser;
import com.project.shiro_test.entiy.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    public User getUserRole(String userId) {
        return sysUserDao.getUserRole(userId);
    }

    public int register(SysUser user) {
        return sysUserDao.insert(user);
    }

    public SysUser getUserByUserId(String userId) {
        return sysUserDao.getUserByUserId(userId);
    }
}
