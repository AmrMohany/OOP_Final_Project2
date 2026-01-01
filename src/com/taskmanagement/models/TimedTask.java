package com.taskmanagement.models;

import java.time.LocalDate;


public class TimedTask extends Task { //Create TimedTask class that inherits from Task
    
    private int estimatedDuration; // in hours
    
    
    public TimedTask(String title, String description, LocalDate deadline, Priority priority, int estimatedDuration) { // Constructor to create a new timed task
        super(title, description, deadline, priority);
        this.estimatedDuration = estimatedDuration;
    }
    
    
    public int getEstimatedDuration() { // Gets the estimated duration
        return estimatedDuration;
    }
    
    
    public void setEstimatedDuration(int estimatedDuration) { // Sets the estimated duration
        this.estimatedDuration = estimatedDuration;
    }
    
   
    @Override
    public String toString() { // method to include duration information
        return "TimedTask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", deadline=" + getDeadline() +
                ", priority=" + getPriority() +
                ", completed=" + isCompleted() +
                ", estimatedDuration=" + estimatedDuration + " hours" +
                '}';
    }
}