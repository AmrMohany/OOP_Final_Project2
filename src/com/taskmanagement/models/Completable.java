package com.taskmanagement.models;

public interface Completable {
    
    void markAsCompleted(); //method for marking items complete
    
    boolean isCompleted(); //method to check completion status
}