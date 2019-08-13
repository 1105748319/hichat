package com.hichat.web.service;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "hichat-provider",fallback = HystrixWebUserHandler.class)
public interface WebUserService {


    @RequestMapping(value="/web-user/login",method = RequestMethod.POST)
    ReturnResult<WebUser> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password);


    @RequestMapping(value = "/web-user/query-list")
    ReturnResult queryList(@RequestBody QueryData<WebUserExample> queryData);

    @RequestMapping(value = "/web-user/save-or-update")
    ReturnResult saveOrUpdate(@RequestBody WebUser webUser);

    @RequestMapping(value = "/web-user/delete/{id}")
    ReturnResult delete(@PathVariable(value = "id") Long id);

    @RequestMapping(value = "/web-user/assign-role")
    ReturnResult assignRole(@RequestBody WebUser webUser);


}
