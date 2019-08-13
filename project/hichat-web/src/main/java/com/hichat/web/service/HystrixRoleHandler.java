package com.hichat.web.service;

import com.hichat.common.pojo.Role;
import com.hichat.common.pojo.RoleExample;
import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;


@Component
public class HystrixRoleHandler implements RoleService{


    @Override
    public ReturnResult queryList(QueryData<RoleExample> queryData) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult saveOrUpdate(Role role) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult delete(int id) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult permissionList() {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult assignPerm(Role role) {
        return ReturnResult.build(400,"系统异常");
    }
}
