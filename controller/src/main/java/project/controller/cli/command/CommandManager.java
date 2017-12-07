package project.controller.cli.command;

import lombok.Getter;
import project.controller.cli.commands.ClearCommand;
import project.controller.cli.commands.SendCommand;
import project.controller.cli.commands.ShutdownCommand;

import java.util.*;

@Getter
public class CommandManager {

    private Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();

        registerCommand(
                new ClearCommand(),
                new SendCommand(),
                new ShutdownCommand()
        );
    }

    public void registerCommand(Command... commands) {
        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }
    }

    public void parseInput(String input) {
        boolean success = false;

        String[] args = input.split(" ");
        String commandName = args[0];

        List<String> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);
        argsList.remove(0);
        args = argsList.toArray(new String[argsList.size()]);

        for (Command command : commands.values()) {
            if (command.isAlias(commandName)) {
                command.execute(args);
                success = true;
            }
        }

        if (!success) {
            System.out.println("Unknown command! Type 'help' for a overview of commands!");
        }
    }
}
