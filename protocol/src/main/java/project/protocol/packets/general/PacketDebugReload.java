package project.protocol.packets.general;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@NoArgsConstructor
@AllArgsConstructor
public class PacketDebugReload extends Packet {

    @Getter
    private int drivingSpeed;
    @Getter
    private int steeringSpeed;

    @Override
    public void read(ByteBuf byteBuf) {
        this.drivingSpeed = byteBuf.readInt();
        this.steeringSpeed = byteBuf.readInt();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(drivingSpeed);
        byteBuf.writeInt(steeringSpeed);
    }
}
