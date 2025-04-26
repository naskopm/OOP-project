import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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

    private void recognizeAutomata(String query, int id) {
        Automata currentAutomata = AutomataUtils.searchAutomata(id);
        if (currentAutomata == null) {
            System.out.println("Не е намерен автомат с ID: " + id);
            return;
        }

        List<Character> characters = new ArrayList<>();
        if (query.length() == 1) {
            characters.add(query.charAt(0));
        } else {
            for (int i = 0; i < query.length(); i++) {
                characters.add(query.charAt(i));
            }
        }
        boolean isRecognised = false;

        Node currentNode = currentAutomata.findInitialNode();
        if (currentNode == null) {
            System.out.println("Не е намерено начално състояние!");
            return;
        }

        for (int i = 0; i < characters.size(); i++) {
            isRecognised = false;

            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getSymbol() == characters.get(i)) {
                    isRecognised = true;
                    currentNode = transition.getNextNode();
                    break;
                }
                if (transition.getSymbol() == 'e')
                {
                    isRecognised = true;
                    currentNode = transition.getNextNode();
                    i--;
                    break;
                }
            }
            if (!isRecognised) {
                System.out.println("Автоматът не беше разпознат");
                return;
            }
            if (currentNode.isFinal()) {
                if (i == characters.size()-1 && isRecognised) {
                    System.out.println("Автоматът беше разпознат");
                    return;
                }
                if (i == characters.size()-1 && !isRecognised) {
                    System.out.println("Автоматът не беше разпознат");
                    return;
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Разпознаване на дума от автомат";
    }
} 