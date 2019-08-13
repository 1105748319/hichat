package com.hichat.provider.controller;

import com.hichat.common.pojo.Essay;
import com.hichat.common.pojo.EssayExample;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.service.EssayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/essay")
public class EssayController {

    @Autowired
    private EssayServiceImpl essayServiceImpl;



    @RequestMapping(value = "/saveEssay",method = RequestMethod.POST)
    public ReturnResult saveEssay(@RequestBody Essay essay){
        ReturnResult result = essayServiceImpl.saveEssay(essay);
        return result;
    }

    @RequestMapping(value = "/getEssay",method = RequestMethod.POST)
    public ReturnResult getEssay(@RequestBody QueryData<EssayExample> queryData){
        ReturnResult result = essayServiceImpl.getEssay(queryData);
        return result;
    }






}
