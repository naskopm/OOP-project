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
    /*public static void Recognise(int id,String query)
    {
        Automata checked = Automata.searchAutomata(id);
        ArrayList<String> tokenizer = new ArrayList<>();
        for (int i = 0; i < tokenizer.size(); i++) {
            if (tokenizer.get(i).equals("*") && i !=0)
            {
                String current = tokenizer.get(i-1);
                current += tokenizer.get(i);
                tokenizer.get(i) = current;
            }
        }

    }*/
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

            recurssion(displayed.transitions.get(i).getNextNode(), automataToBeDisplayed);
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
    public class Node implements Serializable{

        private int id;
        private boolean isFinal;
        private boolean isInitial;
        public ArrayList<Transition> transitions;
        private Node previousNode;
        private Node nextNode;


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
                System.out.println("Въведете id на следващият възел");
                Node nextNode = new Node();
                //nextNode.setId(Integer.parseInt(scanner.nextLine()));

                edittedNode.addTransition(symbol, nextNode,edittedNode);
                nodes.add(nextNode);
            }
            automataList.add(this);
        }
        
    }
    

}
