import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MenuHandling {

    public static void startMenu(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Add User");
            System.out.println("4. View Users");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Show all available books");
            System.out.println("8. Show all books");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addPerson(conn, scanner, "employee");
                case 2 -> viewPersons(conn, "employee");
                case 3 -> addPerson(conn, scanner, "users");
                case 4 -> viewPersons(conn, "users");
                case 5 -> assign(conn, scanner, "book");
                case 6 -> returnBook(conn, scanner);
                case 7 -> showAvailableBooks(conn);
                case 8 -> showAllBooks(conn);
                case 9 -> {
                    System.out.println("---Exiting---");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void showAllBooks(Connection conn) {
        System.out.println("All Books: ");
        String sql = "SELECT * FROM books";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookId = rs.getInt("buchId");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String autor = rs.getString("autor");
                String description = rs.getString("description");
                boolean borrowed = rs.getBoolean("borrowed");

                System.out.println("Book ID: " + bookId);
                System.out.println("Title: " + title);
                System.out.println("Genre: " + genre);
                System.out.println("Author: " + autor);
                System.out.println("Description: " + description);
                System.out.println("Borrowed: " + (borrowed ? "Yes" : "No"));
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showAvailableBooks(Connection conn) {
        System.out.println("Available Books: ");
        String sql = "SELECT * FROM books WHERE borrowed = FALSE";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookId = rs.getInt("buchId");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String autor = rs.getString("autor");
                String description = rs.getString("description");

                System.out.println("Book ID: " + bookId);
                System.out.println("Title: " + title);
                System.out.println("Genre: " + genre);
                System.out.println("Author: " + autor);
                System.out.println("Description: " + description);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving available books: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addPerson(Connection conn, Scanner scanner, String type) {
        System.out.printf("Adding new %s:%n", capitalize(type));

        Map<String, String> data = new HashMap<>();
        data.put("name", input(scanner, "Name"));
        data.put("forename", input(scanner, "Forename"));
        data.put("number", input(scanner, "Number"));
        data.put("email", input(scanner, "Email"));
        data.put("address", input(scanner, "Address"));

        String sql;
        if (type.equals("employee")) {
            data.put("workingHours", input(scanner, "Working hours"));
            data.put("salary", input(scanner, "Salary"));
            data.put("position", input(scanner, "Position"));
            sql = "INSERT INTO employee (name, forename, number, email, address,workingHours, salary, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO users (name, forename, number, email, address) VALUES (?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, data.get("name"));
            stmt.setString(2, data.get("forename"));
            stmt.setInt(3, Integer.parseInt(data.get("number")));
            stmt.setString(4, data.get("email"));
            stmt.setString(5, data.get("address"));

            if (type.equals("employee")) {
                stmt.setString(6, data.get("workingHours"));
                stmt.setString(7, data.get("salary"));
                stmt.setString(8, data.get("position"));
            }

            stmt.executeUpdate();
            System.out.printf("%s was successfully added.%n", capitalize(type));
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewPersons(Connection conn, String type) {
        String sql = switch (type) {
            case "employee" -> "SELECT * FROM employee";
            case "users" -> "SELECT * FROM users";
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("%s List:%n", capitalize(type));
            while (rs.next()) {
                Map<String, String> data = new LinkedHashMap<>();
                data.put("ID", String.valueOf(rs.getInt(type.equals("employee") ? "employeeId" : "userId"))); // FUNKTIONIERT NICht
                data.put("Name", rs.getString("name"));
                data.put("Forename", rs.getString("forename"));
                data.put("Number", String.valueOf(rs.getInt("number")));
                data.put("Email", rs.getString("email"));
                data.put("Address", rs.getString("address"));

                if (type.equals("employee")) {
                    data.put("WorkingHours", rs.getString("workingHours"));
                    data.put("Salary", rs.getString("salary"));
                    data.put("Position", rs.getString("position"));
                }

                data.forEach((k, v) -> System.out.printf("%s: %s%n", k, v));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void assign(Connection conn, Scanner scanner, String type) {
        System.out.printf("Assigning %s:%n", capitalize(type));

        int userId = Integer.parseInt(input(scanner, "User ID"));
        int bookId = Integer.parseInt(input(scanner, "Book ID"));
        LocalDate borrowDate = LocalDate.now();  // Get current date for borrowDate

        // Check if the book is already borrowed
        String checkSql = "SELECT * FROM borrowed_books WHERE bookId = ? AND returnDate IS NULL";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // If there is a result, the book is already borrowed
                System.out.printf("Book %d is already borrowed. Please choose another book.%n", bookId);
                return;  // Exit the method
            }
        } catch (SQLException e) {
            System.out.println("Error checking if the book is borrowed: " + e.getMessage());
            return;  // Exit the method if there's an error
        }

        // If the book is not already borrowed, proceed with the assignment
        String sql = "INSERT INTO borrowed_books (bookId, userId, borrowDate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.setDate(3, java.sql.Date.valueOf(borrowDate));  // Insert the current date as borrowDate
            stmt.executeUpdate();
            System.out.printf("Book %d has been successfully borrowed by User %d on %s.%n", bookId, userId, borrowDate);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void returnBook(Connection conn, Scanner scanner) {
        System.out.println("Returning a book:");

        int bookId = Integer.parseInt(input(scanner, "Book ID"));
        int userId = Integer.parseInt(input(scanner, "User ID"));

        String sql = "SELECT borrowDate FROM borrowed_books WHERE bookId = ? AND userId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Fetch the borrow date
                Date borrowDate = rs.getDate("borrowDate");
                LocalDate returnDate = LocalDate.now(); // Current date

                // Calculate the duration, ensuring at least one day is charged
                long daysBorrowed = ChronoUnit.DAYS.between(((java.sql.Date) borrowDate).toLocalDate(), returnDate);
                if (daysBorrowed == 0) {
                    daysBorrowed = 1; // If returned on the same day, count as one day
                }

                double fee = daysBorrowed * 5; // 5€ per day

                System.out.printf("Book %d has been borrowed for %d days. Total fee: €%.2f%n", bookId, daysBorrowed, fee);

                // Delete record from borrowed_books (Book is returned)
                String deleteSql = "DELETE FROM borrowed_books WHERE bookId = ? AND userId = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, bookId);
                    deleteStmt.setInt(2, userId);
                    deleteStmt.executeUpdate();
                    System.out.printf("Book %d has been successfully returned by User %d.%n", bookId, userId);
                }

                // Call payment method to process the fee (even if it's €0)
                payment(scanner, fee);

            } else {
                System.out.println("No record found for this book and user.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void payment(Scanner scanner, double fee) {
        System.out.println("How do you want to pay? \n" +
                "1. Visa\n" +
                "2. Cash\n" +
                "3. Credit Card\n" +
                "Your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.printf("You have chosen Visa. You need to pay €%.2f%n", fee);
                break;
            case 2:
                System.out.printf("You have chosen Cash. You need to pay €%.2f%n", fee);
                break;
            case 3:
                System.out.printf("You have chosen Credit Card. You need to pay €%.2f%n", fee);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static String input(Scanner scanner, String field) {
        System.out.print(field + ": ");
        return scanner.nextLine();
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}