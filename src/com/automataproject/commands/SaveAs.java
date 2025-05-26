package com.automataproject.commands;

import com.automataproject.model.Automata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveAs implements Command{
/*
 * Saves the automates in a file, with a path provided by the user
 */
    @Override
    public void execute(ArrayList<String> arguments) {
            if (arguments.size() == 0){
                System.out.println("Please provide a file path!");
                return;
            }
            File file = new File(arguments.get(0));
            try (FileOutputStream fileOut = new FileOutputStream(arguments.get(0));
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(Automata.getAutomataList());
                System.out.println("Saved automata list to " + arguments.get(0));
        }
            catch (IOException e){
                System.out.println("The file was not save succesfully");
            }
    }

    @Override
    public String getDescription() {
        return "Save as";
    }
}
