package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;

/**
 * Command for checking transition information in an automaton.
 * This command displays all transitions for a selected node in the automaton.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class CheckTransitionInfoCommand implements Command {
    /**
     * Executes the command by displaying transition information.
     * Prompts the user to select an automaton and node, then shows all transitions
     * from that node.
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 2) {
            System.out.println("Please provide automaton ID and node ID as arguments");
            return;
        }
        
        int automataID = Integer.parseInt(arguments.get(0));
        int nodeID = Integer.parseInt(arguments.get(1));
        
        Automata found = AutomataUtils.searchAutomata(automataID);
        if (found != null) {
            checkInfoForTransition(found, nodeID);
        } else {
            System.out.println("Automaton not found with ID: " + automataID);
        }
    }

    private void checkInfoForTransition(Automata automata, int id) {
        Node searchedNode = null;
        for(Node node : automata.getNodes()) {
            if (node.getId() == id) {
                searchedNode = node;
                break;
            }
        }
        if (searchedNode != null) {
            for (Transition transition : searchedNode.getTransitions()) {
                System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
                System.out.println();
            }
        } else {
            System.out.println("Node not found with ID: " + id);
        }
    }

    /**
     * Gets the description of this command.
     *
     * @return A string describing the command's purpose
     */
    @Override
    public String getDescription() {
        return "Search for transitions in a given automaton";
    }
} 