package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.services.AutomataUtils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CreateAutomataFromRegexCommand implements Command {
    static ArrayList<Automata> automatas = new ArrayList<Automata>();
    @Override
    public void execute() {
        System.out.println("Напишете regex симовла по който искате да създадете автомата скоби не се подържат");
        Scanner scanner = new Scanner(System.in);
        String regex = scanner.nextLine();
        createAutomata(tokenizeString(regex));
    }

    @Override
    public String getDescription() {
        return "Създаване на автомат от регулярен израз";
    }
    public static ArrayList<ArrayList<String>> tokenizeString(String query)
    {
        Boolean isRecognised = true;
        ArrayList<ArrayList<String>> tokenizers = new ArrayList<>();
        String checkedSequences [] = query.split("\\+");
        for (int i = 0; i < checkedSequences.length; i++) {
            ArrayList<String> tokenizer = new ArrayList<>();
            checkedSequences[i] = checkedSequences[i].trim();
            String querySplitted[] = checkedSequences[i].split("");
            for (int j = 0; j < querySplitted.length; j++) {
                querySplitted[j]=querySplitted[j].trim();
                tokenizer.add(querySplitted[j]);

            }
            for (int j = 0; j < tokenizer.size(); j++) {
                if (tokenizer.get(j).equals("*") && j != 0) {
                    String current = tokenizer.get(j - 1);
                    current += tokenizer.get(j);
                    tokenizer.set(j - 1, current);
                    tokenizer.remove(j);
                }
            }
            tokenizers.add(tokenizer);
        }
        return tokenizers;
    }
    private static Automata unionAutomatas(Automata first, Automata second){
        Automata firstCopy = new Automata(true);
        Automata secondCopy = new Automata(true);
        firstCopy = first.clone();
        secondCopy = second.clone();
        Node initialNode = firstCopy.findInitialNode();
        initialNode.addTransition('e', secondCopy.findInitialNode());
        firstCopy.setMaxNodeID(firstCopy.maxNodeID - 1);
        secondCopy.findInitialNode().setInitial(false);
        for(Node node:secondCopy.getNodes())
        {
            node.makeANodeID(firstCopy);
            firstCopy.getNodes().add(node);
        }
        return firstCopy;
    }
    public void createAutomata(ArrayList<ArrayList<String>> splitted){
        for(ArrayList<String> list: splitted){
            Automata automata = new Automata(true);
            Node currentNode = new Node(automata,true);
            automatas.add(automata);
            currentNode.setInitial(true);
            for (String token: list){
                if (token.contains("*")){
                    Character newToken;
                    newToken = token.charAt(0);
                    currentNode.addTransition(newToken, currentNode);
                }
                else if(token.contains("+"))
                {
                    Character newToken;
                    newToken = token.charAt(0);
                    currentNode.addTransition(newToken, currentNode);
                }
                else {
                    Node nextNode = new Node(automata, true);
                    currentNode.addTransition(token.charAt(0),nextNode);
                    nextNode.setPreviousNode(currentNode);
                    currentNode = nextNode;
                }
                if (list.indexOf(token) == list.size()-1) {
                    currentNode.setFinal(true);
                }
            }
        }
        int automataSize = automatas.size()-1;
        Automata sum = automatas.get(automataSize);
        while(automataSize !=-1){
            sum = unionAutomatas(sum,automatas.get(automataSize));
            automataSize--;
        }
        sum.makeAnID();
        Automata.automataList.add(sum);
    }

} 