import java.util.Scanner;

public class CheckEmptyLanguageCommand implements Command {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете id на автомата на който искате да проверите, дали езика е празен");
        int input = Integer.parseInt(scanner.nextLine());
        Automata foundAutomata = AutomataUtils.searchAutomata(input);
        if (foundAutomata != null) {
            System.out.println(checkIfEmptyAlphabet(foundAutomata));
        } else {
            System.out.println("Не е намерен автомат с ID: " + input);
        }
    }

    private boolean checkIfEmptyAlphabet(Automata automata) {
        Node initialNode = automata.findInitialNode();
        return initialNode != null && initialNode.checkIfEmptyAlphabet();
    }

    @Override
    public String getDescription() {
        return "Проверка дали езикът на автомата е празен";
    }
} 