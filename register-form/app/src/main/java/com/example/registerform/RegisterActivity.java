package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Включаем поддержку JavaScript
        webSettings.setDomStorageEnabled(true); // Включаем поддержку DOM-хранилища

        webView.setWebViewClient(new WebViewClient()); // Открытие ссылок в WebView
        webView.loadUrl("file:///android_asset/register_form.html");

        // Добавляем интерфейс для взаимодействия с JavaScript
        webView.addJavascriptInterface(new WebAppInterface(webView), "Android");
    }

    public class WebAppInterface {
        private final WebView webView;

        public WebAppInterface(WebView webView) {
            this.webView = webView;
        }

        @JavascriptInterface
        public void registerUser(String username, String email, String password) {
            UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
            Call<UserResponse> call = userApi.registerUser(username, email, password);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    Log.d("RegisterActivity", "Response received: " + response.toString());
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse userResponse = response.body();

                        if (userResponse.isSuccess()) {
                            Log.d("RegisterActivity", "User registered successfully. Redirecting to LoginActivity...");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String message = userResponse.getMessage() != null
                                    ? userResponse.getMessage()
                                    : "Unknown error occurred";
                            Log.e("RegisterActivity", "Registration failed: " + message);
                            webView.post(() -> webView.loadUrl("javascript:alert('Error: " + message + "')"));
                        }
                    } else {
                        Log.e("RegisterActivity", "Response error. Code: " + response.code());
                        webView.post(() -> webView.loadUrl("javascript:alert('Response Error')"));
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("RegisterActivity", "Failed to connect to server: " + t.getMessage());
                    webView.post(() -> webView.loadUrl("javascript:alert('Failed to connect to server')"));
                }
            });


        }

        @JavascriptInterface
        public void redirectToLogin() {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Закрыть текущую RegisterActivity
        }
    }
}
