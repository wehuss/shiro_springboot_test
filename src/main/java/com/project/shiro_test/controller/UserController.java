package com.project.shiro_test.controller;

import com.project.shiro_test.entiy.SysUser;
import com.project.shiro_test.entiy.User;
import com.project.shiro_test.entiy.UserRole;
import com.project.shiro_test.service.SysUserService;
import com.project.shiro_test.service.UserRoleService;
import com.project.shiro_test.shiroToken.CustomizedToken;
import com.project.shiro_test.util.JWTUtil;
import com.project.shiro_test.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private UserRoleService userRoleService;

    @RequestMapping("/login")
    public String login(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "userPwd") String userPwd) {
        User user = sysUserService.getUserRole(userId);
        if (user == null) {
            return Util.getErrorInfo("账号不存在");
        }
        // 创建自定义的usernamePasswordToken
        CustomizedToken customizedToken = new CustomizedToken(userId, userPwd, "PasswordLogin", user.getPassword());
        try {
            // 调用passwordLoginRealm进行登录验证
            SecurityUtils.getSubject().login(customizedToken);
        } catch (IncorrectCredentialsException incorrectCredentialsException) {
            return Util.getErrorInfo("密码错误");
        }
        return Util.getSuccessInfo(JWTUtil.sign(userId, user.getPassword()));
    }

    @RequestMapping("/register")
    public String register(SysUser user) {
        if (user.getUserid() == null || user.getUsername() == null || user.getPassword() == null) {
            return Util.getErrorInfo("传入信息不完整");
        }

        if (sysUserService.getUserByUserId(user.getUserid()) != null) {
            return Util.getErrorInfo("此用户Id已被注册，请尝试使用其他用户Id");
        }
        user.setPassword(Util.encryptPassword(user.getPassword(), user.getUserid()));
        try {
            if (sysUserService.register(user) == 0) {
                throw new MyBatisSystemException(new Throwable());
            }
        } catch (MyBatisSystemException e) {
            return Util.getErrorInfo("注册失败");
        }

        return Util.getSuccessInfo("注册成功");
    }

    @RequestMapping("/updateUserRole")
    @RequiresRoles("superAdmin")
    public String updateUserRole(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "roleId") String roleId) {
        if ("100".equals(roleId) || "200".equals(roleId)) {
            try {
                if (userRoleService.updateUserRole(userId, roleId) == 0) {
                    throw new MyBatisSystemException(new Throwable());
                }
            } catch (MyBatisSystemException e) {
                return Util.getErrorInfo("更新用户角色失败");
            }

            return Util.getSuccessInfo("修改成功");
        }
        return Util.getErrorInfo("传入信息有误");
    }


    @RequestMapping("/admin")
    //AND为与，OR为或
    @RequiresRoles(value = {"admin", "superAdmin"}, logical = Logical.OR)
    public String test() {
        return Util.getSuccessInfo("你的权限为admin或superAdmin");
    }
}
