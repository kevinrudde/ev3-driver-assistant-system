package project.controller.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import project.protocol.CoreBootstrap;
import project.protocol.packets.general.PacketDebugReload;
import project.protocol.packets.general.PacketLogin;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    @FXML
    Circle coreConnection;
    @FXML
    Circle parkConnection;
    @FXML
    Label speedLabel;
    @FXML
    TextField drivingSpeed;
    @FXML
    TextField steeringSpeed;
    @FXML
    CheckBox convert;

    @Getter
    private static GUI instance;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));

            Parent root = loader.load(); //FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
            Scene scene = new Scene(root, 640, 440);

            primaryStage.setTitle("Driver Assistant System");
            primaryStage.setScene(scene);
            primaryStage.show();

            instance = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        speedLabel.setText("5 m/s");
    }

    @FXML
    public void onConvert() {
        updateInformation(Double.parseDouble(this.speedLabel.getText().replace(" km/h", "").replace(" m/s", "")));
    }

    @FXML
    public void onReload() {
        int drivingSpeed = 0;
        int steeringSpeed = 0;

        try {
            drivingSpeed = Integer.parseInt(this.drivingSpeed.getText());
        } catch (NumberFormatException ex) {
            this.drivingSpeed.setText("Nur Zahlen!");
        }
        try {
            steeringSpeed = Integer.parseInt(this.steeringSpeed.getText());
        } catch (NumberFormatException ex) {
            this.drivingSpeed.setText("Nur Zahlen!");
        }

        if (drivingSpeed != 0 && steeringSpeed != 0) {
            PacketDebugReload packet = new PacketDebugReload(drivingSpeed, steeringSpeed);
            CoreBootstrap.sendPacket(packet, PacketLogin.ClientType.CORE);
        }
    }

    public void updateInformation(double speed) {
        if (convert.isSelected()) {
            speed *= 3.6;
            this.speedLabel.setText(speed + " km/h");
        } else {
            speed /= 3.6;
            this.speedLabel.setText(speed + " m/s");
        }
    }

    public void connected(PacketLogin.ClientType clientType) {
        switch (clientType) {
            case PARKING:
                this.parkConnection.setFill(Color.GREEN);
                break;
            case CORE:
                this.coreConnection.setFill(Color.GREEN);
                break;
            case SERVER:
                break;
        }
    }

    public void disconnected(PacketLogin.ClientType clientType) {
        switch (clientType) {
            case PARKING:
                this.parkConnection.setFill(Color.RED);
                break;
            case CORE:
                this.coreConnection.setFill(Color.RED);
                break;
            case SERVER:
                break;
        }
    }
}
