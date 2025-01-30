package hu.nye.progtech.connectfour.move.humanPlayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class HumanMove {

    private static final Logger logger = LoggerFactory.getLogger(HumanMove.class);
    private final Scanner scanner;

    // Konstruktor, amely a Scanner objektumot paraméterként várja
    public HumanMove(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getMove() {
        logger.info("Add meg az oszlop számát (0-tól kezdve):");
        return scanner.nextInt();
    }
}