package com.automataproject.commands;

import java.util.ArrayList;

public class ExitCommand implements Command {
    @Override
    public void execute(ArrayList<String> arguments) {
        System.out.println("Exiting program...");
    }

    @Override
    public String getDescription() {
        return "Exit";
    }
} 