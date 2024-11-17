package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.example.registerform.models.UserResponse;
import com.example.registerform.network.ApiService;
import com.example.registerform.network.UserApi;
import androidx.appcompat.app.AppCompatActivity;

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

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/login_form.html");

        // Добавляем интерфейс для взаимодействия с JavaScript
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
                        if (userResponse.isSuccess()) {
                            webView.post(() -> webView.loadUrl("javascript:alert('Login successful!')"));
                        } else {
                            webView.post(() -> webView.loadUrl("javascript:alert('Error: " + userResponse.getMessage() + "')"));
                        }
                    } else {
                        webView.post(() -> webView.loadUrl("javascript:alert('Response Error')"));
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    webView.post(() -> webView.loadUrl("javascript:alert('Failed to connect to server')"));
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