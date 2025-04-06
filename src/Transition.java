import java.io.Serializable;

public class Transition implements Serializable {
    private Automata.Node previousNode;
    private char symbol;
    private Automata.Node nextNode;

    public Transition(char symbol, Automata.Node nextNode, Automata.Node previousNode) {
        this.symbol = symbol;
        this.nextNode = nextNode;
        this.previousNode = previousNode;
    }
    public char getSymbol() {
        return symbol;
    }

    public Automata.Node getNextNode() {
        return nextNode;
    }
    public Automata.Node getPreviousNode() {
        return previousNode;
    }
} 