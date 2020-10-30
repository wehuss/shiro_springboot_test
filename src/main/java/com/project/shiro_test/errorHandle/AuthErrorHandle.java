package com.project.shiro_test.errorHandle;

import com.project.shiro_test.util.Util;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthErrorHandle {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String defaultErrorHandle() {
        return Util.getErrorInfo("无权访问此接口");
    }
}
