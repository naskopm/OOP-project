package com.automataproject.commands;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;

public class ReadFromFileCommand implements Command {
    @Override
    public void execute(ArrayList<String> arguments) {
        readFromFile();
        System.out.println("Automatons were successfully loaded!");
    }

    private void readFromFile() {
        try (FileInputStream fileIn = new FileInputStream("AutomataList.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            List<Automata> loadedList = (List<Automata>) in.readObject();
            // Update the automata list
            for (Automata automata : loadedList) {
                Automata.getAutomataList().add(automata);
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDescription() {
        return "Load automatons from file";
    }
} 