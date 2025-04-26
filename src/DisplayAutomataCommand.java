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
            displayAutomata(automataToBeDisplayed);
        } else {
            System.out.println("Не е намерен автомат с ID: " + id);
        }
    }

    private void displayAutomata(Automata automataToBeDisplayed) {
        for(Node node: automataToBeDisplayed.getNodes()){
            for (Transition transition: node.getTransitions()){
                checkInfoForTransition(transition);
            }
        }
    }

    private void checkInfoForTransition(Transition transition) {
        System.out.println(transition.getPreviousNode().getId() + " -> " + transition.getSymbol() + " -> " + transition.getNextNode().getId());
    }

    @Override
    public String getDescription() {
        return "Показване на автомат";
    }
} 