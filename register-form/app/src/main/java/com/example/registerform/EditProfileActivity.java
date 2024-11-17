package com.example.registerform;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        saveButton = findViewById(R.id.saveButton);

        // Загрузка текущих данных из Intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        nameEditText.setText(name);
        emailEditText.setText(email);

        saveButton.setOnClickListener(v -> {
            // Реализуйте логику сохранения данных
        });
    }
}
