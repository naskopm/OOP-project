//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int menu = 0;
        while (true) {
            System.out.println("Изберете опция 1 - Създаване на нов автомат 2 - Търсене на преход в даден автомат");
            menu = Integer.parseInt(scanner.nextLine());
           switch (menu) {
            case 1:
            Automata automata = new Automata();
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