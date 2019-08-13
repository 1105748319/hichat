package com.hichat.mobile.util;

import com.hichat.common.pojo.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

public class Constant {
    private HashMap<Long,WebSocketSession> socketSessionMap=new HashMap<Long,WebSocketSession>();

    private HashMap<String,User> userMap=new HashMap<String,User>();


    private static Constant constant;

    private Constant(){}

    public static Constant newInstance() {
        if(constant==null){
            constant=new Constant();
        }
        return constant;
    }

    public void putWebSocketSession(Long id,WebSocketSession session){
        socketSessionMap.put(id,session);
    }

    public WebSocketSession getWebSocketSession(Long id){
        WebSocketSession session=socketSessionMap.get(id);
        return session;
    }

    public void removeWebSocketSession(Long id){
        socketSessionMap.remove(id);
    }


    public HashMap<Long, WebSocketSession> getMap() {
        return socketSessionMap;
    }



    public void putUser(String token,User user){
        userMap.put(token,user);
    }

    public User getUser(String token){
        User user=userMap.get(token);
        return user;
    }

    public void removeUser(String token){
        userMap.remove(token);
    }


}
