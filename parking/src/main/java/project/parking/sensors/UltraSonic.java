package project.parking.sensors;

import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import project.protocol.CoreBootstrap;
import project.protocol.Packet;
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

        float rightSample;
        float leftSample;
        float frontSample;
        float backSample;

        while (true) {

            Delay.msDelay(500);

            rightSample = getSample(usRight);
            leftSample = getSample(usLeft);
            frontSample = getSample(usFront);
            backSample = getSample(usBack);

            PacketUltraSonicSamples packet = new PacketUltraSonicSamples(rightSample, leftSample, frontSample, backSample);
            CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.SERVER);
        }
    }

    private float getSample(EV3UltrasonicSensor sensor) {
        provider = sensor.getDistanceMode();
        samples = new float[provider.sampleSize()];
        provider.fetchSample(samples, 0);
        return samples[0];
    }
}
