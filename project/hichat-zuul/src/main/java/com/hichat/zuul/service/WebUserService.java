package com.hichat.zuul.service;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hichat-provider",fallback = HystrixWebUserHandler.class)
public interface WebUserService {


    @RequestMapping(value="/web-user/login",method = RequestMethod.POST)
    ReturnResult<WebUser> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password);

}
