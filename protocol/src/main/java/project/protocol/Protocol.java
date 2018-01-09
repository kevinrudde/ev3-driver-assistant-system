package project.protocol;

import project.protocol.packets.ev3.PacketCommandInput;
import project.protocol.packets.ev3.PacketSoundBeep;
import project.protocol.packets.ev3.PacketUpdateInformation;
import project.protocol.packets.general.PacketDebugReload;
import project.protocol.packets.general.PacketDisconnect;
import project.protocol.packets.general.PacketLogin;
import project.protocol.packets.general.PacketLoginSuccessful;

public enum Protocol {

    LOGIN(0x01, PacketLogin.class),
    LOGIN_SUCCESSFUL(0x02, PacketLoginSuccessful.class),
    DISCONNECT(0x03, PacketDisconnect.class),
    SOUND_BEEP(0x04, PacketSoundBeep.class),
    COMMAND_INPUT(0x05, PacketCommandInput.class),
    DEBUG_RELOAD(0x06, PacketDebugReload.class),
    UPDATE_INFORMATION(0x07, PacketUpdateInformation.class)
    ;

    private int packetId;
    private Class<? extends Packet> packetClass;

    Protocol(int packetId, Class<? extends Packet> packetClass) {
        this.packetId = packetId;
        this.packetClass = packetClass;
    }

    public static int getPacketIdByClass(Class<? extends Packet> packetClass) {
        for (Protocol protocol : Protocol.values()) {
            if (protocol.getPacketClass() != packetClass)
                continue;

            return protocol.getPacketId();
        }
        return -1;
    }

    public static Class<? extends Packet> getPacketClassById(int packetId) {
        for (Protocol protocol : Protocol.values()) {
            if (protocol.getPacketId() != packetId)
                continue;

            return protocol.getPacketClass();
        }
        return null;
    }

    public int getPacketId() {
        return packetId;
    }

    public Class<? extends Packet> getPacketClass() {
        return packetClass;
    }
}

