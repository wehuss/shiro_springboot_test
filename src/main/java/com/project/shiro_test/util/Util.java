package com.project.shiro_test.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;

public class Util {
    public static String getErrorInfo(String info) {
        JSONObject errInfo = new JSONObject();
        errInfo.put("code", "500");
        errInfo.put("data", info);
        return errInfo.toJSONString();
    }

    public static String getSuccessInfo(String info) {
        JSONObject successInfo = new JSONObject();
        successInfo.put("code", "200");
        successInfo.put("data", info);
        return successInfo.toJSONString();
    }

    public static String encryptPassword(String password, String userId) { //userId作为盐值
        return String.valueOf(new SimpleHash("MD5", password, userId, 1024));
    }
}
