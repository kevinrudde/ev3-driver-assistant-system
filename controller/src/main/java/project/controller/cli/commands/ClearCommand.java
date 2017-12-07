package project.controller.cli.commands;

import project.controller.Controller;
import project.controller.cli.Console;
import project.controller.cli.command.Command;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", "cl", "cls");
    }

    @Override
    public boolean execute(String[] args) {
            Console console = Controller.getInstance().getConsole();
            console.clearScreen();
        return true;
    }
}
