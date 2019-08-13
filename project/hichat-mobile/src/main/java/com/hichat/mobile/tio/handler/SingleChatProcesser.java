package com.hichat.mobile.tio.handler;

import com.hichat.common.pojo.Message;
import com.hichat.common.util.ReturnResult;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsResponse;

public class SingleChatProcesser implements WebSocketProcesser{
    @Override
    public void process(String text, ChannelContext channelContext) {


        GroupContext groupContext = channelContext.getGroupContext();

        Message message = Json.toBean(text, Message.class);

        Long toUser = message.getToUserId();
        Long fromUserId = message.getFromUserId();

        ReturnResult sendResult = ReturnResult.build(101,"",message);

        WsResponse wsResponse=WsResponse.fromText(Json.toJson(sendResult),"utf-8");

        Aio.sendToUser(groupContext,String.valueOf(toUser),wsResponse);

        //多端登录时
        WsResponse fromResponse=WsResponse.fromText(Json.toJson(ReturnResult.build(100,"发送成功",text)),"utf-8");

        Aio.sendToUser(groupContext,String.valueOf(fromUserId),fromResponse);

    }


}
