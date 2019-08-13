package com.hichat.provider.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.*;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.mapper.WebUserMapper;
import com.hichat.provider.mapper.WebUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class WebUserServiceImpl {

    @Autowired
    private WebUserMapper webUserMapper;
    @Autowired
    private WebUserRoleMapper webUserRoleMapper;

    public ReturnResult login(String account, String password) {
        WebUser webUser= webUserMapper.login(account,DigestUtils.md5DigestAsHex(password.getBytes()));
        if(webUser!=null){
            return ReturnResult.ok(webUser);
        }
        return ReturnResult.build(400,"用户名或密码错误");
    }


    public ReturnResult queryList(QueryData<WebUserExample> queryData){
        PageInfo pageInfo = queryData.getPageInfo();
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        WebUserExample param = queryData.getParam();
        List<WebUser> users = webUserMapper.selectByExampleNew(param);
        PageInfo<WebUser> resultInfo=new PageInfo<WebUser>(users);
        return ReturnResult.ok(resultInfo);
    }

    @Transactional
    public ReturnResult saveOrUpdate(WebUser webUser){
        if(webUser.getId()==null){
            String password = webUser.getPassword();
            webUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            webUserMapper.insert(webUser);
        }else{
            if(webUser.getPassword()!=null){
                String password = webUser.getPassword();
                webUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            }
            webUserMapper.updateByPrimaryKeySelective(webUser);
        }
        return ReturnResult.ok();
    }

    @Transactional
    public ReturnResult delete(Long id){
         webUserMapper.deleteByPrimaryKey(id);
         return ReturnResult.ok();
    }

    @Transactional
    public ReturnResult assignRole(WebUser webUser){
        Long[] roleIds = webUser.getRoleIds();
        WebUserRoleExample webUserRoleExample=new WebUserRoleExample();
        WebUserRoleExample.Criteria criteria = webUserRoleExample.createCriteria();
        criteria.andWebUserIdEqualTo(webUser.getId().intValue());
        webUserRoleMapper.deleteByExample(webUserRoleExample);
        for(Long roleId:roleIds){
            WebUserRole webUserRole=new WebUserRole();
            webUserRole.setRoleId(roleId.intValue());
            webUserRole.setWebUserId(webUser.getId().intValue());
            webUserRoleMapper.insert(webUserRole);
        }
        return ReturnResult.ok();
    }

}
