	package com.taskmanagement.utiles;

import com.taskmanagement.models.Task;


public class NotificationService {
    
	
    public static void notifyTaskCreated(Task task) { // method to announce new tasks
        System.out.println("✓ NOTIFICATION:\n" + "Task created : " + task.getTitle());
        System.out.println("ID: " + task.getId() + "\n" + 
                           "Priority: " + task.getPriority() + "\n" +
                           "Deadline: " + task.getDeadline());
        System.out.println();
    }
    
    
    public static void notifyTaskCompleted(Task task) { // method to announce completed tasks
        System.out.println("✓ NOTIFICATION:\n" + "Task completed - " + task.getTitle());
        System.out.println("ID: " + task.getId() + " | Marked as DONE");
        System.out.println();
    }
}