package com.hichat.mobile.controller;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Comment;
import com.hichat.common.pojo.User;
import com.hichat.common.util.ReturnResult;
import com.hichat.mobile.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/publishComment",method = RequestMethod.POST)
    public ReturnResult publishComment(HttpServletRequest request, @RequestBody Comment comment){
        User user=(User) request.getAttribute("user");

        comment.setUserId(user.getId());

        ReturnResult result = commentService.saveComment(comment);
        return result;
    }


    @RequestMapping(value = "/getCommentByEssayId/{id}",method = RequestMethod.POST)
    public ReturnResult getCommentByEssayId(@PathVariable Long id, @RequestBody PageInfo pageInfo){
        ReturnResult result = commentService.getCommentByEssayId(id, pageInfo);
        return result;
    }




}
