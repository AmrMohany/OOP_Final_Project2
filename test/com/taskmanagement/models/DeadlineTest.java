package com.taskmanagement.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


class DeadlineTest {
    
    @Test
    void testIsOverdueWithFutureDeadline() {
        LocalDate futureDeadline = LocalDate.now().plusDays(5);
        assertFalse(Deadline.isOverdue(futureDeadline, false));
    }
    
    @Test
    void testIsOverdueWithPastDeadline() {
        LocalDate pastDeadline = LocalDate.now().minusDays(3);
        assertTrue(Deadline.isOverdue(pastDeadline, false));
    }
    
    @Test
    void testIsOverdueWithCompletedTask() {
        LocalDate pastDeadline = LocalDate.now().minusDays(3);
        
        assertFalse(Deadline.isOverdue(pastDeadline, true)); // Completed tasks should not be considered overdue
    }
    
    @Test
    void testIsOverdueWithNullDeadline() {
        
        assertFalse(Deadline.isOverdue(null, false)); // Null deadline should not be overdue
    }
    
    @Test
    void testIsOverdueWithTodayDeadline() {
        LocalDate today = LocalDate.now();
        
        assertFalse(Deadline.isOverdue(today, false)); // Today's deadline is not overdue yet
    }
    
    @Test
    void testIsUpcomingWithFutureDeadline() {
        LocalDate futureDeadline = LocalDate.now().plusDays(7);
        assertTrue(Deadline.isUpcoming(futureDeadline, false));
    }
    
    @Test
    void testIsUpcomingWithPastDeadline() {
        LocalDate pastDeadline = LocalDate.now().minusDays(2);
        
        assertFalse(Deadline.isUpcoming(pastDeadline, false)); // Overdue tasks are not upcoming
    }
    
    @Test
    void testIsUpcomingWithCompletedTask() {
        LocalDate futureDeadline = LocalDate.now().plusDays(5);
        
        assertFalse(Deadline.isUpcoming(futureDeadline, true)); // Completed tasks are not upcoming
    }
    
    @Test
    void testIsUpcomingWithNullDeadline() {
        
        assertTrue(Deadline.isUpcoming(null, false)); // Tasks with no deadline should be considered upcoming if not completed
    }
    
    @Test
    void testGetDaysUntilDeadlineWithFutureDate() {
        LocalDate futureDeadline = LocalDate.now().plusDays(10);
        long days = Deadline.getDaysUntilDeadline(futureDeadline);
        assertEquals(10, days);
    }
    
    @Test
    void testGetDaysUntilDeadlineWithPastDate() {
        LocalDate pastDeadline = LocalDate.now().minusDays(5);
        long days = Deadline.getDaysUntilDeadline(pastDeadline);
        assertEquals(-5, days);
    }
    
    @Test
    void testGetDaysUntilDeadlineWithToday() {
        LocalDate today = LocalDate.now();
        long days = Deadline.getDaysUntilDeadline(today);
        assertEquals(0, days);
    }
    
    @Test
    void testGetDaysUntilDeadlineWithNull() {
        // Null deadline should return max value (no deadline)
        long days = Deadline.getDaysUntilDeadline(null);
        assertEquals(Long.MAX_VALUE, days);
    }
    
    @Test
    void testIsOverdueAndUpcomingLogicConsistency() {
        LocalDate futureDeadline = LocalDate.now().plusDays(5);
        LocalDate pastDeadline = LocalDate.now().minusDays(3);
        
        // Future deadline: not overdue, is upcoming
        assertFalse(Deadline.isOverdue(futureDeadline, false));
        assertTrue(Deadline.isUpcoming(futureDeadline, false));
        
        // Past deadline: is overdue, not upcoming
        assertTrue(Deadline.isOverdue(pastDeadline, false));
        assertFalse(Deadline.isUpcoming(pastDeadline, false));
    }
    
    @Test
    void testCompletedTaskLogic() {
        LocalDate deadline = LocalDate.now().plusDays(5);
        
        // Not completed: upcoming
        assertTrue(Deadline.isUpcoming(deadline, false));
        assertFalse(Deadline.isOverdue(deadline, false));
        
        // Completed: not upcoming, not overdue
        assertFalse(Deadline.isUpcoming(deadline, true));
        assertFalse(Deadline.isOverdue(deadline, true));
    }
}