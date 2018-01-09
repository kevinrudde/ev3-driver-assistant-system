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
    private PacketLogin.ClientType clientType;

    @Getter
    private String disconnectReason;

    public PacketDisconnect(PacketLogin.ClientType clientType) {
        this.clientType = clientType;
    }

    public PacketDisconnect(String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        this.disconnectReason = readString(byteBuf);
        this.clientType = PacketLogin.ClientType.values()[byteBuf.readInt()];
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, this.disconnectReason);
        byteBuf.writeInt(clientType.ordinal());
    }
}

