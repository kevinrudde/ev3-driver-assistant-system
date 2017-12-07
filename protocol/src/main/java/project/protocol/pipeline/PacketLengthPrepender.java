package project.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketLengthPrepender extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, ByteBuf out) throws Exception {
        out.writeInt(in.readableBytes());

        out.writeBytes(in);
    }
}
