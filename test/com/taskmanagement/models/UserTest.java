package com.taskmanagement.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserTest {
    
    private User user;
    private Project project1;
    private Project project2;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task personalTask;
    
    @BeforeEach
    void setUp() {
        
        user = new User("Test User"); // Create a user before each test
        
        // Create test projects
        project1 = new Project("Project 1");
        project2 = new Project("Project 2");
        
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
            "Overdue task",
            LocalDate.now().minusDays(2),
            Priority.LOW
        );
        
        personalTask = new Task(
            "Personal Task",
            "Personal task",
            LocalDate.now().plusDays(7),
            Priority.MEDIUM
        );
    }
    
    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals(0, user.getProjects().size());
        assertEquals(0, user.getPersonalTasks().size());
    }
    
    @Test
    void testAddProject() {
        user.addProject(project1);
        assertEquals(1, user.getProjects().size());
        
        user.addProject(project2);
        assertEquals(2, user.getProjects().size());
    }
    
    @Test
    void testGetProjects() {
        user.addProject(project1);
        user.addProject(project2);
        
        List<Project> projects = user.getProjects();
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
    }
    
    @Test
    void testAddPersonalTask() {
        user.addPersonalTask(personalTask);
        assertEquals(1, user.getPersonalTasks().size());
        
        user.addPersonalTask(task1);
        assertEquals(2, user.getPersonalTasks().size());
    }
    
    @Test
    void testGetPersonalTasks() {
        user.addPersonalTask(personalTask);
        user.addPersonalTask(task1);
        
        List<Task> tasks = user.getPersonalTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(personalTask));
        assertTrue(tasks.contains(task1));
    }
    
    @Test
    void testAssignTaskToProject() {
        user.addProject(project1);
        user.assignTaskToProject(project1, task1);
        
        assertEquals(1, project1.getAllTasks().size());
        assertTrue(project1.getAllTasks().contains(task1));
    }
    
    @Test
    void testCompleteTask() {
        assertFalse(task1.isCompleted());
        user.completeTask(task1);
        assertTrue(task1.isCompleted());
    }
    
    @Test
    void testGetAllUpcomingTasks() {
        // Add projects with tasks
        user.addProject(project1);
        user.assignTaskToProject(project1, task1); // upcoming
        user.assignTaskToProject(project1, task2); // upcoming
        user.assignTaskToProject(project1, task3); // overdue
        
        // Add personal task
        user.addPersonalTask(personalTask); // upcoming
        
        List<Task> upcomingTasks = user.getAllUpcomingTasks();
        
        // Should return 3 upcoming tasks (task1, task2, personalTask)
        // Should NOT include task3 (overdue)
        assertEquals(3, upcomingTasks.size());
        assertTrue(upcomingTasks.contains(task1));
        assertTrue(upcomingTasks.contains(task2));
        assertTrue(upcomingTasks.contains(personalTask));
        assertFalse(upcomingTasks.contains(task3));
    }
    
    @Test
    void testGetAllUpcomingTasksExcludesCompleted() {
        user.addProject(project1);
        user.assignTaskToProject(project1, task1);
        user.assignTaskToProject(project1, task2);
        
        // Mark task1 as completed
        task1.markAsCompleted();
        
        List<Task> upcomingTasks = user.getAllUpcomingTasks();
        
        // Should only return task2
        assertEquals(1, upcomingTasks.size());
        assertFalse(upcomingTasks.contains(task1));
        assertTrue(upcomingTasks.contains(task2));
    }
    
    @Test
    void testGetAllUpcomingTasksFromMultipleProjects() {
        user.addProject(project1);
        user.addProject(project2);
        
        user.assignTaskToProject(project1, task1);
        user.assignTaskToProject(project2, task2);
        
        List<Task> upcomingTasks = user.getAllUpcomingTasks();
        
        // Should return tasks from both projects
        assertEquals(2, upcomingTasks.size());
        assertTrue(upcomingTasks.contains(task1));
        assertTrue(upcomingTasks.contains(task2));
    }
    
    @Test
    void testUserNameGetterSetter() {
        assertEquals("Test User", user.getName());
        
        user.setName("Updated User");
        assertEquals("Updated User", user.getName());
    }
    
    @Test
    void testDefensiveCopyOfProjects() {
        user.addProject(project1);
        
        List<Project> projects = user.getProjects();
        projects.add(project2); // Try to modify returned list
        
        // Original user should still have only 1 project
        assertEquals(1, user.getProjects().size());
    }
    
    @Test
    void testDefensiveCopyOfPersonalTasks() {
        user.addPersonalTask(personalTask);
        
        List<Task> tasks = user.getPersonalTasks();
        tasks.add(task1); // Try to modify returned list
        
        // Original user should still have only 1 personal task
        assertEquals(1, user.getPersonalTasks().size());
    }
    
    @Test
    void testUserToString() {
        user.addProject(project1);
        user.addPersonalTask(personalTask);
        
        String result = user.toString();
        
        assertTrue(result.contains("Test User"));
        assertTrue(result.contains("projects=1"));
        assertTrue(result.contains("personalTasks=1"));
    }
}