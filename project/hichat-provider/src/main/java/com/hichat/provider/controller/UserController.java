package com.hichat.provider.controller;


import com.hichat.common.pojo.User;
import com.hichat.common.pojo.UserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;


    /**
     * 登陆接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnResult login(@RequestParam String account, @RequestParam String password) {
        ReturnResult loginResult = userServiceImpl.login(account, password);
        return loginResult;
    }


    /**
     * 注册接口
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnResult register(@RequestBody User user) {
        ReturnResult result = userServiceImpl.register(user);

        return result;
    }



    @RequestMapping("/getUserList")
    public ReturnResult getUserList(@RequestBody QueryData<UserExample> queryData){
        ReturnResult result = userServiceImpl.getUserList(queryData);
        return result;
    }
    @RequestMapping("/getUserRoleList")
    public ReturnResult getUserRoleList(@RequestBody QueryData<UserExample> queryData){
        ReturnResult result = userServiceImpl.getUserRoleList(queryData);
        return result;
    }


    @RequestMapping("/updateUser")
    public ReturnResult updateUser(@RequestBody User user){
        ReturnResult result = userServiceImpl.updateUser(user);
        return result;
    }





}