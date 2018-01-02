package project.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import project.protocol.listener.ListenerRegistry;
import project.protocol.listener.PacketListener;
import project.protocol.packets.general.PacketLogin;
import project.protocol.pipeline.*;
import project.protocol.utility.PipelineUtility;
import project.protocol.utility.Utility;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class CoreBootstrap {

    private static ListenerRegistry listenerRegistry = new ListenerRegistry();
    private static Map<PacketLogin.ClientType, Channel> channels = new HashMap<>();
    
    private static boolean client;
    private static PacketHandler clientHandler;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Registers the given handlers
     *
     * @param listeners array of the listeners should be registered
     */
    public static void registerListeners(PacketListener... listeners) {
        int listenerCount = 0;
        ListenerRegistry reg = listenerRegistry;

        for (PacketListener listener : listeners) {

            reg.register(listener);

            listenerCount++;
        }

        System.out.println("Registered " + listenerCount + " handlers!");
    }

    /**
     * Connects a client to the given ip & port
     *
     * @param ip                ip-address of the server
     * @param port              port of the server
     * @param password          password the client should connect with
     * @param clientType        type of the client that connects
     * @param bootstrapCallback callback of CoreBootstrap
     */
    public static void runClientBootstrap(String ip, int port, String password, PacketLogin.ClientType clientType,
                                          Consumer<Bootstrap> bootstrapCallback) {
        // I know, that this is kinda ugly :/
        client = true;

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Constructing CoreBootstrap
                final Bootstrap bootstrap = new Bootstrap()
                        .group(PipelineUtility.getEventLoopGroup(0))
                        .channel(PipelineUtility.getSocketChannelClass())
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                PacketHandler packetHandler = preparePipeline(socketChannel);

                                clientHandler = packetHandler;
                                packetHandler.sendPacket(new PacketLogin(Utility.convertToMd5(password), clientType));
                            }

                        });

                // Connecting to given ip & port
                ChannelFuture f = bootstrap.connect(ip, port).awaitUninterruptibly();

                // Checking if the connection was successfully established
                if (!f.isSuccess()) {
                    // "Callbacking" null if the client didn't connect successfully
                    bootstrapCallback.accept(null);

                    // Shutdowning EventLoopGroup
                    bootstrap.group().shutdownGracefully();
                    return;
                }

                // "Callbacking" the Bootstrap
                bootstrapCallback.accept(bootstrap);

                // Adding shutdown hook
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        bootstrap.group().shutdownGracefully();
                    }
                }));
            }
        });
    }

    /**
     * Binds the server-bootstrap to the given port
     *
     * @param port                    port of the server
     * @param serverBootstrapConsumer callback of ServerBootstrap
     */
    public static void runServerBootstrap(int port, Consumer<ServerBootstrap> serverBootstrapConsumer) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Creating EventLoopGroups
                EventLoopGroup bossGroup = PipelineUtility.getEventLoopGroup(0);
                EventLoopGroup workerGroup = PipelineUtility.getEventLoopGroup(0);

                // Constructing ServerBootstrap
                ServerBootstrap serverBootstrap = new ServerBootstrap()
                        .group(bossGroup, workerGroup)
                        .channel(PipelineUtility.getServerSocketChannelClass())
                        .childHandler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                preparePipeline(socketChannel);
                            }

                        })
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_SNDBUF, 64 * 1024)
                        .childOption(ChannelOption.SO_RCVBUF, 64 * 1024)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                // Binding to given port
                ChannelFuture channelFuture = serverBootstrap.bind(port).awaitUninterruptibly();

                // Checking for successful connection
                if (!channelFuture.isSuccess()) {
                    // "Callbacking" null
                    serverBootstrapConsumer.accept(null);

                    // Shutdowning group(s)
                    serverBootstrap.group().shutdownGracefully();
                    return;
                }

                // "Callbacking" the ServerBootstrap
                serverBootstrapConsumer.accept(serverBootstrap);

                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        serverBootstrap.group().shutdownGracefully();
                    }
                }));
            }
        });
    }

    /**
     * Prepares the pipeline for the given socket-channel
     *
     * @param socketChannel socket-channel that is initializing
     * @return packet-listener that was added to the pipeline
     */
    private static PacketHandler preparePipeline(SocketChannel socketChannel) {
        PacketHandler packetHandler = new PacketHandler(socketChannel);
        ChannelPipeline pipeline = socketChannel.pipeline();

        //pipeline.addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4 ) );
        pipeline.addLast(new PacketLengthSplitter());
        pipeline.addLast(new PacketDecoder());

        //pipeline.addLast( new LengthFieldPrepender( 4 ) );
        pipeline.addLast(new PacketLengthPrepender());
        pipeline.addLast(new PacketEncoder());

        pipeline.addLast(packetHandler);

        return packetHandler;
    }
    
    public static Channel getClient(PacketLogin.ClientType clientType) {
        return channels.get(clientType);
    }

    public static boolean sendPacket(Packet packet, PacketLogin.ClientType clientType) {
        Channel client = getClient(clientType);
        if (client == null) {
            return false;
        } else {
            client.writeAndFlush(packet);
            return true;
        }
    }

    public static ListenerRegistry getListenerRegistry() {
        return listenerRegistry;
    }

    public static Map<PacketLogin.ClientType, Channel> getChannels() {
        return channels;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static boolean isClient() {
        return client;
    }

    public static PacketHandler getClientHandler() {
        return clientHandler;
    }
}

