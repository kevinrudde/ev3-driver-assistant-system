package project.core.networking.listener;

import ev3dev.actuators.Sound;
import io.netty.channel.ChannelHandlerContext;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketLoginSuccessful;

public class LoginSuccessfulListener extends PacketListener {

    @PacketHandler
    public void onLoginSuccess(ChannelHandlerContext ctx, PacketLoginSuccessful packet) {
        Sound sound = Sound.getInstance();
        sound.beep();
    }
}
