package com.automataproject.commands;

import java.io.*;
import java.util.List;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;

import java.util.Scanner;

public class ReadFromFileCommand implements Command {
    @Override
    public void execute() {
        readFromFile();
        System.out.println("Автоматите бяха заредени успешно!");
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
        return "Зареждане на автоматите от файл";
    }
} 