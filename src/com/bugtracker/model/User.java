package com.bugtracker.model;

import com.bugtracker.enums.UserRole;
import java.util.Objects;

public class User {
    private final String userId;
    private final String name;
    private final String email;
    private final UserRole role;
    
    public User(String userId, String name, String email, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, email, role);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(userId, user.userId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}