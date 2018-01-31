package project.core.handler;

import lejos.utility.Delay;
import project.core.Core;
import project.protocol.CoreBootstrap;
import project.protocol.packets.ev3.PacketUpdateInformation;

public class VelocityHandler extends Thread {

    @Override
    public void run() {
        Delay.msDelay(50000);
        System.out.println("VelocityHandler start");

        while (true) {
            Delay.msDelay(135);

            int velocity = Core.getInstance().getDrivingMotor().getRotationSpeed();
            PacketUpdateInformation packet = new PacketUpdateInformation(velocity);
            try {
                CoreBootstrap.getClientHandler().sendPacket(packet);
            } catch (Exception e) {
            }

        }
    }

}
