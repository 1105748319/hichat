package com.hichat.mobile.tio.handler;

import org.tio.core.ChannelContext;

public interface WebSocketProcesser {

    void process(String text, ChannelContext channelContext);
}
