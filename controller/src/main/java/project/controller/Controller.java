package project.controller;

import com.google.gson.Gson;
import javafx.application.Application;
import lombok.Getter;
import lombok.Setter;
import project.controller.cli.Console;
import project.controller.cli.command.CommandManager;
import project.controller.gui.GUI;
import project.controller.input.ControllerHandler;
import project.controller.networking.Server;
import project.protocol.utility.Utility;

import javax.swing.*;

@Getter
@Setter
public class Controller {

    @Getter
    private static Controller instance;

    private boolean running;
    private Gson gson;

    private Server server;
    private Console console;
    private ControllerHandler controllerHandler;

    private CommandManager commandManager;

    private String password = Utility.convertToMd5("ev3");

    private Controller() {
        instance = this;

        init();
    }

    public static void main(String[] args) {
        new Controller();
    }

    private void init() {
        this.running = true;

        this.commandManager = new CommandManager();
        this.server = new Server();
        server.initialize();

        this.console = new Console();
        console.setName("Console");
        console.start();

        this.controllerHandler = new ControllerHandler();
        controllerHandler.setName("ControllerHandler");
        controllerHandler.start();

        Application.launch(GUI.class);
    }
}
