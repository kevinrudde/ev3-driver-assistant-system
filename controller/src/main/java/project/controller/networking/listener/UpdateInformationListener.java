package project.controller.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.controller.gui.GUI;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketUpdateInformation;

public class UpdateInformationListener extends PacketListener {

    @PacketHandler
    public void onUpdateInformation(ChannelHandlerContext ctx, PacketUpdateInformation packet) {
        int velocity = packet.getVelocity();

        //TODO: calculate actual speed
        GUI.getInstance().updateSpeed(velocity);
    }

}
