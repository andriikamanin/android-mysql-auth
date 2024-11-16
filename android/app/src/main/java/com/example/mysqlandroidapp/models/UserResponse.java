package com.example.mysqlandroidapp.models;




public class UserResponse {

    private String status;
    private String message;

    // Геттер для статуса
    public String getStatus() {
        return status;
    }

    // Сеттер для статуса (необязательно, если используете только для чтения)
    public void setStatus(String status) {
        this.status = status;
    }

    // Геттер для сообщения
    public String getMessage() {
        return message;
    }

    // Сеттер для сообщения (необязательно, если используете только для чтения)
    public void setMessage(String message) {
        this.message = message;
    }
}
