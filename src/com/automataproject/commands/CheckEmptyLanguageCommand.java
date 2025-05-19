package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;

import java.util.Scanner;

/**
 * Command for checking if an automaton's language is empty.
 * This command verifies whether the automaton accepts any strings.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class CheckEmptyLanguageCommand implements Command {
    /**
     * Executes the command by checking if an automaton's language is empty.
     * Prompts the user to select an automaton, then checks if there exists
     * a path from an initial state to a final state.
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете id на автомата на който искате да проверите, дали езика е празен");
        int input = Integer.parseInt(scanner.nextLine());
        Automata foundAutomata = AutomataUtils.searchAutomata(input);
        if (foundAutomata != null) {
            System.out.println(checkIfEmptyAlphabet(foundAutomata));
        } else {
            System.out.println("Не е намерен автомат с ID: " + input);
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
        return "Проверка дали езикът на автомата е празен";
    }
} 