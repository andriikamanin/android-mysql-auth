package com.example.mysqlandroidapp.models;



public class UserResponse {
    private boolean success;
    private String message;
    private String username;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
