package project.parking;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.actuators.lego.motors.Motor;
import lejos.hardware.port.MotorPort;
import project.parking.networking.Client;

public class ParkingRobot {

    private Client client;

    public static void main(final String[] args) {
        new ParkingRobot();
    }

    private ParkingRobot() {
        /*this.client = new Client();
        client.connect("127.0.0.1", 8081, "ev3");
        */
        new KeyHandler().start();

        EV3LargeRegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3MediumRegulatedMotor steering = new EV3MediumRegulatedMotor(MotorPort.A);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            motor.stop();
            steering.stop();
        }));

        steering.forward();

    }
}
