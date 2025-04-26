import java.util.Scanner;
import java.util.HashSet;

public class CheckDeterministicCommand implements Command {
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Въведете ID на автомата: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Automata automata = Automata.searchAutomata(id);
        if (automata != null) {
            Node initialNode = automata.findInitialNode();
            if (initialNode != null) {
                Automata.setDeterministic(true); // Reset determinism flag
                initialNode.recurssionForDeterminism(new HashSet<>());
                System.out.println("Автоматът " + (Automata.isDeterministic() ? "е" : "не е") + " детерминиран");
            } else {
                System.out.println("Не е намерено начално състояние!");
            }
        } else {
            System.out.println("Не е намерен автомат с ID: " + id);
        }
    }

    @Override
    public String getDescription() {
        return "Проверка дали автоматът е детерминиран";
    }
} 