package project.controller.input;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import project.controller.Controller;
import project.protocol.CoreBootstrap;
import project.protocol.Packet;
import project.protocol.packets.ev3.PacketCommandInput;
import project.protocol.packets.ev3.PacketSoundBeep;
import project.protocol.packets.general.PacketLogin;

public class ControllerHandler extends Thread {

    private ControllerManager controllers;

    public ControllerHandler() {
        this.controllers = new ControllerManager();
        controllers.initSDLGamepad();
    }

    @Override
    public void run() {
        while (Controller.getInstance().isRunning()) {
            ControllerState state = controllers.getState(0);
            Packet packet;

            if (!state.isConnected) {
                continue;
            }

            if (state.rightTrigger >= 0.2) {
                packet = new PacketCommandInput(PacketCommandInput.Action.FORWARD, state.rightTrigger);
                CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
            }

            if (state.leftTrigger >= 0.2) {
                packet = new PacketCommandInput(PacketCommandInput.Action.BACKWARDS, state.leftTrigger);
                CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
            }

            if (state.leftStickX >= 0.35) {
                packet = new PacketCommandInput(PacketCommandInput.Action.RIGHT, state.leftStickX);
                CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
            }

            if (state.leftStickX <= -0.35) {
                float extra = state.leftStickX * (-1);
                if (extra > 1) extra = 1;

                packet = new PacketCommandInput(PacketCommandInput.Action.LEFT, extra);
                CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
            }

            if (state.rb) {
                packet = new PacketSoundBeep();
                CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
