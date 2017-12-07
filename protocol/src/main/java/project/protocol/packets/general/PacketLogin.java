package project.protocol.packets.general;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@NoArgsConstructor
@AllArgsConstructor
public class PacketLogin extends Packet {

    @Getter
    private String password;
    @Getter
    private ClientType clientType;

    @Override
    public void read(ByteBuf byteBuf) {
        this.password = readString(byteBuf);
        this.clientType = ClientType.values()[byteBuf.readInt()];
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, this.password);
        byteBuf.writeInt(clientType.ordinal());
    }

    public static enum ClientType {
        PARKING, CORE, SERVER;
    }
}
