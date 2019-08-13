package com.hichat.provider.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hichat.common.dto.UsersMessagesDTO;
import com.hichat.common.pojo.Message;
import com.hichat.common.util.QueryData;
import com.hichat.common.util.ReturnResult;
import com.hichat.provider.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl {

    @Autowired
    private MessageMapper messageMapper;


    @Transactional
    public ReturnResult insertMessage(Message message) {
        int insert = messageMapper.insert(message);
        if(insert>0){
            return ReturnResult.ok();
        }
        return ReturnResult.build(400,"消息保存失败");
    }

    public ReturnResult getMessage(Long userId, Long otherUserId, Date time, PageInfo pageInfo) {

        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        List<Message> messages = messageMapper.getMessage(userId,otherUserId,time);
        PageInfo<Message> resultInfo=new PageInfo<Message>(messages);
        return ReturnResult.ok(resultInfo);
    }


    public ReturnResult getUsersMessage(QueryData<UsersMessagesDTO> queryData){
        PageInfo pageInfo = queryData.getPageInfo();
        UsersMessagesDTO param = queryData.getParam();
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        List<UsersMessagesDTO> usersMessage = messageMapper.getUsersMessage(param);
        PageInfo<UsersMessagesDTO> resultInfo=new PageInfo<UsersMessagesDTO>(usersMessage);
        return ReturnResult.ok(resultInfo);
    }

}
