package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
import com.automataproject.services.AutomataUtils;

import java.util.Scanner;

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
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете ID на търсения автомат");
        int automataID = Integer.parseInt(scanner.nextLine());
        Automata found = AutomataUtils.searchAutomata(automataID);
        if (found != null) {
            System.out.println("Въведете id на състоянието, което искате да изследвате");
            int nodeID = Integer.parseInt(scanner.nextLine());
            checkInfoForTransition(found, nodeID);
        } else {
            System.out.println("Не е намерен автомат с ID: " + automataID);
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
            System.out.println("Не е намерен възел с ID: " + id);
        }
    }

    /**
     * Gets the description of this command.
     *
     * @return A string describing the command's purpose
     */
    @Override
    public String getDescription() {
        return "Търсене на преход в даден автомат";
    }
} 