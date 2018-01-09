package project.protocol.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import project.protocol.CoreBootstrap;
import project.protocol.Packet;
import project.protocol.packets.general.PacketDisconnect;
import project.protocol.packets.general.PacketLogin;
import project.protocol.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

    @Getter
    private Channel channel;
    private List<Packet> sentBeforeChannelActive = new ArrayList<>();

    public PacketHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (!(cause instanceof IOException)) {
            System.err.println("Exception caught (" + Utility.getHostString(ctx.channel().remoteAddress()) + "):");
            cause.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        for (Packet packet : sentBeforeChannelActive) {
            channel.writeAndFlush(packet);
        }

        sentBeforeChannelActive.clear();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        if (!CoreBootstrap.isClient()) {
            System.out.println(Utility.getHostString(ctx.channel().remoteAddress()) + " disconnected!");

            PacketLogin.ClientType clientType = CoreBootstrap.getChannels().get(ctx.channel());


            CoreBootstrap.getChannels().remove(ctx.channel());

            PacketDisconnect packet = new PacketDisconnect(clientType);
            CoreBootstrap.getListenerRegistry().callEvent(ctx, packet); // "Call" DisconnectPacket to the server
        } else {
            System.out.println("Disconnected from server!");
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {

        if (!CoreBootstrap.getListenerRegistry().getPacketListener().containsRow(packet.getClass()))
            return;

        if (!CoreBootstrap.isClient() && !(packet instanceof PacketLogin) && !CoreBootstrap.getChannels().containsKey(channelHandlerContext.channel())) {
            channelHandlerContext.writeAndFlush(new PacketDisconnect("You sent packets while you weren't logged in!"));
            channelHandlerContext.channel().close();
            return;
        }

        CoreBootstrap.getListenerRegistry().callEvent(channelHandlerContext, packet);
    }

    public void sendPacket(Packet packet) {
        if (!channel.isActive()) {
            sentBeforeChannelActive.add(packet);
            return;
        }

        channel.writeAndFlush(packet);
    }
}

