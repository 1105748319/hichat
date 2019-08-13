package com.hichat.web.service;

import com.hichat.common.pojo.Role;
import com.hichat.common.pojo.RoleExample;
import com.hichat.common.pojo.WebUser;
import com.hichat.common.pojo.WebUserExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "hichat-provider",fallback = HystrixRoleHandler.class)
public interface RoleService {



    @RequestMapping(value = "/role/query-list")
    ReturnResult queryList(@RequestBody QueryData<RoleExample> queryData);

    @RequestMapping(value = "/role/save-or-update")
    ReturnResult saveOrUpdate(@RequestBody Role role);

    @RequestMapping(value = "/role/delete/{id}")
    ReturnResult delete(@PathVariable(value = "id") int id);

    @RequestMapping(value = "/role/permission-list")
    ReturnResult permissionList();

    @RequestMapping(value = "/role/assign-perm")
    ReturnResult assignPerm(@RequestBody Role role);

}
