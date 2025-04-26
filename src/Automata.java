import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Automata implements Serializable, Cloneable {
    private static final long serialVersionUID =  -8172004642272343082L;
    private int id;
    private ArrayList<Node> nodes;
    private static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    public int maxNodeID = 0;
    private static int maxID = 0;
    private static boolean isDeterministic = true;

    // Getters and Setters
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public static Character[] getAlphabet() {
        return alphabet;
    }

    public static List<Automata> getAutomataList() {
        return automataList;
    }

    public int getMaxNodeID() {
        return maxNodeID;
    }

    public void setMaxNodeID(int maxNodeID) {
        this.maxNodeID = maxNodeID;
    }

    public static boolean isDeterministic() {
        return isDeterministic;
    }

    public static void setDeterministic(boolean isDeterministic) {
        Automata.isDeterministic = isDeterministic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Constructor
    public Automata(boolean bypass) {
        this.nodes = new ArrayList<>();
    }

    public Automata() {
        this.nodes = new ArrayList<>();
        this.makeAnID();
        System.out.println("Създаване на автомат, ако искате да приключите напишете stop ");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(!input.equals("stop")) {
            System.out.println("Въведете към кой възел искате да добавите транзиция");
            input = scanner.nextLine();
            if (input.equals("stop")) {
                break;
            }
            int nodeId = Integer.parseInt(input);
            Node edittedNode = null;
            if (nodes.size() == 0) {
                edittedNode = new Node(this);
            }
            
            for(Node searchedNode : nodes) {
                if(searchedNode.getId() == nodeId) {
                    edittedNode = searchedNode;
                    break;
                }
            }
            System.out.println("Въведете броя на транзициите");
            input = scanner.nextLine();
            if (input.equals("stop")) {
                break;
            }
            int numberOfTransitions = Integer.parseInt(input);
            for(int i = 0; i < numberOfTransitions; i++) {
                System.out.println("Въведете символа на транзицията");
                input = scanner.nextLine();
                if (input.equals("stop")) {
                    break;
                }
                char symbol = input.charAt(0);
                Node nextNode = new Node(this);
                edittedNode.addTransition(symbol, nextNode);
            }
        }
        automataList.add(this);
    }

    // Node management methods
    public Node findInitialNode() {
        for (Node node : nodes) {
            if (node.isInitial()) {
                return node;
            }
        }
        return null;
    }



    public void makeAnID() {
        for (Automata automata : automataList) {
            if (maxID < automata.getId()) {
                maxID = automata.getId();
            }
        }
        ++maxID;
        this.setId(maxID);
    }

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

    public static Automata searchAutomata(int id) {
        for (Automata automata : automataList) {
            if (automata.getId() == id) {
                return automata;
            }
        }
        return null;
    }
}
