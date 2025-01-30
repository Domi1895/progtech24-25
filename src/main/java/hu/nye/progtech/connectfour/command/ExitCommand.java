package hu.nye.progtech.connectfour.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitCommand implements Command {

    // Logger létrehozása
    private static final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

    @Override
    public void execute() {
        // A System.out.println helyett logback használata
        logger.info("Kilépés a játékból...");

        // A játék bezárása
        System.exit(0);
    }

    @Override
    public boolean isApplicable(String input) {
        return "exit".equalsIgnoreCase(input);
    }
}