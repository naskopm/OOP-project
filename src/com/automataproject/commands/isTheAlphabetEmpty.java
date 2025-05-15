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
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Въведете ID на автомата: ");
        int id = scanner.nextInt();
        checkForEmptyLanguage(id);
    }

    private void checkForEmptyLanguage(int id) {
        Automata currentAutomata = AutomataUtils.searchAutomata(id);
        if (currentAutomata == null) {
            System.out.println("Не е намерен автомат с ID: " + id);
            return;
        }

        Node initialNode = currentAutomata.findInitialNode();
        if (initialNode == null) {
            System.out.println("Не е намерено начално състояние!");
            return;
        }
        if (initialNode.getTransitions().isEmpty()){
            System.out.println("Азбуката на автомата е празна");
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
        return "Намира дали азбуката на един автомат е празна";
    }
}
