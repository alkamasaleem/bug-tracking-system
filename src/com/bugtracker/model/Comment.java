package com.bugtracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private final String commentId;
    private final String content;
    private final User author;
    private final LocalDateTime timestamp;
    
    public Comment(String commentId, String content, User author) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getCommentId() { return commentId; }
    public String getContent() { return content; }
    public User getAuthor() { return author; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("[%s] %s: %s", 
                timestamp.format(formatter), author.getName(), content);
    }
}