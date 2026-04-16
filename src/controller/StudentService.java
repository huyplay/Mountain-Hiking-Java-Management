package controller;

import java.io.*;

import model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.util.HashMap;
import java.util.List;

public class StudentService {

    private static boolean isSaveData = false;
    private static final String DATA_PATH = "src/data/registrations.dat";
    private final ArrayList<Student> students = new ArrayList<>();

//    public void addSampleStudent() {
//        students.add(new Student("SE196508", "Truong Dan Huy", "0326181205", "truongdanhuyak@gmail.com", "1", 6000000 * 0.65));
//        students.add(new Student("QE191823", "Huy Play", "0904634354", "truongdanhuyak@gmail.com", "2", 6000000));
//        students.add(new Student("SE203485", "Huy Qua Dep Trai", "0394938454", "truongdanhuyak@gmail.com", "2", 6000000 * 0.65));
//        students.add(new Student("QE196508", "Huy Truong Dan", "0558384958", "truongdanhuyak@gmail.com", "11", 6000000));
//        students.add(new Student("SE203485", "Playyyy AK", "0554938454", "truongdanhuyak@gmail.com", "3", 6000000));
//        students.add(new Student("HE196508", "Vip Po ro", "0558384958", "truongdanhuyak@gmail.com", "12", 6000000 ));
//    }
    public void addStudent(Scanner sc, MountainService mountainService) {
        System.out.println("== New Registration ==");

        // Enter Student ID
        String studentID;
        while (true) {
            System.out.print("Enter Student ID:");

            studentID = sc.nextLine().toUpperCase();
            if (studentID.length() != 8) {
                System.err.println("Student ID is invalid: Must be 8 characters!");
                continue; // nhap lai
            }

            if (InputValidator.isCampusFPTU(studentID.substring(0, 2)) == false) {
                System.err.println("Student ID is invalid: Campus code is invalid!");
                continue; // nhap lai
            }

            if (!InputValidator.isPositiveInteger(studentID.substring(2))) {
                System.err.println("Student ID is invalid: Must be 6 digits after campus code!");
                continue;
            }

            if (checkStudentByID(studentID) == true) {
                System.err.println("Student ID already exists!");
                continue;
            }
            break;
        }

        // Enter Name
        String studentName;
        while (true) {
            System.out.print("Enter Name:");
            studentName = sc.nextLine().trim();

            if (studentName.isEmpty()) {
                System.err.println("Name cannot be empty!");
                continue;
            }

            if (!InputValidator.isValidName(studentName)) {
                System.err.println("Student name must be in alphabetical characters! ");
                continue;
            }

            if (studentName.length() < 2 || studentName.length() > 20) {
                System.err.println("Name must be between 2 and 20 characters!");
                continue;
            }
            break;
        }

        //Enter phone number
        String studentPhone;
        while (true) {
            System.out.print("Enter Phone Number:");
            studentPhone = sc.nextLine();

            if (!InputValidator.isPositiveInteger(studentPhone)) {
                System.err.println("Phone number is invalid!");
                continue;
            }

            if (studentPhone.length() != 10) {
                System.err.println("Phone number is invalid: Must be 10 digits!");
                continue;
            }

            if (InputValidator.isPhoneVN(studentPhone) == false) {
                System.err.println("Phone number is invalid: Must belong to a valid Vietnamese network operator.");
                continue;
            }

            if (checkStudentByPhone(studentPhone)) {
                System.err.println("Phone number already exists!");
                continue;
            }

            break;
        }

        // Enter Email
        String studentEmail;
        while (true) {
            System.out.print("Enter Email:");
            studentEmail = sc.nextLine();

            if (!InputValidator.isValidEmail(studentEmail)) {
                System.err.println("Email is invalid!");
                continue;
            }
            break;
        }

        //Enter mountain code
        int studentMountainID = 0;
        String studentMountainCode;
        while (true) {
            mountainService.displayMountainChoose();
            while (true) {
                // Enter Mountain Code
                System.out.print("Enter Mountain ID:");
                studentMountainCode = sc.nextLine();

                if (studentMountainCode == null || studentMountainCode.isEmpty()) {
                    System.err.println("Mountain ID can not empty. Please enter again!");
                    continue;
                }
                if (!InputValidator.isPositiveInteger(studentMountainCode)) {
                    System.err.println("Mountain ID is invalid. Must be number!");
                    continue;
                }
                studentMountainID = Integer.parseInt(studentMountainCode);

                //MountainService mountainService = new MountainService();
                if (mountainService.isIDMountain(studentMountainID) == false) {
                    System.err.println("Mountain ID is invalid. Enter 1->"+mountainService.countMountain()+" to choose!");
                    continue;
                }

                break;
            }
            break;
        }

        // Get code mountain
        studentMountainCode = mountainService.getCodeMountain(studentMountainID - 1);

        // Tuition Fee Calculation
        double studentFeeTuition = 6000000;

        // Discout 
        if (InputValidator.isPhoneVTandVNPT(studentPhone) == true) {
            studentFeeTuition = studentFeeTuition * 0.65;
        }

        students.add(new Student(studentID.toUpperCase(), studentName, studentPhone, studentEmail, studentMountainCode,
                studentFeeTuition));
        System.out.println("Register student mountain hiking success!");

    }

    public void updateStudent(Scanner sc, MountainService mountainService) {
        //Update studentCode
        String studentCode;
        boolean found = false;
        boolean updated = false;
        while (!updated) {
            System.out.print("Enter Student Code:");
            studentCode = sc.nextLine().toUpperCase();
            if (studentCode.isEmpty()) {
                break;
            }
            if (studentCode.length() != 8) {
                System.err.println("Student Code is invalid: Must be 8 characters!");
                continue; // nhap lai
            }
            if (InputValidator.isCampusFPTU(studentCode.substring(0, 2)) == false) {
                System.err.println("Student Code is invaild!");
                continue;
            }
            if (InputValidator.isPositiveInteger(studentCode.substring(2)) == false) {
                System.err.println("Student Code is invaild!");
                continue;
            }

            for (Student s : students) {
                if (s.getId().equals(studentCode)) {

                    found = true;

                    displayStudentUpdate(s);

                    System.out.println("You can Enter to not update that information field!");
                    // Enter Name
                    String studentName;
                    while (true) {
                        System.out.print("Enter Name:");
                        studentName = sc.nextLine();

                        if (studentName.isEmpty()) {
                            break;
                        }

                        if (!InputValidator.isValidName(studentName)) {
                            System.err.println("Student name must be in alphabetical characters! ");
                            continue;
                        }

                        if (studentName.length() < 2 || studentName.length() > 20) {
                            System.err.println("Name must be between 2 and 20 characters!");
                            continue;
                        }

                        s.setName(studentName);
                        break;
                    }

                    //Enter phone number
                    String studentPhone;
                    while (true) {
                        System.out.print("Enter Phone Number:");
                        studentPhone = sc.nextLine();

                        if (studentPhone.isEmpty()) {
                            break;
                        }

                        if (!InputValidator.isPositiveInteger(studentPhone)) {
                            System.err.println("Phone number is invalid!");
                            continue;
                        }

                        if (studentPhone.length() != 10) {
                            System.err.println("Phone number must be 10 digits!");
                            continue;
                        }

                        if (InputValidator.isPhoneVN(studentPhone) == false) {
                            System.err.println("Phone number is invalid: Must belong to a valid Vietnamese network operator.");
                            continue;
                        }

                        if (checkStudentByPhone(studentPhone)) {
                            System.err.println("Phone number already exists!");
                            continue;
                        }

                        // Valid phone number => Update
                        s.setPhone(studentPhone);

                        //Update fee
                        double studentFeeTuition = 6000000;
                        if (InputValidator.isPhoneVTandVNPT(studentPhone) == true) {
                            studentFeeTuition = studentFeeTuition * 0.65;
                        }
                        s.setFeeTuition(studentFeeTuition);

                        break;
                    }
                    // Enter Email
                    String studentEmail;
                    while (true) {
                        System.out.print("Enter Email:");
                        studentEmail = sc.nextLine();

                        if (studentEmail.isEmpty()) {
                            break;
                        }

                        if (!InputValidator.isValidEmail(studentEmail)) {
                            System.err.println("Email is invalid!");
                            continue;
                        }
                        s.setEmail(studentEmail);
                        break;
                    }

                    //Enter mountain code
                    String studentMountainCode;
                    int studentMountainID;
                    while (true) {
                        mountainService.displayMountainChoose();
                        while (true) {
                            // Enter Mountain Code
                            System.out.print("Enter Mountain ID:");
                            studentMountainCode = sc.nextLine();
                            if (studentMountainCode == null || studentMountainCode.isEmpty()) {
                                break;
                            }
                            if (!InputValidator.isPositiveInteger(studentMountainCode)) {
                                System.err.println("Mountain ID is invalid: Must be number!");
                                continue;
                            }
                            studentMountainID = Integer.parseInt(studentMountainCode);

                            //MountainService mountainService = new MountainService();
                            if (mountainService.isIDMountain(studentMountainID) == false) {
                                 System.err.println("Mountain ID is invalid. Enter 1->"+mountainService.countMountain()+" to choose!");
                                continue;
                            }
                            s.setCodeMountain(mountainService.getCodeMountain(studentMountainID - 1));

                            break;
                        }
                        break;
                    }
                    updated = true;
                    System.out.println("Update register information for student id " + studentCode + " successfuly!");
                    break;
                }

            }
            if (!found) {
                System.err.println("This student has not registered yet!");
                break;
            }
        }

    }

    public void deleteStudent(Scanner sc) {
        while (true) {
            System.out.print("Enter Student Code to delete: ");
            String studentCode = sc.nextLine().toUpperCase();

            if (studentCode.isEmpty()) {
                break;
            }
            if (studentCode.length() != 8) {
                System.err.println("Student Code is invalid: Must be 8 characters!");
                continue; 
            }
            
            if (InputValidator.isCampusFPTU(studentCode.substring(0, 2)) == false) {
                System.err.println("Student Code is invaild!");
                continue;
            }
            if (!InputValidator.isPositiveInteger(studentCode.substring(2))) {
                System.err.println("Student Code is invaild!");
                continue;
            }

            Student s = findStudentById(studentCode);
            if (s == null) {
                System.err.println("This student has not registered yet.");
                break;
            }
            displayStudent(s);
            while (true) {
                System.out.print("Are you sure you want to delete this registration? (Y/N):");
                String choose = sc.nextLine().toLowerCase();
                if (choose.equalsIgnoreCase("y")) {
                    students.remove(s);
                    System.out.println("The registration has been successfully deleted.");
                    break;
                } else if (choose.equalsIgnoreCase("n")) {
                    break;
                } else {
                    System.err.println("Invalid choice!");
                    //continue;
                }

            }
            break;
        }
    }

    public void searchStudent(Scanner sc) {

        while (true) {
            System.out.print("Enter name the registered to search: ");
            String keyword = sc.nextLine().toLowerCase();
            boolean found = false;

            ArrayList<Student> studentSearch = new ArrayList<>();
            for (Student s : students) {
                if (s.getName().toLowerCase().contains(keyword)) {
                    studentSearch.add(s);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("No one matches the search criteria!");
                break;

            } else {
                System.out.println("== Matching Students ==");
                System.out.println("------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-20s | %-10s | %-10s | %-10s", "Student ID", "Name", "Phone", "Peak Code", "Fee"));
                System.out.println("------------------------------------------------------------------------");
                for (Student s : studentSearch) {

                    System.out.println(s.toString());

                }
                System.out.println("------------------------------------------------------------------------");
            }
            break;
        }

    }

    public void filterStudent(Scanner sc, MountainService mountainService) {

        HashMap<String, String> nameCampus = new HashMap<>();
        nameCampus.put("SE", "Ho Chi Minh");
        nameCampus.put("CE", "Can Tho");
        nameCampus.put("DE", "Da Nang");
        nameCampus.put("HE", "Ha Noi");
        nameCampus.put("QE", "Quy Nhon");

        System.out.println("");

        for (HashMap.Entry<String, String> entry : nameCampus.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("");

        while (true) {
            System.out.print("Enter Campus Code to filter: ");
            String campusCode = sc.nextLine().toUpperCase();
            if (!InputValidator.isCampusFPTU(campusCode)) {
                System.err.println("Campus Code is invalid!");
                continue;
            }
            boolean found = false;

            ArrayList<Student> studentFitter = new ArrayList<>();
            for (Student s : students) {
                if (s.getId().startsWith(campusCode)) {
                    studentFitter.add(s);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No students have registered under this campus.");
                break;

            } else {
                System.out.println("Registered Students Under " + nameCampus.get(campusCode) + " Campus " + "(" + campusCode + "):");
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-20s | %-10s | %-20s | %-10s", "Student ID", "Name", "Phone", "Mountain", "Fee"));
                System.out.println("----------------------------------------------------------------------------------");
                for (Student s : studentFitter) {

                    System.out.println(String.format("%-10s | %-20s | %-10s | %-20s | %-10s", s.getId(), s.getName(), s.getPhone(), mountainService.getNameMountain(s.getCodeMountain()), s.formatFee()));

                }
                System.out.println("----------------------------------------------------------------------------------");
            }
            break;

        }
    }

    public void displayMountainLocation() {
        System.out.println("");
        if (students.isEmpty()) {
            System.out.println("No mountain have registered yet.");
            return;
        }

        HashMap<String, List<Double>> statisticMountain = new HashMap<>();

        for (Student s : students) {
            String key = s.getCodeMountainMT();
            List<Double> values = statisticMountain.getOrDefault(key, Arrays.asList(0.0, 0.0));

            double newCount = values.get(0) + 1.0;
            double newFee = values.get(1) + s.getFeeTuition();

            statisticMountain.put(key, Arrays.asList(newCount, newFee));
        }

        System.out.println("Statistics of Registration by Mountain Peak:");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-25s | %-10s", "Peak Name", "Number of Participants", "Total Cost"));
        System.out.println("------------------------------------------------------------------------");
        for (HashMap.Entry<String, List<Double>> entry : statisticMountain.entrySet()) {
            String code = entry.getKey();
            List<Double> values = entry.getValue();
            System.out.println(String.format("%-10s | %-25s | %-10s", code, values.get(0).intValue(), InputValidator.formatFee(values.get(1))));
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void saveFile() {
        ObjectOutputStream oos = null;
        try {
            // tao luong file nhi phan
            FileOutputStream fos = new FileOutputStream(DATA_PATH);
            oos = new ObjectOutputStream(fos);

            // ghi list vao file
            oos.writeObject(students);

            isSaveData = true;

            System.out.println("Moutain Hiking data has been saved to a file named registrations.dat");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Dong stream
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("Error: Cannot close.");
                }
            }
        }
    }

//    public void saveDataFile(Scanner sc) {
//        while (true) {
//
//            saveFile();
//
//            System.out.print("Enter or anything to return:");
//            String input = sc.nextLine();
//            if (input == null || input.isEmpty()) {
//                break;
//            } else {
//                break;
//            }
//        }
//
//    }

    public void loadDataFile() {
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream(DATA_PATH);
            ois = new ObjectInputStream(fis);

            // Read file ra list
            ArrayList<Student> loadedList = (ArrayList<Student>) ois.readObject();

            // Add lai vao list student
            for (Student s : loadedList) {
                students.add(new Student(
                        s.getId(),
                        s.getName(),
                        s.getPhone(),
                        s.getEmail(),
                        s.getCodeMountain(),
                        s.getFeeTuition()
                ));
            }

            //System.out.println("Data loaded successfully from registrations.dat");
//        for (Student s : students) {
//            System.out.println(s);
//        }
        } catch (FileNotFoundException e) {
            //System.err.println("File not found. Starting with empty student list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.err.println("Error: Cannot close input stream.");
                }
            }
        }
    }

    public void displayAll() {
        System.out.println("");
        System.out.println("Registered Students:");
        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-20s | %-10s | %-10s | %-10s", "Student ID", "Name", "Phone", "Peak Code", "Fee"));
        System.out.println("------------------------------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void displayStudent(Student s) {
        System.out.println("");
        System.out.println("Student Details:");
        System.out.println("------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s", "Student ID", ": " + s.getId()));
        System.out.println(String.format("%-10s %-20s", "Name", ": " + s.getName()));
        System.out.println(String.format("%-10s %-20s", "Phone", ": " + s.getPhone()));
        System.out.println(String.format("%-10s %-20s", "Mountain", ": " + s.getCodeMountainMT()));
        System.out.println(String.format("%-10s %-20s", "Fee", ": " + s.formatFee()));
        System.out.println("------------------------------------------------------");

    }

    public void displayStudentUpdate(Student s) {
        System.out.println("");
        System.out.println("Student Details:");
        System.out.println("------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s", "Student ID", ": " + s.getId()));
        System.out.println(String.format("%-10s %-20s", "Name", ": " + s.getName()));
        System.out.println(String.format("%-10s %-20s", "Phone", ": " + s.getPhone()));
        System.out.println(String.format("%-10s %-20s", "Email", ": " + s.getEmail()));
        System.out.println(String.format("%-10s %-20s", "Mountain", ": " + s.getCodeMountainMT()));
        System.out.println(String.format("%-10s %-20s", "Fee", ": " + s.formatFee()));
        System.out.println("------------------------------------------------------");

    }

    public boolean checkStudentByID(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkStudentByPhone(String phone) {
        for (Student s : students) {
            if (s.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private Student findStudentById(String studentCode) {
        for (Student s : students) {
            if (s.getId().equals(studentCode)) {
                return s;
            }
        }
        return null;
    }

    public void exitProgram(Scanner sc) {
        while (true) { // ASK 1 TIME
            if (!isSaveData) {
                System.out.print("Do you want to save the changes before exiting? (Y/N):");
                String input = sc.nextLine().toLowerCase();
                if (input.equals("y")) {
                    saveFile();

                } else if (input.equals("n")) {

                    while (true) { // ASK 2 TIME

                        System.out.print("You have unsaved changes. Are you sure you want to exit without saving? (Y/N):");
                        String input2 = sc.nextLine().toLowerCase();
                        if (input2.equals("n")) {
                            saveFile(); // Save data
                            System.out.println("Exited program!");
                            System.exit(0);
                        } else if (input2.equals("y")) {
                            System.out.println("Exited program without saving!");
                            System.exit(0);
                        } else {
                            System.err.println("Invalid choice!");
                            continue;
                        }
                        break;
                    }
                    break;
                } else {
                    System.err.println("Invalid choice!");
                    continue;
                }
            }

            System.out.println("Exited program!");
            System.exit(0);
        }

    }
}
