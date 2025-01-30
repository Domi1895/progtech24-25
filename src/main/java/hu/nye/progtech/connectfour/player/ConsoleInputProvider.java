package hu.nye.progtech.connectfour.player;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner;

    // Konstruktor módosítása, hogy a Scanner paraméterként legyen átadva
    public ConsoleInputProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput() {
        return scanner.nextLine(); // Beolvassa a teljes sort
    }
}