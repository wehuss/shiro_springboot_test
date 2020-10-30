package com.project.shiro_test.realm;

import com.project.shiro_test.service.SysUserService;
import com.project.shiro_test.shiroToken.CustomizedToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
public class PasswordLoginRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        // 使realm支持自定义token
        return token instanceof CustomizedToken;
    }

    @Resource
    private SysUserService sysUserService;

    // 检测并设置权限 只有在加入@RequiresRoles("对应角色")或@RequiresPermissions才会被调用
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    // 检测用户身份,判断是否存在，token内信息是否正确
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
        //获取加密后的密码
        Object md5Password = customizedToken.getMD5Password();
        String userName = customizedToken.getUsername();
        // SimpleAuthenticationInfo会将authenticationToken中的的明文password加密后与md5Password进行对比
        return new SimpleAuthenticationInfo(userName, md5Password, ByteSource.Util.bytes(userName), getName());
    }
}
