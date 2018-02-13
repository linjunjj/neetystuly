package com.two;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class CustomDecoder extends LengthFieldBasedFrameDecoder {
    private  static  final  int HEADER_SIZE=6;
    private byte type;
    private  byte flag;
    private  int length;
    protected String body;

    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                         int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in==null){
            return null;
        }
        if (in.readableBytes()<HEADER_SIZE){
            throw new Exception("可读信息段比头部信息都小，你在逗我？");
        }

        type = in.readByte();

        flag = in.readByte();

        length = in.readInt();

        if (in.readableBytes() < length) {
            throw new Exception("body字段你告诉我长度是"+length+",但是真实情况是没有这么多，你又逗我？");
        }
        ByteBuf buf = in.readBytes(length);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        body = new String(req, "UTF-8");

        CustomMsg customMsg = new CustomMsg(type,flag,length,body);
        return customMsg;
    }
}
