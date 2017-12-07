package project.core.networking.listener;

import ev3dev.actuators.Sound;
import io.netty.channel.ChannelHandlerContext;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketSoundBeep;

public class SoundBeepListener extends PacketListener {

    @PacketHandler
    public void onSoundBeep(ChannelHandlerContext ctx, PacketSoundBeep packet) {
        Sound sound = Sound.getInstance();
        sound.beep();
    }

}
