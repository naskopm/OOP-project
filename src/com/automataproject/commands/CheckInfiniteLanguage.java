package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
// No AutomataUtils needed here based on current code

import java.util.*;

/**
 * The command checks if an automata has infinite language
 */
public class CheckInfiniteLanguage implements Command{
    Boolean isTheLanguageFinite = true;
    /**
     * Cheks if an automata has an empty language
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int id = Integer.parseInt(arguments.get(0));
        Automata automata = Automata.searchAutomata(id);
        if (automata == null) {
            System.out.println("Automaton not found with ID: " + id);
            return;
        }
        
        isTheLanguageFinite = true;
        dfsExtract(automata.findInitialNode(), automata);
        if (isTheLanguageFinite) {
            System.out.println("The automaton has a finite language");
        } else {
            System.out.println("The automaton has an infinite language");
        }
    }

    @Override
    public String getDescription(){
        return "Check if the automaton has a finite or infinite language";
    }


    private void dfsExtract(Node startNode,  Automata source) {
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(startNode);
        visited.add(startNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();

            for (Transition t : currentNode.getTransitions()) {
                Node nextNode = t.getNextNode();
                if (nextNode != null) {
                    if (visited.contains(nextNode)) {
                        // Cycle detected, handle accordingly
                        isTheLanguageFinite = false;
                        return;
                    } else {
                        stack.push(nextNode);
                        visited.add(nextNode);
                    }
                }
            }
        }
    }


}
