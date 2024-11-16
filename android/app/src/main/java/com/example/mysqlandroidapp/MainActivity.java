package com.example.mysqlandroidapp;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysqlandroidapp.models.UserResponse;
import com.example.mysqlandroidapp.network.ApiService;
import com.example.mysqlandroidapp.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Получаем данные из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Unknown User");
        String email = sharedPreferences.getString("email", "No Email");

        // Устанавливаем значения
        usernameTextView.setText("Username: " + username);
        emailTextView.setText("Email: " + email);

        // Обработчик кнопки Logout
        logoutButton.setOnClickListener(view -> {
            // Очищаем SharedPreferences при выходе
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Возвращаемся на экран логина
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            finish();
        });
    }


}
