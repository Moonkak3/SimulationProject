package main;

import java.io.*;

public class placeHolderMain {
    public static void main(String[] args) {
/*
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            File output = new File("crash-logs.txt");
            try {
                output.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            PrintStream writer = null;
            try {
                writer = new PrintStream(output);
                writer.println(e.getClass() + ": " + e.getMessage());
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    writer.println(e.getStackTrace()[i].toString());
                }
            } catch (FileNotFoundException exception) {
                // TODO Auto-generated catch block
                exception.printStackTrace();
            } finally {
                writer.close();
            }
        });
*/

        Main.main(null);
    }
}
