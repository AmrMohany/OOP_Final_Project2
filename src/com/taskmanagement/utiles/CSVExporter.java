package com.taskmanagement.utiles;

import com.taskmanagement.models.Task;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class CSVExporter {
    
	
    public static void exportTasksToCSV(List<Task> tasks, String filename) throws IOException { // method to write tasks to CSV file
        FileWriter writer = new FileWriter(filename);
        
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
        
        writer.flush();
        writer.close();
        
        System.out.println("✓ Tasks exported successfully to " + filename);
        System.out.println("  Total tasks exported: " + tasks.size());
        System.out.println();
    }
    
    
    private static String escapeCSV(String value) { // Helper method to handle special characters
        if (value == null) {
            return "";
        }
        
       
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
        
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        
        return value;
    }
}