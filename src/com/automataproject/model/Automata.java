package com.automataproject.model;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Represents a finite automaton with nodes and transitions.
 * This class implements Serializable and Cloneable interfaces to support
 * persistence and cloning of automata.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class Automata implements Serializable, Cloneable {
    private static final long serialVersionUID =  -8172004642272343082L;
    private int id;
    private ArrayList<Node> nodes;
    private static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    public int maxNodeID = 0;
    private static int maxID = 0;
    private static boolean isDeterministic = true;

    /**
     * Gets the list of nodes in this automaton.
     *
     * @return ArrayList of nodes in the automaton
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Adds a new node to the automaton.
     *
     * @param node The node to be added
     */
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    /**
     * Gets the alphabet used by the automaton.
     *
     * @return Array of characters representing the alphabet
     */
    public static Character[] getAlphabet() {
        return alphabet;
    }

    /**
     * Gets the list of all automata in the system.
     *
     * @return List of all automata
     */
    public static List<Automata> getAutomataList() {
        return automataList;
    }

    /**
     * Gets the maximum node ID in this automaton.
     *
     * @return The maximum node ID
     */
    public int getMaxNodeID() {
        return maxNodeID;
    }

    /**
     * Sets the maximum node ID for this automaton.
     *
     * @param maxNodeID The new maximum node ID
     */
    public void setMaxNodeID(int maxNodeID) {
        this.maxNodeID = maxNodeID;
    }

    /**
     * Checks if this automaton is deterministic.
     *
     * @return true if the automaton is deterministic, false otherwise
     */
    public boolean isDeterministic() {
        return isDeterministic;
    }

    /**
     * Sets whether this automaton is deterministic.
     *
     * @param isDeterministic The new deterministic state
     */
    public void setDeterministic(boolean isDeterministic) {
        this.isDeterministic = isDeterministic;
    }

    /**
     * Gets the ID of this automaton.
     *
     * @return The automaton's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this automaton.
     *
     * @param id The new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Constructs a new Automata instance.
     * Initializes an empty list of nodes.
     *
     * @param bypass A flag to bypass certain initialization steps
     */
    public Automata(boolean bypass) {
        this.nodes = new ArrayList<>();
    }

    /**
     * Adds a new transition to a node in the automaton.
     * Prompts the user for transition details including target node and symbol.
     *
     * @param parentAutomata The automaton containing the nodes
     */
    private void AddNewTransition(Automata parentAutomata) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете към кой възел искате да добавите транзиция");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Node toBeEditted = parentAutomata.searchNode(id);
        System.out.println("Към кой възел искате да сочи новата транзиция");
        id = Integer.parseInt(scanner.nextLine().trim());
        Node transitioned = parentAutomata.searchNode(id);
        System.out.println("С каква буква да бъде транзицията");
        String converter = scanner.nextLine();
        char symbol = converter.trim().charAt(0);
        toBeEditted.addTransition(symbol,transitioned);
    }

    /**
     * Default constructor for Automata.
     * Creates a new automaton and prompts the user to add nodes and transitions.
     * The automaton is automatically added to the global automata list.
     */
    public Automata() {
        this.nodes = new ArrayList<>();
        this.makeAnID();
        System.out.println("Създаване на автомат, ако искате да приключите напишете stop ");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Boolean shouldBreak = false;
        while(!input.equals("stop")) {
            System.out.println("Въведете дали искате да добавите нов възел - 1, нова транзиция към съществуващ възел - 2, или да приключите -3");
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    Node node = new Node(this);
                    break;
                case 2:
                    AddNewTransition(this);
                    break;
                case 3:
                    shouldBreak = true;
                    break;
            }
            if (shouldBreak)
                break;
        }
        Automata.automataList.add(this);
    }

    /**
     * Finds the initial node in this automaton.
     *
     * @return The initial node, or null if no initial node exists
     */
    public Node findInitialNode() {
        for (Node node : nodes) {
            if (node.isInitial()) {
                return node;
            }
        }
        return null;
    }

    /**
     * Generates a unique ID for this automaton.
     * The ID is based on the maximum existing ID in the automata list.
     */
    public void makeAnID() {
        for (Automata automata : automataList) {
            if (maxID < automata.getId()) {
                maxID = automata.getId();
            }
        }
        ++maxID;
        this.setId(maxID);
    }

    /**
     * Creates a deep copy of this automaton.
     * Clones all nodes and transitions while maintaining their relationships.
     *
     * @return A new Automata instance that is a deep copy of this automaton
     * @throws AssertionError if cloning is not supported
     */
    @Override
    public Automata clone() {
        try {
            Automata copy = (Automata) super.clone();
            Map<Node, Node> map = new HashMap<>();
            copy.nodes = new ArrayList<>();
            
            // Clone all nodes
            for (Node n : this.nodes) {
                Node c = new Node(copy,true);
                c.setInitial(n.isInitial());
                c.setFinal(n.isFinal());
                c.setTransitions(new ArrayList<>());
                c.setId(n.getId());
                map.put(n, c);
            }
            
            // Clone all transitions
            for (Node n : this.nodes) {
                Node cn = map.get(n);
                for (Transition t : n.getTransitions()) {
                    Node tgt = map.get(t.getNextNode());
                    cn.addTransition(t.getSymbol(), tgt);
                }
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Gets all transitions in this automaton.
     *
     * @return ArrayList containing all transitions from all nodes
     */
    public ArrayList<Transition> getAllTransitions() {
        ArrayList<Transition> allTransitions = new ArrayList<Transition>();
        for (Node node : this.getNodes()) {
            for (Transition transition : node.getTransitions()) {
                allTransitions.add(transition);
            }
        }
        return allTransitions;
    }

    /**
     * Searches for an automaton in the global automata list by ID.
     *
     * @param id The ID of the automaton to find
     * @return The found automaton, or null if no automaton with the given ID exists
     */
    public static Automata searchAutomata(int id) {
        for (Automata automata : automataList) {
            if (automata.getId() == id) {
                return automata;
            }
        }
        return null;
    }

    /**
     * Searches for a node in this automaton by ID.
     *
     * @param id The ID of the node to find
     * @return The found node, or null if no node with the given ID exists
     */
    public Node searchNode(int id) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i).getId() == id)
            {
                return this.nodes.get(i);
            }
        }
        return null;
    }
}
