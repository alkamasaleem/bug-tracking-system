package com.bugtracker.model;

import com.bugtracker.enums.Priority;
import com.bugtracker.enums.Status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Bug {
    private final String bugId;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private User assignee;
    private final User reporter;
    private final LocalDateTime createdDate;
    private LocalDateTime lastModified;
    private final List<Comment> comments;
    private final Set<String> tags;
    
    public Bug(String bugId, String title, String description, Priority priority, User reporter) {
        this.bugId = bugId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = Status.OPEN;
        this.reporter = reporter;
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.tags = new HashSet<>();
    }
    
    // Getters
    public String getBugId() { return bugId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public User getAssignee() { return assignee; }
    public User getReporter() { return reporter; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastModified() { return lastModified; }
    public List<Comment> getComments() { return new ArrayList<>(comments); }
    public Set<String> getTags() { return new HashSet<>(tags); }
    
    // Setters with validation
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
        this.lastModified = LocalDateTime.now();
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.lastModified = LocalDateTime.now();
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
        this.lastModified = LocalDateTime.now();
    }
    
    public void setStatus(Status status) {
        this.status = status;
        this.lastModified = LocalDateTime.now();
    }
    
    public void setAssignee(User assignee) {
        this.assignee = assignee;
        this.lastModified = LocalDateTime.now();
    }
    
    public void addComment(Comment comment) {
        comments.add(comment);
        this.lastModified = LocalDateTime.now();
    }
    
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            tags.add(tag.toLowerCase().trim());
            this.lastModified = LocalDateTime.now();
        }
    }
    
    public void removeTag(String tag) {
        if (tag != null) {
            tags.remove(tag.toLowerCase().trim());
            this.lastModified = LocalDateTime.now();
        }
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Bug ID: %s\n", bugId));
        sb.append(String.format("Title: %s\n", title));
        sb.append(String.format("Description: %s\n", description));
        sb.append(String.format("Priority: %s\n", priority));
        sb.append(String.format("Status: %s\n", status));
        sb.append(String.format("Reporter: %s\n", reporter.getName()));
        sb.append(String.format("Assignee: %s\n", assignee != null ? assignee.getName() : "Unassigned"));
        sb.append(String.format("Created: %s\n", createdDate.format(formatter)));
        sb.append(String.format("Last Modified: %s\n", lastModified.format(formatter)));
        if (!tags.isEmpty()) {
            sb.append(String.format("Tags: %s\n", String.join(", ", tags)));
        }
        if (!comments.isEmpty()) {
            sb.append(String.format("Comments (%d):\n", comments.size()));
            for (Comment comment : comments) {
                sb.append("  ").append(comment.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}