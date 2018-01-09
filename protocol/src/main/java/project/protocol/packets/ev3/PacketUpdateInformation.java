package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PacketUpdateInformation extends Packet {

    private double speed;

    @Override
    public void read(ByteBuf byteBuf) {
        this.speed = byteBuf.readDouble();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeDouble(speed);
    }
}
