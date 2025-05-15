package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
// No AutomataUtils needed here based on current code

import java.sql.SQLOutput;
import java.util.*;

public class CheckInfiniteLanguage implements Command{
    Boolean isTheLanguageFinite = true;
    @Override
    public void execute(){
        System.out.println("Въведете ID на автомата на който искате да намерите цикли");
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        findingLoops(Automata.searchAutomata(a));
    }
    @Override
    public String getDescription(){
        return "Проверява дали автомата има крайна или безкрайна азбука";
    }
    void removeLeavesRecurssion(Automata nodesContainer, Node currentNode,Set<Integer> visited) {
        if (visited.contains(currentNode.getId())) {
            if (currentNode.getTransitions().isEmpty()) {
                Node.removeNode(currentNode, nodesContainer);
            }
            return;
        }
        visited.add(currentNode.getId());
        ArrayList<Transition> snapshot = new ArrayList<>(currentNode.getTransitions());
        for(Transition transition: snapshot) {
            if (transition.getNextNode() != null) {
                removeLeavesRecurssion(nodesContainer,transition.getNextNode(),visited);
            }
        }
        if (currentNode.getTransitions().isEmpty()){
            Node.removeNode(currentNode,nodesContainer);
        }

    }
    public void findingLoopsRecursion(Node currentNode,Set<Integer> visited,Automata originalAutomata) {
        if (visited.contains(currentNode.getId())) {
            Automata createdAutomata = new Automata(true);
            createdAutomata.makeAnID();
            int searchedID = currentNode.getId();
            ArrayList<Integer> ids = new ArrayList<>(visited);
            for (int i = 0; i < currentNode.getTransitions().size(); i++) {
                if(visited.contains(currentNode.getId()))
                {
                    int searchedId = 0;
                    ArrayList<Node> visitedNodes = new ArrayList<>();
                    for (int j = 0; j < ids.size(); j++) {
                        if (currentNode.getId() == ids.get(j)){
                            searchedId = j;
                        }
                    }
                    for (int j = searchedId; j <visited.size() ; j++) {
                        if (!createdAutomata.getNodes().contains(originalAutomata.searchNode(ids.get(j))))
                            createdAutomata.addNode(originalAutomata.searchNode(ids.get(j)));
                    }
                }
            }
            isTheLanguageFinite = false;
            return;
        }
        visited.add(currentNode.getId());

        for (int i = 0; i < currentNode.getTransitions().size(); i++) {
            if (currentNode.getTransitions().get(i).getNextNode() != null){
                findingLoopsRecursion(currentNode.getTransitions().get(i).getNextNode(),visited,originalAutomata);
            }
        }
    }
    private void findingLoops(Automata automata)
    {
        Automata copy = automata.clone();
        Node initialNode = copy.findInitialNode();
        if (initialNode != null) {
            LinkedHashSet<Integer> visitedFirstRecurssion = new LinkedHashSet<>();
            findingLoopsRecursion(initialNode,visitedFirstRecurssion,copy);
            if(isTheLanguageFinite)
            {
                System.out.println("Езика на автомата е краен");
            }
            else {
                System.out.println("Езика на автомата е безкраен");
            }
        }
    }

}
