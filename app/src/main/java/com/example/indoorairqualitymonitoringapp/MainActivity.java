package com.example.indoorairqualitymonitoringapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private Button signUpButton;
    private Button btnchangelang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Loginactivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signupactivity.class);
                startActivity(intent);
            }
        });

        btnchangelang=findViewById(R.id.ChangeLang);
        btnchangelang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showChangeLanguageDialog();
            }
        });
    }

    private void showChangeLanguageDialog(){

        final String[] listname = {"VietNam", "English"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
        mbuilder.setSingleChoiceItems(listname, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("vi");
                    recreate();

                } else if (i==1) {
                    setLocale("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mbuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(language);
    }
}

