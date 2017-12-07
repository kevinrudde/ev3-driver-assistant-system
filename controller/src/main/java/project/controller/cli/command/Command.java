package project.controller.cli.command;

public abstract class Command {

    private String name;
    private String[] aliases;

    private int minArgs;
    private int maxArgs;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public boolean isAlias(String command) {
        if (command.equals(name)) return true;

        for (String alias : aliases) {
            if (alias.equals(command)) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract boolean execute(String[] args);

}
