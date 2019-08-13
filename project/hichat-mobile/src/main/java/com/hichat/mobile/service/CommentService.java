package com.hichat.mobile.service;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Comment;
import com.hichat.common.util.ReturnResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hichat-provider",fallback = HystrixCommentHandler.class)
public interface CommentService {

    @RequestMapping(value = "/comment/publishComment",method = RequestMethod.POST)
    ReturnResult saveComment(@RequestBody Comment comment);

    @RequestMapping(value = "/comment/getCommentByEssayId/{id}",method = RequestMethod.POST)
    ReturnResult getCommentByEssayId(@PathVariable(value = "id") Long id, @RequestBody PageInfo pageInfo);
}
