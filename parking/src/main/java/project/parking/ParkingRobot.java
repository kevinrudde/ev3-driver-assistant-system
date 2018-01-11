package project.parking;

import lejos.utility.Delay;
import project.parking.networking.Client;
import project.parking.sensors.UltraSonic;

public class ParkingRobot {

    private KeyHandler keyHandler;
    private UltraSonic ultraSonic;
    private Client client;

    public static void main(final String[] args) {
        new ParkingRobot();
    }

    private ParkingRobot() {
        this.keyHandler = new KeyHandler();
        keyHandler.setName("KeyHandler");
        keyHandler.start();

        this.client = new Client();

        this.ultraSonic = new UltraSonic();
        ultraSonic.setName("UltraSonic");
        ultraSonic.start();
    }
}
