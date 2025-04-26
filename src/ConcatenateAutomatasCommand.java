import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class ConcatenateAutomatasCommand implements Command {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Въведете ID на първия автомат: ");
        int id1 = scanner.nextInt();
        System.out.print("Въведете ID на втория автомат: ");
        int id2 = scanner.nextInt();
        
        Automata first = AutomataUtils.searchAutomata(id1);
        Automata second = AutomataUtils.searchAutomata(id2);
        
        if (first != null && second != null) {
            concatenateAutomatas(first, second);
            System.out.println("Автоматите бяха конкатенирани успешно!");
        } else {
            System.out.println("Невалидни ID-та на автомати!");
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
        for (Node finalNode:finalNodes){
            finalNode.setFinal(false);
        }
        int biggestID = 0;
        for (Node node : firstAutomata.getNodes()) {
            if (node.getId() > biggestID) {
                biggestID = node.getId();
            }
        }
        int currentID = firstAutomata.getMaxNodeID();
        currentID -=1;
        secondAutomata.setMaxNodeID(currentID);
        for (int i = 0; i < secondAutomata.getNodes().size(); i++) {


            secondAutomata.getNodes().get(i).makeANodeID(secondAutomata);
            firstAutomata.getNodes().add(secondAutomata.getNodes().get(i));
        }
        for (Node finalNode : finalNodes) {
            finalNode.addTransition('e', secondAutomata.findInitialNode());
        }
        firstAutomata.makeAnID();
        Automata.getAutomataList().add(firstAutomata);
    }

    @Override
    public String getDescription() {
        return "Конкатенация на два автомата";
    }
} 