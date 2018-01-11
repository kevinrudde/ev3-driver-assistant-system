package project.controller.networking.listener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import project.controller.gui.GUI;
import project.protocol.CoreBootstrap;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketEmergencyStop;
import project.protocol.packets.ev3.PacketUltraSonicSamples;
import project.protocol.packets.general.PacketLogin;

public class UltraSonicListener extends PacketListener {

    @PacketHandler
    public void onUltraSonicSamples(ChannelHandlerContext ctx, PacketUltraSonicSamples packet) {
        float rightSample = packet.getRightSample();
        float leftSample = packet.getLeftSample();
        float frontSample = packet.getFrontSample();
        float backSample = packet.getBackSample();

        GUI.getInstance().updateUltraSonicSamples(rightSample, leftSample, frontSample, backSample);
    }


    @PacketHandler
    public void onEmergencyStop(ChannelHandlerContext ctx, PacketEmergencyStop packet) {
        CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
    }
}
