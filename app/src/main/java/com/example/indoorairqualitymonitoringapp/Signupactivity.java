package com.example.indoorairqualitymonitoringapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Signupactivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editConfirmpass;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editConfirmpass = findViewById(R.id.editConfirmpass);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(view -> {
            // Lấy thông tin từ các trường EditText
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editConfirmpass.getText().toString();
//
//            // Thực hiện các bước liên quan đến WebView và JavaScript ở đây
//            // Điền thông tin vào trang web và gửi biểu mẫu
//            String jsCode = "document.getElementById('username').value = '" + username + "';" +
//                    "document.getElementById('email').value = '" + email + "';" +
//                    "document.getElementById('password').value = '" + password + "';" +
//                    "document.getElementById('confirmPassword').value = '" + confirmPassword + "';" +
//                    "document.getElementById('submitButton').click();";
//            webView.evaluateJavascript(jsCode, null);
//        });
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new MyWebViewClient());
//        webView.loadUrl("https://uiot.ixxc.dev/auth/realms/master/login-actions/registration?execution=1d078099-4b5b-4d04-abe6-94624ba99657&client_id=account&tab_id=Xp-mnRxMkZM");
//    }
//
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            // Hiển thị ProgressBar khi trang web đang được tải
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if (url != null && url.contains("/auth/realms/master/login-actions/registration?client_id=account&amp;tab_id=-v1bjFOPB8M")) {
//                // Lấy thông tin từ các trường EditText
//                String username = editTextUsername.getText().toString();
//                String email = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//                String confirmPassword = editConfirmpass.getText().toString();
//
//                // Tạo các mã JavaScript để điền thông tin vào trang web
//                String userScript = "document.getElementById('username').value = '" + username + "';";
//                String emailScript = "document.getElementById('email').value = '" + email + "';";
//                String pwdScript = "document.getElementById('password').value = '" + password + "';";
//                String repwdScript = "document.getElementById('confirmPassword').value = '" + confirmPassword + "';";
//
//                // Sử dụng evaluateJavascript để thực thi các mã JavaScript
//                view.evaluateJavascript(userScript, null);
//                view.evaluateJavascript(emailScript, null);
//                view.evaluateJavascript(pwdScript, null);
//                view.evaluateJavascript(repwdScript, null);
//
//                // Simulate a click on the submit button
//                view.evaluateJavascript("document.getElementById('submitButton').submit();", value -> {
//                    // Xử lý kết quả nếu cần
//                    if (value != null && value.contains("registration-successful")) {
//                        // Đăng ký thành công, chuyển sang SignInActivity
//                        Intent intent = new Intent(Signupactivity.this, Loginactivity.class);
//                        startActivity(intent);
//                        finish(); // Đóng Activity hiện tại
//                    } else {
//                        // Đăng ký không thành công, hiển thị thông báo lỗi
//                        Toast.makeText(Signupactivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
//                    }
                });
            }

//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url.contains("token=")) {
//                // Tách token từ URL và lưu lại
//                String token = url.split("token=")[1];
//                // Xử lý token ở đây (ví dụ: hiển thị token hoặc lưu lại)
//                Toast.makeText(Signupactivity.this, "Token: " + token, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Signupactivity.this, Signupactivity.class);
//                startActivity(intent);
//                finish(); // Đóng Activity hiện tại
//                return true; // Ngăn chuyển hướng tiếp theo
//            } else if (url.contains("registration-failed")) {
//                // Xử lý khi đăng ký không thành công
//                Toast.makeText(Signupactivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
//                return true; // Ngăn chuyển hướng tiếp theo
//            }
//            return false;
//       }
//   });
//}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

