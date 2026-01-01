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
    
    /**
     * Constructor to create a new task.
     */
    public Task(String title, String description, LocalDate deadline, Priority priority) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
    }
}