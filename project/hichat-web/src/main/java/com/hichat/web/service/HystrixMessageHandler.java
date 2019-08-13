package com.hichat.web.service;

import com.github.pagehelper.PageInfo;
import com.hichat.common.dto.UsersMessagesDTO;
import com.hichat.common.pojo.Message;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HystrixMessageHandler implements MessageService{




    @Override
    public ReturnResult getMessage(Long userId, Long otherUserId, Date time, PageInfo pageInfo) {
        return ReturnResult.build(400,"系统异常");
    }

    @Override
    public ReturnResult getUsersMessage(QueryData<UsersMessagesDTO> queryData) {
        return ReturnResult.build(400,"系统异常");
    }
}
