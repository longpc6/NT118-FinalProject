package com.example.indoorairqualitymonitoringapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
    private EditText editPassConfirm;
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
        editPassConfirm = findViewById(R.id.editConfirmpass);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFunc();
                buttonSignUp.setBackgroundColor(Color.parseColor("#841FAF"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonSignUp.setBackgroundColor(Color.parseColor("#835E35B1"));
                    }
                }, 500);
            }
        });
    }

    private void SignUpFunc() {
        Intent intent =new Intent(Signupactivity.this, WebViewActivity.class);
        Bundle extras = new Bundle();

        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editPassConfirm.getText().toString();

        extras.putString("USERNAME", username);
        extras.putString("EMAIL", email);
        extras.putString("PASSWORD", password);
        extras.putString("CONFIRM_PASSWORD", confirmPassword);

        intent.putExtras(extras);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

