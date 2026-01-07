package com.taskmanagement;

import com.taskmanagement.models.*;
import com.taskmanagement.utiles.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Main application with user input/output.
 * Allows users to create and manage tasks through a menu system.
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> allUsers = new ArrayList<>();
    private static User currentUser = null;
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  Task & Project Management System         ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        boolean running = true;
        
        while (running) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
            
            int choice = getIntInput("Enter your choice: ");
            
            if (currentUser == null) {
                running = handleLoginMenu(choice);
            } else {
                running = handleMainMenu(choice);
            }
        }
        
        System.out.println("\n✓ Thank you for using Task Management System!");
        System.out.println("Goodbye!");
        scanner.close();
    }
    
    // ==================== MENU DISPLAYS ====================
    
    private static void showLoginMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           LOGIN / REGISTRATION             ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("1. Create New User");
        System.out.println("2. Login as Existing User");
        System.out.println("3. View All Users");
        System.out.println("0. Exit");
        System.out.println("────────────────────────────────────────────");
    }
    
    private static void showMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         MAIN MENU - " + currentUser.getName());
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("1.  Create Project");
        System.out.println("2.  View All Projects");
        System.out.println("3.  Create Task for Project");
        System.out.println("4.  Create Timed Task for Project");
        System.out.println("5.  Add Personal Task");
        System.out.println("6.  View All Tasks");
        System.out.println("7.  View Upcoming Tasks");
        System.out.println("8.  Mark Task as Completed");
        System.out.println("9.  Assign Personal Task to Project");
        System.out.println("10. View User Summary");
        System.out.println("11. Export Tasks to CSV");
        System.out.println("12. Export Comprehensive Data");
        System.out.println("13. Switch User");
        System.out.println("0.  Exit");
        System.out.println("────────────────────────────────────────────");
    }
    
    // ==================== MENU HANDLERS ====================
    
    private static boolean handleLoginMenu(int choice) {
        switch (choice) {
            case 1:
                createNewUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                viewAllUsers();
                break;
            case 0:
                return false;
            default:
                System.out.println("✗ Invalid choice. Please try again.");
        }
        return true;
    }
    
    private static boolean handleMainMenu(int choice) {
        switch (choice) {
            case 1:
                createProject();
                break;
            case 2:
                viewAllProjects();
                break;
            case 3:
                createTaskForProject();
                break;
            case 4:
                createTimedTaskForProject();
                break;
            case 5:
                addPersonalTask();
                break;
            case 6:
                viewAllTasks();
                break;
            case 7:
                viewUpcomingTasks();
                break;
            case 8:
                markTaskCompleted();
                break;
            case 9:
                assignPersonalTaskToProject();
                break;
            case 10:
                viewUserSummary();
                break;
            case 11:
                exportTasksToCSV();
                break;
            case 12:
                exportComprehensiveData();
                break;
            case 13:
                currentUser = null;
                System.out.println("✓ Logged out successfully!");
                break;
            case 0:
                return false;
            default:
                System.out.println("✗ Invalid choice. Please try again.");
        }
        return true;
    }
    
    // ==================== USER MANAGEMENT ====================
    
    private static void createNewUser() {
        System.out.println("\n── Create New User ──");
        String name = getStringInput("Enter user name: ");
        
        User newUser = new User(name);
        allUsers.add(newUser);
        currentUser = newUser;
        
        System.out.println("✓ User '" + name + "' created successfully!");
        System.out.println("✓ You are now logged in as " + name);
    }
    
    private static void loginUser() {
        if (allUsers.isEmpty()) {
            System.out.println("✗ No users found. Please create a new user first.");
            return;
        }
        
        System.out.println("\n── Login ──");
        System.out.println("Available users:");
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println((i + 1) + ". " + allUsers.get(i).getName());
        }
        
        int userIndex = getIntInput("Select user number: ") - 1;
        
        if (userIndex >= 0 && userIndex < allUsers.size()) {
            currentUser = allUsers.get(userIndex);
            System.out.println("✓ Logged in as " + currentUser.getName());
        } else {
            System.out.println("✗ Invalid user selection.");
        }
    }
    
    private static void viewAllUsers() {
        if (allUsers.isEmpty()) {
            System.out.println("\n✗ No users registered yet.");
            return;
        }
        
        System.out.println("\n── All Users ──");
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            System.out.println((i + 1) + ". " + user.getName() + 
                             " (Projects: " + user.getProjects().size() + 
                             ", Tasks: " + (user.getPersonalTasks().size() + 
                             getTotalTasksForUser(user)) + ")");
        }
    }
    
    // ==================== PROJECT MANAGEMENT ====================
    
    private static void createProject() {
        System.out.println("\n── Create New Project ──");
        String projectName = getStringInput("Enter project name: ");
        
        Project project = new Project(projectName);
        currentUser.addProject(project);
        
        System.out.println("✓ Project '" + projectName + "' created successfully!");
    }
    
    private static void viewAllProjects() {
        List<Project> projects = currentUser.getProjects();
        
        if (projects.isEmpty()) {
            System.out.println("\n✗ No projects found. Create a project first.");
            return;
        }
        
        System.out.println("\n── All Projects ──");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.println((i + 1) + ". " + project.getName() + 
                             " (Tasks: " + project.getAllTasks().size() + 
                             ", Upcoming: " + project.getUpcomingTasks().size() + ")");
        }
    }
    
    // ==================== TASK MANAGEMENT ====================
    
    private static void createTaskForProject() {
        List<Project> projects = currentUser.getProjects();
        
        if (projects.isEmpty()) {
            System.out.println("\n✗ No projects available. Create a project first.");
            System.out.println("  Or use option 5 to create a personal task.");
            return;
        }
        
        System.out.println("\n── Create Task for Project ──");
        
        // Select project first
        System.out.println("Select project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        int projectIndex = getIntInput("Enter project number: ") - 1;
        
        if (projectIndex < 0 || projectIndex >= projects.size()) {
            System.out.println("✗ Invalid project selection.");
            return;
        }
        
        Project selectedProject = projects.get(projectIndex);
        
        // Get task details
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        LocalDate deadline = getDateInput("Enter deadline (yyyy-MM-dd) or press Enter for no deadline: ");
        Priority priority = getPriorityInput();
        
        Task task = new Task(title, description, deadline, priority);
        NotificationService.notifyTaskCreated(task);
        
        // Assign directly to selected project
        currentUser.assignTaskToProject(selectedProject, task);
        
        System.out.println("✓ Task created and assigned to '" + selectedProject.getName() + "' successfully!");
        System.out.println("  Task ID: " + task.getId());
    }
    
    private static void createTimedTaskForProject() {
        List<Project> projects = currentUser.getProjects();
        
        if (projects.isEmpty()) {
            System.out.println("\n✗ No projects available. Create a project first.");
            System.out.println("  Or use option 5 to create a personal task.");
            return;
        }
        
        System.out.println("\n── Create Timed Task for Project ──");
        
        // Select project first
        System.out.println("Select project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        int projectIndex = getIntInput("Enter project number: ") - 1;
        
        if (projectIndex < 0 || projectIndex >= projects.size()) {
            System.out.println("✗ Invalid project selection.");
            return;
        }
        
        Project selectedProject = projects.get(projectIndex);
        
        // Get task details
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        LocalDate deadline = getDateInput("Enter deadline (yyyy-MM-dd) or press Enter for no deadline: ");
        Priority priority = getPriorityInput();
        int duration = getIntInput("Enter estimated duration (hours): ");
        
        TimedTask task = new TimedTask(title, description, deadline, priority, duration);
        NotificationService.notifyTaskCreated(task);
        
        // Assign directly to selected project
        currentUser.assignTaskToProject(selectedProject, task);
        
        System.out.println("✓ Timed task created and assigned to '" + selectedProject.getName() + "' successfully!");
        System.out.println("  Task ID: " + task.getId());
        System.out.println("  Duration: " + duration + " hours");
    }
    
    private static void createTask() {
        System.out.println("\n── Create New Task ──");
        
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        LocalDate deadline = getDateInput("Enter deadline (yyyy-MM-dd) or press Enter for no deadline: ");
        Priority priority = getPriorityInput();
        
        Task task = new Task(title, description, deadline, priority);
        NotificationService.notifyTaskCreated(task);
        
        System.out.println("✓ Task created successfully!");
        System.out.println("  Task ID: " + task.getId());
        
        // Ask if user wants to assign to project
        String assign = getStringInput("Do you want to assign this task to a project? (y/n): ");
        if (assign.equalsIgnoreCase("y")) {
            assignNewTaskToProject(task);
        } else {
            currentUser.addPersonalTask(task);
            System.out.println("✓ Task added to personal tasks.");
        }
    }
    
    private static void createTimedTask() {
        System.out.println("\n── Create New Timed Task ──");
        
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        LocalDate deadline = getDateInput("Enter deadline (yyyy-MM-dd) or press Enter for no deadline: ");
        Priority priority = getPriorityInput();
        int duration = getIntInput("Enter estimated duration (hours): ");
        
        TimedTask task = new TimedTask(title, description, deadline, priority, duration);
        NotificationService.notifyTaskCreated(task);
        
        System.out.println("✓ Timed task created successfully!");
        System.out.println("  Task ID: " + task.getId());
        System.out.println("  Duration: " + duration + " hours");
        
        // Ask if user wants to assign to project
        String assign = getStringInput("Do you want to assign this task to a project? (y/n): ");
        if (assign.equalsIgnoreCase("y")) {
            assignNewTaskToProject(task);
        } else {
            currentUser.addPersonalTask(task);
            System.out.println("✓ Task added to personal tasks.");
        }
    }
    
    private static void addPersonalTask() {
        System.out.println("\n── Add Personal Task ──");
        
        String title = getStringInput("Enter task title: ");
        String description = getStringInput("Enter task description: ");
        LocalDate deadline = getDateInput("Enter deadline (yyyy-MM-dd) or press Enter for no deadline: ");
        Priority priority = getPriorityInput();
        
        Task task = new Task(title, description, deadline, priority);
        currentUser.addPersonalTask(task);
        NotificationService.notifyTaskCreated(task);
        
        System.out.println("✓ Personal task added successfully!");
    }
    
    private static void viewAllTasks() {
        List<Task> allTasks = getAllTasksForCurrentUser();
        
        if (allTasks.isEmpty()) {
            System.out.println("\n✗ No tasks found.");
            return;
        }
        
        System.out.println("\n── All Tasks ──");
        for (Task task : allTasks) {
            displayTask(task);
        }
        System.out.println("Total tasks: " + allTasks.size());
    }
    
    private static void viewUpcomingTasks() {
        List<Task> upcomingTasks = currentUser.getAllUpcomingTasks();
        
        if (upcomingTasks.isEmpty()) {
            System.out.println("\n✗ No upcoming tasks found.");
            return;
        }
        
        System.out.println("\n── Upcoming Tasks ──");
        for (Task task : upcomingTasks) {
            displayTask(task);
        }
        System.out.println("Total upcoming tasks: " + upcomingTasks.size());
    }
    
    private static void markTaskCompleted() {
        List<Task> allTasks = getAllTasksForCurrentUser();
        
        if (allTasks.isEmpty()) {
            System.out.println("\n✗ No tasks available.");
            return;
        }
        
        System.out.println("\n── Mark Task as Completed ──");
        System.out.println("Available tasks:");
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            System.out.println((i + 1) + ". [ID:" + task.getId() + "] " + 
                             task.getTitle() + " - " + 
                             (task.isCompleted() ? "✓ Completed" : "○ Pending"));
        }
        
        int taskIndex = getIntInput("Select task number to mark as completed: ") - 1;
        
        if (taskIndex >= 0 && taskIndex < allTasks.size()) {
            Task task = allTasks.get(taskIndex);
            if (task.isCompleted()) {
                System.out.println("✗ Task is already completed!");
            } else {
                currentUser.completeTask(task);
                System.out.println("✓ Task marked as completed!");
            }
        } else {
            System.out.println("✗ Invalid task selection.");
        }
    }
    
    private static void assignPersonalTaskToProject() {
        List<Project> projects = currentUser.getProjects();
        List<Task> personalTasks = currentUser.getPersonalTasks();
        
        if (projects.isEmpty()) {
            System.out.println("\n✗ No projects available. Create a project first.");
            return;
        }
        
        if (personalTasks.isEmpty()) {
            System.out.println("\n✗ No personal tasks available.");
            System.out.println("  Use option 3 or 4 to create tasks directly for projects.");
            return;
        }
        
        System.out.println("\n── Assign Personal Task to Project ──");
        
        // Select project
        System.out.println("Select project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        int projectIndex = getIntInput("Enter project number: ") - 1;
        
        if (projectIndex < 0 || projectIndex >= projects.size()) {
            System.out.println("✗ Invalid project selection.");
            return;
        }
        
        // Select task
        System.out.println("\nSelect task:");
        for (int i = 0; i < personalTasks.size(); i++) {
            Task task = personalTasks.get(i);
            System.out.println((i + 1) + ". [ID:" + task.getId() + "] " + task.getTitle());
        }
        int taskIndex = getIntInput("Enter task number: ") - 1;
        
        if (taskIndex < 0 || taskIndex >= personalTasks.size()) {
            System.out.println("✗ Invalid task selection.");
            return;
        }
        
        Project selectedProject = projects.get(projectIndex);
        Task selectedTask = personalTasks.get(taskIndex);
        
        currentUser.assignTaskToProject(selectedProject, selectedTask);
        System.out.println("✓ Task assigned to project successfully!");
    }
    
    private static void assignNewTaskToProject(Task task) {
        List<Project> projects = currentUser.getProjects();
        
        if (projects.isEmpty()) {
            System.out.println("✗ No projects available. Task added to personal tasks.");
            currentUser.addPersonalTask(task);
            return;
        }
        
        System.out.println("\nSelect project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        int projectIndex = getIntInput("Enter project number: ") - 1;
        
        if (projectIndex >= 0 && projectIndex < projects.size()) {
            currentUser.assignTaskToProject(projects.get(projectIndex), task);
            System.out.println("✓ Task assigned to project successfully!");
        } else {
            System.out.println("✗ Invalid selection. Task added to personal tasks.");
            currentUser.addPersonalTask(task);
        }
    }
    
    // ==================== DATA EXPORT ====================
    
    private static void exportTasksToCSV() {
        List<Task> allTasks = getAllTasksForCurrentUser();
        
        if (allTasks.isEmpty()) {
            System.out.println("\n✗ No tasks to export.");
            return;
        }
        
        System.out.println("\n── Export Tasks to CSV ──");
        String filename = getStringInput("Enter filename (e.g., my_tasks.csv): ");
        
        if (!filename.endsWith(".csv")) {
            filename += ".csv";
        }
        
        try {
            CSVExporter.exportTasksToCSV(allTasks, filename);
        } catch (IOException e) {
            System.err.println("✗ Error exporting tasks: " + e.getMessage());
        }
    }
    
    private static void exportComprehensiveData() {
        if (allUsers.isEmpty()) {
            System.out.println("\n✗ No users to export.");
            return;
        }
        
        System.out.println("\n── Export Comprehensive Data ──");
        String filename = getStringInput("Enter filename (e.g., all_data.csv): ");
        
        if (!filename.endsWith(".csv")) {
            filename += ".csv";
        }
        
        try {
            CSVExporter.exportComprehensiveDataToCSV(allUsers, filename);
        } catch (IOException e) {
            System.err.println("✗ Error exporting data: " + e.getMessage());
        }
    }
    
    // ==================== DISPLAY HELPERS ====================
    
    private static void viewUserSummary() {
        System.out.println("\n── User Summary ──");
        System.out.println("Name: " + currentUser.getName());
        System.out.println("Total Projects: " + currentUser.getProjects().size());
        System.out.println("Personal Tasks: " + currentUser.getPersonalTasks().size());
        System.out.println("Upcoming Tasks: " + currentUser.getAllUpcomingTasks().size());
        System.out.println("Total Tasks: " + getAllTasksForCurrentUser().size());
    }
    
    private static void displayTask(Task task) {
        String status = task.isCompleted() ? "✓ Completed" : 
                       task.isOverdue() ? "✗ Overdue" : "○ Pending";
        
        String taskType = (task instanceof TimedTask) ? 
            " [Timed: " + ((TimedTask)task).getEstimatedDuration() + "h]" : "";
        
        System.out.println("  [ID:" + task.getId() + "] " + task.getTitle() + taskType);
        System.out.println("    Status: " + status + " | Priority: " + task.getPriority());
        System.out.println("    Deadline: " + (task.getDeadline() != null ? task.getDeadline() : "No deadline"));
        System.out.println("    Description: " + task.getDescription());
        System.out.println();
    }
    
    // ==================== INPUT HELPERS ====================
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a number.");
            }
        }
    }
    
    private static LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                return null; // No deadline
            }
            
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("✗ Invalid date format. Use yyyy-MM-dd (e.g., 2025-01-15)");
            }
        }
    }
    
    private static Priority getPriorityInput() {
        while (true) {
            System.out.println("Select priority:");
            System.out.println("1. LOW");
            System.out.println("2. MEDIUM");
            System.out.println("3. HIGH");
            int choice = getIntInput("Enter choice (1-3): ");
            
            switch (choice) {
                case 1: return Priority.LOW;
                case 2: return Priority.MEDIUM;
                case 3: return Priority.HIGH;
                default: System.out.println("✗ Invalid choice. Please select 1-3.");
            }
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    private static List<Task> getAllTasksForCurrentUser() {
        List<Task> allTasks = new ArrayList<>();
        
        // Add tasks from all projects
        for (Project project : currentUser.getProjects()) {
            allTasks.addAll(project.getAllTasks());
        }
        
        // Add personal tasks
        allTasks.addAll(currentUser.getPersonalTasks());
        
        return allTasks;
    }
    
    private static int getTotalTasksForUser(User user) {
        int total = 0;
        for (Project project : user.getProjects()) {
            total += project.getAllTasks().size();
        }
        return total;
    }
}