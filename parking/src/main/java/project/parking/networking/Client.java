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
    }

    public void connect(String ip, int port, String password) {
        CoreBootstrap.runClientBootstrap(ip, port, password, PacketLogin.ClientType.PARKING, bootstrap -> {
            if (bootstrap == null) {
                System.err.println("Failed connecting to 127.0.0.1:8081!");
                System.exit(0);
                return;
            }

            Client.this.bootstrap = bootstrap;

            System.out.println("Connected to 127.0.0.1:8081!");
        });
    }

}
