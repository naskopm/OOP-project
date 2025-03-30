import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Automata implements Serializable {
    private int id;
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    static int maxID = 0;
    private void makeAnID(){
        for (int i = 0; i < automataList.size(); i++) {
            if (maxID < automataList.get(i).getId())
                maxID = automataList.get(i).getId();
        }
        ++maxID;
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
        try (FileOutputStream fileOut = new FileOutputStream("person.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(automataList);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    static void ReadFromFile() {
        try (FileInputStream fileIn = new FileInputStream("person.ser");
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
                System.out.print(transition.getSymbol() + " -> " + transition.getNextNode().getId());
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
        public void addTransition(char symbol, Node nextNode){
            Transition transition = new Transition(symbol, nextNode);
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
                char symbol = input.charAt(0);
                System.out.println("Въведете id на следващият възел");
                Node nextNode = new Node();
                //nextNode.setId(Integer.parseInt(scanner.nextLine()));

                edittedNode.addTransition(symbol, nextNode);
                nodes.add(nextNode);
            }
            automataList.add(this);
        }
        
    }
    

}
