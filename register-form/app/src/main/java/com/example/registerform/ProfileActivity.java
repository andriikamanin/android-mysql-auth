package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ImageView avatarImageView;
    private TextView nameTextView, emailTextView;
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImageView = findViewById(R.id.avatarImageView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        editProfileButton = findViewById(R.id.editProfileButton);

        // Загрузка данных пользователя
        loadUserData();

        // Обработка нажатия кнопки "Изменить профиль"
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("name", nameTextView.getText().toString());
            intent.putExtra("email", emailTextView.getText().toString());
            startActivity(intent);
        });
    }

    private void loadUserData() {
        String userEmail = getIntent().getStringExtra("email"); // Получаем email из Intent

        UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
        Call<UserResponse> call = userApi.getUser(userEmail);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    // Получаем данные пользователя из ответа
                    UserResponse.User user = response.body().getUser();
                    if (user != null) {
                        nameTextView.setText(user.getName());
                        emailTextView.setText(user.getEmail());

                        // Загрузка аватарки (используем заглушку, если в базе нет URL аватарки)
                        String avatarUrl = "https://via.placeholder.com/150";
                        Picasso.get().load(avatarUrl).into(avatarImageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Обработайте ошибку, если требуется
            }
        });
    }
}
