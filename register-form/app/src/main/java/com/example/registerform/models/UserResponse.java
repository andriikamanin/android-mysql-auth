package com.example.registerform.models;

import com.google.gson.annotations.SerializedName;
public class UserResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public static class User {
        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;

        @SerializedName("user_avatar")
        private String userAvatar;

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getUserAvatar() {
            return userAvatar;
        }
    }
}
