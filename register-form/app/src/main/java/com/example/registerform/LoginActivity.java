package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/login_form.html");

        webView.addJavascriptInterface(new WebAppInterface(webView), "Android");
    }

    public class WebAppInterface {
        private final WebView webView;

        public WebAppInterface(WebView webView) {
            this.webView = webView;
        }

        @JavascriptInterface
        public void loginUser(String email, String password) {
            UserApi userApi = ApiService.getRetrofitInstance().create(UserApi.class);
            Call<UserResponse> call = userApi.loginUser(email, password);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse userResponse = response.body();

                        if (userResponse.isSuccess()) { // Проверяем, что успех == true
                            // Получаем данные пользователя
                            if (userResponse.getUser() != null) {
                                String userEmail = userResponse.getUser().getEmail();
                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                intent.putExtra("email", userEmail);
                                startActivity(intent);
                                finish();
                            } else {
                                webView.post(() -> webView.loadUrl("javascript:alert('Error: User data not found')"));
                            }
                        } else {
                            // Если сервер вернул сообщение об ошибке
                            webView.post(() -> webView.loadUrl("javascript:alert('Error: " + userResponse.getMessage() + "')"));
                        }
                    } else {
                        // Если ответ неуспешный
                        webView.post(() -> webView.loadUrl("javascript:alert('Server error. Please try again.')"));
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    // Ошибка при подключении к серверу
                    webView.post(() -> webView.loadUrl("javascript:alert('Failed to connect to server: " + t.getMessage() + "')"));
                }
            });



        }

        @JavascriptInterface
        public void redirectToRegister() {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
