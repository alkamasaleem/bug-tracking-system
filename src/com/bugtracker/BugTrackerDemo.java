package com.bugtracker;

import com.bugtracker.system.BugTrackingSystem;
import com.bugtracker.model.Bug;
import com.bugtracker.enums.*;
import com.bugtracker.exception.BugTrackingException;
import java.util.List;

public class BugTrackerDemo {
    public static void main(String[] args) {
        BugTrackingSystem system = new BugTrackingSystem();
        
        try {
            System.out.println("=== Bug Tracking System Demo ===\n");
            
            // Create some sample bugs
            String bug1 = system.createBug(
                "Login page crashes on mobile", 
                "The login page crashes when accessed from mobile Safari browser", 
                Priority.HIGH, 
                "TEST001"
            );
            
            String bug2 = system.createBug(
                "Search results not displaying correctly", 
                "Search results show empty list even when data exists", 
                Priority.MEDIUM, 
                "DEV001"
            );
            
            String bug3 = system.createBug(
                "Database connection timeout", 
                "Application throws timeout exception when connecting to database", 
                Priority.CRITICAL, 
                "PM001"
            );
            
            // Assign bugs
            system.assignBug(bug1, "DEV001");
            system.assignBug(bug2, "DEV001");
            system.assignBug(bug3, "ADMIN001");
            
            // Add tags
            system.addTagToBug(bug1, "mobile");
            system.addTagToBug(bug1, "ui");
            system.addTagToBug(bug2, "search");
            system.addTagToBug(bug2, "ui");
            system.addTagToBug(bug3, "database");
            system.addTagToBug(bug3, "critical");
            
            // Add comments
            system.addComment(bug1, "Reproduced the issue on iPhone 12 Safari", "TEST001");
            system.addComment(bug1, "Working on a fix, will have it ready by tomorrow", "DEV001");
            system.addComment(bug2, "Need more details about the search query used", "DEV001");
            
            // Update status
            system.updateBugStatus(bug1, Status.IN_PROGRESS);
            system.updateBugStatus(bug3, Status.IN_PROGRESS);
            
            // Print summary
            system.printBugSummary();
            
            // Print specific bug details
            system.printBugDetails(bug1);
            
            // Search examples
            System.out.println("\n=== Search Examples ===");
            System.out.println("High priority bugs:");
            List<Bug> highPriorityBugs = system.searchBugsByPriority(Priority.HIGH);
            highPriorityBugs.forEach(bug -> System.out.println("  " + bug.getBugId() + ": " + bug.getTitle()));
            
            System.out.println("\nBugs assigned to DEV001:");
            List<Bug> devBugs = system.searchBugsByAssignee("DEV001");
            devBugs.forEach(bug -> System.out.println("  " + bug.getBugId() + ": " + bug.getTitle()));
            
            System.out.println("\nBugs with 'ui' tag:");
            List<Bug> uiBugs = system.searchBugsByTag("ui");
            uiBugs.forEach(bug -> System.out.println("  " + bug.getBugId() + ": " + bug.getTitle()));
            
        } catch (BugTrackingException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}