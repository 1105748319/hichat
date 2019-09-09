package com.hichat.mobile.controller;


import com.hichat.common.pojo.User;
import com.hichat.common.pojo.UserExample;
import com.hichat.common.util.FileUtil;
import com.hichat.common.util.IDUtil;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.mobile.util.ConstantProperties;
import com.hichat.mobile.redis.RedisUtil;
import com.hichat.mobile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.tio.utils.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ConstantProperties constantProperties;

    /**
     * 登陆接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnResult login(HttpServletRequest request, @RequestBody User user) {
        String api = "登陆接口";
        request.setAttribute("api", api);
        String account = user.getAccount();
        String password = user.getPassword();
        if (StringUtils.isEmpty(account)) {
            return ReturnResult.build(400, "用户名为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ReturnResult.build(400, "密码为空");
        }
        ReturnResult loginResult = userService.login(account, password);
        Integer status = loginResult.getStatus();
        if (status == 200) {
            User resultUser = (User) loginResult.getData();
            String token = UUID.randomUUID().toString();
            String userJson = Json.toJson(resultUser);
            redisUtil.set(token, userJson, 7200l);
            return ReturnResult.ok(token);
        }
        return loginResult;
    }


    /**
     * 注册接口
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnResult register(HttpServletRequest request, @RequestBody User user) {
        String api = "注册接口";
        request.setAttribute("api", api);
        ReturnResult result = userService.register(user);
        return result;
    }


    /**
     * 根据token获取用户信息
     */
    @RequestMapping("/getByToken")
    public ReturnResult getByToken(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return ReturnResult.ok(user);
    }

    @RequestMapping("/getUserList")
    public ReturnResult getUserList(@RequestBody QueryData<UserExample> queryData) throws Exception {
        ReturnResult result = userService.getUserList(queryData);
        return result;
    }
    @RequestMapping("/getUserRoleList")
    public ReturnResult getUserRoleList(@RequestBody QueryData<UserExample> queryData) throws Exception {
        ReturnResult result = userService.getUserRoleList(queryData);
        return result;
    }


    @RequestMapping("/updateUser")
    public ReturnResult updateUser(@RequestBody User user, HttpServletRequest request) {
        ReturnResult result = userService.updateUser(user);
        if (result.getStatus() == 200) {
            String token = (String) request.getAttribute("token");
            String userJson = Json.toJson(user);
            redisUtil.set(token, userJson, 7200l);
        }
        return result;
    }


    @RequestMapping("/logout")
    public ReturnResult logout(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        redisUtil.remove(token);
        return ReturnResult.ok();
    }


    /**
     * 保存用户的头像并返回地址
     */
    @RequestMapping(value = "/saveUserHead/{imgType}/{md5}", method = RequestMethod.POST)
    public ReturnResult saveUserHead(HttpServletRequest request, @PathVariable String imgType, @PathVariable String md5) throws Exception {
        InputStream inputStream = request.getInputStream();
        String genImageName = IDUtil.genImageName();
        int middlePath = FileUtil.getSavePath(constantProperties.getImgUploadPath() + constantProperties.getTxImgPath());
        String path = constantProperties.getImgUploadPath() + constantProperties.getTxImgPath() + middlePath + "/" + genImageName + "." + imgType;
        boolean writeToFile = FileUtil.writeToFile(inputStream, path, md5);
        String headUrl = null;
        if (writeToFile) {
            headUrl = constantProperties.getTxImgPath() + middlePath + "/" + genImageName + "." + imgType;
           /* if (id > 0) {
                userService.updateUser(id ,avatar);
            }*/

        } else {
            return ReturnResult.build(400, "头像生成失败");
        }
        return ReturnResult.ok(headUrl);
    }


}