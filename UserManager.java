import java.sql.*;
import java.util.Scanner;

public class UserManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/usermanager"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "1234567890"; // Replace with your password

    // Register a new user
    public void registerUser(String id, String name, String major, String school) {
        String insertQuery = "INSERT INTO user (id, name, major, school) VALUES (?, ?, ?, ?);";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, major);
            preparedStatement.setString(4, school);
            
            preparedStatement.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.out.println("Error while registering user: " + e.getMessage());
        }
    }

    // Authenticate a user by ID
    public boolean authenticateUserById(String id) {
        String selectQuery = "SELECT COUNT(*) FROM user WHERE id = ?;";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        
        return false;
    }

    // View all users (accessible only with admin credentials)
    public void viewAllUsers(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String adminUser = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String adminPass = scanner.nextLine();

        if (!"admin".equals(adminUser) || !"password".equals(adminPass)) {
            System.out.println("Access denied. Incorrect admin credentials.");
            return;
        }

        String selectQuery = "SELECT id, name, major, school FROM user;";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            System.out.println("\n=== Registered Users ===");
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No users registered yet.");
            } else {
                while (resultSet.next()) {
                    System.out.printf("ID: %s | Name: %s | Major: %s | School: %s\n",
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("major"),
                            resultSet.getString("school"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving users: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Register User");
            System.out.println("2. Authenticate User");
            System.out.println("3. View All Users");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter full name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter major: ");
                    String major = scanner.nextLine();
                    System.out.print("Enter school: ");
                    String school = scanner.nextLine();
                    userManager.registerUser(id, name, major, school);
                }
                case 2 -> {
                    System.out.print("Enter ID to authenticate: ");
                    String id = scanner.nextLine();
                    boolean authenticated = userManager.authenticateUserById(id);
                    System.out.println(authenticated ? "Authentication successful!" : "Authentication failed.");
                }
                case 3 -> userManager.viewAllUsers(scanner);
                case 4 -> {
                    System.out.println("Exiting User Management.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
