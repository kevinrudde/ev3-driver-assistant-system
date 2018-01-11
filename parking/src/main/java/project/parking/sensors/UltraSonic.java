package project.parking.sensors;

import ev3dev.actuators.Sound;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import project.protocol.CoreBootstrap;
import project.protocol.Packet;
import project.protocol.packets.ev3.PacketEmergencyStop;
import project.protocol.packets.ev3.PacketUltraSonicSamples;
import project.protocol.packets.general.PacketLogin;

public class UltraSonic extends Thread {

    private EV3UltrasonicSensor usRight = new EV3UltrasonicSensor(SensorPort.S1);
    private EV3UltrasonicSensor usLeft = new EV3UltrasonicSensor(SensorPort.S2);
    private EV3UltrasonicSensor usFront = new EV3UltrasonicSensor(SensorPort.S3);
    private EV3UltrasonicSensor usBack = new EV3UltrasonicSensor(SensorPort.S4);

    private SampleProvider provider;
    private float[] samples;

    @Override
    public void run() {

        Delay.msDelay(20000);
        System.out.println("UltraSonic start");

        float rightSample;
        float leftSample;
        float frontSample;
        float backSample;

        while (true) {

            Delay.msDelay(150);

            if (CoreBootstrap.getClientHandler() != null) {

                rightSample = getSample(usRight);
                leftSample = getSample(usLeft);
                frontSample = getSample(usFront);
                backSample = getSample(usBack);

                if (frontSample < 12) {
                    Sound.getInstance().beep();
                }
                if (frontSample < 10) {
                    Sound.getInstance().twoBeeps();
                }
                if (frontSample < 5) {
                    PacketEmergencyStop packet = new PacketEmergencyStop();
                    CoreBootstrap.getClientHandler().sendPacket(packet);
                }

                PacketUltraSonicSamples packet = new PacketUltraSonicSamples(rightSample, leftSample, frontSample, backSample);
                CoreBootstrap.getClientHandler().sendPacket(packet);
            }

        }
    }

    private float getSample(EV3UltrasonicSensor sensor) {
        provider = sensor.getDistanceMode();
        samples = new float[provider.sampleSize()];
        provider.fetchSample(samples, 0);
        return samples[0];
    }
}
