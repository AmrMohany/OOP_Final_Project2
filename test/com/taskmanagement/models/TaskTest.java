package com.taskmanagement.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TaskTest {
    
    private Task task;
    
    @BeforeEach
    void setUp() {
        
        task = new Task( // Create a task before each test
            "Test Task",
            "This is a test task",
            LocalDate.now().plusDays(5),
            Priority.MEDIUM
        );
    }
    
    @Test
    void testTaskCreation() {
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("This is a test task", task.getDescription());
        assertEquals(Priority.MEDIUM, task.getPriority());
        assertFalse(task.isCompleted());
    }
    
    @Test
    void testTaskCompletion() {
        assertFalse(task.isCompleted());
        task.markAsCompleted();
        assertTrue(task.isCompleted());
    }
    
    @Test
    void testTaskIsUpcoming() {
        
        assertTrue(task.isUpcoming()); // Task with future deadline and not completed should be upcoming
        
        task.markAsCompleted(); // When we mark it as completed should not be upcoming
        assertFalse(task.isUpcoming());
    }
    
    @Test
    void testTaskIsOverdue() {
        
        assertFalse(task.isOverdue()); // Task with future deadline should not be overdue
        
        
        Task overdueTask = new Task( 
            "Overdue Task",
            "This task is overdue",
            LocalDate.now().minusDays(2),
            Priority.HIGH
        );
        assertTrue(overdueTask.isOverdue());
        
        
        overdueTask.markAsCompleted(); // Completed overdue task should not be marked as overdue
        assertFalse(overdueTask.isOverdue());
    }
    
    @Test
    void testTaskWithNullDeadline() {
        Task noDeadlineTask = new Task(
            "No Deadline",
            "Task without deadline",
            null,
            Priority.LOW
        );
        
        assertFalse(noDeadlineTask.isOverdue());
        assertTrue(noDeadlineTask.isUpcoming());
    }
    
    @Test
    void testSettersAndGetters() {
        task.setTitle("Updated Title");
        assertEquals("Updated Title", task.getTitle());
        
        task.setDescription("Updated Description");
        assertEquals("Updated Description", task.getDescription());
        
        task.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, task.getPriority());
        
        LocalDate newDeadline = LocalDate.now().plusDays(10);
        task.setDeadline(newDeadline);
        assertEquals(newDeadline, task.getDeadline());
    }
}