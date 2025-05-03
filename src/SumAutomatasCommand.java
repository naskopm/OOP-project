import java.util.Scanner;

public class SumAutomatasCommand implements Command{
    @Override
    public void execute() {
        System.out.println("Въведете автомат 1");
        Scanner scanner = new Scanner(System.in);
        int automataId = Integer.parseInt(scanner.nextLine());
        Automata first = AutomataUtils.searchAutomata(automataId);
        System.out.print("Въведете ID на автомата: ");
        automataId = Integer.parseInt(scanner.nextLine());
        Automata second = AutomataUtils.searchAutomata(automataId);
        unionAutomatas(first, second);
    }
    public String getDescription(){
        return "Намира обединението на два автомата";
    }
    private static void unionAutomatas(Automata first, Automata second){
        Automata firstCopy = new Automata(true);
        Automata secondCopy = new Automata(true);
        firstCopy = first.clone();
        secondCopy = second.clone();
        Node initialNode = firstCopy.findInitialNode();
        initialNode.addTransition('e', secondCopy.findInitialNode());
        firstCopy.setMaxNodeID(firstCopy.maxNodeID - 1);
        secondCopy.findInitialNode().setInitial(false);
        for(Node node:secondCopy.getNodes())
        {
            node.makeANodeID(firstCopy);
            firstCopy.getNodes().add(node);
        }
        firstCopy.makeAnID();
        Automata.automataList.add(firstCopy);
    }

}
