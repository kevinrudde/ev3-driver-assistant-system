package project.debugger.networking;

import io.netty.bootstrap.Bootstrap;
import lombok.Getter;
import project.protocol.CoreBootstrap;
import project.protocol.packets.general.PacketLogin;

public class Client {

    @Getter
    private Bootstrap bootstrap;

    public void initialize() {
        CoreBootstrap.registerListeners(
                new DebugListener()
        );

        connect("127.0.0.1", 8081, "ev3"); // Localhost
    }

    private void connect(String ip, int port, String password) {
        CoreBootstrap.runClientBootstrap(ip, port, password, PacketLogin.ClientType.CORE, bootstrapConsumer -> {
            if (bootstrapConsumer == null) {
                System.err.println("Failed to connected to " + ip + ":" + port);
                System.exit(0);
                return;
            }

            this.bootstrap = bootstrapConsumer;

            System.out.println("Connected to " + ip + ":" + port);
        });
    }

}
