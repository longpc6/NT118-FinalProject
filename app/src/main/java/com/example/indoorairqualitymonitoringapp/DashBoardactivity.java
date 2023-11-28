package com.example.indoorairqualitymonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DashBoardactivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }

//    private void signOut() {
//        Intent intent = new Intent(dashboard.this, Loginactivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//        finish();
//    }
}