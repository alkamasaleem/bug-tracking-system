package com.bugtracker.system;

import com.bugtracker.enums.*;
import com.bugtracker.model.*;
import com.bugtracker.exception.BugTrackingException;
import java.util.*;
import java.util.stream.Collectors;

public class BugTrackingSystem {
    private final Map<String, Bug> bugs;
    private final Map<String, User> users;
    private int nextBugId;
    private int nextCommentId;
    
    public BugTrackingSystem() {
        this.bugs = new HashMap<>();
        this.users = new HashMap<>();
        this.nextBugId = 1;
        this.nextCommentId = 1;
        initializeDefaultUsers();
    }
    
    // Initialize some default users for testing
    private void initializeDefaultUsers() {
        addUser(new User("DEV001", "Alice Johnson", "alice@company.com", UserRole.DEVELOPER));
        addUser(new User("TEST001", "Bob Smith", "bob@company.com", UserRole.TESTER));
        addUser(new User("PM001", "Carol Davis", "carol@company.com", UserRole.PROJECT_MANAGER));
        addUser(new User("ADMIN001", "David Wilson", "david@company.com", UserRole.ADMIN));
    }
    
    // User management methods
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    // Bug creation and management methods
    public String createBug(String title, String description, Priority priority, String reporterId) 
            throws BugTrackingException {
        User reporter = users.get(reporterId);
        if (reporter == null) {
            throw new BugTrackingException("Reporter not found: " + reporterId);
        }
        
        String bugId = String.format("BUG-%04d", nextBugId++);
        Bug bug = new Bug(bugId, title, description, priority, reporter);
        bugs.put(bugId, bug);
        
        System.out.println("Bug created successfully: " + bugId);
        return bugId;
    }
    
    public Bug getBug(String bugId) {
        return bugs.get(bugId);
    }
    
    public List<Bug> getAllBugs() {
        return new ArrayList<>(bugs.values());
    }
    
    public void assignBug(String bugId, String assigneeId) throws BugTrackingException {
        Bug bug = bugs.get(bugId);
        if (bug == null) {
            throw new BugTrackingException("Bug not found: " + bugId);
        }
        
        User assignee = users.get(assigneeId);
        if (assignee == null) {
            throw new BugTrackingException("Assignee not found: " + assigneeId);
        }
        
        bug.setAssignee(assignee);
        System.out.println("Bug " + bugId + " assigned to " + assignee.getName());
    }
    
    public void updateBugStatus(String bugId, Status newStatus) throws BugTrackingException {
        Bug bug = bugs.get(bugId);
        if (bug == null) {
            throw new BugTrackingException("Bug not found: " + bugId);
        }
        
        Status oldStatus = bug.getStatus();
        bug.setStatus(newStatus);
        System.out.println("Bug " + bugId + " status changed from " + oldStatus + " to " + newStatus);
    }
    
    public void updateBugPriority(String bugId, Priority newPriority) throws BugTrackingException {
        Bug bug = bugs.get(bugId);
        if (bug == null) {
            throw new BugTrackingException("Bug not found: " + bugId);
        }
        
        Priority oldPriority = bug.getPriority();
        bug.setPriority(newPriority);
        System.out.println("Bug " + bugId + " priority changed from " + oldPriority + " to " + newPriority);
    }
    
    public void addComment(String bugId, String content, String authorId) throws BugTrackingException {
        Bug bug = bugs.get(bugId);
        if (bug == null) {
            throw new BugTrackingException("Bug not found: " + bugId);
        }
        
        User author = users.get(authorId);
        if (author == null) {
            throw new BugTrackingException("Author not found: " + authorId);
        }
        
        String commentId = String.format("COMMENT-%04d", nextCommentId++);
        Comment comment = new Comment(commentId, content, author);
        bug.addComment(comment);
        
        System.out.println("Comment added to bug " + bugId);
    }
    
    public void addTagToBug(String bugId, String tag) throws BugTrackingException {
        Bug bug = bugs.get(bugId);
        if (bug == null) {
            throw new BugTrackingException("Bug not found: " + bugId);
        }
        
        bug.addTag(tag);
        System.out.println("Tag '" + tag + "' added to bug " + bugId);
    }
    
    // Search and filter methods
    public List<Bug> searchBugsByStatus(Status status) {
        return bugs.values().stream()
                .filter(bug -> bug.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public List<Bug> searchBugsByPriority(Priority priority) {
        return bugs.values().stream()
                .filter(bug -> bug.getPriority() == priority)
                .collect(Collectors.toList());
    }
    
    public List<Bug> searchBugsByAssignee(String assigneeId) {
        return bugs.values().stream()
                .filter(bug -> bug.getAssignee() != null && bug.getAssignee().getUserId().equals(assigneeId))
                .collect(Collectors.toList());
    }
    
    public List<Bug> searchBugsByReporter(String reporterId) {
        return bugs.values().stream()
                .filter(bug -> bug.getReporter().getUserId().equals(reporterId))
                .collect(Collectors.toList());
    }
    
    public List<Bug> searchBugsByTag(String tag) {
        String lowerTag = tag.toLowerCase();
        return bugs.values().stream()
                .filter(bug -> bug.getTags().contains(lowerTag))
                .collect(Collectors.toList());
    }
    
    public List<Bug> searchBugsByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return bugs.values().stream()
                .filter(bug -> 
                    bug.getTitle().toLowerCase().contains(lowerKeyword) ||
                    bug.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    // Reporting methods
    public void printBugSummary() {
        System.out.println("\n=== BUG TRACKING SYSTEM SUMMARY ===");
        System.out.println("Total Bugs: " + bugs.size());
        
        Map<Status, Long> statusCounts = bugs.values().stream()
                .collect(Collectors.groupingBy(Bug::getStatus, Collectors.counting()));
        
        System.out.println("\nBugs by Status:");
        for (Status status : Status.values()) {
            long count = statusCounts.getOrDefault(status, 0L);
            System.out.println("  " + status + ": " + count);
        }
        
        Map<Priority, Long> priorityCounts = bugs.values().stream()
                .collect(Collectors.groupingBy(Bug::getPriority, Collectors.counting()));
        
        System.out.println("\nBugs by Priority:");
        for (Priority priority : Priority.values()) {
            long count = priorityCounts.getOrDefault(priority, 0L);
            System.out.println("  " + priority + ": " + count);
        }
    }
    
    public void printBugDetails(String bugId) {
        Bug bug = bugs.get(bugId);
        if (bug != null) {
            System.out.println("\n=== BUG DETAILS ===");
            System.out.println(bug.toString());
        } else {
            System.out.println("Bug not found: " + bugId);
        }
    }
}