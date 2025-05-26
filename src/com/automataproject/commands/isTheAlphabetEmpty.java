package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
import com.automataproject.services.AutomataUtils;

import java.security.cert.TrustAnchor;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class isTheAlphabetEmpty implements Command {
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int id = Integer.parseInt(arguments.get(0));
        checkForEmptyLanguage(id);
    }

    private void checkForEmptyLanguage(int id) {
        Automata currentAutomata = AutomataUtils.searchAutomata(id);
        if (currentAutomata == null) {
            System.out.println("Automaton not found with ID: " + id);
            return;
        }

        Node initialNode = currentAutomata.findInitialNode();
        if (initialNode == null) {
            System.out.println("No initial state found!");
            return;
        }
        if (initialNode.getTransitions().isEmpty()) {
            System.out.println("The automaton's alphabet is empty");
            return;
        }
        // Use a stack to track current state and position in word
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            // Process all transitions
            for (Transition transition : current.getTransitions()) {
                if (transition.getSymbol() == 'e') {
                    // Epsilon transition - don't consume any input
                    stack.push(transition.getNextNode());
                } else if (transition.getSymbol()!= 'e') {
                    System.out.println("Азбуката на автомата не е празна");
                    return;
                }
            }
        }
        System.out.println("Азбуката на автомата е празна");
    }

    @Override
    public String getDescription() {
        return "Check if the automaton's alphabet is empty";
    }
}
