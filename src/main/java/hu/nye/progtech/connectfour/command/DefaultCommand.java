package hu.nye.progtech.connectfour.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCommand implements Command {

    // Logger létrehozása
    private static final Logger logger = LoggerFactory.getLogger(DefaultCommand.class);

    @Override
    public void execute() {
        // A System.out.println helyett logback használata
        logger.info("Ismeretlen parancs. Az elérhető parancsok: save, load, exit.");
    }

    @Override
    public boolean isApplicable(String input) {
        // Ez a parancs akkor alkalmazható, ha egyik másik sem illeszkedik.
        return true; // Ez mindig "tartalékként" működik.
    }
}