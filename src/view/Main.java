package view;

import controller.StudentService;
import controller.MountainService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MountainService mountainService = new MountainService();

        mountainService.readMountainsFromCSV();

        //mountainService.displayAllMountains();
        StudentService service = new StudentService();

        service.loadDataFile();

        //ADD STUDENT
        //service.addSampleStudent();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== MOUNTAIN HIKING MANAGER =====");
            System.out.println("1. New Registration");
            System.out.println("2. Update Registration Information");
            System.out.println("3. Display Registered List");
            System.out.println("4. Delete Registration Information");
            System.out.println("5. Search Participants by Name");
            System.out.println("6. Filter Data by Campus");
            System.out.println("7. Statistics of Registration Numbers by Location");
            System.out.println("8. Save Data to File");
            System.out.println("9. Exit the Program");
            while (true) {
                System.out.print("Enter your choose: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        service.addStudent(sc, mountainService);
                        break;
                    case "2":
                        service.updateStudent(sc, mountainService);
                        break;
                    case "3":
                        service.displayAll();
                        break;
                    case "4":
                        service.deleteStudent(sc);
                        break;
                    case "5":
                        service.searchStudent(sc);
                        break;
                    case "6":
                        service.filterStudent(sc, mountainService);
                        break;
                    case "7":
                        service.displayMountainLocation();
                        break;
                    case "8":
                        service.saveFile();
                        break;
                    case "9":
                        service.exitProgram(sc);
                        break;
                    default:

                        System.err.println("Invalid choose. Enter 1->9 to use program!");
                        continue;
                }

                break;
            }

        }
    }
}
