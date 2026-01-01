package com.taskmanagement.models;

import java.time.LocalDate;

public class Task implements Completable {
    
    // Private fields for encapsulation
    private static int idCounter = 1;
    private int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Priority priority;
    private boolean completed;
    

    public Task(String title, String description, LocalDate deadline, Priority priority) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
    }
    
    
    public int getId() { //Gets the unique task ID
        return id;
    }
    
    
    public String getTitle() { //Gets the task title
        return title;
    }
    
    
    public void setTitle(String title) { //Sets the task title
        this.title = title;
    }
    
    
    public String getDescription() { //Gets the task description
        return description;
    }
    
    
    public void setDescription(String description) { //Sets the task description
        this.description = description;
    }
    
   
    public LocalDate getDeadline() { //Gets the task deadline
        return deadline;
    }
    
    
    public void setDeadline(LocalDate deadline) { //Sets the task deadline
        this.deadline = deadline;
    }
    
   
    public Priority getPriority() { //Gets the task priority
        return priority;
    }
    
   
    public void setPriority(Priority priority) { //Sets the task priority
        this.priority = priority;
    }
    
    @Override
    public void markAsCompleted() { //method to set task completion status
        this.completed = true;
    }
    

    @Override
    public boolean isCompleted() { // method to check task completion status.return true if the task is completed, false otherwise
        return completed;
    }
}