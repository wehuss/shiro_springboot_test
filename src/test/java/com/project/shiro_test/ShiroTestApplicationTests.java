package com.project.shiro_test;

import com.auth0.jwt.JWT;
import com.project.shiro_test.entiy.SysUser;
import com.project.shiro_test.entiy.User;
import com.project.shiro_test.service.SysUserService;
import com.project.shiro_test.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class ShiroTestApplicationTests {
    @Resource
    SysUserService sysUserService;

    @Test
    void contextLoads() {

//        String token = JWTUtil.sign("userAccount", "userPwd");
//        log.info("jwt生成成功" + token);
//        boolean isJwt = JWTUtil.verify(token, "userAccount", "userPwd");
//        assert token != null;
//        log.info("尝试解码" + JWT.decode(token).getClaim("userAccount"));

        SysUser user = sysUserService.getUserByUserId("4396");
        log.info("user" + user + user.getUserid() + user.getPassword() + user.getUsername());
    }

}
