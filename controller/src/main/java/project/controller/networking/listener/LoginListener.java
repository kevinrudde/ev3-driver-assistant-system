package project.controller.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.controller.Controller;
import project.protocol.CoreBootstrap;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketDisconnect;
import project.protocol.packets.general.PacketLogin;
import project.protocol.packets.general.PacketLoginSuccessful;

import java.net.InetSocketAddress;

public class LoginListener extends PacketListener {

    @PacketHandler
    public static void onLogin(ChannelHandlerContext ctx, PacketLogin packet) {
        if (CoreBootstrap.getChannels().containsKey(ctx.channel()))
            return;

        if (!packet.getPassword().equals(Controller.getInstance().getPassword())) {
            ctx.channel().writeAndFlush(new PacketDisconnect("The password you typed is incorrect!"));
            ctx.channel().close();
            return;
        }

        System.out.println(((InetSocketAddress) ctx.channel().remoteAddress()).getHostString() + " logged in successfully!");

        ctx.channel().writeAndFlush(new PacketLoginSuccessful());

        if (!CoreBootstrap.isClient()) {
            CoreBootstrap.getChannels().put(packet.getClientType(), ctx.channel());
        }
    }

}
