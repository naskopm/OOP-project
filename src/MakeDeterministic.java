import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class MakeDeterministic implements Command{
    @Override
    public void execute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете id на автомата, който искате да направите детерминиран");
        int id = Integer.parseInt(scanner.nextLine());
        Automata toBeMadeDeterministic = Automata.searchAutomata(id);
        HashSet<Node> visitedNodes = new HashSet<Node>();
        recurssionHandleNode(toBeMadeDeterministic,visitedNodes);
    }

    private void recurssionHandleNode(Automata automata, HashSet<Node> visitedNodes) {
        automata.setDeterministic(true);
        Node intialNodeFirst = automata.findInitialNode();
        HashSet<Integer> initialCheck = new HashSet<Integer>();
        intialNodeFirst.recurssionForDeterminism(initialCheck, automata);
        Stack<Node> stack = new Stack<Node>();
        stack.push(automata.findInitialNode());
        do {
            automata.setDeterministic(true);
            Node intialNode = automata.findInitialNode();
            HashSet<Integer> checkDeterminism = new HashSet<Integer>();
            intialNode.recurssionForDeterminism(checkDeterminism, automata);
            Node currentNode = (Node) stack.pop();

            visitedNodes.add(automata.findInitialNode());
            HashSet<Character> visited = new HashSet<Character>();
            Character repeatedChar = '~';
            ArrayList<Transition> combinedTransitions = new ArrayList<Transition>();
            for (Transition transition : currentNode.getTransitions()) {
                if (visited.contains(transition.getSymbol()) && (repeatedChar == transition.getSymbol() || repeatedChar == '~')) {
                    repeatedChar = transition.getSymbol();

                }
                visited.add(transition.getSymbol());
            }
            if (repeatedChar != '~') {
                for (Transition transition: currentNode.getTransitions()){
                    for(Transition secondTransition: transition.getNextNode().getTransitions()) {
                        if (transition.getSymbol() == repeatedChar){
                            Transition add = new Transition(transition.getSymbol(), transition.getNextNode());
                            combinedTransitions.add(add);
                        }
                    }

                }
                Node combined = new Node(automata, true);
                combined.makeANodeID(automata);
                for (Transition transition : combinedTransitions) {
                    combined.addTransition(transition.getSymbol(), transition.getNextNode());
                }

                currentNode.getTransitions().clear();
                currentNode.addTransition(repeatedChar, combined);
                stack.push(combined);
                visitedNodes.add(currentNode);
                continue;
            }
            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getSymbol() == 'e' && currentNode.getTransitions().size() > 1) {

                }
            }
            for (Transition transition : currentNode.getTransitions()) {
                if (transition.getNextNode() != null && !visitedNodes.contains(transition.getNextNode())) {
                    stack.push(transition.getNextNode());
                    visitedNodes.add(transition.getNextNode());
                }
            }

        } while (!stack.isEmpty());
    }
        @Override
        public String getDescription () {
            return "Детерминира даден автомат";
        }

}
