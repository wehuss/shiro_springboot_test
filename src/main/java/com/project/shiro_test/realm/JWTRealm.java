package com.project.shiro_test.realm;

import com.project.shiro_test.entiy.SysUser;
import com.project.shiro_test.entiy.User;
import com.project.shiro_test.service.SysUserService;
import com.project.shiro_test.shiroToken.JWTToken;
import com.project.shiro_test.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class JWTRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        // 使realm支持自定义token
        return token instanceof JWTToken;
    }

    @Resource
    private SysUserService sysUserService;

    @Override
    // 检测并设置权限 只有在加入@RequiresRoles("对应角色")或@RequiresPermissions才会被调用
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入doGetAuthorizationInfo方法:{}", principalCollection.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String token = principalCollection.toString();
        String userId = JWTUtil.getUserId(token);
        User user = sysUserService.getUserRole(userId);
        log.info("获得用户信息:{}", user);
        //添加权限
        simpleAuthorizationInfo.addRole(user.getUserRole());
        log.info("获得用户权限" + simpleAuthorizationInfo.getRoles());
        return simpleAuthorizationInfo;
    }

    @Override
    // 检测用户身份,判断是否存在，token内信息是否正确
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入doGetAuthenticationInfo方法");
        String token = (String) authenticationToken.getCredentials();
        log.info("doGetAuthenticationInfo获取token" + token);
        String userId = JWTUtil.getUserId(token);
        SysUser user = sysUserService.getUserByUserId(userId);
        log.info("userAccount：  " + userId);
        if (!JWTUtil.verify(token, userId, user.getPassword())) {
            throw new AuthenticationException("验签失败");
        }
        log.info("通过验证" + userId);
        log.info("获得shiroRealmName" + getName());
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
