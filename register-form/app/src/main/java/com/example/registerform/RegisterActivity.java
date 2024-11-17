package com.example.registerform;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        WebView webView = findViewById(R.id.webView);

        // Включение JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Загрузка HTML-формы
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/register_form.html"); // Поместите ваш HTML-файл в папку assets
    }
}

