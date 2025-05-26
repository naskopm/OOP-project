package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
import java.util.ArrayList;

/**
 * Command for displaying detailed information about a specific automaton.
 * This command shows the structure of the automaton including all nodes and transitions.
 *
 * @author Atanas Petrov Margaritov
 * @version 1.0
 */
public class DisplayAutomataCommand implements Command {
    /**
     * Executes the command by displaying detailed information about an automaton.
     * Prompts the user to select an automaton, then shows its complete structure
     * including nodes, their properties, and all transitions.
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int automatonId = Integer.parseInt(arguments.get(0));
        Automata automata = Automata.searchAutomata(automatonId);
        
        if (automata == null) {
            System.out.println("Automaton not found!");
            return;
        }

        System.out.println("\nAutomaton ID: " + automata.getId());
        System.out.println("Is deterministic: " + automata.isDeterministic());
        System.out.println("\nNodes:");
        
        for (Node node : automata.getNodes()) {
            System.out.println("\nNode ID: " + node.getId());
            System.out.println("Is initial: " + node.isInitial());
            System.out.println("Is final: " + node.isFinal());
            System.out.println("Transitions:");
            
            for (Transition transition : node.getTransitions()) {
                System.out.println("  " + node.getId() + " --" + transition.getSymbol() + "--> " + 
                                 transition.getNextNode().getId());
            }
        }
    }

    /**
     * Gets the description of this command.
     *
     * @return A string describing the command's purpose
     */
    @Override
    public String getDescription() {
        return "Display detailed information about a specific automaton";
    }
} 