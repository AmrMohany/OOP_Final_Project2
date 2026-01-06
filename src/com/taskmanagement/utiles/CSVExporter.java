package com.taskmanagement.utiles;

import com.taskmanagement.models.Task;
import com.taskmanagement.models.TimedTask;
import com.taskmanagement.models.User;
import com.taskmanagement.models.Project;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    
    public static void exportTasksToCSV(List<Task> tasks, String filename) throws IOException {

        // try-with-resources to ensure the file closes automatically after completion
        try (FileWriter writer = new FileWriter(filename)) {

            // header
            writer.append("Task ID,Title,Description,Deadline,Priority,Completion Status\n");
            
            // data
            for (Task task : tasks) {
                writer.append(String.valueOf(task.getId())).append(",");
                writer.append(escapeCSV(task.getTitle())).append(",");
                writer.append(escapeCSV(task.getDescription())).append(",");
                writer.append(task.getDeadline() != null ? task.getDeadline().toString() : "No Deadline").append(",");
                writer.append(task.getPriority().toString()).append(",");
                writer.append(task.isCompleted() ? "Completed" : "Pending").append("\n");
            }
        }
        
        System.out.println("✓ Tasks exported successfully to " + filename);
        System.out.println("  Total tasks exported: " + tasks.size());
        System.out.println();
    }
    
    
    // Exports comprehensive user and task information to a detailed CSV file.
    public static void exportComprehensiveDataToCSV(List<User> users, String filename) throws IOException {

        // try-with-resources to ensure the file closes automatically after completion
        try (FileWriter writer = new FileWriter(filename)) {

            // Write comprehensive CSV header
            writer.append("User ID,User Name,Project Name,Task ID,Task Title,Task Description,");
            writer.append("Deadline,Days Until Deadline,Priority,Task Status,Completion Status,");
            writer.append("Is Overdue,Is Upcoming,Task Type\n");
            
            // Counter for user IDs
            int userId = 1;
            
            // Process each user
            for (User user : users) {
                String userName = user.getName();
                String userIdStr = String.valueOf(userId);
                
                // Track if user has any tasks
                boolean hasAnyTasks = false;
                
                // Export tasks from all projects
                for (Project project : user.getProjects()) {
                    String projectName = project.getName();
                    
                    for (Task task : project.getAllTasks()) {
                        hasAnyTasks = true;
                        writeTaskRow(writer, userIdStr, userName, projectName, task);
                    }
                }
                
                // Export personal tasks (not in any project)
                for (Task task : user.getPersonalTasks()) {
                    hasAnyTasks = true;
                    writeTaskRow(writer, userIdStr, userName, "Personal Tasks", task);
                }
                
                // If user has no tasks, still show the user in the CSV
                if (!hasAnyTasks) {
                    writer.append(userIdStr).append(",");
                    writer.append(escapeCSV(userName)).append(",");
                    writer.append("No Projects,");
                    writer.append("N/A,No Tasks,No tasks assigned yet,");
                    writer.append("N/A,N/A,N/A,N/A,N/A,N/A,N/A,N/A\n");
                }
                
                userId++;
            }
        }
        
        // Calculate statistics
        int totalTasks = 0;
        int completedTasks = 0;
        int overdueTasks = 0;
        int upcomingTasks = 0;
        
        for (User user : users) {
            for (Project project : user.getProjects()) {
                for (Task task : project.getAllTasks()) {
                    totalTasks++;
                    if (task.isCompleted()) completedTasks++;
                    else if (task.isOverdue()) overdueTasks++;
                    else if (task.isUpcoming()) upcomingTasks++;
                }
            }
            for (Task task : user.getPersonalTasks()) {
                totalTasks++;
                if (task.isCompleted()) completedTasks++;
                else if (task.isOverdue()) overdueTasks++;
                else if (task.isUpcoming()) upcomingTasks++;
            }
        }
        
        System.out.println("✓ Comprehensive data exported successfully to " + filename);
        System.out.println("  Total users: " + users.size());
        System.out.println("  Total tasks: " + totalTasks);
        System.out.println("  Completed: " + completedTasks + " | Overdue: " + overdueTasks + " | Upcoming: " + upcomingTasks);
        System.out.println();
    }
    
    
    //Helper method to write a single task row to the CSV file.
    private static void writeTaskRow(FileWriter writer, String userId, String userName, 
                                     String projectName, Task task) throws IOException {
        // User ID
        writer.append(userId).append(",");
        
        // User Name
        writer.append(escapeCSV(userName)).append(",");
        
        // Project Name
        writer.append(escapeCSV(projectName)).append(",");
        
        // Task ID
        writer.append(String.valueOf(task.getId())).append(",");
        
        // Task Title
        writer.append(escapeCSV(task.getTitle())).append(",");
        
        // Task Description
        writer.append(escapeCSV(task.getDescription())).append(",");
        
        // Deadline
        if (task.getDeadline() != null) {
            writer.append(task.getDeadline().toString()).append(",");
            
            // Days Until Deadline
            long daysUntil = java.time.LocalDate.now().until(task.getDeadline(), 
                                                             java.time.temporal.ChronoUnit.DAYS);
            writer.append(String.valueOf(daysUntil)).append(",");
        } else {
            writer.append("No Deadline,");
            writer.append("N/A,");
        }
        
        // Priority
        writer.append(task.getPriority().toString()).append(",");
        
        // Task Status (detailed status)
        String taskStatus;
        if (task.isCompleted()) {
            taskStatus = "Completed";
        } else if (task.isOverdue()) {
            taskStatus = "Overdue - Needs Attention";
        } else if (task.isUpcoming()) {
            taskStatus = "In Progress";
        } else {
            taskStatus = "Pending";
        }
        writer.append(taskStatus).append(",");
        
        // Completion Status (Yes/No)
        writer.append(task.isCompleted() ? "Completed" : "Not Completed").append(",");
        
        // Is Overdue (Yes/No)
        writer.append(task.isOverdue() ? "Yes" : "No").append(",");
        
        // Is Upcoming (Yes/No)
        writer.append(task.isUpcoming() ? "Yes" : "No").append(",");
        
        // Task Type (Basic Task or Timed Task)
        String taskType = (task instanceof TimedTask) ? 
            "Timed Task (" + ((TimedTask) task).getEstimatedDuration() + "h)" : 
            "Basic Task";
        writer.append(taskType).append("\n");
    }
    
    
    //Escapes special characters in CSV fields.
    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        
        // If value contains comma, quote, or newline, wrap it in quotes
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            // Escape existing quotes by doubling them
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        
        return value;
    }
}
