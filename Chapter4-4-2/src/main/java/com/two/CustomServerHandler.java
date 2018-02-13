package com.two;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof CustomMsg) {
            CustomMsg customMsg = (CustomMsg)o;
            System.out.println("Client->Server:"+channelHandlerContext.channel().remoteAddress()+" send "+customMsg.getBody());
        }
    }
}
