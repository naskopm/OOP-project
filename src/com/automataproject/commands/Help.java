package com.automataproject.commands;

import java.util.Map;
import java.util.ArrayList;

/**
 * Print out all the commands
 */
public class Help implements Command {
    @Override
    public String getDescription() {
        return "Displays the menu";
    }
    /**
    * Prints out all the commands
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        CommandManager commandManager = CommandManager.commandManager;
        Map<String, Command> commands = commandManager.getCommands();
        System.out.println("\nMenu:");
        commands.forEach((key, command) ->
                System.out.println(key + " -> " + command.getDescription())
        );
    }
}
