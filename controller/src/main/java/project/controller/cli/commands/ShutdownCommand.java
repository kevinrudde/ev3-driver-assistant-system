package project.controller.cli.commands;

import io.netty.channel.Channel;
import project.controller.cli.command.Command;
import project.protocol.CoreBootstrap;
import project.protocol.packets.general.PacketDisconnect;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", "close", "exit");
    }

    @Override
    public boolean execute(String[] args) {
        System.out.println("Shutting down...");

        for (Channel channel : CoreBootstrap.getChannels().keySet()) {
            channel.writeAndFlush(new PacketDisconnect("Shutdown"));
        }

        System.exit(1);
        return true;
    }
}
