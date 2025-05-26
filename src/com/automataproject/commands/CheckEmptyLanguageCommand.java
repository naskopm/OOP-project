package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;

/**
 * Command for checking if an automaton's language is empty.
 * This command verifies whether the automaton accepts any strings.
 *
 * @author Atanas Petrov Margaritov
 * @version 1.0
 */
public class CheckEmptyLanguageCommand implements Command {
    /**
     * Executes the command by checking if an automaton's language is empty.
     * Prompts the user to select an automaton, then checks if there exists
     * a path from an initial state to a final state.
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int input = Integer.parseInt(arguments.get(0));
        Automata foundAutomata = AutomataUtils.searchAutomata(input);
        if (foundAutomata != null) {
            System.out.println(checkIfEmptyAlphabet(foundAutomata));
        } else {
            System.out.println("Automaton not found with ID: " + input);
        }
    }

    private boolean checkIfEmptyAlphabet(Automata automata) {
        Node initialNode = automata.findInitialNode();
        return initialNode != null && initialNode.checkIfEmptyAlphabet();
    }

    /**
     * Gets the description of this command.
     *
     * @return A string describing the command's purpose
     */
    @Override
    public String getDescription() {
        return "Check if an automaton's language is empty";
    }
} 