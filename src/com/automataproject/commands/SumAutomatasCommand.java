package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;

public class SumAutomatasCommand implements Command {
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 2) {
            System.out.println("Please provide two automaton IDs as arguments");
            return;
        }
        
        int firstId = Integer.parseInt(arguments.get(0));
        int secondId = Integer.parseInt(arguments.get(1));
        
        Automata first = AutomataUtils.searchAutomata(firstId);
        Automata second = AutomataUtils.searchAutomata(secondId);
        
        if (first == null || second == null) {
            System.out.println("One or both automatons not found!");
            return;
        }
        
        unionAutomatas(first, second);
    }

    @Override
    public String getDescription() {
        return "Find the union of two automatons";
    }

    private static void unionAutomatas(Automata first, Automata second) {
        Automata firstCopy = new Automata(true);
        Automata secondCopy = new Automata(true);
        firstCopy = first.clone();
        secondCopy = second.clone();
        Node initialNode = firstCopy.findInitialNode();
        initialNode.addTransition('e', secondCopy.findInitialNode());
        firstCopy.setMaxNodeID(firstCopy.maxNodeID - 1);
        secondCopy.findInitialNode().setInitial(false);
        for(Node node:secondCopy.getNodes()) {
            node.makeANodeID(firstCopy);
            firstCopy.getNodes().add(node);
        }
        firstCopy.makeAnID();
        Automata.automataList.add(firstCopy);
    }
}
