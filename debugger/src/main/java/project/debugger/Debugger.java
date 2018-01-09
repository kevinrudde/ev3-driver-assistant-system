package project.debugger;

import project.debugger.networking.Client;
import project.protocol.CoreBootstrap;
import project.protocol.packets.ev3.PacketUltraSonicSamples;
import project.protocol.packets.general.PacketLogin;

/*
 * Copyright (C) 2017 Vindalia - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Vindalia <development@vindalia.net>, 2017
 */
public class Debugger {

    private Client client;

    public static void main(String[] args) {
        new Debugger();
    }

    private Debugger() {
        this.client = new Client();
        client.initialize();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PacketUltraSonicSamples packet = new PacketUltraSonicSamples(10f, 2f, 8f, 9f);
        System.out.println(CoreBootstrap.getClientHandler() == null);
        CoreBootstrap.getClientHandler().sendPacket(packet);
    }

}
