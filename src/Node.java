import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean isFinal;
    private boolean isInitial;
    public ArrayList<Transition> transitions;
    private Node previousNode;
    private Node nextNode;

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

    public Node(Automata currentAutomata){
        System.out.println("Създаване на възел");
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        currentAutomata.makeANodeID();
        this.id = currentAutomata.maxNodeID;

        System.out.println("Is this a final node? (true/false):");
        this.isFinal = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Is this an initial node? (true/false):");
        this.isInitial = Boolean.parseBoolean(scanner.nextLine());

        this.transitions = new ArrayList<Transition>();

        if(!isInitial){
            System.out.println("Enter previous node ID:");
            int previousNodeId = Integer.parseInt(scanner.nextLine());
            // Assuming we have access to the nodes list from Automata
            for(Node node : currentAutomata.nodes){
                if(node.getId() == previousNodeId){
                    this.previousNode = node;
                    break;
                }
            }
        }
        currentAutomata.nodes.add(this);
        System.out.println("Създадохте нов възел с id: " + this.id);
    }

    public Node(boolean bypass) {
        this.transitions = new ArrayList<Transition>();
    }

    public void removeTransition(Transition transition) {
        this.transitions.remove(transition);
    }

    public void addTransition(char symbol, Node nextNode) {
        Transition transition = new Transition(symbol, nextNode);
        transition.setPreviousNode(this);
        this.transitions.add(transition);
    }

    public void checkInfoForTransition() {
        for (int i = 0; i < transitions.size(); i++) {
            Transition transition = transitions.get(i);
            System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
            System.out.println();
        }
    }

    public boolean checkIfEmptyAlphabet() {
        boolean foundLetter = false;
        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i).getSymbol() == '\u0000') {
                if (transitions.get(i).getNextNode() != null) {
                    foundLetter = true;
                    return transitions.get(i).getNextNode().checkIfEmptyAlphabet();
                }
            } else {
                return false;
            }
        }
        return foundLetter;
    }

    public void recurssionForDeterminism() {
        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < transitions.size(); j++) {
                if (i != j) {
                    if(transitions.get(i).getSymbol() == transitions.get(j).getSymbol()) {
                        Automata.isDeterministic = false;
                        break;
                    }
                }
                transitions.get(i).getNextNode().recurssionForDeterminism();
            }
        }
    }

    public void recurssion() {
        checkInfoForTransition();
        for (int i = 0; i < transitions.size(); i++) {
            if(transitions.get(i).getNextNode() != this) {
                transitions.get(i).getNextNode().recurssion();
            }
        }
    }

    public static void init() {
        Automata automata = new Automata(true);

        // Create nodes with explicit unique IDs
        Node node1 = new Node(automata); node1.setId(1); node1.setInitial(true); node1.setFinal(false);
        Node node2 = new Node(automata); node2.setId(2); node2.setInitial(false); node2.setFinal(false);
        Node node3 = new Node(automata); node3.setId(3); node3.setInitial(false); node3.setFinal(false);
        Node node4 = new Node(automata); node4.setId(4); node4.setInitial(false); node4.setFinal(true);
        Node node5 = new Node(automata); node5.setId(5); node5.setInitial(false); node5.setFinal(false);
        Node node6 = new Node(automata); node6.setId(6); node6.setInitial(false); node6.setFinal(true);

        // Set previous and next nodes for clear reference
        node1.setNextNode(node2);
        node2.setPreviousNode(node1); node2.setNextNode(node3);
        node3.setPreviousNode(node2); node3.setNextNode(node4);
        node4.setPreviousNode(node3); node4.setNextNode(node5);
        node5.setPreviousNode(node4); node5.setNextNode(node6);
        node6.setPreviousNode(node5); node6.setNextNode(node1); // cycle back

        // Define clear transitions as visualized
        node1.addTransition('a', node2);
        node1.addTransition('b', node3);
        node1.addTransition('c', node5);

        node2.addTransition('d', node3);
        node2.addTransition('e', node4);

        node3.addTransition('f', node4);
        node3.addTransition('g', node5);

        node4.addTransition('h', node5);
        node4.addTransition('i', node6);

        node5.addTransition('j', node6);
        node5.addTransition('k', node1);

        node6.addTransition('l', node1);
        node6.addTransition('m', node2);

        Automata.automataList.add(automata);

        System.out.println("Automata successfully created with explicitly set node IDs, ensuring correct visualization and transitions.");
    }
}
    
    