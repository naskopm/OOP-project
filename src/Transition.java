public class Transition {
    private char symbol;
    private int nextNodeId;

    public Transition(char symbol, int nextNodeId) {
        this.symbol = symbol;
        this.nextNodeId = nextNodeId;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getNextNodeId() {
        return nextNodeId;
    }
} 