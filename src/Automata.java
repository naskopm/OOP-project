import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;
public class Automata{
    private int id;
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    public static Automata searchAutomata(int id)
    {
        for (int i = 0; i < automataList.size(); i++) {
            if (id == automataList.get(i).getId()) {
                return  automataList.get(i);
            }
        }
        return null;
    }
    public int getId()
    {
        int copyID = this.id;
        return copyID;
    }

    public void checkInfoForTransition(int id)
    {
        Node searchedNode = searchNode(id);
        if (searchedNode != null) {
            for (int i = 0; i < searchedNode.transitions.size(); i++) {
                Transition transition = searchedNode.transitions.get(i);
                System.out.print(transition.getSymbol() + " -> " + transition.getNextNode());
                System.out.println();
            }
        }
    }

    private Node searchNode(int id) {
        Node searchedNode = null;
        for(int i=0; i< nodes.size(); i++)
        {
            if (nodes.get(i).getId() == id) {
                searchedNode = nodes.get(i);
                break;
            }
        }
        return searchedNode;
    }

    public class Node{
        private int id;
        private boolean isFinal;
        private boolean isInitial;
        public ArrayList<Transition> transitions;
        private int previousNode;
        private int nextNode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isFinal() {
            return isFinal;
        }

        public void setFinal(boolean isFinal) {
            this.isFinal = isFinal;
        }

        public boolean isInitial() {
            return isInitial;
        }

        public void setInitial(boolean isInitial) {
            this.isInitial = isInitial;
        }

        public ArrayList<Transition> getTransitions() {
            return transitions;
        }

        public void setTransitions(ArrayList<Transition> transitions) {
            this.transitions = transitions;
        }

        public int getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(int previousNode) {
            this.previousNode = previousNode;
        }

        public int getNextNode() {
            return nextNode;
        }

        public void setNextNode(int nextNode) {
            this.nextNode = nextNode;
        }
        public Node()
        {

        }

        public Node(Boolean overload){
            System.out.println("Създаване на възел");
            java.util.Scanner scanner = new java.util.Scanner(System.in);

            System.out.println("Enter node ID:");
            this.id = Integer.parseInt(scanner.nextLine());

            System.out.println("Is this a final node? (true/false):");
            this.isFinal = Boolean.parseBoolean(scanner.nextLine());

            System.out.println("Is this an initial node? (true/false):");
            this.isInitial = Boolean.parseBoolean(scanner.nextLine());

            this.transitions = new ArrayList<Transition>();

            if(!isInitial){
                System.out.println("Enter previous node ID:");
                int previousNodeId = Integer.parseInt(scanner.nextLine());
                // Assuming we have access to the nodes list from Automata
                for(Node node : nodes){
                    if(node.getId() == previousNodeId){
                        this.previousNode = node.getId();
                        break;
                    }
                }
            }
            System.out.println("Създадохте нов възел с id: " + this.id);
            nodes.add(this);
        }


        public void removeTransition(Transition transition){
            this.transitions.remove(transition);
        }
        public void addTransition(char symbol, int nextNode){
            Transition transition = new Transition(symbol, nextNode);
            this.transitions.add(transition);
        }

    }

    public Automata()
    {

    }

    public Automata(Boolean overload){
        this.id = 2;
        System.out.println("Създаване на автомат, ако искате да приключите напишете stop ");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(input != "stop"){

            System.out.println("Въведете към кой възел искате да добавите транзиция");
            input = scanner.nextLine();
            if (input.equals("stop")){
                break;
            }
            int nodeId = Integer.parseInt(input);
            Node edittedNode = null;
            if (nodes.size() == 0){
                edittedNode = new Node(true);
            }

            for(Node searchedNode : nodes){
                if(searchedNode.getId() == nodeId){
                    edittedNode = searchedNode;
                    break;
                }
            }
            System.out.println("Въведете броя на транзициите");
            input = scanner.nextLine();
            if (input.equals("stop")){
                break;
            }
            int numberOfTransitions = Integer.parseInt(input);
            for(int i = 0; i < numberOfTransitions; i++){
                System.out.println("Въведете символа на транзицията");
                input = scanner.nextLine();
                if (input.equals("stop")){
                    break;
                }
                char symbol = input.charAt(0);
                System.out.println("Въведете id на следващият възел");
                input = scanner.nextLine();
                int TheTransition = Integer.parseInt(input);
                Node TheTransitionNode = searchNode(TheTransition);
                Node nextNode;
                if (TheTransitionNode == null)
                {
                     nextNode = new Node(true);
                }
                else
                {
                    nextNode  = TheTransitionNode;
                }
                //nextNode.setId(Integer.parseInt(scanner.nextLine()));

                edittedNode.addTransition(symbol, nextNode.getId());
            }
            automataList.add(this);
        }

    }
    // Method to write the automata structure to file "Automata.txt" (in append mode)
    public void WriteToFile() {
        try (FileWriter writer = new FileWriter("Automata.txt", true)) {
            // Write automata id
            writer.write("Automata," + this.id + "\n");

            // Write each node's information:
            // Format: Node,<nodeId>,<isInitial: 1 or 0>,<isFinal: 1 or 0>,<previousNode>,<nextNode>
            for (Node node : nodes) {
                writer.write("Node,"
                        + node.getId() + ","
                        + (node.isInitial() ? "1" : "0") + ","
                        + (node.isFinal() ? "1" : "0") + ","
                        + node.getPreviousNode() + ","
                        + node.getNextNode() + "\n");

                // Write each transition for this node:
                // Format: Transition,<sourceNodeId>,<symbol>,<destinationNodeId>
                for (Transition t : node.getTransitions()) {
                    writer.write("Transition,"
                            + node.getId() + ","
                            + t.getSymbol() + ","
                            + t.getNextNode() + "\n");
                }
            }
            writer.flush();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Static method to read an automata structure from a file
    public static Automata readFromFile(String filename) {
        Automata automata = new Automata(); // Assumes a default, non-interactive constructor exists
        java.util.Map<Integer, Node> nodeMap = new java.util.HashMap<>();

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 0) continue;

                String type = tokens[0].trim();
                if (type.equals("Automata")) {
                    // Format: Automata,<automataId>
                    automata.id = Integer.parseInt(tokens[1].trim());
                    automataList.add(automata);
                } else if (type.equals("Node")) {
                    // Format: Node,<nodeId>,<isInitial>,<isFinal>,<previousNode>,<nextNode>
                    int nodeId = Integer.parseInt(tokens[1].trim());
                    boolean isInitial = tokens[2].trim().equals("1");
                    boolean isFinal = tokens[3].trim().equals("1");
                    int previousNode = Integer.parseInt(tokens[4].trim());
                    int nextNode = Integer.parseInt(tokens[5].trim());

                    // Create a new Node (using the default constructor) and set its attributes
                    Node node = automata.new Node();
                    node.setId(nodeId);
                    node.setInitial(isInitial);
                    node.setFinal(isFinal);
                    node.setPreviousNode(previousNode);
                    node.setNextNode(nextNode);
                    node.transitions = new ArrayList<>();  // initialize transitions list

                    automata.nodes.add(node);
                    nodeMap.put(nodeId, node);
                } else if (type.equals("Transition")) {
                    // Format: Transition,<sourceNodeId>,<symbol>,<destinationNodeId>
                    int sourceNodeId = Integer.parseInt(tokens[1].trim());
                    char symbol = tokens[2].trim().charAt(0);
                    int destNodeId = Integer.parseInt(tokens[3].trim());

                    Node sourceNode = nodeMap.get(sourceNodeId);
                    if (sourceNode != null) {
                        // Assuming Transition constructor is: Transition(char symbol, int nextNode)
                        sourceNode.addTransition(symbol, destNodeId);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        }
        return automata;
    }

}



