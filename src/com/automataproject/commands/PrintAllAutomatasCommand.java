package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;

/**
 * Prints out the IDs of the automatas that are currently loaded
 */
public class PrintAllAutomatasCommand implements Command {
    /**
     * Prints out the IDs of the automatas that are currently loaded
     */
    @Override
    public void execute(ArrayList<String> arguments) {
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