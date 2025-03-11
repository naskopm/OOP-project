import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Automata{
    ArrayList<Node> nodes = new ArrayList<Node>();
    public static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public Automata(){
        System.out.println("Create initial node");
        Node initialNode = new Node(0, false, true);
        nodes.add(initialNode);
        System.out.println("Създаване на автомат ако искате да спрете напишете stop");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(true){
            System.out.println("Създаване на нов възел ако искате да спрете напишете stop");
            System.out.println("Въведете id на възела");
            int id = scanner.nextInt();
            System.out.println("Въведете дали е финален възел ако искате да спрете напишете stop");
            boolean isFinal = scanner.nextBoolean();
            System.out.println("Въведете дали е начален възел ако искате да спрете напишете stop");
            boolean isInitial = scanner.nextBoolean();
            Node node = new Node(id, isFinal, isInitial);
            System.out.println("Въведете колко транзиции има този възел");
            int numberOfTransitions = scanner.nextInt();
            for(int i = 0; i < numberOfTransitions; i++){
                System.out.println("Въведете символа на транзицията");
                char symbol = scanner.next().charAt(0);
                System.out.println("Въведете id на следващият възел");
                int nextNode = scanner.nextInt();
                node.addTransition(symbol, nextNode);
            }
            for(int i = 0; i < numberOfTransitions; i++)
            {
                Node nextNode = new Node(5, false, false);
                nodes.add(nextNode);
            }
            String nextNode = scanner.nextLine();
            nodes.add(node);
            if(input.equals("stop")){
                break;
            }

           
           
        }
        
    }
    

}
