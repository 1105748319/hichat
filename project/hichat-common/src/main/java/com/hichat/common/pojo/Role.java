package com.hichat.common.pojo;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

public class Role {
    private Integer id;

    private String roleName;

    private Date createTime;

    private Date updateTime;

    private Long[] permIds=new Long[0];

    private String permIdStr;

    private Long[] pPermIds=new Long[0];

    private String pPermIdStr;

    public Long[] getpPermIds() {
        return pPermIds;
    }

    public void setpPermIds(Long[] pPermIds) {
        this.pPermIds = pPermIds;
    }

    public String getpPermIdStr() {
        return pPermIdStr;
    }

    public void setpPermIdStr(String pPermIdStr) {
        this.pPermIdStr = pPermIdStr;
        if(!StringUtils.isEmpty(pPermIdStr)){
            String[] split = pPermIdStr.split(",");
            setpPermIds((Long[])ConvertUtils.convert(split,Long.class));
        }
    }

    public String getPermIdStr() {
        return permIdStr;
    }

    public void setPermIdStr(String permIdStr) {
        this.permIdStr = permIdStr;
        if(!StringUtils.isEmpty(permIdStr)){
            String[] split = permIdStr.split(",");
            setPermIds((Long[])ConvertUtils.convert(split,Long.class));
        }
    }

    public Long[] getPermIds() {
        return permIds;
    }

    public void setPermIds(Long[] permIds) {
        this.permIds = permIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}