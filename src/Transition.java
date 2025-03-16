import java.lang.classfile.components.ClassPrinter.Node;

public class Transition {
    private char symbol;
    private Automata.Node nextNode;

    public Transition(char symbol, Automata.Node nextNode) {
        this.symbol = symbol;
        this.nextNode = nextNode;
    }

    public char getSymbol() {
        return symbol;
    }

    public Automata.Node getNextNode() {
        return nextNode;
    }
} 