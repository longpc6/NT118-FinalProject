package com.example.indoorairqualitymonitoringapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private ImageButton passwordToggleButton, confirmToggleButton;

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
        passwordToggleButton = findViewById(R.id.img_btn_showPasswordSignUp);
        confirmToggleButton = findViewById(R.id.img_btn_showConfirmSignUp);

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

        passwordToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextPassword.getText().toString().isEmpty()) {
                    if (editTextPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordToggleButton.setImageResource(R.drawable.baseline_visibility_24);
                    } else {
                        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordToggleButton.setImageResource(R.drawable.baseline_visibility_off_24);
                    }
                }
            }
        });

        confirmToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editPassConfirm.getText().toString().isEmpty()) {
                    if (editPassConfirm.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                        editPassConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordToggleButton.setImageResource(R.drawable.baseline_visibility_24);
                    } else {
                        editPassConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordToggleButton.setImageResource(R.drawable.baseline_visibility_off_24);
                    }
                }
            }
        });
    }

    private void SignUpFunc() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editPassConfirm.getText().toString();

        int signal = ValidationUtil(username, email, password, confirmPassword);
        switch (signal) {
            case 1:
                Toast.makeText(Signupactivity.this, "Please fill in the blank", Toast.LENGTH_SHORT).show();
                return;
            case 2:
                Toast.makeText(Signupactivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                return;
            case 3:
                Toast.makeText(Signupactivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                return;
            case 4:
                Toast.makeText(Signupactivity.this, "the confirmed-password doesn't match", Toast.LENGTH_SHORT).show();
                return;
            default:
                break;
        }

        Intent intent =new Intent(Signupactivity.this, WebViewActivity.class);
        Bundle extras = new Bundle();

        extras.putString("USERNAME", username);
        extras.putString("EMAIL", email);
        extras.putString("PASSWORD", password);
        extras.putString("CONFIRM_PASSWORD", confirmPassword);

        intent.putExtras(extras);
        startActivity(intent);
    }

    private int ValidationUtil(String username, String email, String password, String confirmPass) {
        int index = email.indexOf("@");
        String emailDomain = email.substring(index + 1);
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty())
            return 1;
        else if (username.contains(" "))
            return 2;
        else if (!email.contains("@") || email.contains(" "))
            return 3;
        else if (!emailDomain.contains("."))
            return 3;
        else if (!password.equals(confirmPass))
            return 4;
        return 0;
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

