package project.protocol.packets.general;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@NoArgsConstructor
@AllArgsConstructor
public class PacketDisconnect extends Packet {

    @Getter
    private String disconnectReason;

    @Override
    public void read(ByteBuf byteBuf) {
        this.disconnectReason = readString(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, this.disconnectReason);
    }
}

