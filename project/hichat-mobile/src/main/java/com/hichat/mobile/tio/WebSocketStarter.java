package com.hichat.mobile.tio;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

public class WebSocketStarter {


    private ServerGroupContext serverGroupContext;
    private WsServerStarter wsServerStarter;

    /**

     *

     * @author tanyaowu

     */
    public WebSocketStarter(WsServerConfig wsServerConfig) throws IOException {
        wsServerStarter = new WsServerStarter(wsServerConfig, new WebSocketHandler());
        serverGroupContext = wsServerStarter.getServerGroupContext();
    }

    /**
     * @return the serverGroupContext

     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }

    public void start() throws IOException {
        wsServerStarter.start();
    }
}
