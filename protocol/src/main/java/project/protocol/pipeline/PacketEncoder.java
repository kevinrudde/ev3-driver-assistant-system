package project.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import project.protocol.Packet;
import project.protocol.Protocol;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        int packetId = Protocol.getPacketIdByClass(packet.getClass());

        if (packetId == -1) {
            byteBuf.release();

            System.err.println("Can't find the packetId of the packet " + packet.getClass().getSimpleName());
            return;
        }

        byteBuf.writeInt(packetId);
        packet.write(byteBuf);
    }
}
