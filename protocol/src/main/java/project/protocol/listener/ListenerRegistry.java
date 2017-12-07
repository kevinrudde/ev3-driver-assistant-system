package project.protocol.listener;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.netty.channel.ChannelHandlerContext;
import project.protocol.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ListenerRegistry {

    private static ListenerRegistry instance;
    private Table<Class<?>, PacketListener, Method> packetListener = HashBasedTable.create();

    public void register(PacketListener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.getAnnotationsByType(PacketHandler.class).length == 0) {
                continue;
            }
            packetListener.put(method.getParameterTypes()[1], listener, method);
        }
    }

    public void callEvent(ChannelHandlerContext ctx, Packet packet) {
        if (!packetListener.containsRow(packet.getClass())) {
            return;
        }
        packetListener.row(packet.getClass()).forEach((listener, method) -> {
            try {
                method.invoke(listener, ctx, packet);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    public Table<Class<?>, PacketListener, Method> getPacketListener() {
        return packetListener;
    }
}
