package com.hichat.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Message;
import com.hichat.common.util.FileUtil;
import com.hichat.common.util.IDUtil;
import com.hichat.common.util.ImageHelper;
import com.hichat.common.util.ReturnResult;
import com.hichat.mobile.util.ConstantProperties;
import com.hichat.mobile.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConstantProperties constantProperties;

    @RequestMapping(value = "/saveMessage",method = RequestMethod.POST)
    public ReturnResult saveMessage(@RequestBody Message message){
        ReturnResult result = messageService.insertMessage(message);
        return result;
    }


    @RequestMapping(value="/getMessage/{userId}/{otherUserId}/{time}",method = RequestMethod.POST)
    public ReturnResult getMessage(@PathVariable Long userId, @PathVariable Long otherUserId,
                                   @PathVariable Date time,
                                   @RequestBody PageInfo pageInfo){
        ReturnResult result = messageService.getMessage(userId, otherUserId, time,pageInfo);
        return result;
    }


    @RequestMapping(value="/uploadMessageImg/{imgType}/{md5}",method = RequestMethod.POST)
    public ReturnResult makeBaseImage(HttpServletRequest request, @PathVariable String imgType, @PathVariable String md5)throws Exception {
        InputStream inputStream = request.getInputStream();
        String genImageName = IDUtil.genImageName();
        int middlePath = FileUtil.getSavePath(constantProperties.getImgUploadPath() + constantProperties.getMessageImgPath());
        String path=constantProperties.getImgUploadPath()+constantProperties.getMessageImgPath()+middlePath+"/"+genImageName+"."+imgType;
        boolean writeToFile = FileUtil.writeToFile(inputStream, path, md5);
        String imgUrl = null;
        String smallUrl=null;
        if (writeToFile) {
            imgUrl=constantProperties.getMessageImgPath()+middlePath+"/"+genImageName+"."+imgType;
            //生成缩略图
            String smallImg = IDUtil.genImageName();
            String smallPath=constantProperties.getImgUploadPath()+constantProperties.getMessageImgPath()+middlePath+"/"+smallImg+"."+imgType;
            smallUrl=constantProperties.getMessageImgPath()+middlePath+"/"+smallImg+"."+imgType;
            ImageHelper.scale(path,smallPath,2,false,imgType,"message");
        } else {
            return ReturnResult.build(400, "上传图片失败");
        }

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("imgUrl",imgUrl);
        map.put("smallUrl",smallUrl);
        return ReturnResult.ok(map);
    }

}
