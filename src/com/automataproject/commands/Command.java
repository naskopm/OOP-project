package com.automataproject.commands;

import java.util.ArrayList;

/**
 * Interface defining the contract for all commands in the automata system.
 * Each command must implement an execute method and provide a description.
 *
 * @author Automata Project Team
 * @version 1.0
 */
public interface Command {
    /**
     * Executes the command's main functionality.
     */
    void execute(ArrayList<String> arguments);

    /**
     * Gets a description of what the command does.
     *
     * @return A string describing the command's purpose
     */
    String getDescription();
} 