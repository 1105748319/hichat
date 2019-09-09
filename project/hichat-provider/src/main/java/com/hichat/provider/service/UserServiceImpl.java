package com.hichat.provider.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.RolePermission;
import com.hichat.common.pojo.User;
import com.hichat.common.pojo.UserExample;
import com.hichat.common.util.ChinaInitial;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.mapper.RolePermissionMapper;
import com.hichat.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public ReturnResult login(String account, String password) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));

        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            User user = users.get(0);
            return ReturnResult.ok(user);
        }
        return ReturnResult.build(400, "用户名或密码错误");
    }


    public ReturnResult register(User user) {
        ChinaInitial.setUserHeader(user);
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        int insert = userMapper.insert(user);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        criteria.andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        int insert1 = 0;
        if(users.size()>0){
            RolePermission rolePermission=new RolePermission();
            rolePermission.setPermissionId(users.get(0).getId().intValue());
            rolePermission.setRoleId(Integer.parseInt(user.getDescription()));
            insert1 = rolePermissionMapper.insert(rolePermission);
        }

        if (insert > 0 && insert1>0) {
            return ReturnResult.ok();
        }
        return ReturnResult.build(400, "注册用户失败");
    }


    public ReturnResult getUserList(QueryData<UserExample> queryData) {
        PageInfo pageInfo = queryData.getPageInfo();
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        UserExample param = queryData.getParam();
        List<User> users = userMapper.selectByExample(param);
        PageInfo<User> resultInfo = new PageInfo<User>(users);
        return ReturnResult.ok(resultInfo);
    }

    public ReturnResult getUserRoleList(QueryData<UserExample> queryData) {
        PageInfo pageInfo = queryData.getPageInfo();
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        UserExample param = queryData.getParam();
        List<User> users = userMapper.selectByRole(param);
        PageInfo<User> resultInfo = new PageInfo<User>(users);
        return ReturnResult.ok(resultInfo);
    }

    @Transactional
    public ReturnResult updateUser(User user) {
        ChinaInitial.setUserHeader(user);
        int i = userMapper.updateByPrimaryKey(user);
        if (i > 0) {
            return ReturnResult.ok();
        }
        return ReturnResult.build(400, "更新用户失败");
    }
}
