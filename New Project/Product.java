import java.sql.*;

public class Product {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234567890";

    public void displayProducts() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {

            System.out.println("\nID  | Name                          | Price (PHP)  | Stock");
            System.out.println("------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-4d| %-30s| %-12.2f| %d\n",
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"));
            }
            System.out.println("------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("\n[!] Error displaying products: " + e.getMessage());
        }
    }

    public void manageInventory() {
        System.out.println("\n[Admin Inventory Management]");
        // Logic for managing inventory (adding/updating/removing products).
    }
}
