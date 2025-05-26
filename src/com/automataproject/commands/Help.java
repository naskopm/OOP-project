package com.automataproject.commands;

import java.util.Map;
import java.util.ArrayList;

public class Help implements Command {
    @Override
    public String getDescription() {
        return "Displays the menu";
    }
    
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
