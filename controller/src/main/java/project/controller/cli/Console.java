package project.controller.cli;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import project.controller.Controller;

import java.io.IOException;

public class Console extends Thread {

    private LineReader reader;

    public Console() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            this.reader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Command Line Interface active!");
        while (Controller.getInstance().isRunning()) {
            String line;
                if ((line = reader.readLine("> ")) != null) {
                    if (line.equals("")) continue;

                    Controller.getInstance().getCommandManager().parseInput(line);
                }

        }
        System.out.println("Shutting down console thread!");
    }

    public void clearScreen() {
        reader.getTerminal().puts(InfoCmp.Capability.clear_screen);
        reader.getTerminal().flush();
    }
}
