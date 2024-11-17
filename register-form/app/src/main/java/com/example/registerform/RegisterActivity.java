package com.example.registerform;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/register_form.html");

        webView.addJavascriptInterface(new WebAppInterface(webView), "Android");
    }
    @JavascriptInterface
    public void redirectToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Закрыть текущую RegisterActivity
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
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse userResponse = response.body();

                        if (userResponse.isSuccess()) {
                            // Извлекаем объект User
                            UserResponse.User user = userResponse.getUser();
                            Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                            intent.putExtra("email", userResponse.getUser().getEmail()); // Передача email
                            startActivity(intent);
                            finish();

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
    }
}
