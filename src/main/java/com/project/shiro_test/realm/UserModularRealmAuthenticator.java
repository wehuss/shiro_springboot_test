package com.project.shiro_test.realm;

import com.project.shiro_test.shiroToken.CustomizedToken;
import com.project.shiro_test.shiroToken.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    //调用realm之前会执行此方法来决定调用的realm
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
//        log.info("UserModularRealmAuthenticator:method doAuthenticate() 执行 ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();

        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 盛放登录类型对应的所有Realm集合
        Collection<Realm> typeRealms = new ArrayList<>();

        // 强制转换回自定义的Token
        try {
//            log.info("进入了UserModularRealmAuthenticator类...得到的authenticationToken是:{}", authenticationToken);
            JWTToken jwtToken = (JWTToken) authenticationToken;
//            log.info("尝试获取jwtToken:{}", jwtToken.getCredentials());
//            log.info("开始循环");
            for (Realm realm : realms) {
//                log.info("获得realm:{}", realm.getName());
                if (realm.getName().contains("JWT")) {
                    typeRealms.add(realm);
                }
            }
//            log.info("typeRealms:{}", typeRealms);
            return doSingleRealmAuthentication(typeRealms.iterator().next(), jwtToken);
        } catch (ClassCastException e) {
            typeRealms.clear();
            // 这个类型转换的警告不需要再关注 如果token错误 后面将会抛出异常信息
            CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
            // 登录类型
            String loginType = customizedToken.getLoginType();
            for (Realm realm : realms) {
//                log.info("正在遍历的realm是:{}", realm.getName());
                if (realm.getName().contains(loginType)) {
//                    log.info("当前realm:{}被注入:", realm.getName());
                    typeRealms.add(realm);
                }
            }
            // 判断是单Realm还是多Realm
//            if (typeRealms.size() == 1) {
//                log.info("一个realm");
//                return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
//            } else {
//                log.info("多个realm");
//                return doMultiRealmAuthentication(typeRealms, customizedToken);
//            }
            return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
        }

    }
}
