package com.hichat.mobile.service;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Essay;
import com.hichat.common.pojo.EssayExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hichat-provider",fallback = HystrixEssayHandler.class)
public interface EssayService {

    @RequestMapping(value = "/essay/saveEssay",method = RequestMethod.POST)
    ReturnResult saveEssay(@RequestBody Essay essay);

    @RequestMapping(value = "/essay/getEssay",method = RequestMethod.POST)
    ReturnResult getEssay(@RequestBody QueryData<EssayExample> queryData);
}
