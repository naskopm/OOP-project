//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Automata.ReadFromFile();
        Scanner scanner = new Scanner(System.in);
        int menu = 0;
        while (true) {
            System.out.println("Изберете опция 1 - Създаване на нов автомат 2 - Търсене на преход в даден автомат, 3-Запиши във файл 4-Прочети от файл");
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
            break;
               case 3:

                   Automata.SaveToFile();

               break;
               case 4:
                   Automata.ReadFromFile();
                   break;
               case 5:
               {
                   System.out.println("Въведете id на автомата на който искате да проверите, дали езика е празен");
                   int input = Integer.parseInt(scanner.nextLine());
                   Automata foundAutomata = Automata.searchAutomata(input);
                   System.out.println(foundAutomata.checkIfEmptyAlphabet());
               }
               break;
               case 6:
               {
                   Automata.printAllAutomatas();
               }
               break;
               case 7:
                   Automata.displayAutomata();
                   break;
               case 8:
                   Automata.checkIfDeterministic();
                   System.out.println(Automata.isDeterministic);
                   break;
               case 9:
                   Automata.Recognise(1,"fasfafsafasf");
                   break;
            default:

           } 
        }
        
    }
}