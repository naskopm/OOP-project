import java.util.*;

public class findLoops implements Command{
    @Override
    public void execute(){
        System.out.println("Въведете ID на автомата на който искате да намерите цикли");
        Scanner scanner = new Scanner(System.in);
        int a = Integer.parseInt(scanner.nextLine());
        findingLoops(Automata.searchAutomata(a));
    }
    @Override
    public String getDescription(){
        return "Намира циклични повторения на автомат";
    }
    public void findingLoopsRecursion(Node currentNode,Set<Integer> visited,Automata originalAutomata) {
        if (visited.contains(currentNode.getId())) {
            int searchedID = currentNode.getId();
            ArrayList<Integer> ids = new ArrayList<>(visited);
            for (int i = 0; i < currentNode.getTransitions().size(); i++) {
                if(visited.contains(currentNode.getId()))
                {
                    Automata createdAutomata = new Automata(true);
                    int searchedId = 0;
                    ArrayList<Node> visitedNodes = new ArrayList<>();
                    for (int j = 0; j < ids.size(); j++) {
                        if (currentNode.getId() == visitedNodes.get(j).getId()){
                            searchedId = j;
                        }
                    }
                    for (int j = searchedId; j <=visited.size() ; j++) {
                        createdAutomata.addNode(currentNode);
                    }
                }
            }
            return;
        }
        visited.add(currentNode.getId());

        for (int i = 0; i < currentNode.getTransitions().size(); i++) {

            findingLoopsRecursion(currentNode,visited,originalAutomata);
        }
    }
    private void findingLoops(Automata automata)
    {
            Node initialNode = automata.findInitialNode();
            if (initialNode != null) {
                Automata.setDeterministic(true); // Reset determinism flag
                findingLoopsRecursion(initialNode,new LinkedHashSet<>(),automata);
                System.out.println("Автоматът " + (Automata.isDeterministic() ? "е" : "не е") + " детерминиран");
            } else {
                System.out.println("Не е намерено начално състояние!");
            }
    }

}
