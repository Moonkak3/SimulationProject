package main.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    public static ArrayList<String[]> readFile(File file){
        try {
            ArrayList<String[]> lines = new ArrayList<>();
            Scanner input = new Scanner(new FileInputStream(file));
            while (input.hasNextLine()){
                String line = input.nextLine();
                line = line.strip();
                if (line.equals("")){
                    continue;
                }
                String[] tokens = line.split(",");
                lines.add(tokens);
            }
            input.close();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(File file, String content){
        PrintWriter output;
        try {
            output = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        output.println(content);
        output.flush();
        output.close();
    }
}
