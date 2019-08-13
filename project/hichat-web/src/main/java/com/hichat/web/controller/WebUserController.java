package com.hichat.web.controller;


import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.web.redis.RedisUtil;
import com.hichat.web.service.WebUserService;
import com.hichat.web.util.JsonMapperTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/web-user")
public class WebUserController {


    @Autowired
    private WebUserService webUserService;

    @RequestMapping("/is-online")
    public ReturnResult isOnline(){
        WebUser webUser =(WebUser) SecurityUtils.getSubject().getPrincipal();
        if(webUser==null){
            return ReturnResult.build(500,"登录标识已过期，请重新登录");
        }
        return ReturnResult.ok(webUser);
    }


    @RequestMapping("/to-login")
    public ReturnResult toLogin(){
        return ReturnResult.build(500,"登录标识已过期，请重新登录");
    }


    @RequiresPermissions("employee:query")
    @RequestMapping("/query-list")
    public ReturnResult queryList(@RequestBody QueryData<WebUserExample> queryData){
        ReturnResult returnResult = webUserService.queryList(queryData);
        return returnResult;
    }

    @RequiresPermissions(value={"employee:edit","employee:reset"},logical= Logical.OR)
    @RequestMapping("/save-or-update")
    public ReturnResult saveOrUpdate(@RequestBody WebUser webUser){
        ReturnResult returnResult = webUserService.saveOrUpdate(webUser);
        return returnResult;
    }

    @RequiresPermissions("employee:delete")
    @RequestMapping("/delete/{id}")
    public ReturnResult delete(@PathVariable Long id){
        ReturnResult returnResult = webUserService.delete(id);
        return returnResult;
    }

    @RequiresPermissions("employee:assign")
    @RequestMapping("/assign-role")
    public ReturnResult assignRole(@RequestBody WebUser webUser){
        ReturnResult returnResult = webUserService.assignRole(webUser);
        return returnResult;
    }

}