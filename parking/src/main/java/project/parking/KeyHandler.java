package project.parking;

import ev3dev.sensors.Button;

public class KeyHandler extends Thread {

    @Override
    public void run() {
        while (true) {
            if (Button.ENTER.isDown()) {
                System.exit(1);
            }
        }
    }
}