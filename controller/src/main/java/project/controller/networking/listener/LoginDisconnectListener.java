package project.controller.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.controller.Controller;
import project.controller.gui.GUI;
import project.protocol.CoreBootstrap;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketDisconnect;
import project.protocol.packets.general.PacketLogin;
import project.protocol.packets.general.PacketLoginSuccessful;

import java.net.InetSocketAddress;

public class LoginDisconnectListener extends PacketListener {

    @PacketHandler
    public void onLogin(ChannelHandlerContext ctx, PacketLogin packet) {
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
            CoreBootstrap.getChannels().put(ctx.channel(), packet.getClientType());
            GUI.getInstance().connected(packet.getClientType());
        }
    }

    @PacketHandler
    public void onDisconnect(ChannelHandlerContext ctx, PacketDisconnect packet) {
        GUI.getInstance().disconnected(packet.getClientType());

    }

}
