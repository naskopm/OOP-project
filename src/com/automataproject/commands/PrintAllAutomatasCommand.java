package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;

public class PrintAllAutomatasCommand implements Command {
    @Override
    public void execute() {
        printAllAutomatas();
    }

    private void printAllAutomatas() {
        for (Automata automata : Automata.getAutomataList()) {
            System.out.print("Automata id: " + automata.getId() + " ");
        }
        System.out.println();
    }

    @Override
    public String getDescription() {
        return "Показване на всички автомати";
    }
} 