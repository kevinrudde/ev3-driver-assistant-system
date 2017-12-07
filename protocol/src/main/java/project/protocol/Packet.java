package project.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public abstract class Packet {

    public abstract void read(ByteBuf byteBuf);

    public abstract void write(ByteBuf byteBuf);

    public String readString(ByteBuf byteBuf) {
        int stringLength = byteBuf.readInt();

        byte[] stringBytes = new byte[stringLength];
        byteBuf.readBytes(stringBytes);

        return new String(stringBytes, Charset.forName("UTF-8"));
    }

    public void writeString(ByteBuf byteBuf, String writeString) {
        byte[] stringBytes = writeString.getBytes(Charset.forName("UTF-8"));

        byteBuf.writeInt(stringBytes.length);
        byteBuf.writeBytes(stringBytes);
    }
}
