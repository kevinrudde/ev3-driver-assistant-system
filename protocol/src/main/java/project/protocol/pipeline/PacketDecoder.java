package project.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import project.protocol.Packet;
import project.protocol.Protocol;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf instanceof EmptyByteBuf)
            return;

        int packetId = byteBuf.readInt();

        Class<? extends Packet> packetClass = Protocol.getPacketClassById(packetId);

        if (packetClass == null) {
            byteBuf.release();

            System.err.println("Failed to get packet for id " + packetId);
            return;
        }

        Packet packet = null;

        packet = packetClass.newInstance();
        packet.read(byteBuf);

        if (byteBuf.readableBytes() > 0) {
            System.err.println("The packet " + packetClass.getSimpleName() + " wasn't read completely!");
        }
        list.add(packet);
    }
}
