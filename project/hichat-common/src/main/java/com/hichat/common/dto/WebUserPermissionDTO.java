package com.hichat.common.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebUserPermissionDTO implements java.io.Serializable{

    private List<String> menuList = new ArrayList<String>();

    private List<String> permissionList= new ArrayList<String>();

    private String permissions;

    private String menus;

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
        if(!StringUtils.isEmpty(menus)){
            String[] split = menus.split(",");
            List<String> list = Arrays.asList(split);
            setMenuList(list);
        }

    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
        if(!StringUtils.isEmpty(permissions)){
            String[] split = permissions.split(",");
            List<String> list = Arrays.asList(split);
            setPermissionList(list);
        }
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }
}
