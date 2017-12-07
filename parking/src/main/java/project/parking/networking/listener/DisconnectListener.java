package project.parking.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketDisconnect;

public class DisconnectListener extends PacketListener {

    @PacketHandler
    public void onDisconnect(ChannelHandlerContext ctx, PacketDisconnect packet) {
        System.out.println("Disconnect reason: " + packet.getDisconnectReason());
        ctx.channel().close();
        System.exit(-1);
    }


}
