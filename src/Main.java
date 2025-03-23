//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        System.out.println("=== Creating Automata Programmatically ===");

        // Create a new automata instance
        Automata testAutomata = Automata.class.newInstance();

        // Clear any nodes that might have been created during Automata initialization
        testAutomata.nodes.clear();

        // Create node 1 (initial state q0)
        Automata.Node node1 = testAutomata.new Node();
        node1.setId(0);
        node1.setInitial(true);
        node1.setFinal(false);
        node1.setTransitions(new ArrayList<>());

        // Create node 2 (state q1)
        Automata.Node node2 =  testAutomata.new Node();
        node2.setId(1);
        node2.setInitial(false);
        node2.setFinal(false);
        node2.setTransitions(new ArrayList<>());
        node2.setPreviousNode(0);

        // Create node 3 (final state q2)
        Automata.Node node3 =  testAutomata.new Node();
        node3.setId(2);
        node3.setInitial(false);
        node3.setFinal(true);
        node3.setTransitions(new ArrayList<>());
        node3.setPreviousNode(1);

        // Remove nodes from the list and add them manually to avoid duplicates
        testAutomata.nodes.clear();
        testAutomata.nodes.add(node1);
        testAutomata.nodes.add(node2);
        testAutomata.nodes.add(node3);

        // Add transitions for node 1 (q0)
        node1.addTransition('a', 1);  // On 'a', go to state q1
        node1.addTransition('b', 0);  // On 'b', stay in state q0

        // Add transitions for node 2 (q1)
        node2.addTransition('a', 2);  // On 'a', go to state q2
        node2.addTransition('b', 0);  // On 'b', go back to state q0

        // Add transitions for node 3 (q2)
        node3.addTransition('a', 2);  // On 'a', stay in state q2
        node3.addTransition('b', 0);  // On 'b', go back to state q0

        // Add the automata to the static list of automata
        Automata.automataList.add(testAutomata);

        // Print automata information
        System.out.println("\n=== Automata Information ===");
        System.out.println("Automata ID: " + testAutomata.getId());
        System.out.println("Number of nodes: " + testAutomata.nodes.size());

        for (Automata.Node node : testAutomata.nodes) {
            System.out.println("\nNode ID: " + node.getId());
            System.out.println("Is Initial: " + node.isInitial());
            System.out.println("Is Final: " + node.isFinal());

            System.out.println("Transitions:");
            if (node.getTransitions() != null && !node.getTransitions().isEmpty()) {
                for (Transition transition : node.getTransitions()) {
                    System.out.println("  Symbol: " + transition.getSymbol() +
                            " -> Next Node: " + transition.getNextNode());
                }
            } else {
                System.out.println("  No transitions");
            }
        }
        Scanner scanner = new Scanner(System.in);
        int menu = 0;
        Automata.readFromFile("Automata.txt");
        while (true) {
            System.out.println("Изберете опция 1 - Създаване на нов автомат 2 - Търсене на преход в даден автомат");
            menu = Integer.parseInt(scanner.nextLine());
            switch (menu) {
                case 1:
                    Automata automata = new Automata(true);
                    automata.WriteToFile();
                    break;

                case 2:
                {
                    System.out.println("Въведете ID на търесния автомат");
                    int automataID = Integer.parseInt(scanner.nextLine());
                    Automata found = Automata.searchAutomata(automataID);
                    System.out.println("Въведете id на състоянието, което искате да изследвате");
                    int nodeID = Integer.parseInt(scanner.nextLine());
                    found.checkInfoForTransition(nodeID);

                }
                default:
                    break;
            }
        }

    }
}