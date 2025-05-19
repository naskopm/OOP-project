package com.automataproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a state (node) in a finite automaton.
 * Each node can be marked as initial or final state and contains
 * transitions to other nodes.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean isFinal;
    private boolean isInitial;
    private ArrayList<Transition> transitions;
    private Node previousNode;
    private final Automata parentAutomata;

    /**
     * Finds a node in the given automaton by its ID.
     *
     * @param automata The automaton to search in
     * @param id The ID of the node to find
     * @return The found node, or null if no node with the given ID exists
     */
    public static Node findNode(Automata automata, int id) {
        for (Node node : automata.getNodes()) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    /**
     * Constructs a new Node instance.
     * Prompts the user for node properties and adds the node to the parent automaton.
     *
     * @param parentAutomata The automaton this node belongs to
     */
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
        for(Node node : parentAutomata.getNodes()) {
            if(node.getId() == previousNodeId) {
                this.previousNode = node;
                break;
            }
        }
        System.out.println("Добавен възел с id: "+this.getId());
        String input;
        System.out.println("Въведете броя на транзициите");
        input = scanner.nextLine().trim();
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
        if (updateTransitions != null)
        {
            if (updateTransitions.getTransitions() != null)
            {
                for(Transition transition: updateTransitions.getTransitions()){
                    if (transition.getPreviousNode().equals(this)){
                        doesTransitionExists = true;
                    }
                }
            }
        }

        if (!doesTransitionExists){
            if (updateTransitions != this) {
                updateTransitions.addTransition('e',this);
            }
        }
    }

    /**
     * Generates a unique ID for this node within the given automaton.
     * The ID is based on the maximum existing node ID in the automaton.
     *
     * @param editted The automaton this node belongs to
     */
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

    /**
     * Constructs a new Node instance with minimal initialization.
     * Used for creating nodes without user input.
     *
     * @param parentAutomata The automaton this node belongs to
     * @param isOverload Flag to indicate this is an overloaded constructor
     */
    public Node(Automata parentAutomata, boolean isOverload)
    {
        this.parentAutomata = parentAutomata;
        this.transitions = new ArrayList<Transition>();

        this.makeANodeID(parentAutomata);
        parentAutomata.addNode(this);
    }

    /**
     * Constructs a new Node instance with user input for properties.
     * Used for creating nodes with specific properties.
     *
     * @param parentAutomata The automaton this node belongs to
     * @param isOverload Flag to indicate this is an overloaded constructor
     * @param secondOverload Flag to indicate this is a second overloaded constructor
     */
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

    /**
     * Gets the ID of this node.
     *
     * @return The node's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this node.
     *
     * @param id The new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if this node is a final state.
     *
     * @return true if this is a final state, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Sets whether this node is a final state.
     *
     * @param isFinal The new final state
     */
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Checks if this node is an initial state.
     *
     * @return true if this is an initial state, false otherwise
     */
    public boolean isInitial() {
        return isInitial;
    }

    /**
     * Sets whether this node is an initial state.
     *
     * @param isInitial The new initial state
     */
    public void setInitial(boolean isInitial) { 
        this.isInitial = isInitial;
    }

    /**
     * Gets all transitions from this node.
     *
     * @return ArrayList of transitions from this node
     */
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Sets the transitions for this node.
     *
     * @param transitions The new list of transitions
     */
    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    /**
     * Gets the previous node connected to this node.
     *
     * @return The previous node
     */
    public Node getPreviousNode() {
        return previousNode;
    }

    /**
     * Sets the previous node connected to this node.
     *
     * @param previousNode The new previous node
     */
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    /**
     * Gets the parent automaton of this node.
     *
     * @return The parent automaton
     */
    public Automata getParentAutomata() {
        return parentAutomata;
    }

    /**
     * Removes a specific transition from this node.
     *
     * @param transition The transition to remove
     */
    public void removeTransition(Transition transition) {
        this.transitions.remove(transition);
    }

    /**
     * Removes a node from an automaton and all its associated transitions.
     *
     * @param target The node to remove
     * @param c The automaton containing the node
     */
    public static void removeNode(Node target, Automata c) {
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

    /**
     * Adds a new transition from this node to another node.
     *
     * @param symbol The symbol that triggers the transition
     * @param nextNode The destination node
     */
    public void addTransition(char symbol, Node nextNode) {
        Transition transition = new Transition(symbol, nextNode);
        transition.setPreviousNode(this);
        this.transitions.add(transition);
    }

    /**
     * Prints information about all transitions from this node.
     * Shows the source node, transition symbol, and destination node for each transition.
     */
    public void checkInfoForTransition() {
        for (Transition transition : transitions) {
            System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
            System.out.println();
        }
    }

    /**
     * Checks if the alphabet for this node's transitions is empty.
     *
     * @return true if the alphabet is empty, false otherwise
     */
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

    /**
     * Recursively checks if this node and its connected nodes form a deterministic automaton.
     * Updates the automaton's deterministic status based on the check.
     *
     * @param visited Set of visited node IDs to prevent cycles
     * @param automata The automaton being checked
     */
    public void recurssionForDeterminism(Set<Integer> visited, Automata automata) {
        if (visited.contains(this.id)) {
            return;
        }
        visited.add(this.id);
        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i).getSymbol() == 'e' && transitions.size() > 1){
                automata.setDeterministic(false);
                return;
            }
            for (int j = 0; j < transitions.size(); j++) {
                if (i != j) {
                    if(transitions.get(i).getSymbol() == transitions.get(j).getSymbol()) {
                        automata.setDeterministic(false);
                        return;
                    }
                }
            }
            transitions.get(i).getNextNode().recurssionForDeterminism(visited,automata);
        }
    }
}
    
    