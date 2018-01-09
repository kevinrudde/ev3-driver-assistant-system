package project.core.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.core.reference.Reference;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketDebugReload;

public class DebugReloadListener extends PacketListener {

    @PacketHandler
    public void onDebugReload(ChannelHandlerContext ctx, PacketDebugReload packet) {
        System.out.println("Reloading Reference values with:");
        System.out.println("STEERING_SPEED: " + packet.getSteeringSpeed());
        System.out.println("DRIVING_SPEED: " + packet.getDrivingSpeed());

        Reference.DRIVING_SPEED = packet.getDrivingSpeed();
        Reference.STEERING_SPEED = packet.getSteeringSpeed();
    }

}
