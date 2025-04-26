import java.util.Scanner;
import java.util.HashSet;

public class DisplayAutomataCommand implements Command {
    @Override
    public void execute() {
        displayAutomata();
    }

    private void displayAutomata() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Въведете ID на автомата: ");
        int id = Integer.parseInt(scanner.nextLine());
        Automata automataToBeDisplayed = AutomataUtils.searchAutomata(id);
        if (automataToBeDisplayed != null) {
            HashSet<Integer> visited = new HashSet<>();
            recurssion(automataToBeDisplayed.findInitialNode(), automataToBeDisplayed, visited);
        } else {
            System.out.println("Не е намерен автомат с ID: " + id);
        }
    }

    private void recurssion(Node displayed, Automata automataToBeDisplayed, HashSet<Integer> visited) {
        if (displayed.getId() == 4)
        {
            System.out.printf("visited");
        }
        for (Transition transition : displayed.getTransitions()) {
            if(transition.getNextNode() != displayed && !visited.contains(displayed.getId())) {
                visited.add(displayed.getId());
                recurssion(transition.getNextNode(), automataToBeDisplayed, visited);
                checkInfoForTransition(automataToBeDisplayed, displayed.getId());
            }
        }
    }

    private void checkInfoForTransition(Automata automata, int id) {
        Node searchedNode = null;
        for(Node node : automata.getNodes()) {
            if (node.getId() == id) {
                searchedNode = node;
                break;
            }
        }
        if (searchedNode != null) {
            for (Transition transition : searchedNode.getTransitions()) {
                System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
                System.out.println();
            }
        }
    }

    @Override
    public String getDescription() {
        return "Показване на автомат";
    }
} 