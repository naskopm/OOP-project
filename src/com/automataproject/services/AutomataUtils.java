package com.automataproject.services;

import com.automataproject.model.Automata;

import java.util.ArrayList;
import java.util.List;

public class AutomataUtils {
    public static Automata searchAutomata(int id) {
        for (Automata automata : Automata.getAutomataList()) {
            if (id == automata.getId()) {
                return automata;
            }
        }
        return null;
    }
} 