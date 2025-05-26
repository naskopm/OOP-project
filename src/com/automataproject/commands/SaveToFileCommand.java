package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;
import java.io.*;
import java.util.ArrayList;

/**
 * Saves the automatas in the opened file, if there is no opened file, it returns a message.
 */

public class SaveToFileCommand implements Command {
    /**
     * Saves the file without closing it
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        saveToFile();
        System.out.println("Automatons were successfully saved!");
    }

    private void saveToFile() {
        if (Automata.currentFileEditted == ""){
            System.out.println("No file opened, the automatas weren't saved");
            return;
        }
        try (FileOutputStream fileOut = new FileOutputStream(Automata.currentFileEditted);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(Automata.getAutomataList());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Save automatons to file";
    }
} 