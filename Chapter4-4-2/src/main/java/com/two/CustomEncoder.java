package com.two;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {

    protected void encode(ChannelHandlerContext channelHandlerContext, CustomMsg customMsg, ByteBuf byteBuf) throws Exception {
        if(null == customMsg){
            throw new Exception("msg is null");
        }

        String body = customMsg.getBody();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        byteBuf.writeByte(customMsg.getType());
        byteBuf.writeByte(customMsg.getFlag());
        byteBuf.writeInt(bodyBytes.length);
        byteBuf.writeBytes(bodyBytes);
    }
}
