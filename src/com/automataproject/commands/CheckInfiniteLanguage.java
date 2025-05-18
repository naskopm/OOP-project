package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;
// No AutomataUtils needed here based on current code

import java.util.*;

public class CheckInfiniteLanguage implements Command{
    Boolean isTheLanguageFinite = true;
    @Override
    public void execute(){

        System.out.println("Въведете ID на автомата на който искате да намерите цикли");
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        LinkedHashSet<Integer> stack = new LinkedHashSet<>();
        isTheLanguageFinite = true;
        dfsExtract(Automata.searchAutomata(a).findInitialNode(),Automata.searchAutomata(a));
        if(isTheLanguageFinite){
            System.out.println("Автомата има краен език");
        }
        else{
            System.out.println("Автомата има безкраен език");

        }
    }
    @Override
    public String getDescription(){
        return "Проверява дали автомата има крайна или безкрайна азбука";
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
