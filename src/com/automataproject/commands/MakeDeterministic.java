package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
// No AutomataUtils needed based on current code, but good to be aware if it changes

import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
/**
 * Takes an automata and makes it deterministic
 */
public class MakeDeterministic implements Command{
    /**
     * Takes an automata and makes it deterministic
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }
        
        int id = Integer.parseInt(arguments.get(0));
        Automata toBeMadeDeterministic = Automata.searchAutomata(id);
        if (toBeMadeDeterministic == null) {
            System.out.println("Automaton not found with ID: " + id);
            return;
        }
        
        HashSet<Node> visitedNodes = new HashSet<Node>();
        recurssionHandleNode(toBeMadeDeterministic, visitedNodes);
    }

    private void recurssionHandleNode(Automata automata, HashSet<Node> visitedNodes) {
        automata.setDeterministic(true);
        Node intialNodeFirst = automata.findInitialNode();
        HashSet<Integer> initialCheck = new HashSet<Integer>();
        intialNodeFirst.recurssionForDeterminism(initialCheck, automata);
        Stack<Node> stack = new Stack<Node>();
        stack.push(automata.findInitialNode());
        do {
            automata.setDeterministic(true);
            Node intialNode = automata.findInitialNode();
            HashSet<Integer> checkDeterminism = new HashSet<Integer>();
            intialNode.recurssionForDeterminism(checkDeterminism, automata);
            Node currentNode = (Node) stack.pop();

            visitedNodes.add(automata.findInitialNode());
            HashSet<Character> visited = new HashSet<Character>();
            Character repeatedChar = '~';
            ArrayList<Transition> combinedTransitions = new ArrayList<Transition>();
            for (Transition transition : currentNode.getTransitions()) {
                if (visited.contains(transition.getSymbol()) && (repeatedChar == transition.getSymbol() || repeatedChar == '~')) {
                    repeatedChar = transition.getSymbol();
                }
                visited.add(transition.getSymbol());
            }
            if (repeatedChar != '~') {
                for (Transition transition: currentNode.getTransitions()) {
                    if (transition.getSymbol() == repeatedChar) {
                        combinedTransitions.add(transition);
                    }
                }
                Node combined = new Node(automata, true);
                combined.makeANodeID(automata);
                for (Transition transition : combinedTransitions) {
                    combined.addTransition(transition.getSymbol(), transition.getNextNode());
                }

                currentNode.getTransitions().clear();
                currentNode.addTransition(repeatedChar, combined);
                stack.push(combined);
                visitedNodes.add(currentNode);
                continue;
            }
            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getSymbol() == 'e' && currentNode.getTransitions().size() > 1) {

                }
            }
            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getNextNode() != null && !visitedNodes.contains(transition.getNextNode())) {
                    stack.push(transition.getNextNode());
                    visitedNodes.add(transition.getNextNode());
                }
            }

        } while (!stack.isEmpty());
    }
        @Override
        public String getDescription () {
            return "Детерминира даден автомат";
        }

}
