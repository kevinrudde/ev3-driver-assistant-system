package project.debugger.networking;

import io.netty.channel.ChannelHandlerContext;
import project.protocol.Packet;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketSoundBeep;
import project.protocol.packets.general.PacketDebugReload;

public class DebugListener extends PacketListener {

    @PacketHandler
    public void onSoundBeep(ChannelHandlerContext ctx, PacketDebugReload packet) {
        System.out.println("Received: " + packet.getClass().getSimpleName());
        System.out.println("Driving Speed: " + packet.getDrivingSpeed());
        System.out.println("Steering Speed: " + packet.getSteeringSpeed());
    }

}
