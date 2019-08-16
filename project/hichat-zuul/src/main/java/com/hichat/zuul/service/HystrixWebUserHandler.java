package com.hichat.zuul.service;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;


@Component
public class HystrixWebUserHandler implements WebUserService{


    @Override
    public ReturnResult<WebUser> login(String account, String password) {
        return ReturnResult.build(400,"系统异常");
    }

}
