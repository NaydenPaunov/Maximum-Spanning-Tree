package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {
    private static final String DESKTOP_DIRECTORY = System.getProperty("user.home") + File.separator + "Desktop";
    private static final String RESULT_DIRECTORY = DESKTOP_DIRECTORY + File.separator + "results";

    public void writeToFile(String filename, String data) {
        String source = RESULT_DIRECTORY + File.separator + filename;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(source, false));
            writer.append(data);
            writer.close();
            System.out.println("File created in: " + source);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createResultDirectory() {
        File dir = new File(RESULT_DIRECTORY);
        if(!dir.exists()) {
            dir.mkdir();
        }
    }
}
