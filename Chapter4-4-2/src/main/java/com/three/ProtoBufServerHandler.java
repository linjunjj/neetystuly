package com.three;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        RichManProto.RichMan req = (RichManProto.RichMan) msg;
        System.out.println(req.getName()+"他有"+req.getCarsCount()+"量车");
        List<Car> lists = req.getCarsList();
        if(null != lists) {

            for(Car car : lists){
                System.out.println(car.getName());
            }
        }
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
