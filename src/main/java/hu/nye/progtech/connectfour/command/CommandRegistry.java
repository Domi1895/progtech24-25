package hu.nye.progtech.connectfour.command;

import java.util.List;

public class CommandRegistry {

    private final List<Command> commands;
    private final Command defaultCommand;

    public CommandRegistry(List<Command> commands) {
        this.commands = commands;
        this.defaultCommand = new DefaultCommand(); // DefaultCommand inicializálása.
    }

    public boolean processCommand(String input) {
        for (Command command : commands) {
            if (command.isApplicable(input)) {
                command.execute();
                return true; // Egy ismert parancsot végrehajtottunk.
            }
        }
        // Ha egyik ismert parancs sem illeszkedik, hívjuk meg a DefaultCommand-et.
        defaultCommand.execute();
        return false; // Ismeretlen parancsot kezeltünk.
    }
}