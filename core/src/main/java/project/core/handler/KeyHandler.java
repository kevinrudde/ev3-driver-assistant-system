package project.core.handler;

import ev3dev.sensors.Button;
import project.core.Core;

public class KeyHandler extends Thread {

    @Override
    public void run() {
        while (Core.getInstance().isRunning()) {
            if (Button.ENTER.isDown()) {
                System.exit(1);
            }
        }
    }
}
