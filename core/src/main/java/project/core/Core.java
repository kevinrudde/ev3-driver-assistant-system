package project.core;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lombok.Getter;
import project.core.handler.KeyHandler;
import project.core.networking.Client;
import project.core.utilities.LCDWriter;
import project.core.utilities.SteeringPilot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class Core {

    @Getter
    private static Core instance;

    /* Handlers */
    private Client client;
    private KeyHandler keyHandler;

    @Getter
    private ExecutorService executor;

    @Getter
    private EV3MediumRegulatedMotor steeringMotor;

    @Getter
    private EV3MediumRegulatedMotor drivingMotor;

    private boolean running;

    public static void main(final String[] args) {
        new Core();
    }

    public Core() {
        instance = this;
        this.running = true;

        this.client = new Client();
        client.initialize();

        this.keyHandler = new KeyHandler();
        keyHandler.setName("KeyHandler");
        keyHandler.start();

        this.executor = Executors.newCachedThreadPool();

        this.steeringMotor = new EV3MediumRegulatedMotor(MotorPort.A);
        steeringMotor.setSpeed(25);
        this.drivingMotor = new EV3MediumRegulatedMotor(MotorPort.B);
        drivingMotor.setSpeed(200);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.steeringMotor.stop();
            this.drivingMotor.stop();
        }));

        while (running) {
            LCDWriter.writeMessage("Driver Assistance System", 32, 10, 0);
            LCDWriter.writeMessage("Press ENTER for exit", 10, 34, 0);
            LCDWriter.writeMessage("by Kevin, Uwe, Jan,", 10, 110, 0);
            LCDWriter.writeMessage("Malte, Marvin", 10, 122, 0);
            LCDWriter.refresh();
            Delay.msDelay(5000);
        }
    }
}
