package com.automataproject.commands;

import java.util.ArrayList;

/**
 * Terminates the program
 */
public class ExitCommand implements Command {
    /**
    * Terminates the program
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        System.out.println("Exiting program...");
    }

    @Override
    public String getDescription() {
        return "Exit";
    }
} 