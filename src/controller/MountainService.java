package controller;

import model.Mountain;

import java.util.ArrayList;
import java.util.List;

public class MountainService {

    private static final String MOUNTAIN_PATH = "src/data/MountainList.csv";
    private List<Mountain> mountains;

    public MountainService() {
        this.mountains = new ArrayList<>();
    }

    public void readMountainsFromCSV() {
        List<String[]> mountainData = ReadCSV.read(MOUNTAIN_PATH);
        for (String[] columns : mountainData) {
            if (columns.length >= 3) {
                String id = columns[0].trim();
                String name = columns[1].trim();
                String province = columns[2].trim();
                String description = columns[3].trim();
                Mountain mountain = new Mountain(id, name, province, description);
                mountains.add(mountain);
            }
        }
        //System.out.println("Update " + mountains.size() + " mountain from file csv successfuly!");
    }

    public void displayAllMountains() {
        if (mountains.isEmpty()) {
            System.err.println("List mountain null.");
            return;
        }
        System.out.println("\nList of mountain:");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(String.format("%-5s | %-20s | %-10s | %-30s |", "ID", "Name", "Province", "Description"));
        System.out.println("------------------------------------------------------------------------");
        for (Mountain m : mountains) {
            System.out.println(m.toString());
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void displayMountainChoose() {
        if (mountains.isEmpty()) {
            System.err.println("List mountain null.");
            return;
        }
        System.out.println("\nList of mountain:");
        System.out.println("------------------------------------------------------------------------");
        int i = 1;
        for (Mountain m : mountains) {
            System.out.println(i + ". " + m.toChoose());
            i++;
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public boolean checkMountain(String id) {
        if (id.isEmpty()) {
            return false;
        }
        for (Mountain mountain : mountains) {
            if (mountain.getId().equals(id)) {

                return true;
            }
        }
        return false;
    }

    public int countMountain() {
        return mountains.size();
    }

    public boolean isIDMountain(int id) {
        int count = mountains.size();
        if (id >= 0 && id <= count) {
            return true;
        } else {
            return false;
        }
    }

    public String getCodeMountain(int id) {
        Mountain m = mountains.get(id);
        return m.getId();
    }

    public String getNameMountain(String code) {
        for (Mountain m : mountains) {
            if (m.getId().equals(code)) {
                return m.getName();
            }
        }
        return null;
    }
}
