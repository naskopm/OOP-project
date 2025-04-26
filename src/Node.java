import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean isFinal;
    private boolean isInitial;
    private ArrayList<Transition> transitions;
    private Node previousNode;
    private Node nextNode;
    private final Automata parentAutomata;

    public Node(Automata parentAutomata) {
        this.parentAutomata = parentAutomata;
        this.transitions = new ArrayList<Transition>();
        System.out.println("Създаване на възел");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        this.makeANodeID(parentAutomata);
        this.id = parentAutomata.getMaxNodeID();
        
        System.out.println("Is this a final node? (true/false):");
        this.isFinal = Boolean.parseBoolean(scanner.nextLine());
        
        System.out.println("Is this an initial node? (true/false):");
        this.isInitial = Boolean.parseBoolean(scanner.nextLine());
        
        if(!isInitial){
            System.out.println("Enter previous node ID:");
            int previousNodeId = Integer.parseInt(scanner.nextLine());
            for(Node node : parentAutomata.getNodes()){
                if(node.getId() == previousNodeId){
                    this.previousNode = node;
                    break;
                }
            }
        }
        parentAutomata.addNode(this);
        System.out.println("Създадохте нов възел с id: " + this.id);
    }
    public void makeANodeID(Automata editted) {
        for (Node node : editted.getNodes()) {
            if (editted.maxNodeID < node.getId()) {
                editted.maxNodeID = node.getId();
            }
        }
        this.setId(editted.maxNodeID);
        ++editted.maxNodeID;
    }
    public Node(Automata parentAutomata, boolean isOverload)
    {
        this.parentAutomata = parentAutomata;
        this.transitions = new ArrayList<Transition>();

        this.makeANodeID(parentAutomata);
        this.id = parentAutomata.getMaxNodeID();
        parentAutomata.addNode(this);
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean isInitial) { 
        this.isInitial = isInitial;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Automata getParentAutomata() {
        return parentAutomata;
    }

    // Node operations
    public void removeTransition(Transition transition) {
        this.transitions.remove(transition);
    }

    public void addTransition(char symbol, Node nextNode) {
        Transition transition = new Transition(symbol, nextNode);
        transition.setPreviousNode(this);
        this.transitions.add(transition);
    }

    public void checkInfoForTransition() {
        for (Transition transition : transitions) {
            System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
            System.out.println();
        }
    }

    public boolean checkIfEmptyAlphabet() {
        boolean foundLetter = false;
        for (Transition transition : transitions) {
            if (transition.getSymbol() == '\u0000') {
                if (transition.getNextNode() != null) {
                    foundLetter = true;
                    return transition.getNextNode().checkIfEmptyAlphabet();
                }
            } else {
                return false;
            }
        }
        return foundLetter;
    }

    public void recurssionForDeterminism(Set<Integer> visited) {
        if (visited.contains(this.id)) {
            return;
        }
        visited.add(this.id);

        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.size(); j++) {
                if (i != j) {
                    if(transitions.get(i).getSymbol() == transitions.get(j).getSymbol()) {
                        Automata.setDeterministic(false);
                        return;
                    }
                }
            }
            transitions.get(i).getNextNode().recurssionForDeterminism(visited);
        }
    }


}
    
    