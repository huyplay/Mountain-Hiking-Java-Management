package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    public static List<String[]> read(String filePath) {
        List<String[]> dataList = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read dong dau tien, roi bo qua
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Check dong do rong khong, rong thi bo qua
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] columns = line.split(csvSplitBy); // cat chuoi thanh mang
                dataList.add(columns);
            }
        } catch (IOException e) {
            System.err.println("Error when read csv file: " + filePath);
        }
        return dataList;
    }
}