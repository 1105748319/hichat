package com.hichat.web.service;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Essay;
import com.hichat.common.pojo.EssayExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;

@Component
public class HystrixEssayHandler implements EssayService{


    @Override
    public ReturnResult saveEssay(Essay essay) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult getEssay(QueryData<EssayExample> queryData) {
        return ReturnResult.build(400,"系统异常");
    }
}
