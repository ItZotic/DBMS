import java.sql.*;

public class Purchase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234567890";

    public boolean buyProduct(String username, int productId, int quantity) {
        String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String insertPurchaseQuery = "INSERT INTO purchases (username, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
             PreparedStatement insertPurchaseStatement = connection.prepareStatement(insertPurchaseQuery)) {

            // Update stock
            updateStockStatement.setInt(1, quantity);
            updateStockStatement.setInt(2, productId);
            updateStockStatement.setInt(3, quantity);

            int rowsAffected = updateStockStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Record purchase
                insertPurchaseStatement.setString(1, username);
                insertPurchaseStatement.setInt(2, productId);
                insertPurchaseStatement.setInt(3, quantity);
                insertPurchaseStatement.executeUpdate();

                return true; // Purchase successful
            }
        } catch (SQLException e) {
            System.out.println("\n[!] Error during purchase: " + e.getMessage());
        }
        return false; // Purchase failed
    }

    public void viewPurchaseHistory(String username) {
        String query = "SELECT p.name, ph.quantity FROM purchases ph JOIN products p ON ph.product_id = p.id WHERE ph.username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\n=== Purchase History ===");
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("\n[!] No purchase history available.");
                } else {
                    while (resultSet.next()) {
                        System.out.printf("Product: %-30s | Quantity: %d\n",
                                resultSet.getString("name"),
                                resultSet.getInt("quantity"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("\n[!] Error retrieving purchase history: " + e.getMessage());
        }
    }
}
