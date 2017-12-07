package project.protocol.utility;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.PlatformDependent;

public class PipelineUtility {

    private static boolean epoll;

    static {
        // Checking for epoll-availability if the OS isn't Windows
        if (!PlatformDependent.isWindows()) {
            // Checking for epoll-availability
            if (epoll = Epoll.isAvailable()) {
                System.out.println("[Pipeline] Using Epoll!");
            } else {
                System.out.println("[Pipeline] Using Nio!");
            }
        }
    }

    /**
     * @return EpollSocketChannel if epoll is available
     */
    public static Class<? extends SocketChannel> getSocketChannelClass() {
        return epoll ? EpollSocketChannel.class : NioSocketChannel.class;
    }

    /**
     * @return EpollServerSocketChannel if epoll is available
     */
    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        return epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    /**
     * @return an EpollEventLoopGroup if epoll is available
     */
    public static EventLoopGroup getEventLoopGroup(int nThreads) {
        return epoll ? new EpollEventLoopGroup(nThreads) : new NioEventLoopGroup(nThreads);
    }

}
