import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Automata implements Serializable {
    private static final long serialVersionUID =  -8172004642272343082L;
    private int id;
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    private static int maxNodeID = 0;
    static int maxID = 0;
    static boolean isDeterministic = true;

    static void checkIfDeterministic()
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        Automata automataToBeDisplayed = Automata.searchAutomata(id);
        recurssionForDeterminism(automataToBeDisplayed.findInitialNode(), automataToBeDisplayed);
    }
    static void recurssionForDeterminism(Node displayed, Automata automataToBeDisplayed)
    {

        for (int i = 0; i < displayed.transitions.size(); i++) {
            for (int j = 0; j < displayed.transitions.size(); j++) {
                if (i!=j){
                    if(displayed.transitions.get(i).getSymbol() == displayed.transitions.get(j).getSymbol())
                    {
                        isDeterministic = false;
                        break;
                    }
                }
                recurssion(displayed.transitions.get(i).getNextNode(),automataToBeDisplayed);
            }
        }

    }

    public static void recogniseAutomata(String query, int id){

        Automata currentAutomata = Automata.searchAutomata(id);
        List<Character> characters = new ArrayList<>();
        if (query.length() == 1)
        {
            characters.add(query.charAt(0));
        }
        else {
            for (int i = 0; i < query.length(); i++) {
                characters.add(query.charAt(i));
            }
        }
        Boolean isRecognised = false;

        Node currentNode = currentAutomata.findInitialNode();
        for (int i = 0; i < characters.size(); i++) {

            isRecognised = false;

            for (int j = 0; j < currentNode.transitions.size(); j++) {
                if (currentNode.transitions.get(j).getSymbol() == characters.get(i))
                {
                    isRecognised = true;
                    currentNode = currentNode.transitions.get(j).getNextNode();
                }
            }
            if (!isRecognised)
            {
                System.out.println("Автоматът не беше разпознат");
                return;

            }
            if (currentNode.isFinal())
            {
                if (i == characters.size()-1 && isRecognised)
                {
                    System.out.println("Автоматът беше разпознат");
                    return;
                }
                if (i == characters.size()-1&& !isRecognised)
                {
                    System.out.println("Автоматът не беше разпознат");
                    return;
                }
            }
        }
        return;
    }
    //Save it for later might be useful for creating an automata
    public static void createAutomata(int id,String query)
    {
        Boolean isRecognised = true;
        ArrayList<ArrayList<String>> tokenizers = new ArrayList<>();
        Automata automata = Automata.searchAutomata(id);
        String checkedSequences [] = query.split("\\+");
        for (int i = 0; i < checkedSequences.length; i++) {
            Automata checked = Automata.searchAutomata(id);
            ArrayList<String> tokenizer = new ArrayList<>();

            String querySplitted [] = checkedSequences[i].split("");
            for (int j = 0; j < querySplitted.length; j++) {
                tokenizer.add(querySplitted[j]);
            }
            for (int j = 0; j < tokenizer.size(); j++) {
                if (tokenizer.get(j).equals("*") && j !=0)
                {
                    String current = tokenizer.get(j-1);
                    current += tokenizer.get(j);
                    tokenizer.set(j-1, current);
                    tokenizer.remove(j);
                }
            }
            tokenizers.add(tokenizer);
        }
        for (int i = 0; i < tokenizers.size(); i++) {
            if (!isRecognised)
            {
                break;
            }
            isRecognised = false;
            ArrayList<String> current = tokenizers.get(i);
            Node currentNode = automata.findInitialNode();
            for (int j = 0; j < current.size(); j++) {
                for (int k = 0; k < currentNode.transitions.size(); k++) {
                    if (current.get(j).indexOf('*') != -1)
                    {
                        if (currentNode.transitions.get(k).getSymbol() == current.get(k).charAt(0) && currentNode.transitions.get(k).getNextNode() == currentNode.transitions.get(k).getPreviousNode())
                        {
                            isRecognised = true;
                        }
                    }
                    else {
                        if (currentNode.transitions.get(k).getSymbol() != current.get(j).charAt(0))
                        {
                            isRecognised = true;
                            currentNode = currentNode.nextNode;
                        }
                    }
                }


            }
        }
        System.out.println(isRecognised);
    }
    static void printAllAutomatas()
    {
        for (int i = 0; i < automataList.size(); i++) {
            System.out.print("Automata id: " + automataList.get(i).getId()+ " ");
        }
    }
    static void displayAutomata()
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        Automata automataToBeDisplayed = Automata.searchAutomata(id);
        recurssion(automataToBeDisplayed.findInitialNode(), automataToBeDisplayed);
    }
    static void recurssion(Node displayed, Automata automataToBeDisplayed)
    {
        automataToBeDisplayed.checkInfoForTransition(displayed.getId());
        for (int i = 0; i < displayed.transitions.size(); i++) {
            if(displayed.transitions.get(i).getNextNode() != displayed) {
                recurssion(displayed.transitions.get(i).getNextNode(), automataToBeDisplayed);
            }
        }

    }

    public Node findInitialNode()
    {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).isInitial)
            {
                return nodes.get(i);
            }
        }
        return null;
    }
    public boolean checkIfEmptyAlphabet(){
        Node initialNode = findInitialNode();
        boolean foundLetter = false;
        while (!foundLetter)
        {
            for (int i = 0; i < initialNode.transitions.size(); i++) {
                if (initialNode.transitions.get(i).getSymbol() == '\u0000')
                {
                    if (initialNode.transitions.get(i).getNextNode() != null)
                    {
                        foundLetter = true;
                        initialNode = initialNode.transitions.get(i).getNextNode();
                    }
                }
                else {
                    return false;
                }
            }
        }

        return true;
    }

    private void makeAnID(){
        for (int i = 0; i < automataList.size(); i++) {
            if (maxID < automataList.get(i).getId())
                maxID = automataList.get(i).getId();
        }
        ++maxID;
    }
    private void makeANodeID(){
        for (int i = 0; i < nodes.size(); i++) {
            if (maxNodeID < nodes.get(i).getId())
                maxNodeID = nodes.get(i).getId();
        }
        ++maxNodeID;
    }
    public static Automata searchAutomata(int id)
    {
        for (int i = 0; i < automataList.size(); i++) {
            if (id == automataList.get(i).getId()) {
                return  automataList.get(i);
            }
        }
        return null;
    }
    static void SaveToFile()
    {
        try (FileOutputStream fileOut = new FileOutputStream("AutomataList.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(automataList);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    static void ReadFromFile() {
        try (FileInputStream fileIn = new FileInputStream("AutomataList.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // Read and cast the object
            automataList = (List<Automata>) in.readObject();

        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int getId()
    {
        int copyID = this.id;
        return copyID;
    }
    
    public void checkInfoForTransition(int id)
    {
        Node searchedNode = null;
        for(int i=0; i< nodes.size(); i++)
        {
            if (nodes.get(i).getId() == id) {
                searchedNode = nodes.get(i);
                break;
            }
        }
        if (searchedNode != null) {
            for (int i = 0; i < searchedNode.transitions.size(); i++) {
                Transition transition = searchedNode.transitions.get(i);
                System.out.print(transition.getPreviousNode().getId() + " -> "+ transition.getSymbol() + " -> " + transition.getNextNode().getId());
                System.out.println();
            }
        }
    }
    public Automata(boolean bypass)
    {
        makeAnID();
        this.id = maxID;

    }
    public  class Node implements Serializable{

        private int id;
        private boolean isFinal;
        private boolean isInitial;
        public ArrayList<Transition> transitions;
        private Node previousNode;
        private Node nextNode;
        public static void init() {
            Automata automata = new Automata(true);

            // Create nodes with explicit unique IDs
            Automata.Node node1 = automata.new Node(true); node1.setId(1); node1.setInitial(true); node1.setFinal(false);
            Automata.Node node2 = automata.new Node(true); node2.setId(2); node2.setInitial(false); node2.setFinal(false);
            Automata.Node node3 = automata.new Node(true); node3.setId(3); node3.setInitial(false); node3.setFinal(false);
            Automata.Node node4 = automata.new Node(true); node4.setId(4); node4.setInitial(false); node4.setFinal(true);
            Automata.Node node5 = automata.new Node(true); node5.setId(5); node5.setInitial(false); node5.setFinal(false);
            Automata.Node node6 = automata.new Node(true); node6.setId(6); node6.setInitial(false); node6.setFinal(true);

            // Set previous and next nodes for clear reference
            node1.setNextNode(node2);
            node2.setPreviousNode(node1); node2.setNextNode(node3);
            node3.setPreviousNode(node2); node3.setNextNode(node4);
            node4.setPreviousNode(node3); node4.setNextNode(node5);
            node5.setPreviousNode(node4); node5.setNextNode(node6);
            node6.setPreviousNode(node5); node6.setNextNode(node1); // cycle back

            // Add all nodes to automata
            automata.nodes.add(node1);
            automata.nodes.add(node2);
            automata.nodes.add(node3);
            automata.nodes.add(node4);
            automata.nodes.add(node5);
            automata.nodes.add(node6);

            // Define clear transitions as visualized
            node1.addTransition('a', node2, node1);
            node1.addTransition('b', node3, node1);
            node1.addTransition('c', node5, node1);

            node2.addTransition('d', node3, node2);
            node2.addTransition('e', node4, node2);

            node3.addTransition('f', node4, node3);
            node3.addTransition('g', node5, node3);

            node4.addTransition('h', node5, node4);
            node4.addTransition('i', node6, node4);

            node5.addTransition('j', node6, node5);
            node5.addTransition('k', node1, node5);

            node6.addTransition('l', node1, node6);
            node6.addTransition('m', node2, node6);

            Automata.automataList.add(automata);

            System.out.println("Automata successfully created with explicitly set node IDs, ensuring correct visualization and transitions.");
        }




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

        public Node getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
            this.previousNode = previousNode;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        public Node(){
            System.out.println("Създаване на възел");
            java.util.Scanner scanner = new java.util.Scanner(System.in);

            makeANodeID();
            this.id = maxNodeID;

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
                        this.previousNode = node;
                        break;
                    }
                }
            }
            nodes.add(this);
            System.out.println("Създадохте нов възел с id: " + this.id);
        }


        public Node(boolean bypass)
        {
            this.transitions= new ArrayList<Transition>();
        }

        public void removeTransition(Transition transition){
            this.transitions.remove(transition);
        }
        public void addTransition(char symbol, Node nextNode, Node previousNode){
            Transition transition = new Transition(symbol, nextNode,previousNode);
            this.transitions.add(transition);
        }
       
    }
    
    
    public Automata(){
        makeAnID();
        this.id = maxID;
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
                 edittedNode = new Node();
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
                char symbol;
                if (input.equals("")) {
                    symbol = '\u0000';
                }
                else {
                    symbol = input.charAt(0);
                }
                System.out.println("Транзицията примка ли е? да/не");
                input = scanner.nextLine();
                if (input.trim().toLowerCase().equals("да"))
                {
                    edittedNode.addTransition(symbol, edittedNode,edittedNode);
                }
                else {
                    Node nextNode = new Node();
                    edittedNode.addTransition(symbol, nextNode,edittedNode);
                    nodes.add(nextNode);
                }



            }

        }
        automataList.add(this);
    }
    

}
