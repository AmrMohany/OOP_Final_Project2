package com.taskmanagement.models;

import java.util.ArrayList;
import java.util.List;


public class Project { 
    
    private String name;
    private List<Task> tasks;
    
   
    public Project(String name) { // Constructor to create a new project
        this.name = name;
        this.tasks = new ArrayList<>();
    }
    
    public String getName() { // Gets the project name
        return name;
    }
    
    
    public void setName(String name) { // Sets the project name
        this.name = name;
    }
    
    
    public void addTask(Task task) { // method to add tasks to project
        tasks.add(task);
    }
    
    
    public List<Task> getAllTasks() { // method returning defensive copy of task list
        return new ArrayList<>(tasks);
    }
    
   
    public List<Task> getUpcomingTasks() { // method to filter active pending tasks
        List<Task> upcomingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isUpcoming()) {
                upcomingTasks.add(task);
            }
        }
        return upcomingTasks;
    }
    
    @Override
    public String toString() { // method to display project name and task counts
        return "Project{" +
                "name='" + name + '\'' +
                ", totalTasks=" + tasks.size() +
                ", upcomingTasks=" + getUpcomingTasks().size() +
                '}';
    }
}