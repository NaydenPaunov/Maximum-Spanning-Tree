package io;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private String filename;

    public CsvReader(String filename) {
        this.filename = filename;
    }

    public List<String[]> readCsvFile() {
        File file = new File(CsvReader.class.getClassLoader().getResource(this.filename).getFile());
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader).build();
            return csvReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
