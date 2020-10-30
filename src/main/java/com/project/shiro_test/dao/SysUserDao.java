package com.project.shiro_test.dao;

import com.project.shiro_test.entiy.SysUser;
import com.project.shiro_test.entiy.User;

public interface SysUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    User getUserRole(String userId);

    SysUser getUserByUserId(String userId);
}