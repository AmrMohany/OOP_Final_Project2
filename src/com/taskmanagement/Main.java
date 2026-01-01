package com.taskmanagement;

import com.taskmanagement.models.*;
import com.taskmanagement.utiles.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("== Task & Project Management System ==\n");
        
        // Create a user
        User user = new User("Amr Hassan");
        System.out.println("User created: " + user.getName() + "\n");
        
        // Create a project
        Project webProject = new Project("Website Redesign");
        System.out.println("Project created: " + webProject.getName() + "\n");
        
        // Add project to user
        user.addProject(webProject);
        
        // Create tasks
        System.out.println("- Creating Tasks -\n");
        
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
        
        // Create a timed task
        TimedTask timedTask = new TimedTask(
            "Code Review",
            "Review pull requests from team",
            LocalDate.now().plusDays(3),
            Priority.MEDIUM,
            4
        );
        NotificationService.notifyTaskCreated(timedTask);
        
        // Assign tasks to project
        System.out.println("- Assigning Tasks to Project -\n");
        user.assignTaskToProject(webProject, task1);
        user.assignTaskToProject(webProject, task2);
        user.assignTaskToProject(webProject, task3);
        user.assignTaskToProject(webProject, timedTask);
        System.out.println("Tasks assigned to project: " + webProject.getName() + "\n");
        
        // Add a personal task
        Task personalTask = new Task(
            "Learn Java Streams",
            "Complete Java Streams tutorial",
            LocalDate.now().plusDays(10),
            Priority.LOW
        );
        NotificationService.notifyTaskCreated(personalTask);
        user.addPersonalTask(personalTask);
        
        // Display all tasks in project
        System.out.println("- All Tasks in Project -\n");
        List<Task> allTasks = webProject.getAllTasks();
        for (Task task : allTasks) {
            System.out.println(task);
        }
        System.out.println();
        
        // Mark some tasks as completed
        System.out.println("- Marking Tasks as Completed -\n");
        user.completeTask(task2);
        NotificationService.notifyTaskCompleted(task2);
        
        user.completeTask(task3);
        NotificationService.notifyTaskCompleted(task3);
        
        // Display upcoming tasks
        System.out.println("- Upcoming Tasks -\n");
        List<Task> upcomingTasks = user.getAllUpcomingTasks();
        System.out.println("Total upcoming tasks: " + upcomingTasks.size() + "\n");
        for (Task task : upcomingTasks) {
            System.out.println(task);
        }
        System.out.println();
        
        // Display user summary
        System.out.println("- User Summary -\n");
        System.out.println(user);
        System.out.println();
        
        // Export tasks to CSV (basic export)
        System.out.println("- Exporting Tasks to CSV -\n");
        try {
            CSVExporter.exportTasksToCSV(allTasks, "tasks_export.csv");
        } catch (IOException e) {
            System.err.println("Error exporting tasks: " + e.getMessage());
        }
        
        // Create comprehensive CSV export with detailed information
        System.out.println("== Comprehensive Data Export ==\n");
        
        // Create a second user for demonstration
        User user2 = new User("Nada Mousa");
        Project mobileProject = new Project("Mobile App Development");
        
        Task mobileTask1 = new Task(
            "Design UI Mockups",
            "Create mobile app UI mockups for iOS and Android",
            LocalDate.now().plusDays(5),
            Priority.HIGH
        );
        
        Task mobileTask2 = new Task(
            "Setup Backend API",
            "Configure cloud backend and database",
            LocalDate.now().minusDays(1), // Overdue
            Priority.MEDIUM
        );
        
        TimedTask mobileTask3 = new TimedTask(
            "Write Unit Tests",
            "Write comprehensive unit tests for all modules",
            LocalDate.now().plusDays(8),
            Priority.MEDIUM,
            6
        );
        
        user2.addProject(mobileProject);
        user2.assignTaskToProject(mobileProject, mobileTask1);
        user2.assignTaskToProject(mobileProject, mobileTask2);
        user2.assignTaskToProject(mobileProject, mobileTask3);
        
        // Mark one task as completed
        user2.completeTask(mobileTask2);
        
        // Create third user with only personal tasks
        User user3 = new User("Wanees Amr");
        Task personalTask2 = new Task(
            "Learn Docker",
            "Complete Docker tutorial and certification",
            LocalDate.now().plusDays(15),
            Priority.LOW
        );
        user3.addPersonalTask(personalTask2);
        
        // Prepare list of all users
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user);
        allUsers.add(user2);
        allUsers.add(user3);
        
        // Export comprehensive data
        try {
            CSVExporter.exportComprehensiveDataToCSV(allUsers, "comprehensive_tasks_data.csv");
        } catch (IOException e) {
            System.err.println("Error exporting comprehensive data: " + e.getMessage());
        }
        
        System.out.println("== Application Complete ==");
    }
}