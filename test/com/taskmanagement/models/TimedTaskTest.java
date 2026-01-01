package com.taskmanagement.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TimedTaskTest {
    
    private TimedTask timedTask;
    
    @BeforeEach
    void setUp() {
        
        timedTask = new TimedTask( // Create a timed task before each test
            "Timed Test Task",
            "This is a timed test task",
            LocalDate.now().plusDays(7),
            Priority.HIGH,
            8
        );
    }
    
    @Test
    void testTimedTaskCreation() {
        assertNotNull(timedTask);
        assertEquals("Timed Test Task", timedTask.getTitle());
        assertEquals("This is a timed test task", timedTask.getDescription());
        assertEquals(Priority.HIGH, timedTask.getPriority());
        assertEquals(8, timedTask.getEstimatedDuration());
        assertFalse(timedTask.isCompleted());
    }
    
    @Test
    void testTimedTaskInheritance() {
        
        assertTrue(timedTask instanceof Task); // TimedTask should inherit from Task
        
        
        assertTrue(timedTask instanceof Completable); // TimedTask should implement Completable
    }
    
    @Test
    void testEstimatedDurationGetterSetter() {
        assertEquals(8, timedTask.getEstimatedDuration());
        
        timedTask.setEstimatedDuration(12);
        assertEquals(12, timedTask.getEstimatedDuration());
    }
    
    @Test
    void testTimedTaskCompletion() {
        assertFalse(timedTask.isCompleted());
        timedTask.markAsCompleted();
        assertTrue(timedTask.isCompleted());
    }
    
    @Test
    void testTimedTaskInheritsDeadlineFunctionality() {
        
        assertTrue(timedTask.isUpcoming()); // Test that TimedTask inherits isUpcoming from Task
        
        assertFalse(timedTask.isOverdue()); // Test that TimedTask inherits isOverdue from Task
        
        
        TimedTask overdueTimedTask = new TimedTask( // Create overdue timed task
            "Overdue Timed Task",
            "This is overdue",
            LocalDate.now().minusDays(3),
            Priority.HIGH,
            5
        );
        
        assertTrue(overdueTimedTask.isOverdue());
        assertFalse(overdueTimedTask.isUpcoming());
    }
    
    @Test
    void testTimedTaskToString() {
        String result = timedTask.toString();
        
        // Verify toString includes duration information
        assertTrue(result.contains("TimedTask"));
        assertTrue(result.contains("estimatedDuration"));
        assertTrue(result.contains("8 hours"));
    }
    
    @Test
    void testPolymorphism() {
        
        Task task = timedTask; // TimedTask can be treated as Task (polymorphism)
        
        assertEquals("Timed Test Task", task.getTitle());
        assertFalse(task.isCompleted());
        
        task.markAsCompleted();
        assertTrue(task.isCompleted());
    }
}