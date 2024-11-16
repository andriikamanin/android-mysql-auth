package com.example.mysqlandroidapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysqlandroidapp.models.UserResponse;
import com.example.mysqlandroidapp.network.ApiService;
import com.example.mysqlandroidapp.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Инициализация компонентов
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        // Логика регистрации
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Проверка на пустоту полей
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Создание экземпляра API для регистрации
            UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
            Call<UserResponse> call = userApi.registerUser(username, email, password);

            // Выполнение асинхронного вызова
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse userResponse = response.body();
                        Toast.makeText(RegisterActivity.this, userResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // Если регистрация успешна, можно перейти к LoginActivity
                        if (userResponse.getStatus().equalsIgnoreCase("success")) {
                            finish(); // Закрываем RegisterActivity
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
