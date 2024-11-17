package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private ImageView avatarImageView;
    private EditText nameEditText, emailEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImageView = findViewById(R.id.avatarImageView);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        updateButton = findViewById(R.id.updateButton);

        // Загружаем данные пользователя
        loadUserData();

        // Обрабатываем нажатие кнопки "Изменить"
        updateButton.setOnClickListener(v -> updateUserData());
    }

    private void loadUserData() {
        String userEmail = getIntent().getStringExtra("email");

        if (userEmail == null || userEmail.isEmpty()) {
            Toast.makeText(this, "No email provided!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No email provided to load user data.");
            return;
        }

        UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
        Call<UserResponse> call = userApi.getUser(userEmail);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess() && userResponse.getUser() != null) {
                        // Получаем данные пользователя из ответа
                        UserResponse.User user = userResponse.getUser();

                        // Устанавливаем данные пользователя
                        nameEditText.setText(user.getUsername() != null ? user.getUsername() : "N/A");
                        emailEditText.setText(user.getEmail() != null ? user.getEmail() : "N/A");

                        // Загружаем аватар
                        String avatarUrl = user.getUserAvatar();
                        if (avatarUrl != null && !avatarUrl.isEmpty()) {
                            Picasso.get()
                                    .load(avatarUrl)
                                    .placeholder(R.drawable.ic_avatar_placeholder)
                                    .error(R.drawable.ic_avatar_placeholder)
                                    .into(avatarImageView);
                        } else {
                            avatarImageView.setImageResource(R.drawable.ic_avatar_placeholder);
                            Log.w(TAG, "User avatar is empty or null.");
                        }
                    } else {
                        Log.e(TAG, "Error loading user data: " + userResponse.getMessage());
                        Toast.makeText(ProfileActivity.this, "Failed to load user data: " + userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Server error while loading user data. Code: " + response.code());
                    Toast.makeText(ProfileActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Error loading user data: " + t.getMessage(), t);
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        String updatedUsername = nameEditText.getText().toString().trim();
        String updatedEmail = emailEditText.getText().toString().trim();

        if (updatedUsername.isEmpty() || updatedEmail.isEmpty()) {
            Toast.makeText(this, "Username and Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
        Call<UserResponse> call = userApi.updateUser(updatedUsername, updatedEmail);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();
                    if (userResponse.isSuccess()) {
                        Toast.makeText(ProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Profile updated successfully.");
                    } else {
                        Log.e(TAG, "Error updating user data: " + userResponse.getMessage());
                        Toast.makeText(ProfileActivity.this, "Failed to update profile: " + userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Server error while updating profile. Code: " + response.code());
                    Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Error updating user data: " + t.getMessage(), t);
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
