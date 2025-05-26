package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;
import java.util.HashSet;

public class CheckDeterministicCommand implements Command {
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int id = Integer.parseInt(arguments.get(0));
        Automata automata = Automata.searchAutomata(id);
        if (automata != null) {
            Node initialNode = automata.findInitialNode();
            if (initialNode != null) {
                automata.setDeterministic(true); // Reset determinism flag
                initialNode.recurssionForDeterminism(new HashSet<>(), automata);
                System.out.println("The automaton is " + (automata.isDeterministic() ? "" : "not ") + "deterministic");
            } else {
                System.out.println("No initial state found!");
            }
        } else {
            System.out.println("Automaton not found with ID: " + id);
        }
    }

    @Override
    public String getDescription() {
        return "Check if an automaton is deterministic";
    }
} 