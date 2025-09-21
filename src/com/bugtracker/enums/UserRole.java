package com.bugtracker.enums;

public enum UserRole {
    DEVELOPER("Developer"),
    TESTER("Tester"),
    PROJECT_MANAGER("Project Manager"),
    ADMIN("Admin");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}