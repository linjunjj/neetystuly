package com.five;

import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;

public class HelloServerAioHandler implements ServerAioHandler {
    @Override
    public Packet decode(ByteBuffer byteBuffer, ChannelContext channelContext) throws AioDecodeException {
        int readableLength=byteBuffer.limit()-byteBuffer.position();
        if (readableLength<HelloPacket.HEADER_LENGHT){
            return  null;
        }
        int bodyLength=byteBuffer.getInt();
        if (bodyLength<0){
            throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());

        }
        System.out.println(byteBuffer);
        int neededLength=HelloPacket.HEADER_LENGHT+bodyLength;
        int isDataEnough=readableLength-neededLength;
        if (isDataEnough<0){
            return  null;
        }else {
            HelloPacket impacket=new HelloPacket();
            if (bodyLength>0){
                byte[] dst=new byte[bodyLength];
                byteBuffer.get(dst);
                impacket.setBody(dst);
            }
            return impacket;
        }
    }

    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        HelloPacket helloPacket= (HelloPacket) packet;
        byte[] body=helloPacket.getBody();
        int bodyLen=0;
        if (body!=null){
            bodyLen=body.length;
        }
        int allLen=HelloPacket.HEADER_LENGHT+bodyLen;
        ByteBuffer buffer=ByteBuffer.allocate(allLen);
        buffer.putInt(bodyLen);
        if (body!=null){
            buffer.put(body);
        }
       return buffer;
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
    HelloPacket helloPacket= (HelloPacket) packet;
    byte[] bady=helloPacket.getBody();
    if (bady!=null){
        String str=new String(bady,HelloPacket.CHARSET);
        System.out.println("sfbsaigfiooooooasgggggggweufuygeyiwf");
        System.out.println("收到信息是"+str);
        HelloPacket respactet=new HelloPacket();
        respactet.setBody(("收到信息是"+str).getBytes(HelloPacket.CHARSET));
        Aio.send(channelContext,respactet);
    }
    }
}
