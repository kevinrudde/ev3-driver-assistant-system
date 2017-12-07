package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

public class PacketSoundBeep extends Packet {

    @Override
    public void read(ByteBuf byteBuf) {
    }

    @Override
    public void write(ByteBuf byteBuf) {
    }
}
