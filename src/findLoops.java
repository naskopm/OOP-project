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
    void removeLeavesRecurssion(Automata nodesContainer, Node currentNode,Set<Integer> visited) {
        if(visited.contains(currentNode.getId()))
            return;
        visited.add(currentNode.getId());
        if (currentNode.getTransitions().isEmpty()){
            Node.removeNode(currentNode,nodesContainer);
        }
        for (int i = 0; i < currentNode.getTransitions().size(); i++) {

            if (currentNode.getTransitions().get(i).getNextNode() != null)
            {
                removeLeavesRecurssion(nodesContainer,currentNode.getTransitions().get(i).getNextNode(),visited);
            }
        }

    }
    public void findingLoopsRecursion(Node currentNode,Set<Integer> visited,Automata originalAutomata) {
        if (visited.contains(currentNode.getId())) {
            Automata createdAutomata = new Automata(true);
            createdAutomata.makeAnID();
            int searchedID = currentNode.getId();
            ArrayList<Integer> ids = new ArrayList<>(visited);
            for (int i = 0; i < currentNode.getTransitions().size(); i++) {
                if(visited.contains(currentNode.getId()))
                {
                    int searchedId = 0;
                    ArrayList<Node> visitedNodes = new ArrayList<>();
                    for (int j = 0; j < ids.size(); j++) {
                        if (currentNode.getId() == ids.get(j)){
                            searchedId = j;
                        }
                    }
                    for (int j = searchedId; j <visited.size() ; j++) {
                        createdAutomata.addNode(originalAutomata.searchNode(ids.get(j)));
                    }
                }
            }
            Automata.automataList.add(createdAutomata);
            return;
        }
        visited.add(currentNode.getId());

        for (int i = 0; i < currentNode.getTransitions().size(); i++) {
            if (currentNode.getTransitions().get(i).getNextNode() != null){
                findingLoopsRecursion(currentNode.getTransitions().get(i).getNextNode(),visited,originalAutomata);
            }
        }
    }
    private void findingLoops(Automata automata)
    {
        Automata copy = automata.clone();
            Node initialNode = copy.findInitialNode();
            if (initialNode != null) {

                LinkedHashSet<Integer> visitedSecondRecurssion = new LinkedHashSet<>();
                Automata.setDeterministic(true); // Reset determinism flag
                findingLoopsRecursion(initialNode,new LinkedHashSet<>(),copy);
                Automata createdAutomata = Automata.getAutomataList().get(Automata.getAutomataList().size()-1);
                removeLeavesRecurssion(createdAutomata, createdAutomata.findInitialNode(),visitedSecondRecurssion);
            }
    }

}
