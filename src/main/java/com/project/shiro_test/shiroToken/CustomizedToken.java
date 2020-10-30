package com.project.shiro_test.shiroToken;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;


@EqualsAndHashCode(callSuper = true)
@Data
public class CustomizedToken extends UsernamePasswordToken {
    public String loginType;

    public String MD5Password;

    public CustomizedToken(final String username, final String password, final String loginType, final String MD5Password) {
        super(username, password);
        this.loginType = loginType;
        this.MD5Password = MD5Password;
    }
}
