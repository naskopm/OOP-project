import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean isFinal;
    private boolean isInitial;
    private ArrayList<Transition> transitions;
    private Node previousNode;
    private final Automata parentAutomata;

    public Node(Automata parentAutomata) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        this.parentAutomata = parentAutomata;


            this.transitions = new ArrayList<Transition>();
            this.makeANodeID(parentAutomata);
            System.out.println("Is this a final node? (true/false):");
            this.isFinal = Boolean.parseBoolean(scanner.nextLine());

            System.out.println("Is this an initial node? (true/false):");
            this.isInitial = Boolean.parseBoolean(scanner.nextLine());
             parentAutomata.getNodes().add(this);

            System.out.println("Enter previous node ID:");
            int previousNodeId = Integer.parseInt(scanner.nextLine());
            for(Node node : parentAutomata.getNodes()){
                if(node.getId() == previousNodeId){
                    this.previousNode = node;
                    break;
                }
            }
        System.out.println("Добавен възел с id: "+this.getId());
            String input;
            System.out.println("Въведете броя на транзициите");
            input = scanner.nextLine();
            int numberOfTransitions = Integer.parseInt(input);
            for(int i = 0; i < numberOfTransitions; i++) {
                System.out.println("Въведете символа на транзицията");
                input = scanner.nextLine();
                if (input.equals("stop")) {
                    break;
                }
                char symbol = input.charAt(0);
                Node nextNode = new Node(parentAutomata,true,true);
                this.addTransition(symbol, nextNode);
            }
        Node updateTransitions = parentAutomata.searchNode(previousNodeId);
        Boolean doesTransitionExists= false;
        if (updateTransitions.getTransitions() != null)
        {
            for(Transition transition: updateTransitions.getTransitions()){
                if (transition.getPreviousNode().equals(this)){
                    doesTransitionExists = true;
                }
            }
        }
        if (!doesTransitionExists){
            updateTransitions.addTransition('e',this);
            //parentAutomata.getNodes().add(this);
            //System.out.println("Добавен възел с id: "+this.getId());
        }

        }


    public void makeANodeID(Automata editted) {
        if (editted.getNodes().isEmpty()){
            editted.maxNodeID =2;
            this.setId(1);
            return;
        }
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
        parentAutomata.addNode(this);
    }
    public Node(Automata parentAutomata, boolean isOverload,boolean secondOverload)
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        this.parentAutomata = parentAutomata;


        this.transitions = new ArrayList<Transition>();
        this.makeANodeID(parentAutomata);

        System.out.println("Is this a final node? (true/false):");
        this.isFinal = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Is this an initial node? (true/false):");
        this.isInitial = Boolean.parseBoolean(scanner.nextLine());


        System.out.println("Enter previous node ID:");
        int previousNodeId = Integer.parseInt(scanner.nextLine());
        for(Node node : parentAutomata.getNodes()){
            if(node.getId() == previousNodeId){
                this.previousNode = node;
                break;
            }
        }
        System.out.println();
    parentAutomata.addNode(this);
    System.out.println("Добавен възел с id: "+this.getId());
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


    public Automata getParentAutomata() {
        return parentAutomata;
    }

    // Node operations
    public void removeTransition(Transition transition) {
        this.transitions.remove(transition);
    }
    static void removeNode(Node target, Automata c) {
        // 1) Remove incoming edges to target
        for (Node n : c.getNodes()) {
            if (n == null) continue;
            n.getTransitions().removeIf(t -> t.getNextNode() == target);
        }

        // 2) Clear outgoing edges from target
        if (target != null) {
            target.getTransitions().clear();
        }

        // 3) Finally remove the node object itself
        c.getNodes().remove(target);
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
    
    