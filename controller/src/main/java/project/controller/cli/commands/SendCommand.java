package project.controller.cli.commands;

import project.controller.cli.command.Command;
import project.protocol.CoreBootstrap;
import project.protocol.packets.ev3.PacketSoundBeep;
import project.protocol.packets.general.PacketLogin;

public class SendCommand extends Command {

    public SendCommand() {
        super("send");
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length >= 1) {
            String type = args[0];
            if (type.equals("beep")) {
                PacketSoundBeep packet = new PacketSoundBeep();
                if (!CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE)) {
                    System.out.println("Something went wrong!");
                }

            }
        }
        return true;
    }
}
