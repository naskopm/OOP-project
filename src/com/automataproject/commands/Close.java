package com.automataproject.commands;

import com.automataproject.model.Automata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * Closes the file and clears the list of automatas
 */
public class Close implements Command{
    /**
     * Closes the file and clears the list of automatas
     */
    @Override
    public void execute(ArrayList<String> arguments) {
        saveToFile();
    }

    private void saveToFile() {
        if (Automata.currentFileEditted == ""){
            System.out.println("No file opened, the automatas weren't saved");
            return;
        }
        try (FileOutputStream fileOut = new FileOutputStream(Automata.currentFileEditted);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(Automata.getAutomataList());
            System.out.println("Automatons were successfully saved!");
            Automata.automataList.clear();
            Automata.setMaxID(0);
            Automata.currentFileEditted = "";

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    @Override
    public String getDescription() {
        return "";
    }
}
