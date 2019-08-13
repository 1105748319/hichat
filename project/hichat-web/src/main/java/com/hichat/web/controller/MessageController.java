package com.hichat.web.controller;

import com.github.pagehelper.PageInfo;
import com.hichat.common.dto.UsersMessagesDTO;
import com.hichat.common.util.*;
import com.hichat.web.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping(value="/getMessage/{userId}/{otherUserId}/{time}",method = RequestMethod.POST)
    public ReturnResult getMessage(@PathVariable Long userId, @PathVariable Long otherUserId,
                                   @PathVariable Date time,
                                   @RequestBody PageInfo pageInfo){
        ReturnResult result = messageService.getMessage(userId, otherUserId, time,pageInfo);
        return result;
    }

    @RequiresPermissions("chatrecord:query")
    @RequestMapping(value = "/get-users-message")
    public ReturnResult getUsersMessage(@RequestBody QueryData<UsersMessagesDTO> queryData){
        ReturnResult usersMessage = messageService.getUsersMessage(queryData);
        return usersMessage;
    }

}
