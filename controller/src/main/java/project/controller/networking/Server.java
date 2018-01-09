package project.controller.networking;

import io.netty.bootstrap.ServerBootstrap;
import project.controller.networking.listener.LoginDisconnectListener;
import project.protocol.CoreBootstrap;

public class Server {

    private boolean running;

    private boolean connected;
    private boolean disabling;

    private boolean loggedIn;

    private ServerBootstrap serverBootstrap;

    public static void main(String[] args) {
        new Server();
    }

    public void initialize() {

        CoreBootstrap.registerListeners(
                new LoginDisconnectListener()
        );

        connect();
    }

    private void connect() {
        CoreBootstrap.runServerBootstrap(8081, serverBootstrap -> {
            if (serverBootstrap == null) {
                System.out.println("Failed listening on port 8081!");
                System.exit(0);
                return;
            }

            this.serverBootstrap = serverBootstrap;

            System.out.println("Listening on port 8081");
        });
    }

}
