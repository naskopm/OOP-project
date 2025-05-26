package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.model.Node;
import com.automataproject.model.Transition;

import java.util.*;

/**
 * Command that extracts every cyclic sub-automaton (loop) from a given automaton
 * and adds the extracted automata back to the global list.
 *
 * The implementation relies on a depth-first search that keeps the current
 * recursion stack in a {@link LinkedHashSet}. When we re-visit a node that is
 * already on the stack, we have found a cycle.  Every node between that first
 * occurrence and the end of the stack forms the loop.
 *
 * After a loop is extracted, the helper {@link #pruneLeaves(Automata, Node, Set)}
 * removes dead-end states (i.e. leaves) that are not part of any further loop.
 */
public class findLoops implements Command {

    @Override
    public void execute(ArrayList<String> arguments) {
        if (Automata.getAutomataList().isEmpty()) {
            System.out.println("No automatons loaded.");
            return;
        }

        if (arguments.size() < 1) {
            System.out.println("Please provide an automaton ID as an argument");
            return;
        }

        try {
            int id = Integer.parseInt(arguments.get(0));
            Automata target = Automata.searchAutomata(id);
            if (target == null) {
                System.out.println("No automaton found with that ID.");
                return;
            }

            findLoops(target);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid ID.");
        }
    }

    @Override
    public String getDescription() {
        return "Find cyclic repetitions in an automaton";
    }

    // ---------------------------------------------------------------------
    //  Loop extraction pipeline
    // ---------------------------------------------------------------------

    private void findLoops(Automata original) {

        Node start = original.findInitialNode();
        if (start == null) {
            System.out.println("The automaton has no initial state.");
            return;
        }

        LinkedHashSet<Integer> stack = new LinkedHashSet<>(); // keeps DFS order
        List<Automata> extractedLoops = new ArrayList<>();

        dfsExtract(start, stack, original, extractedLoops);

        for (Automata loop : extractedLoops) {
            pruneLeaves(loop, loop.findInitialNode(), new HashSet<>());
            loop.makeAnID();
            Automata.getAutomataList().add(loop);
        }

        if (extractedLoops.isEmpty()) {
            System.out.println("No cycles were found.");
        } else {
            System.out.printf("Found and added %d cyclic automatons.%n", extractedLoops.size());
        }
    }

    /**
     * Depth-first search that keeps the nodes in the current recursion stack.
     * When we encounter a node that is already on the stack we have discovered a
     * cycle; the nodes from its first appearance up to the top of the stack form
     * the loop.
     */
    private void dfsExtract(Node current,
                            LinkedHashSet<Integer> stack,
                            Automata source,
                            List<Automata> out) {

        if (stack.contains(current.getId())) {
            // Build a new automaton that contains only the nodes of the cycle.
            Automata cycle = new Automata(true);

            List<Integer> ordered = new ArrayList<>(stack); // insertion order
            int startIdx = ordered.indexOf(current.getId());

            for (int i = startIdx; i < ordered.size(); i++) {
                Node n = source.searchNode(ordered.get(i));
                if (n != null && !cycle.getNodes().contains(n)) {
                    cycle.addNode(n);
                }
            }

            // Make sure the first node inside the cycle is initial
            Node first = cycle.searchNode(current.getId());
            if (first != null) {
                first.setInitial(true);
            }

            out.add(cycle);
            return; // stop exploring this branch – we already closed the loop
        }

        stack.add(current.getId());

        for (Transition t : new ArrayList<>(current.getTransitions())) {
            if (t.getNextNode() != null) {
                dfsExtract(t.getNextNode(), stack, source, out);
            }
        }

        stack.remove(current.getId()); // back-track
    }

    // ---------------------------------------------------------------------
    //  Utility – remove dangling leaves (dead ends)
    // ---------------------------------------------------------------------

    private void pruneLeaves(Automata container, Node node, Set<Integer> visited) {
        if (node == null || visited.contains(node.getId())) {
            return;
        }

        visited.add(node.getId());

        for (Transition t : new ArrayList<>(node.getTransitions())) {
            pruneLeaves(container, t.getNextNode(), visited);
        }

        // if a node has become a leaf, remove it from the automaton
        if (node.getTransitions().isEmpty()) {
            Node.removeNode(node, container);
        }
    }
}
