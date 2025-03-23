
public class Transition {
    private char symbol;
    private int nextNode;

    public Transition(char symbol, int nextNode) {
        this.symbol = symbol;
        this.nextNode = nextNode;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getNextNode() {
        return nextNode;
    }
} 