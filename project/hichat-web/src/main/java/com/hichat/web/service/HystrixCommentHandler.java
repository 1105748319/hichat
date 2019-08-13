package com.hichat.web.service;

import com.github.pagehelper.PageInfo;
import com.hichat.common.pojo.Comment;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;

@Component
public class HystrixCommentHandler implements CommentService{



    @Override
    public ReturnResult getCommentByEssayId(Long id, PageInfo pageInfo) {
        return ReturnResult.build(400,"系统异常");
    }
}
