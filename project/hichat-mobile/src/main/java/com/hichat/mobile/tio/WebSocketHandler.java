package com.hichat.mobile.tio;

import com.hichat.common.pojo.User;
import com.hichat.common.util.ReturnResult;
import com.hichat.mobile.redis.RedisUtil;
import com.hichat.mobile.tio.handler.SingleChatProcesser;
import com.hichat.mobile.tio.handler.WebSocketProcesser;
import com.hichat.mobile.tio.handler.WebSocketTextType;
import com.hichat.mobile.util.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.util.StringUtils;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.HashMap;


public class WebSocketHandler implements IWsMsgHandler{

    private static HashMap<String,WebSocketProcesser> proccessMap=new HashMap<String,WebSocketProcesser>();

    static{
        proccessMap.put("single",new SingleChatProcesser());
    }

    private static WebSocketProcesser getProcesser(String type){
        WebSocketProcesser processer = proccessMap.get(type);
        return processer;
    }

    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        return handleHandshakeUserInfo(httpRequest,httpResponse,channelContext);
    }

    /**
     *
     * 解析客户端的token，获取用户信息转化为ContextUser对象
     * 将ContextUser对象，以UserId为Key，ContextUser为value存入ChannelContext的Attribute中，方便后续使用当前用户信息
     * 获取用户群组，遍历调用 Aio.bindGroup方法加入群组
     * */
    private HttpResponse handleHandshakeUserInfo(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws  Exception {
        //增加token验证方法
        String token = httpRequest.getRequestLine().getPath().replaceAll("/","");
        if (StringUtils.isEmpty(token)) {
            //没有token 未授权
            httpResponse.setStatus(HttpResponseStatus.C401);
        } else {
            //解析token
            RedisUtil redisUtil = SpringUtil.getBean("redisUtil", RedisUtil.class);
            String userJson = (String) redisUtil.get(token);
            User user = Json.toBean(userJson, User.class);
            if (user == null) {
                //没有找到用户
                httpResponse.setStatus(HttpResponseStatus.C404);
            } else {
                channelContext.setAttribute(String.valueOf(user.getId()),user);
                //绑定用户ID
                Aio.bindUser(channelContext, String.valueOf(user.getId()));
            }
        }
        return httpResponse;
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return "暂不支持字节消息解析";
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Aio.remove(channelContext, "receive close flag");
        return null;
    }

    /**

     * 字符消息（binaryType = blob）过来后会走这个方法

     */
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        try{
            WebSocketTextType websocketTextType = Json.toBean(text,WebSocketTextType.class);
            //拿到对应的消息处理器
            WebSocketProcesser processer = getProcesser(websocketTextType.getType());
            //处理消息
            processer.process(text, channelContext);

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return WsResponse.fromText(Json.toJson(ReturnResult.build(400,"发送失败")),"utf-8");
        }

    }
}
