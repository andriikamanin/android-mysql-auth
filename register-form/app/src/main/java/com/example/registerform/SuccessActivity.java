package com.example.registerform;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // Получаем данные, переданные через Intent
        String username = getIntent().getStringExtra("username");

        // Устанавливаем приветствие
        TextView successMessage = findViewById(R.id.successMessage );
        successMessage.setText("Welcome, " + username + "! Registration Successful.");
    }
}

