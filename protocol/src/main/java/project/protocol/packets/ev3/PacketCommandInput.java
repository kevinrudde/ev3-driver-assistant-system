package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PacketCommandInput extends Packet {

    @Getter
    private Action action;

    @Getter
    private float extra;

    @Override
    public void read(ByteBuf byteBuf) {
        this.action = Action.values()[byteBuf.readInt()];
        this.extra = byteBuf.readFloat();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(action.ordinal());
        byteBuf.writeFloat(extra);
    }

    public enum Action {
        FORWARD,
        BACKWARDS,
        RIGHT,
        LEFT,
        DRIVING_STOP,
        STEERING_STOP,
        TEMPOMAT
    }
}
