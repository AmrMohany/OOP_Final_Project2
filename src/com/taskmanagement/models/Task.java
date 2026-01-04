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
        if (title==null || title.trim().isEmpty()) {
        	throw new IllegalArgumentException ("Title can't be Empty");
        }
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
    
    public boolean isOverdue() { // method to check if task deadline has passed. return true if the task is overdue, false otherwise
        if (deadline == null || completed) {
            return false;
        }
        return deadline.isBefore(LocalDate.now());
    }
    
    
    public boolean isUpcoming() { // method to identify active pending tasks. return true if the task is upcoming, false otherwise
        return !completed && !isOverdue();
    }
    
    @Override
    public String toString() { // method to provide formatted task information
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", completed=" + completed +
                '}';
    }
}