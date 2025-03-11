import java.util.ArrayList;
import java.util.List;

public class Node{
    private int id;
    private boolean isFinal;
    private boolean isInitial;
    public ArrayList<Transition> transitions;
    private Node previousNode;

    public Node(int id, boolean isFinal, boolean isInitial, Node previousNode){
        this.id = id;
        this.isFinal = isFinal;
        this.isInitial = isInitial;
        this.transitions = new ArrayList<Transition>();
        this.previousNode = previousNode;
    }

    public void addTransition(Transition transition){
        this.transitions.add(transition);
    }

    public void removeTransition(Transition transition){
        this.transitions.remove(transition);
    }
    public void addTransition(char symbol, int nextNode){
        Transition transition = new Transition(symbol, nextNode);
        this.transitions.add(transition);
    }
}
