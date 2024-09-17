import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Establish connection to the database
        Connection conn = DatabaseConnector.connect();
        Scanner scanner = new Scanner(System.in);

        if (conn != null) {
            try {
                while (true) {
                    System.out.println("Hello! Would you like to work with the \n1) GUI or with the \n2) Command Line? \n3) Or exit the program \nPlease enter your choice (1 - 3):");

                    int choice = getValidIntegerInput(scanner);

                    if (choice == 1) {
                        System.out.println("Starting GUI...");
                        // GUI.main(null); // Start GUI
                        break;

                    } else if (choice == 2) {
                        // Start command line menu
                        MenuHandling.startMenu(conn);
                    } else if (choice == 3) {
                        return;
                    } else {
                        System.out.println("Please enter a valid number between 1 and 3.");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace(); // Detailed error output
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close(); // Close the connection
                    }
                } catch (SQLException ex) {
                    System.out.println("Error closing the database connection: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Could not establish a connection to the database.");
        }

        scanner.close(); // Close scanner to avoid resource leaks
    }

    private static int getValidIntegerInput(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input == 1 || input == 2 || input == 3) {
                    return input;
                } else {
                    System.out.println("Please enter a valid number (1 - 3):");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer:");
                scanner.next(); // Clear input buffer to discard invalid input
            }
        }
    }
}
