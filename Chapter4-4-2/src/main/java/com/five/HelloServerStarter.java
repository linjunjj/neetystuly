package com.five;

import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import java.io.IOException;

public class HelloServerStarter {
    public static ServerAioHandler aioHandler=new HelloServerAioHandler();
    public  static ServerAioListener serverAioListener=null;
    public  static ServerGroupContext serverGroupContext=new ServerGroupContext(aioHandler,serverAioListener);
    public  static AioServer aioServer=new AioServer(serverGroupContext);
    public  static  String serverIp=null;
    public  static  int serverPort=Const.PORT;

    public static void main(String[] args) throws IOException {
        serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        aioServer.start(serverIp,serverPort);
    }
}
