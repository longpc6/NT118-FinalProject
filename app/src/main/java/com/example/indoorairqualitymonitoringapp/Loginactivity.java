package com.example.indoorairqualitymonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.JsonObject;
import com.example.indoorairqualitymonitoringapp.ApiService;
import android.content.Intent;

public class Loginactivity extends AppCompatActivity {
    private EditText editTextuserName;
    private EditText editTextpassword;
    private Button signinButton;
    private CheckBox rememberMeCheckbox;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    private Button forgotPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextuserName = findViewById(R.id.editTextUsername);
        editTextpassword = findViewById(R.id.editTextPassword);
        signinButton = findViewById(R.id.buttonLogin);
        rememberMeCheckbox = findViewById(R.id.checkboxRememberMe);
        forgotPasswordButton = findViewById(R.id.buttonForgotPassword);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        editTextuserName.setText(savedUsername);
        editTextpassword.setText(savedPassword);
        rememberMeCheckbox.setChecked(!savedUsername.isEmpty() && !savedPassword.isEmpty());

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextuserName.getText().toString();
                String password = editTextpassword.getText().toString();
                if(isValid(username,password)){
                    loginWithToken(username, password);
                }else{
                    Toast.makeText(Loginactivity.this,"Nhap mat khau hop le",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //loginWithToken();


    }

    private boolean isValid(String username, String password) {

        return !username.isEmpty() && !password.isEmpty();
    }
    private void loginWithToken(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uiot.ixxc.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<JsonObject> call = apiService.getToken("password", "openremote", null, username, password);


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject tokenResponse = response.body();
                    String accessToken = tokenResponse.get("access_token").getAsString();
                    Intent intent = new Intent(Loginactivity.this, dashboard.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Loginactivity.this, "dang nhap khong thanh cong!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Loginactivity.this, "Error:Not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveLoginDetails(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void clearLoginDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}