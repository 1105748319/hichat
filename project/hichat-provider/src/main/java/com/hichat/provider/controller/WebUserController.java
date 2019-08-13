package com.hichat.provider.controller;

import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.service.WebUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;

@RestController
@RequestMapping("/web-user")
public class WebUserController {

    @Autowired
    private WebUserServiceImpl webUserServiceImpl;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnResult login(@RequestParam String account, @RequestParam String password) {
        ReturnResult loginResult = webUserServiceImpl.login(account, password);
        return loginResult;
    }

    @RequestMapping(value = "/query-list")
    public ReturnResult queryList(@RequestBody QueryData<WebUserExample> queryData){
        ReturnResult returnResult = webUserServiceImpl.queryList(queryData);
        return returnResult;
    }

    @RequestMapping("/save-or-update")
    public ReturnResult saveOrUpdate(@RequestBody WebUser webUser){
        ReturnResult returnResult = webUserServiceImpl.saveOrUpdate(webUser);
        return returnResult;
    }


    @RequestMapping("/delete/{id}")
    public ReturnResult delete(@PathVariable Long id){
        ReturnResult returnResult = webUserServiceImpl.delete(id);
        return returnResult;
    }

    @RequestMapping("/assign-role")
    public ReturnResult assignRole(@RequestBody WebUser webUser){
        ReturnResult returnResult = webUserServiceImpl.assignRole(webUser);
        return returnResult;
    }


}
