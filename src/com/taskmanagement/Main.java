package com.taskmanagement;

import com.taskmanagement.models.*;
import com.taskmanagement.utiles.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Task & Project Management System ===\n");
        
        
        User user = new User("Amr Hassan"); // Create a user
        System.out.println("User created: " + user.getName() + "\n");
        
        
        Project webProject = new Project("Website Redesign"); // Create a project
        System.out.println("Project created: " + webProject.getName() + "\n");
        
        
        user.addProject(webProject); // Add project to user
        
        
        System.out.println("--- Creating Tasks ---\n"); // Create tasks
        
        Task task1 = new Task(
            "Design Homepage",
            "Create mockups for the new homepage design",
            LocalDate.now().plusDays(7),
            Priority.HIGH
        );
        NotificationService.notifyTaskCreated(task1);
        
        Task task2 = new Task(
            "Write Documentation",
            "Document the API endpoints",
            LocalDate.now().plusDays(14),
            Priority.MEDIUM
        );
        NotificationService.notifyTaskCreated(task2);
        
        Task task3 = new Task(
            "Fix Bug #123",
            "Resolve the login issue",
            LocalDate.now().minusDays(2), // Overdue task
            Priority.HIGH
        );
        NotificationService.notifyTaskCreated(task3);
        
        
        TimedTask timedTask = new TimedTask( // Create a timed task
            "Code Review",
            "Review pull requests from team",
            LocalDate.now().plusDays(3),
            Priority.MEDIUM,
            4 
        );
        NotificationService.notifyTaskCreated(timedTask);
        
        
        System.out.println("--- Assigning Tasks to Project ---\n"); // Assign tasks to project
        user.assignTaskToProject(webProject, task1);
        user.assignTaskToProject(webProject, task2);
        user.assignTaskToProject(webProject, task3);
        user.assignTaskToProject(webProject, timedTask);
        System.out.println("Tasks assigned to project: " + webProject.getName() + "\n");
        
        
        Task personalTask = new Task( // Add a personal task
            "Learn Java Streams",
            "Complete Java Streams tutorial",
            LocalDate.now().plusDays(10),
            Priority.LOW
        );
        NotificationService.notifyTaskCreated(personalTask);
        user.addPersonalTask(personalTask);
        
        
        System.out.println("--- All Tasks in Project ---\n"); // Display all tasks in project
        List<Task> allTasks = webProject.getAllTasks();
        for (Task task : allTasks) {
            System.out.println(task);
        }
        System.out.println();
        
        
        System.out.println("--- Marking Tasks as Completed ---\n"); // Mark some tasks as completed
        user.completeTask(task2);
        NotificationService.notifyTaskCompleted(task2);
        
        user.completeTask(task3);
        NotificationService.notifyTaskCompleted(task3);
        
        
        System.out.println("--- Upcoming Tasks ---\n"); // Display upcoming tasks
        List<Task> upcomingTasks = user.getAllUpcomingTasks();
        System.out.println("Total upcoming tasks: " + upcomingTasks.size() + "\n");
        for (Task task : upcomingTasks) {
            System.out.println(task);
        }
        System.out.println();
        
        
        System.out.println("--- User Summary ---\n"); // Display user summary
        System.out.println(user);
        System.out.println();
        
        
        System.out.println("--- Exporting Tasks to CSV ---\n"); // Export tasks to CSV
        try {
            CSVExporter.exportTasksToCSV(allTasks, "tasks_export.csv");
        } catch (IOException e) {
            System.err.println("Error exporting tasks: " + e.getMessage());
        }
        
        System.out.println("=== Application Complete ===");
    }
}