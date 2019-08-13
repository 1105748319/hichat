package com.hichat.common.util;


import com.github.pagehelper.PageInfo;

public class QueryData<T> {

    private T param;

    private PageInfo pageInfo;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
