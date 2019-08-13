package com.hichat.web.exception;

import com.hichat.common.util.ReturnResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnResult jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        String api=(String) req.getAttribute("api");
        logger.error(api+"报错,信息:"+e.getMessage(),e);
        return ReturnResult.build(400,"系统异常");
    }

    /**
     * 权限不足报错拦截
     *
     * @return
     * @throws Exception
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ReturnResult unauthorizedExceptionHandler() throws Exception {
        return ReturnResult.build(400,"权限不足");
    }

}
