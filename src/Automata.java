import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Automata implements Serializable, Cloneable {
    private static final long serialVersionUID =  -8172004642272343082L;
    private int id;
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public static Character alphabet [] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static List<Automata> automataList = new ArrayList<>();
    public int maxNodeID = 0;
    static int maxID = 0;
    static boolean isDeterministic = true;

    static void checkIfDeterministic()
    {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        Automata automataToBeDisplayed = Automata.searchAutomata(id);
        automataToBeDisplayed.findInitialNode().recurssionForDeterminism();
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

            for (int j = 0; j < currentNode.getTransitions().size(); j++) {
                if (currentNode.getTransitions().get(j).getSymbol() == characters.get(i))
                {
                    isRecognised = true;
                    currentNode = currentNode.getTransitions().get(j).getNextNode();
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

    }
    @Override
    public Automata clone() {
        try {
            Automata copy = (Automata) super.clone();
            // deep‐copy the node list
            Map<Node,Node> map = new HashMap<>();
            copy.nodes = new ArrayList<>();
            // 1) clone all nodes
            for (Node n : this.nodes) {
                Node c = new Node(true);
                c.setId(n.getId());
                c.setInitial(n.isInitial());
                c.setFinal(n.isFinal());
                c.setTransitions(new ArrayList<>());
                map.put(n, c);
                copy.nodes.add(c);
            }
            // 2) clone all transitions
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

    public static void concatenateAutomatas(Automata first, Automata second)
    {
        Automata newAutomata = new Automata(true);
        newAutomata = (Automata)second.clone();
        newAutomata.findInitialNode().setInitial(false);
        ArrayList<Node> finalNodes = new ArrayList<Node>();
        for (int i = 0; i < first.nodes.size(); i++) {
            if (first.nodes.get(i).isFinal())
                finalNodes.add(first.nodes.get(i));
        }
        int biggestID = 0;
        for (int i = 0; i < first.nodes.size(); i++) {
            if (first.nodes.get(i).getId() > biggestID)
                biggestID = first.nodes.get(i).getId();
        }
        int firstNewNode = biggestID +1;
        for (int i = 0; i < newAutomata.nodes.size(); i++) {
            newAutomata.nodes.get(i).setId(++biggestID);
        }
        for (int i = 0; i < finalNodes.size(); i++) {
            finalNodes.get(i).addTransition('e', second.findInitialNode());
        }
        for (int i = 0; i < first.nodes.size(); i++) {
            newAutomata.nodes.add(first.nodes.get(i));
        }
        automataList.add(newAutomata);
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
        HashSet<Integer> visited = new HashSet<>();
        recurssion(automataToBeDisplayed.findInitialNode(), automataToBeDisplayed,visited);
    }
    static void recurssion(Node displayed, Automata automataToBeDisplayed,HashSet visited)
    {


        for (int i = 0; i < displayed.transitions.size(); i++) {
            if(displayed.transitions.get(i).getNextNode() != displayed && !visited.contains(displayed.getId())) {
                visited.add(displayed.getId());
                automataToBeDisplayed.checkInfoForTransition(displayed.getId());
                recurssion(displayed.transitions.get(i).getNextNode(), automataToBeDisplayed, visited);
            }
        }


    }

    public Node findInitialNode()
    {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).isInitial())
            {
                return nodes.get(i);
            }
        }
        return null;
    }
    public boolean checkIfEmptyAlphabet(){
        Node initialNode = findInitialNode();
        return initialNode != null && initialNode.checkIfEmptyAlphabet();
    }

    private void makeAnID(){
        for (int i = 0; i < automataList.size(); i++) {
            if (maxID < automataList.get(i).getId())
                maxID = automataList.get(i).getId();
        }
        ++maxID;
    }
    public void makeANodeID(){
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
        return this.id;
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
            for (int i = 0; i < searchedNode.getTransitions().size(); i++) {
                Transition transition = searchedNode.getTransitions().get(i);
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
    public Automata() {
        makeAnID();
        this.id = maxID;
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
                edittedNode = new Node(this  );
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
               // nodes.add(nextNode);
            }
        }
        automataList.add(this);
    }
}
