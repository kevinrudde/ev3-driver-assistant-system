package project.core.utilities;

import ev3dev.actuators.LCD;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.Color;

public class LCDWriter {

    private static GraphicsLCD lcd = LCD.getInstance();

    public static void writeMessage(String message, int x, int y, int anchor) {
        lcd.setColor(Color.BLACK);
        lcd.drawString(message, x, y, anchor);
    }

    public static void refresh() {
        lcd.refresh();
    }

    public static void clear() {
        lcd.setColor(Color.WHITE);
        lcd.fillRect(0,0, lcd.getWidth(), lcd.getHeight());
    }

}
