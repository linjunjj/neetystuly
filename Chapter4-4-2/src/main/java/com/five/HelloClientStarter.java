package com.five;


import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HelloClientStarter {
    public  static Node serverNode=new Node(Const.SERVER,Const.PORT);
    public  static ClientAioHandler clientAioHandler=new HellClientAioHander();
    public static ClientAioListener clientAioListener=null;
    private  static ReconnConf reconnConf=new ReconnConf(500L);
    public  static ClientGroupContext clientGroupContext=new ClientGroupContext(clientAioHandler,clientAioListener);
    public  static AioClient aioClient=null;
    public  static ClientChannelContext clientChannelContext=null;

    public static void main(String[] args) throws Exception {
        clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        aioClient=new AioClient(clientGroupContext);
        clientChannelContext=aioClient.connect(serverNode);
        send();
    }

    public static  void send() throws UnsupportedEncodingException {
        HelloPacket packet=new HelloPacket();
        packet.setBody("sfsfasfweafffffffffffffffaaa world".getBytes(HelloPacket.CHARSET));
        Aio.send(clientChannelContext,packet);
    }


}
