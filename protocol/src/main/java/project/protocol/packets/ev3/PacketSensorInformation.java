package project.protocol.packets.ev3;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import project.protocol.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PacketSensorInformation extends Packet {

    private Sensor sensor;
    private float value;

    @Override
    public void read(ByteBuf byteBuf) {
        this.sensor = Sensor.values()[byteBuf.readInt()];
        this.value = byteBuf.readFloat();
    }

    @Override
    public void write(ByteBuf byteBuf) {
        byteBuf.writeInt(sensor.ordinal());
        byteBuf.writeFloat(value);
    }

    public enum Sensor {
        ULTRA_SONIC_RIGHT,
        ULTRA_SONIC_LEFT,
        ULTRA_SONIC_FRONT,
        ULTRA_SONIC_BACK,

        COLOR_RIGHT,
        COLOR_LEFT
    }
}
