package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;
/**
 * Concatenates two automatas
 */

public class ConcatenateAutomatasCommand implements Command {
    /**
     * Finds the concatenation of two automatas and saves it into a new automata
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        if (arguments.size() < 2) {
            System.out.println("Please provide two automaton IDs as arguments");
            return;
        }
        
        int id1 = Integer.parseInt(arguments.get(0));
        int id2 = Integer.parseInt(arguments.get(1));
        
        Automata first = AutomataUtils.searchAutomata(id1);
        Automata second = AutomataUtils.searchAutomata(id2);
        
        if (first != null && second != null) {
            concatenateAutomatas(first, second);
            System.out.println("Automatons were successfully concatenated!");
        } else {
            System.out.println("Invalid automaton IDs!");
        }
    }

    private void concatenateAutomatas(Automata first, Automata second) {
        Automata firstAutomata = new Automata(true);
        Automata secondAutomata = new Automata(true);
        secondAutomata = (Automata)second.clone();
        firstAutomata = (Automata)first.clone();
        
        ArrayList<Node> finalNodes = new ArrayList<>();
        for (Node node : firstAutomata.getNodes()) {
            if (node.isFinal()) {
                finalNodes.add(node);
            }
        }
        
        Node initialNode = secondAutomata.findInitialNode();
        if (initialNode != null) {
            for (Node finalNode : finalNodes) {
                finalNode.addTransition('e', initialNode);
                finalNode.setFinal(false);
            }
            initialNode.setInitial(false);
            
            for(Node node : secondAutomata.getNodes()) {
                node.makeANodeID(firstAutomata);
                firstAutomata.getNodes().add(node);
            }
            firstAutomata.makeAnID();
            Automata.automataList.add(firstAutomata);
        }
    }

    @Override
    public String getDescription() {
        return "Concatenate two automatons";
    }
} 