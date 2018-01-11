package project.parking.networking;

import io.netty.bootstrap.Bootstrap;
import lombok.Getter;
import lombok.Setter;
import project.parking.networking.listener.DisconnectListener;
import project.protocol.CoreBootstrap;
import project.protocol.packets.general.PacketLogin;

public class Client {

    @Getter
    @Setter
    private boolean connected;

    @Getter
    @Setter
    private boolean loggedIn;

    private Bootstrap bootstrap;

    public Client() {
        CoreBootstrap.registerListeners(
                new DisconnectListener()
        );

        connect("10.3.141.140", 8081, "ev3"); // Home
    }

    private void connect(String ip, int port, String password) {
        CoreBootstrap.runClientBootstrap(ip, port, password, PacketLogin.ClientType.PARKING, bootstrapConsumer -> {
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
