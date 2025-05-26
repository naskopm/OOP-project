package com.automataproject.main;

import com.automataproject.commands.CommandManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager();
        //commandManager.executeCommand("Read from file", null);
        commandManager.run();
    }
}