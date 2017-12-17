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

        if (packet.getAction() == PacketCommandInput.Action.DRIVING_STOP) {
            Core.getInstance().getDrivingMotor().stop();
            return;
        }
        if (packet.getAction() == PacketCommandInput.Action.STEERING_STOP) {
            Core.getInstance().getSteeringMotor().stop();
            return;
        }

        if (!Core.getInstance().getSteeringMotor().isMoving()) {
            if (packet.getAction() == PacketCommandInput.Action.LEFT) {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;

                int turn = Math.round(turnDegree) * (-1);

                float position = Core.getInstance().getSteeringMotor().getPosition();
                System.out.println("Pos:" + position + " / Turn: " + turn);

                Core.getInstance().getExecutor().execute(() -> Core.getInstance().getSteeringMotor().rotate(turn));
            }
            if (packet.getAction() == PacketCommandInput.Action.RIGHT) {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;
                int turn = Math.round(turnDegree);

                float position = Core.getInstance().getSteeringMotor().getPosition();
                System.out.println("Pos:" + position + " / Turn: " + turn);

                Core.getInstance().getExecutor().execute(() -> Core.getInstance().getSteeringMotor().rotate(turn));
            }
        }
        if (!Core.getInstance().getDrivingMotor().isMoving()) { //TODO: Steering to Driving
            if (packet.getAction() == PacketCommandInput.Action.FORWARD) {
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);

                Core.getInstance().getExecutor().execute(() -> {
                    Core.getInstance().getDrivingMotor().setSpeed(speed);
                    Core.getInstance().getDrivingMotor().forward();
                });
            }
            if (packet.getAction() == PacketCommandInput.Action.BACKWARDS) {
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);

                Core.getInstance().getExecutor().execute(() -> {
                    Core.getInstance().getDrivingMotor().setSpeed(speed);
                    Core.getInstance().getDrivingMotor().backward();
                });
            }
        }
    }

}
