package com.hichat.web.controller;

import com.hichat.common.pojo.EssayExample;
import com.hichat.common.util.*;
import com.hichat.web.service.EssayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/essay")
public class EssayController {


    @Autowired
    private EssayService essayService;

    @RequiresPermissions("essayinfo:query")
    @RequestMapping(value = "/getEssay",method = RequestMethod.POST)
    public ReturnResult getEssay(@RequestBody QueryData<EssayExample> queryData){
        ReturnResult result = essayService.getEssay(queryData);
        return result;
    }




}
