package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PacketUltraSonicSamples extends Packet {

    private float rightSample;
    private float leftSample;
    private float frontSample;
    private float backSample;

    @Override
    public void read(ByteBuf byteBuf) {
        this.rightSample = byteBuf.readFloat();
        this.leftSample = byteBuf.readFloat();
        this.frontSample = byteBuf.readFloat();
        this.backSample = byteBuf.readFloat();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeFloat(rightSample);
        byteBuf.writeFloat(leftSample);
        byteBuf.writeFloat(frontSample);
        byteBuf.writeFloat(backSample);
    }
}
