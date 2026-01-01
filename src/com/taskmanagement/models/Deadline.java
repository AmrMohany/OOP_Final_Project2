package com.taskmanagement.models;

import java.time.LocalDate;


public class Deadline {
    
	
    public static boolean isOverdue(LocalDate deadline, boolean isCompleted) { // method to check if deadline has passed
        if (deadline == null || isCompleted) {
            return false;
        }
        return deadline.isBefore(LocalDate.now());
    }
    
    
    public static boolean isUpcoming(LocalDate deadline, boolean isCompleted) { // method to check if task is active and pending
        return !isCompleted && !isOverdue(deadline, isCompleted);
    }
    
    
    public static long getDaysUntilDeadline(LocalDate deadline) { // method to calculate remaining days
        if (deadline == null) {
            return Long.MAX_VALUE; // No deadline
        }
        return LocalDate.now().until(deadline).getDays();
    }
}