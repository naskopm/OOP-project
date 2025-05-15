package com.automataproject.model;

import java.io.Serializable;

public class Transition implements Serializable {
    private static final long serialVersionUID = 1L;
    private char symbol;
    private Node nextNode;
    private Node previousNode;

    public Transition(char symbol, Node nextNode) {
        this.symbol = symbol;
        this.nextNode = nextNode;
    }

    public char getSymbol() {
        return symbol;
    }
    public Node getNextNode() {
        return nextNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
} 