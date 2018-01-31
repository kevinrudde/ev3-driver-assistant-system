package project.core.networking.listener;

import io.netty.channel.ChannelHandlerContext;
import lejos.utility.Delay;
import project.core.Core;
import project.core.reference.Reference;
import project.protocol.listener.PacketHandler;
import project.protocol.listener.PacketListener;
import project.protocol.packets.ev3.PacketCommandInput;
import project.protocol.packets.ev3.PacketEmergencyStop;

public class CommandInputListener extends PacketListener {

    private int currentAngle;
    private boolean forward;
    private boolean emergencyStop;

    private final boolean RESET = true; //TODO maybe corrupted

    public CommandInputListener() {
        currentAngle = 0;
    }

    @PacketHandler
    public void onCommand(ChannelHandlerContext ctx, PacketCommandInput packet) {
        float TURN_RATIO = Reference.STEERING_SPEED / 100f;
        float SPEED_RATIO = Reference.DRIVING_SPEED / 100f;

        System.out.println("Action: " + packet.getAction().toString() + " / " + packet.getExtra() + " moving: " + Core.getInstance().getSteeringMotor().isMoving());

        if (packet.getAction() == PacketCommandInput.Action.DRIVING_STOP) {
            Core.getInstance().getExecutor().execute(() -> {
                if (forward) {
                    Core.getInstance().getDrivingMotor().forward();
                } else {
                    Core.getInstance().getDrivingMotor().backward();
                }

                Core.getInstance().getDrivingMotor().stop();
            });
            return;
        }
        if (packet.getAction() == PacketCommandInput.Action.STEERING_STOP) {
            Core.getInstance().getExecutor().execute(() -> {
                Core.getInstance().getSteeringMotor().stop();

                if (RESET) {
                    int turnValue = currentAngle * -1;

                    Core.getInstance().getSteeringMotor().rotate(turnValue);
                    currentAngle = 0;
                    System.out.println("CurrentAngle: " + currentAngle);
                }
            });
            return;
        }

        if (packet.getAction() == PacketCommandInput.Action.LEFT) {
            Core.getInstance().getExecutor().execute(() -> {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;

                int turn = Math.round(turnDegree);

                if ((currentAngle - turn) < -38) {
                    turn = -38 - currentAngle;
                } else {
                    turn *= -1;
                }
                currentAngle += turn;
                System.out.println("CurrentAngle: " + currentAngle);
                Core.getInstance().getSteeringMotor().rotate(turn);
            });
        }
        if (packet.getAction() == PacketCommandInput.Action.RIGHT) {
            Core.getInstance().getExecutor().execute(() -> {
                float value = packet.getExtra() * 100f;

                float turnDegree = TURN_RATIO * value;
                int turn = Math.round(turnDegree);

                if ((currentAngle + turn) > 38) {
                    turn = 38 - currentAngle;
                }
                currentAngle += turn;
                System.out.println("CurrentAngle: " + currentAngle);
                Core.getInstance().getSteeringMotor().rotate(turn);
            });

        }
        if (packet.getAction() == PacketCommandInput.Action.FORWARD) {
            if (emergencyStop) return;

            Core.getInstance().getExecutor().execute(() -> {
                this.forward = true;
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);
                Core.getInstance().getDrivingMotor().setSpeed(speed);
                Core.getInstance().getDrivingMotor().backward();
            });
        }
        if (packet.getAction() == PacketCommandInput.Action.BACKWARDS) {
            Core.getInstance().getExecutor().execute(() -> {
                this.forward = false;
                float value = packet.getExtra() * 100f;

                float speedCalc = SPEED_RATIO * value;
                int speed = Math.round(speedCalc);

                Core.getInstance().getDrivingMotor().setSpeed(speed);
                Core.getInstance().getDrivingMotor().brake();
                Core.getInstance().getDrivingMotor().forward();
            });
        }
    }

    @PacketHandler
    public void onEmergencyStop(ChannelHandlerContext ctx, PacketEmergencyStop packet) {
        Core.getInstance().getExecutor().execute(() -> {
            emergencyStop = true;
            Core.getInstance().getDrivingMotor().stop();

            Delay.msDelay(5000);
            emergencyStop = false;
        });
    }

}
