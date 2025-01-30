package hu.nye.progtech.connectfour.command;

public interface Command {

    void execute();

    boolean isApplicable(String input);
}