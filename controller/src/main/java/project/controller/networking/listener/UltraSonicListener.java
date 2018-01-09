package project.controller.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import project.controller.gui.GUI;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketUltraSonicSamples;

public class UltraSonicListener extends PacketListener {

    @PacketHandler
    public void onUltraSonicSamples(ChannelHandlerContext ctx, PacketUltraSonicSamples packet) {
        float rightSample = packet.getRightSample();
        float leftSample = packet.getLeftSample();
        float frontSample = packet.getFrontSample();
        float backSample = packet.getBackSample();

        System.out.println("UltraSonicSamples");

        GUI.getInstance().updateUltraSonicSamples(rightSample, leftSample, frontSample, backSample);
    }

}
