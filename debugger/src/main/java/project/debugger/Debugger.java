package project.debugger;

import project.debugger.networking.Client;

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
    }

}
