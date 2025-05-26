package com.automataproject.commands;

import java.util.*;

/**
 * Manages and executes commands in the automata system.
 * Maintains a mapping of command IDs to their implementations and handles user input.
 *
 * @author Atanas Petrov Margaritov
 * @version 1.0
 */
public class CommandManager {
    private final Map<String, Command> commands;
    private final Scanner scanner;
     public static CommandManager commandManager;
     static ArrayList<String> commandKeys = new ArrayList<String>();
     public Map<String, Command> getCommands(){
         return this.commands;
     }
    /**
     * Constructs a new CommandManager instance.
     * Initializes the command map and scanner, then sets up all available commands.
     */
    public CommandManager() {
        this.commands = new HashMap<>();
        this.scanner = new Scanner(System.in);
        initializeCommands();

    }

    /**
     * Initializes all available commands and adds them to the command map.
     * Each command is assigned a unique integer ID.
     */
    private void initializeCommands() {
        // Add all commands to the map
        commands.put("Create automata", new CreateAutomataCommand());
        commands.put("Check transition", new CheckTransitionInfoCommand());
        commands.put("Close", new Close());
        commands.put("Save", new SaveToFileCommand());
        commands.put("Check empty language", new CheckEmptyLanguageCommand());
        commands.put("Print all automatas", new PrintAllAutomatasCommand());
        commands.put("Display automata", new DisplayAutomataCommand());
        commands.put("Check Deterministic", new CheckDeterministicCommand());
        commands.put("Recognise a word", new RecognizeAutomataCommand());
        commands.put("Concatenate automatas", new ConcatenateAutomatasCommand());
        commands.put("Sum Automatas", new SumAutomatasCommand());
        commands.put("Find loops", new findLoops());
        commands.put("Find empty alphabet", new isTheAlphabetEmpty());
        commands.put("Check infinte language", new CheckInfiniteLanguage());
        commands.put("Make automata deteministic", new MakeDeterministic());
        commands.put("Create from regex", new CreateAutomataFromRegexCommand());
        commands.put("SaveAs", new SaveAs());
        commands.put("Open", new Open());
        commands.put("help", new Help());
        commands.put("exit", new ExitCommand());
        commandManager = this;
        String[] holder = commands.keySet().toArray(new String[0]);
        commandKeys.addAll(Arrays.asList(holder));
    }

    /**
     * Displays the main menu of available commands.
     * Shows each command's ID and description.
     */


    /**
     * Executes a command based on user input.
     * Prompts the user to select a command by ID and executes it.
     */
    public void executeCommand(String choice, ArrayList<String> arguments) {
        Command command = commands.get(choice);
        if (command != null) {
            command.execute(arguments);
        } else {
            System.out.println("Невалиден избор!");
        }
    }

    public void run() {
        while (true) {
            String choice = scanner.nextLine();

            if (choice.equals("exit")) { // Exit command
                break;
            }
            ArrayList<String> arguments = new ArrayList<String>();
            String tokenizer [] = choice.split(" ");
            StringBuilder sb = new StringBuilder();
            Boolean isValidCommands = false;
            String CommandToExecute = "";
            int i= 0;
            for(i= 0; i<tokenizer.length; i++){
                sb.append(tokenizer[i]);
                sb.append(" ");
                if (commandKeys.contains(sb.toString().trim())){
                    isValidCommands = true;
                    CommandToExecute = sb.toString().trim();
                    break;
                }
            }
            for (int j = i+1; j< tokenizer.length; j++){
                arguments.add(tokenizer[j].trim());
            }
            executeCommand(CommandToExecute, arguments);
        }
    }
} 