import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public TaskManager() {
    }
    private final List<Task> tasks = new ArrayList<>();

    // Add a task to the list
    public void addTask(Scanner scanner) {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();
    
        tasks.add(new Task(name));
        System.out.println("Task added successfully.");
    }
    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
    
        System.out.println("\n=== To-Do List Tasks ===");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.printf("%d. %s - Status: %s\n", 
                    i + 1, task.getName(), 
                    task.isCompleted() ? "Completed" : "In Progress");
        }
    }

    
    // Mark a task as completed
    public void markTaskCompleted(Scanner scanner) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.print("Enter task number to mark as completed: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); 

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number.");
        } else {
            tasks.get(taskNumber - 1).setCompleted(true);
            System.out.println("Task marked as completed.");
        }
    }

    // Getter for tasks
    public List<Task> getTasks() {
        return tasks;
    }

    // Inner Task class
    public static class Task {
        private final String name;
        private boolean completed;

        public Task(String name) {
            this.name = name;
            this.completed = false;
        }
        public String getName() {
            return name;
        }
        public boolean isCompleted() {
            return completed;
        }
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
