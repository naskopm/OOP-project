import java.util.*;

public class findLoops implements Command{
    int automataNumbers;
    Automata editted;
    @Override
    public void execute(){
        automataNumbers  = Automata.getAutomataList().size();
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
        if (currentNode == null)
            return;
        if (visited.contains(currentNode.getId())) {
            if (currentNode.getTransitions().isEmpty()) {
                Node.removeNode(currentNode, nodesContainer);
            }
            return;
        }
        visited.add(currentNode.getId());
        ArrayList<Transition> snapshot = new ArrayList<>(currentNode.getTransitions());
        for(Transition transition: snapshot) {
            if (transition.getNextNode() != null) {
                removeLeavesRecurssion(nodesContainer,transition.getNextNode(),visited);
            }
        }
        if (currentNode.getTransitions().isEmpty()) {
            Node.removeNode(currentNode, nodesContainer);
        }

    }
    public void findingLoopsRecursion(Node currentNode,Set<Integer> visited,Automata originalAutomata) {
        if (visited.contains(currentNode.getId())) {
            Automata createdAutomata = new Automata(true);
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
                        if (!createdAutomata.getNodes().contains(originalAutomata.searchNode(ids.get(j))))
                             createdAutomata.addNode(originalAutomata.searchNode(ids.get(j)));
                    }
                }
            }
                editted = createdAutomata;

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
                LinkedHashSet<Integer> visitedFirstRecurssion = new LinkedHashSet<>();
                automata.setDeterministic(true); // Reset determinism flag
                findingLoopsRecursion(initialNode,visitedFirstRecurssion,copy);
                removeLeavesRecurssion(editted, editted.findInitialNode(),visitedSecondRecurssion);
                editted.makeAnID();
                Automata.automataList.add(editted);
            }
    }

}
