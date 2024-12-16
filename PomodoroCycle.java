import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PomodoroCycle {
    private int workDuration = 25;       // Work duration in minutes
    private int shortBreakDuration = 5;  // Short break duration in minutes
    private int longBreakDuration = 15;  // Long break duration in minutes
    private static final int POMODOROS_BEFORE_LONG_BREAK = 4;
    
    private int pomodorosCompleted = 0;

    public void startPomodoroCycle(Scanner scanner, List<TaskManager.Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available. Add tasks in the To-Do List before starting.");
            return;
        }

        // Allow user to modify timer durations
        modifyTimerDurations(scanner);

        while (true) {
            // Display tasks and let the user choose one
            System.out.println("\n=== Tasks to Focus On ===");
            for (int i = 0; i < tasks.size(); i++) {
                TaskManager.Task task = tasks.get(i);
                System.out.printf("%d. %s (Status: %s)\n", i + 1, task.getName(),
                                  task.isCompleted() ? "Completed" : "In Progress");
            }
            System.out.print("Select a task number to focus on: ");

            int taskNumber = validateTaskNumber(scanner, tasks.size());

            if (taskNumber < 1 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number. Please try again.");
                continue;
            }

            TaskManager.Task selectedTask = tasks.get(taskNumber - 1);

            System.out.println("Focusing on task: " + selectedTask.getName());

            runPomodoroCycle();

            System.out.print("\nMark the task as completed? (yes/no): ");
            String markComplete = scanner.nextLine();
            if (markComplete.equalsIgnoreCase("yes")) {
                selectedTask.setCompleted(true);
                System.out.println("Task marked as completed!");
            }

            System.out.print("\nContinue with another Pomodoro? (yes/no): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("\nThanks for using the Pomodoro Timer!");
                displaySessionSummary();
                break;
            }
        }
    }

    private void runPomodoroCycle() {
        for (int cycle = 1; cycle <= POMODOROS_BEFORE_LONG_BREAK; cycle++) {
            System.out.println("\nStarting Pomodoro " + cycle + "!");
            runTimer(workDuration, "Work");
            pomodorosCompleted++;
            displayMotivationalMessage();

            if (cycle < POMODOROS_BEFORE_LONG_BREAK) {
                System.out.println("Take a short break!");
                runTimer(shortBreakDuration, "Short Break");
            } else {
                System.out.println("Take a long break!");
                runTimer(longBreakDuration, "Long Break");
            }
        }
    }

    private int validateTaskNumber(Scanner scanner, int maxTasks) {
        while (true) {
            if (scanner.hasNextInt()) {
                int taskNumber = scanner.nextInt();
                scanner.nextLine();
                if (taskNumber >= 1 && taskNumber <= maxTasks) {
                    return taskNumber;
                } else {
                    System.out.println("Please enter a valid task number between 1 and " + maxTasks + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }

    private void runTimer(int durationMinutes, String sessionType) {
        try {
            for (int i = durationMinutes * 60; i > 0; i--) {
                int minutes = i / 60;
                int seconds = i % 60;
                System.out.printf("\r%s Time left: %02d:%02d ", sessionType, minutes, seconds);

                // Notify when time is almost up (1 minute remaining)
                if (i == 60) {
                    System.out.println("\nAlmost done! One minute left for the " + sessionType + ".");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("\nTime's up for " + sessionType + "!");
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }

    private void modifyTimerDurations(Scanner scanner) {
        System.out.println("Current Pomodoro Timer settings:");
        System.out.println("Work duration: " + workDuration + " minutes");
        System.out.println("Short break duration: " + shortBreakDuration + " minutes");
        System.out.println("Long break duration: " + longBreakDuration + " minutes");

        // Modify work duration
        System.out.print("Would you like to change the work duration? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new work duration in minutes: ");
            workDuration = Integer.parseInt(scanner.nextLine());
        }

        // Modify short break duration
        System.out.print("Would you like to change the short break duration? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new short break duration in minutes: ");
            shortBreakDuration = Integer.parseInt(scanner.nextLine());
        }

        // Modify long break duration
        System.out.print("Would you like to change the long break duration? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("Enter new long break duration in minutes: ");
            longBreakDuration = Integer.parseInt(scanner.nextLine());
        }
    }

    private void displayMotivationalMessage() {
        String[] messages = {
            "Great job! Keep going!",
            "You're awesome! Keep up the great work!",
            "One step closer to your goals!",
            "You're unstoppable!",
            "Take a deep breath and let's go again!"
        };
        System.out.println("\n" + messages[pomodorosCompleted % messages.length] + "\n");
    }

    private void displaySessionSummary() {
        System.out.println("\n=== Summary of Completed Pomodoros ===");
        System.out.println("Total Pomodoros completed: " + pomodorosCompleted);
        System.out.println("Great work today! See you next time!");
    }
}
