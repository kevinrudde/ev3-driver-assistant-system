package project.core.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import lejos.utility.Delay;
import project.core.Core;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketCommandInput;

public class CommandInputListener extends PacketListener {

    private static final float TURN_RATIO = 38f / 100f;
    private static final float SPEED_RATIO = 740f / 100f;

    @PacketHandler
    public void onCommand(ChannelHandlerContext ctx, PacketCommandInput packet) {
        System.out.println("Action: " + packet.getAction().toString() + " / " + packet.getExtra() + " moving: " + Core.getInstance().getSteeringMotor().isMoving());

        if (!Core.getInstance().getSteeringMotor().isMoving()) {
            if (packet.getAction() == PacketCommandInput.Action.LEFT) {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;

                System.out.println(turnDegree);

                int turn = Math.round(turnDegree) * (-1);

                System.out.println(turn + " degree");
                System.out.println();

                Core.getInstance().getExecutor().execute(() -> Core.getInstance().getSteeringMotor().rotate(turn));
            }
            if (packet.getAction() == PacketCommandInput.Action.RIGHT) {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;
                int turn = Math.round(turnDegree);

                System.out.println(turn + " degree");
                System.out.println();

                Core.getInstance().getExecutor().execute(() -> Core.getInstance().getSteeringMotor().rotate(turn));
            }
        }
        if (!Core.getInstance().getSteeringMotor().isMoving()) { //TODO: Steering to Driving
            if (packet.getAction() == PacketCommandInput.Action.FORWARD) {
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);

                Core.getInstance().getExecutor().execute(() -> {
                    Core.getInstance().getSteeringMotor().setSpeed(speed);
                    Core.getInstance().getSteeringMotor().forward();
                    Delay.msDelay(20);
                    Core.getInstance().getSteeringMotor().stop();
                });
            }
            if (packet.getAction() == PacketCommandInput.Action.BACKWARDS) {
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);

                Core.getInstance().getExecutor().execute(() -> {
                    Core.getInstance().getSteeringMotor().setSpeed(speed);
                    Core.getInstance().getSteeringMotor().backward();
                    Delay.msDelay(20);
                    Core.getInstance().getSteeringMotor().stop();
                });
            }
        }
    }

}
