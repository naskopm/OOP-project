package com.automataproject.commands;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Излизане от програмата...");
    }

    @Override
    public String getDescription() {
        return "Изход";
    }
} 