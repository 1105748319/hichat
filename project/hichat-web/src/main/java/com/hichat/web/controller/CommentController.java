package com.hichat.web.controller;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Comment;
import com.hichat.common.pojo.User;
import com.hichat.common.util.ReturnResult;
import com.hichat.web.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/getCommentByEssayId/{id}",method = RequestMethod.POST)
    public ReturnResult getCommentByEssayId(@PathVariable Long id, @RequestBody PageInfo pageInfo){
        ReturnResult result = commentService.getCommentByEssayId(id, pageInfo);
        return result;
    }




}
