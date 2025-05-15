package com.automataproject.commands;

import com.automataproject.model.Automata;
import com.automataproject.services.AutomataUtils;

import java.io.*;
import java.util.Scanner;

public class SaveToFileCommand implements Command {
    @Override
    public void execute() {
        saveToFile();
        System.out.println("Автоматите бяха запазени успешно!");
    }

    private void saveToFile() {
        try (FileOutputStream fileOut = new FileOutputStream("AutomataList.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(Automata.getAutomataList());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Запазване на автоматите във файл";
    }
} 