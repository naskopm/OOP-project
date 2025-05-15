package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;

public class CreateAutomataCommand implements Command {
    @Override
    public void execute() {
        new Automata();
    }

    @Override
    public String getDescription() {
        return "Създаване на нов автомат";
    }
} 