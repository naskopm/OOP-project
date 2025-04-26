import java.security.cert.TrustAnchor;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class RecognizeAutomataCommand implements Command {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Въведете ID на автомата: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Въведете дума за разпознаване: ");
        String word = scanner.nextLine();
        recognizeAutomata(word, id);
    }

    private void recognizeAutomata(String word, int id) {
        Automata currentAutomata = AutomataUtils.searchAutomata(id);
        if (currentAutomata == null) {
            System.out.println("Не е намерен автомат с ID: " + id);
            return;
        }

        Node initialNode = currentAutomata.findInitialNode();
        if (initialNode == null) {
            System.out.println("Не е намерено начално състояние!");
            return;
        }

        // Use a stack to track current state and position in word
        Stack<StatePosition> stack = new Stack<>();
        stack.push(new StatePosition(initialNode, 0));

        while (!stack.isEmpty()) {
            StatePosition current = stack.pop();
            Node currentNode = current.node;
            int currentPosition = current.position;

            // If we've processed all characters and are in a final state
            if (currentPosition == word.length() && currentNode.isFinal()) {
                System.out.println("Изразът е разпознат");
                return;
            }

            // Process all transitions
            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getSymbol() == 'e') {
                    // Epsilon transition - don't consume any input
                    stack.push(new StatePosition(transition.getNextNode(), currentPosition));
                } else if (currentPosition < word.length() && transition.getSymbol() == word.charAt(currentPosition)) {
                    // Regular transition - consume one character
                    stack.push(new StatePosition(transition.getNextNode(), currentPosition + 1));
                }
            }
        }

        System.out.println("Изразът не беше разпознат");
    }

    // Helper class to track state and position in word
    private static class StatePosition {
        Node node;
        int position;

        StatePosition(Node node, int position) {
            this.node = node;
            this.position = position;
        }
    }

    @Override
    public String getDescription() {
        return "Разпознаване на дума от автомат";
    }
} 