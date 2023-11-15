package com.example.indoorairqualitymonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    final static String baseURL = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/auth?client_id=openremote&redirect_uri=https%3A%2F%2Fuiot.ixxc.dev%2Fmanager%2F&state=e2d1bdbc-ef9b-4cb4-9789-69e4af40871e&response_mode=fragment&response_type=code&scope=openid&nonce=1a892f30-ea5d-40bf-a01d-c4defc3b5fa2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        CookieManager.getInstance().removeAllCookies(null);

        Intent intent = getIntent();
        Bundle extras =intent.getExtras();

        String username = extras.getString("USERNAME");
        String email = extras.getString("EMAIL");
        String password = extras.getString("PASSWORD");
        String passwordConfirm = extras.getString("CONFIRM_PASSWORD");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("auth")) {
                    view.evaluateJavascript("document.getElementsByTagName('a')[0].click();", null);
                    if (url.contains("login-actions/registration")) {
                        Log.d("webView", "onPageFinished: Fill form");
                        String usrScrript = "document.getElementById('username').value='" + username + "';";
                        String emailScrript = "document.getElementById('email').value='" + email + "';";
                        String pwdScrript = "document.getElementById('password').value='" + password + "';";
                        String rePwdScrript = "document.getElementById('password-confirm').value='" + passwordConfirm + "';";

                        view.evaluateJavascript(usrScrript, null);
                        view.evaluateJavascript(emailScrript, null);
                        view.evaluateJavascript(pwdScrript, null);
                        view.evaluateJavascript(rePwdScrript, null);
                        view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                        Log.d("webView", "onPageFinished: Fill form");

                    }
                    if (url.contains("manager")) {
                        Intent intent = new Intent(WebViewActivity.this, Loginactivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (url.contains("execution"))
                        Toast.makeText(WebViewActivity.this, "error!", Toast.LENGTH_SHORT).show();
                }
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(baseURL);
    }
}