package com.hichat.web.service;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


@Component
public class HystrixWebUserHandler implements WebUserService{


    @Override
    public ReturnResult<WebUser> login(String account, String password) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult queryList(@RequestBody QueryData<WebUserExample> queryData){
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult saveOrUpdate(WebUser webUser) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult delete(Long id) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult assignRole(WebUser webUser) {
        return ReturnResult.build(400,"系统异常");
    }


}
