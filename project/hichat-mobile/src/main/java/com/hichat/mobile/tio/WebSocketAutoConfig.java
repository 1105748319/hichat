package com.hichat.mobile.tio;

import com.hichat.mobile.util.ConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;


@Configuration
@EnableConfigurationProperties(ConstantProperties.class)
public class WebSocketAutoConfig {

    @Autowired
    ConstantProperties constantProperties;

    @Bean
    WebSocketStarter websocketStarter() throws Exception{

        WsServerConfig wsServerConfig=new WsServerConfig(Integer.valueOf(constantProperties.getTioServerPort()));
        wsServerConfig.setBindIp(constantProperties.getTioServerIp());

        WebSocketStarter websocketStarter = new WebSocketStarter(wsServerConfig);

        ServerGroupContext serverGroupContext = websocketStarter.getServerGroupContext();

        serverGroupContext.setHeartbeatTimeout(constantProperties.getTioTimeout());

        //启动程序
        websocketStarter.start();
        //返回
        return websocketStarter;
    }
}
