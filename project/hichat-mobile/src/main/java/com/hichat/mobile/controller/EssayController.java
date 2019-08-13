package com.hichat.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Essay;
import com.hichat.common.pojo.EssayExample;
import com.hichat.common.pojo.User;
import com.hichat.common.util.*;
import com.hichat.mobile.util.ConstantProperties;
import com.hichat.mobile.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/essay")
public class EssayController {

    @Autowired
    private EssayService essayService;

    @Autowired
    private ConstantProperties constantProperties;


    @RequestMapping(value = "/saveEssay",method = RequestMethod.POST)
    public ReturnResult saveEssay(HttpServletRequest request, @RequestBody Essay essay){
        User user=(User)request.getAttribute("user");
        essay.setUserId(user.getId());
        ReturnResult result = essayService.saveEssay(essay);
        return result;
    }

    @RequestMapping(value = "/getEssay",method = RequestMethod.POST)
    public ReturnResult getEssay(@RequestBody QueryData<EssayExample> queryData){
        ReturnResult result = essayService.getEssay(queryData);
        return result;
    }


    @RequestMapping(value="/uploadEssayImg/{imgType}/{md5}",method = RequestMethod.POST)
    public ReturnResult makeBaseImage(HttpServletRequest request, @PathVariable String imgType, @PathVariable String md5)throws Exception {
        InputStream inputStream = request.getInputStream();
        String genImageName = IDUtil.genImageName();
        int middlePath = FileUtil.getSavePath(constantProperties.getImgUploadPath() + constantProperties.getEssayImgPath());
        String path=constantProperties.getImgUploadPath()+constantProperties.getEssayImgPath()+middlePath+"/"+genImageName+"."+imgType;
        boolean writeToFile = FileUtil.writeToFile(inputStream, path, md5);
        String imgUrl = null;
        String smallUrl=null;
        if (writeToFile) {
            imgUrl = constantProperties.getEssayImgPath() + middlePath+"/"+genImageName + "." + imgType;
            //生成缩略图
            String smallImg = IDUtil.genImageName();
            String smallPath=constantProperties.getImgUploadPath()+constantProperties.getEssayImgPath()+middlePath+"/"+smallImg+"."+imgType;
            smallUrl=constantProperties.getEssayImgPath()+middlePath+"/"+smallImg+"."+imgType;
            ImageHelper.scale(path,smallPath,2,false,imgType,"essay");
            ImageHelper.cutCenterImage(smallPath,smallPath,450,450,imgType);

        } else {
            return ReturnResult.build(400, "上传图片失败");
        }
        HashMap map=new HashMap();
        map.put("imgUrl",imgUrl);
        map.put("smallUrl",smallUrl);
        return ReturnResult.ok(map);
    }


}
