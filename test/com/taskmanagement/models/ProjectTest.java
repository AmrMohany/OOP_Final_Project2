package com.taskmanagement.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProjectTest {
    
    private Project project;
    private Task task1;
    private Task task2;
    private Task task3;
    
    @BeforeEach
    void setUp() {
        
        project = new Project("Test Project"); // Create a project before each test
        
     // Create test tasks
        task1 = new Task( 
            "Task 1",
            "First task",
            LocalDate.now().plusDays(5),
            Priority.HIGH
        );
        
        task2 = new Task(
            "Task 2",
            "Second task",
            LocalDate.now().plusDays(10),
            Priority.MEDIUM
        );
        
        task3 = new Task(
            "Task 3",
            "Third task - overdue",
            LocalDate.now().minusDays(2),
            Priority.LOW
        );
    }
    
    @Test
    void testProjectCreation() {
        assertNotNull(project);
        assertEquals("Test Project", project.getName());
        assertEquals(0, project.getAllTasks().size());
    }
    
    @Test
    void testAddTask() {
        project.addTask(task1);
        assertEquals(1, project.getAllTasks().size());
        
        project.addTask(task2);
        assertEquals(2, project.getAllTasks().size());
    }
    
    @Test
    void testGetAllTasks() {
        project.addTask(task1);
        project.addTask(task2);
        project.addTask(task3);
        
        List<Task> allTasks = project.getAllTasks();
        assertEquals(3, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
        assertTrue(allTasks.contains(task3));
    }
    
    @Test
    void testGetUpcomingTasks() {
        project.addTask(task1); // upcoming
        project.addTask(task2); // upcoming
        project.addTask(task3); // overdue
        
        List<Task> upcomingTasks = project.getUpcomingTasks();
        
        // Should return only upcoming tasks (not overdue, not completed)
        assertEquals(2, upcomingTasks.size());
        assertTrue(upcomingTasks.contains(task1));
        assertTrue(upcomingTasks.contains(task2));
        assertFalse(upcomingTasks.contains(task3)); // overdue task should not be included
    }
    
    @Test
    void testGetUpcomingTasksExcludesCompleted() {
        project.addTask(task1);
        project.addTask(task2);
        
        
        task1.markAsCompleted(); // Mark task1 as completed
        
        List<Task> upcomingTasks = project.getUpcomingTasks();
        
        // Should only return task2 (task1 is completed)
        assertEquals(1, upcomingTasks.size());
        assertFalse(upcomingTasks.contains(task1));
        assertTrue(upcomingTasks.contains(task2));
    }
    
    @Test
    void testProjectNameGetterSetter() {
        assertEquals("Test Project", project.getName());
        
        project.setName("Updated Project");
        assertEquals("Updated Project", project.getName());
    }
    
    @Test
    void testDefensiveCopyOfTaskList() {
        project.addTask(task1);
        
        List<Task> tasks = project.getAllTasks();
        
        
        tasks.add(task2); // Try to modify the returned list
        
        
        assertEquals(1, project.getAllTasks().size()); // Original project should still have only 1 task (defensive copy)
    }
    
    @Test
    void testEmptyProjectUpcomingTasks() {
        // Empty project should return empty list
        List<Task> upcomingTasks = project.getUpcomingTasks();
        assertNotNull(upcomingTasks);
        assertEquals(0, upcomingTasks.size());
    }
    
    @Test
    void testProjectToString() {
        project.addTask(task1);
        project.addTask(task2);
        
        String result = project.toString();
        
        assertTrue(result.contains("Test Project"));
        assertTrue(result.contains("totalTasks=2"));
    }
}