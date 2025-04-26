import java.util.Scanner;

public class CheckTransitionInfoCommand implements Command {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете ID на търсения автомат");
        int automataID = Integer.parseInt(scanner.nextLine());
        Automata found = AutomataUtils.searchAutomata(automataID);
        if (found != null) {
            System.out.println("Въведете id на състоянието, което искате да изследвате");
            int nodeID = Integer.parseInt(scanner.nextLine());
            checkInfoForTransition(found, nodeID);
        } else {
            System.out.println("Не е намерен автомат с ID: " + automataID);
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
        } else {
            System.out.println("Не е намерен възел с ID: " + id);
        }
    }

    @Override
    public String getDescription() {
        return "Търсене на преход в даден автомат";
    }
} 