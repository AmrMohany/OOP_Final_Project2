package com.taskmanagement.models;

import java.util.ArrayList;
import java.util.List;


public class User { // This class Represents a user who owns projects and manages tasks
    
    private String name;
    private List<Project> projects;
    private List<Task> personalTasks;
    
    
    public User(String name) { // Constructor to create a new user
        this.name = name;
        this.projects = new ArrayList<>();
        this.personalTasks = new ArrayList<>();
    }
    
    public String getName() { // Gets the user name
        return name;
    }
    
    
    public void setName(String name) { // Sets the user name
        this.name = name;
    }
    
    
    public void addProject(Project project) { // method to add projects to user
        projects.add(project);
    }
    
    
    public List<Project> getProjects() { // method returning defensive copy
        return new ArrayList<>(projects);
    }
}