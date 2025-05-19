package com.automataproject.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.HashSet;

/**
 * Manages and executes commands in the automata system.
 * Maintains a mapping of command IDs to their implementations and handles user input.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public class CommandManager {
    private final Map<Integer, Command> commands;
    private final Scanner scanner;

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
        commands.put(1, new CreateAutomataCommand());
        commands.put(2, new CheckTransitionInfoCommand());
        commands.put(3, new SaveToFileCommand());
        commands.put(4, new ReadFromFileCommand());
        commands.put(5, new CheckEmptyLanguageCommand());
        commands.put(6, new PrintAllAutomatasCommand());
        commands.put(7, new DisplayAutomataCommand());
        commands.put(8, new CheckDeterministicCommand());
        commands.put(9, new RecognizeAutomataCommand());
        commands.put(10, new CreateAutomataFromRegexCommand());
        commands.put(11, new ConcatenateAutomatasCommand());
        commands.put(12, new SumAutomatasCommand());
        commands.put(13, new findLoops());
        commands.put(14, new isTheAlphabetEmpty());
        commands.put(15, new CheckInfiniteLanguage());
        commands.put(16, new MakeDeterministic());
        commands.put(17, new CreateAutomataFromRegexCommand());
        commands.put(18, new ExitCommand());
    }

    /**
     * Displays the main menu of available commands.
     * Shows each command's ID and description.
     */
    public void displayMenu() {
        System.out.println("\nМеню:");
        commands.forEach((key, command) -> 
            System.out.println(key + ". " + command.getDescription())
        );
    }

    /**
     * Executes a command based on user input.
     * Prompts the user to select a command by ID and executes it.
     */
    public void executeCommand(int choice) {
        Command command = commands.get(choice);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Невалиден избор!");
        }
    }

    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Изберете опция: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 18) { // Exit command
                break;
            }
            
            executeCommand(choice);
        }
    }
} 