package com.automataproject.commands;

import com.automataproject.model.Automata;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Open implements Command{

    @Override
    public void execute(ArrayList<String> arguments) {
        try (FileInputStream fileIn = new FileInputStream(arguments.get(0));
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // Read and cast the object
            Automata.automataList = (ArrayList<Automata>)in.readObject();
            Automata.currentFileEditted = arguments.get(0);
            System.out.println("File openned succesfully");

        }
        catch (Exception e){
            System.out.println("Please give a file location");
        }
    }

    @Override
    public String getDescription() {
        return "";
    }

}
