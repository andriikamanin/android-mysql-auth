package com.example.registerform;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;




public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);


    }


}