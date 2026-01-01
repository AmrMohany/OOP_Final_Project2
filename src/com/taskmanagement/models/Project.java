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
}