package com.automataproject.model;

import java.io.Serializable;

/**
 * Represents a transition between states in a finite automaton.
 * Each transition has a symbol and connects two nodes.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class Transition implements Serializable {
    private static final long serialVersionUID = 1L;
    private char symbol;
    private Node nextNode;
    private Node previousNode;

    /**
     * Constructs a new Transition instance.
     *
     * @param symbol The symbol that triggers this transition
     * @param nextNode The destination node of this transition
     */
    public Transition(char symbol, Node nextNode) {
        this.symbol = symbol;
        this.nextNode = nextNode;
    }

    /**
     * Gets the symbol associated with this transition.
     *
     * @return The transition symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Gets the destination node of this transition.
     *
     * @return The next node
     */
    public Node getNextNode() {
        return nextNode;
    }

    /**
     * Gets the source node of this transition.
     *
     * @return The previous node
     */
    public Node getPreviousNode() {
        return previousNode;
    }

    /**
     * Sets the source node of this transition.
     *
     * @param previousNode The node to set as the source
     */
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
} 