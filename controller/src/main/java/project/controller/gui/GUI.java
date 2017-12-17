package project.controller.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    @FXML Circle coreConnection;
    @FXML Circle parkConnection;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
            Scene scene = new Scene(root, 640, 440);

            primaryStage.setTitle("Driver Assistant System");
            primaryStage.setScene(scene);
            primaryStage.show();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
    }
}
