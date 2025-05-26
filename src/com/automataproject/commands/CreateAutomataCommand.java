package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;
import java.util.ArrayList;

/**
 * Creates an Automata with an interactive menu
 */
public class CreateAutomataCommand implements Command {
    /**
     * Creates an Automata with an interactive menu
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        new Automata();
    }

    @Override
    public String getDescription() {
        return "Create a new automaton";
    }
} 