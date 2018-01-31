package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PacketUpdateInformation extends Packet {

    @Getter
    private int velocity;

    @Override
    public void read(ByteBuf byteBuf) {
        this.velocity = byteBuf.readInt();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(velocity);
    }
}
