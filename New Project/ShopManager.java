import java.util.Scanner;

public class ShopManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final User userManager = new User();
    private static final Product productManager = new Product();
    private static final Purchase purchaseManager = new Purchase();
    private boolean isAdmin = false;
    private String loggedInUser = null;

    public static void main(String[] args) {
        ShopManager shopManager = new ShopManager();
        shopManager.run();
    }

    public void run() {
        while (true) {
            System.out.println("\n=====================================");
            System.out.println("       Welcome to PCG Peripherals ");
            System.out.println("=====================================");
            System.out.println("1. Log In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> logIn();
                case "2" -> signUp();
                case "3" -> {
                    System.out.println("\nThank you for visiting PC Peripherals Shop!");
                    System.out.println("Have a great day!\n");
                    return;
                }
                default -> System.out.println("\n[!] Invalid choice. Please try again.");
            }
        }
    }

    private void logIn() {
        System.out.println("\n=== Log In ===");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (userManager.login(username, password)) {
            loggedInUser = username;
            isAdmin = userManager.isAdmin(username);
            System.out.println("\n[✔] Login successful! Welcome, " + username + "!");
            mainMenu();
        } else {
            System.out.println("\n[!] Invalid username or password. Please try again.");
        }
    }

    private void signUp() {
        System.out.println("\n=== Sign Up ===");
        System.out.print("Enter a new Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a new Password: ");
        String password = scanner.nextLine();

        if (userManager.signUp(username, password)) {
            System.out.println("\n[✔] Registration successful! You can now log in.");
        } else {
            System.out.println("\n[!] Registration failed. Username might already exist.");
        }
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n=====================================");
            System.out.println("              Main Menu");
            System.out.println("=====================================");
            System.out.println("1. View Products");
            System.out.println("2. Buy Product");
            if (isAdmin) {
                System.out.println("3. Manage Inventory (Admin)");
            }
            System.out.println("4. View Purchase History");
            System.out.println("5. Logout");
            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> productManager.displayProducts();
                case "2" -> buyProductWithProductList();
                case "3" -> {
                    if (isAdmin) {
                        productManager.manageInventory();
                    } else {
                        System.out.println("\n[!] Invalid choice. Please try again.");
                    }
                }
                case "4" -> purchaseManager.viewPurchaseHistory(loggedInUser);
                case "5" -> {
                    System.out.println("\n[✔] You have been logged out.");
                    return;
                }
                default -> System.out.println("\n[!] Invalid choice. Please try again.");
            }
        }
    }

    private void buyProductWithProductList() {
        System.out.println("\n=== Buy Product ===");
        System.out.println("\n[Available Products]");
        productManager.displayProducts(); // Show the list of available products

        System.out.print("\nEnter Product ID to Buy: ");
        int productId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (purchaseManager.buyProduct(loggedInUser, productId, quantity)) {
            System.out.println("\n[✔] Purchase successful! Thank you for shopping with us.");
        } else {
            System.out.println("\n[!] Purchase failed. Check product availability or stock.");
        }
    }
}
