package com.example.registerform;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        TextView successMessage = findViewById(R.id.successMessage);

        // Получение имени пользователя из Intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            successMessage.setText("Welcome, " + username + "!");
        }
    }
}

