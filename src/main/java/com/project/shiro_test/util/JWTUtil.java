package com.project.shiro_test.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {
    // 一周后过期
    private static final long expireTime = 3600 * 24 * 7 * 1000;

    public static boolean verify(String token, String userId, String userPwd) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(userPwd);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            log.info("获得verifier" + verifier);
            DecodedJWT jwt = verifier.verify(token);
            log.info("验证jwt完成" + jwt.getToken());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String sign(String userId, String userPwd) {
        try {
            Date date = new Date(System.currentTimeMillis() + expireTime);
            Algorithm algorithm = Algorithm.HMAC256(userPwd);
            log.info("获得加密后的userPwd" + algorithm);
            return JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsatisfiedLinkError e) {
            return null;
        }
    }

    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
