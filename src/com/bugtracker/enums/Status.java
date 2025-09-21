package com.bugtracker.enums;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    REJECTED("Rejected");
    
    private final String displayName;
    
    Status(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}