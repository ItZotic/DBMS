import java.util.Scanner;

public class ChibiPomodoro {
    private static final Scanner scanner = new Scanner(System.in);
    private final PomodoroCycle pomodoroCycle = new PomodoroCycle();
    private final TaskManager taskManager = new TaskManager();
    private final UserManager userManager = new UserManager();

    public static void main(String[] args) {
        ChibiPomodoro pomodoro = new ChibiPomodoro();
        pomodoro.startApplication();
    }

    private void startApplication() {
        System.out.println("\n=== Welcome to Chibi Pomodoro ===");
        while (true) {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidatedChoice(1, 3);

            switch (choice) {
                case 1 -> {
                    if (logInUser()) {
                        showMenu();
                        return;
                    }
                }
                case 2 -> {
                    registerUser();
                    showMenu();
                    return;
                }
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private boolean logInUser() {
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();

        // Authenticate using UserManager
        try {
            boolean isAuthenticated = userManager.authenticateUserById(id);
            if (isAuthenticated) {
                System.out.println("Login successful! Welcome, User " + id);
                return true;
            } else {
                System.out.println("Invalid ID. Please try again.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
            return false;
        }
    }

    private void registerUser() {
        System.out.println("Registering a new user:");
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your major: ");
        String major = scanner.nextLine();
        System.out.print("Enter your school: ");
        String school = scanner.nextLine();

        // Register user with UserManager
        try {
            userManager.registerUser(id, name, major, school);
            System.out.println("Registration successful! Welcome, " + name);
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n=== Pomodoro Timer ===");
            System.out.println("1. Start Pomodoro Timer");
            System.out.println("2. To-Do List");
            System.out.println("3. What is Pomodoro Timer?");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidatedChoice(1, 4);

            switch (choice) {
                case 1 -> pomodoroCycle.startPomodoroCycle(scanner, taskManager.getTasks());
                case 2 -> manageToDoList();
                case 3 -> displayPomodoroInfo();
                case 4 -> {
                    System.out.println("Thank you for using the Pomodoro Timer! Have a productive day!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private int getValidatedChoice(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private void displayPomodoroInfo() {
        System.out.println("\n=== What is Pomodoro Timer? ===");
        System.out.println("The Pomodoro Technique alternates work sessions (pomodoros) with short breaks to maintain concentration and avoid burnout.");
        System.out.println("===========================================================");
        System.out.println("Identify a task or tasks that you need to complete\n" +
                           "~ Set a timer for 25 minutes ~\n" +
                           "~ Work on a task with no distractions ~\n" +
                           "~ Take a 5-minute break ~\n" +
                           "~ Repeat the process 3 more times ~\n" +
                           "~ Take a longer 30-minute break and start again");
        System.out.println("===========================================================");
    }

    private void manageToDoList() {
        while (true) {
            System.out.println("\n=== To-Do List ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getValidatedChoice(1, 4);

            switch (choice) {
                case 1 -> taskManager.addTask(scanner);
                case 2 -> taskManager.viewTasks();
                case 3 -> taskManager.markTaskCompleted(scanner);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
