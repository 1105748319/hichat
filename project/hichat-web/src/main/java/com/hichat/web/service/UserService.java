package com.hichat.web.service;

import com.hichat.common.pojo.UserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "hichat-provider",fallback = HystrixUserHandler.class)
public interface UserService {


    @RequestMapping("/user/getUserList")
    ReturnResult getUserList(@RequestBody QueryData<UserExample> queryData);

}
